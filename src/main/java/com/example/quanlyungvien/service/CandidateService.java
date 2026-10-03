package com.example.quanlyungvien.service;

import com.example.quanlyungvien.entity.*;
import com.example.quanlyungvien.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}/\\d{2}/\\d{2}$");

    @Transactional
    public List<Map<String, Object>> processAndSaveFile(InputStream inputStream) {
        // Danh sách chứa các dòng lỗi theo cấu trúc ma trận của đề bài
        List<Map<String, Object>> errorMatrixList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            String line;
            int lineNum = 0;

            while ((line = reader.readLine()) != null) {
                lineNum++;
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(", ");
                if (data.length < 17) continue;

                // Khởi tạo các cờ đánh dấu lỗi cho dòng hiện tại 
                boolean isBirthDateError = false;
                boolean isPhoneError = false;
                boolean isEmailError = false;
                boolean isExpError = false;
                boolean isThuaDuLieu = false;

                int type = 0;
                try {
                    type = Integer.parseInt(data[0]);
                } catch (NumberFormatException e) {
                    isThuaDuLieu = true;
                }

                // 1. Kiểm tra ngày tháng năm sinh (YYYY/MM/DD) [cite: 34]
                String birthDate = data[2];
                if (!DATE_PATTERN.matcher(birthDate).matches()) {
                    isBirthDateError = true;
                }

                // 2. Kiểm tra số điện thoại (Tối thiểu 7 chữ số) [cite: 35]
                String phone = data[5];
                if (phone == null || phone.replaceAll("\\D", "").length() < 7) {
                    isPhoneError = true;
                }

                // 3. Kiểm tra Email [cite: 36]
                String email = data[6];
                int atCount = email.length() - email.replace("@", "").length();
                if (atCount != 1 || !email.contains(".")) {
                    isEmailError = true;
                }

                // 4. Kiểm tra dữ liệu riêng và dữ liệu thừa theo loại ứng viên [cite: 37, 38]
                if (type == 1) { // Experience
                    String expStr = data[7];
                    if ("None".equals(expStr)) {
                        isExpError = true;
                    } else {
                        try {
                            double exp = Double.parseDouble(expStr);
                            if (exp <= 0 || exp >= 100) isExpError = true; // [cite: 37]
                        } catch (NumberFormatException e) {
                            isExpError = true;
                        }
                    }
                    // Kiểm tra thừa dữ liệu của Fresher/Intern [cite: 38, 39]
                    if (!"None".equals(data[10]) || !"None".equals(data[11]) || !"None".equals(data[12]) ||
                            !"None".equals(data[13]) || !"None".equals(data[14]) || !"None".equals(data[15]) || !"None".equals(data[16])) {
                        isThuaDuLieu = true;
                    }

                } else if (type == 2) { // Fresher
                    if (!"None".equals(data[7]) || !"None".equals(data[8]) || !"None".equals(data[9]) ||
                            !"None".equals(data[13]) || !"None".equals(data[14]) || !"None".equals(data[15]) || !"None".equals(data[16])) {
                        isThuaDuLieu = true; // [cite: 38]
                    }
                } else if (type == 3) { // Intern
                    if (!"None".equals(data[7]) || !"None".equals(data[8]) || !"None".equals(data[9]) ||
                            !"None".equals(data[10]) || !"None".equals(data[11]) || !"None".equals(data[12])) {
                        isThuaDuLieu = true; // [cite: 38]
                    }
                }

                // XỬ LÝ PHÂN LOẠI ĐẦU RA [cite: 32]
                if (isBirthDateError || isPhoneError || isEmailError || isExpError || isThuaDuLieu) {
                    // Nếu có bất kỳ lỗi nào -> Gom vào Ma trận lỗi để hiển thị lên giao diện 
                    Map<String, Object> errorRow = new HashMap<>();
                    errorRow.put("lineNum", lineNum);
                    errorRow.put("birthDateErr", isBirthDateError ? "Yes" : "");
                    errorRow.put("phoneErr", isPhoneError ? "Yes" : "");
                    errorRow.put("emailErr", isEmailError ? "Yes" : "");
                    errorRow.put("expErr", isExpError ? "Yes" : "");
                    errorRow.put("thuaDuLieuErr", isThuaDuLieu ? "Yes" : "");
                    errorMatrixList.add(errorRow);
                } else {
                    // NẾU HỢP LỆ -> TIẾN HÀNH LƯU VÀO DATABASE QUA HIBERNATE (Hoàn thiện Yêu cầu 3) [cite: 31, 40]
                    if (type == 1) {
                        ExperienceCandidate expCand = new ExperienceCandidate();
                        setCommonProperties(expCand, data);
                        expCand.setExpInYear(Double.parseDouble(data[7]));
                        expCand.setProSkill(data[8]);
                        expCand.setLastWorkingPlace(data[9]);
                        candidateRepository.save(expCand); // Thực hiện lưu [cite: 40]
                    } else if (type == 2) {
                        FresherCandidate fresher = new FresherCandidate();
                        setCommonProperties(fresher, data);
                        fresher.setGraduationDate(data[10]);
                        fresher.setGraduationRank(data[11]);
                        fresher.setEducation(data[12]);
                        candidateRepository.save(fresher); // Thực hiện lưu [cite: 40]
                    } else if (type == 3) {
                        InternCandidate intern = new InternCandidate();
                        setCommonProperties(intern, data);
                        intern.setMajor(data[13]);
                        intern.setSemester(Integer.parseInt(data[14]));
                        intern.setUniversityName(data[15]);
                        intern.setExpectedGraduationDate(data[16]);
                        candidateRepository.save(intern); // Thực hiện lưu [cite: 40]
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorMatrixList;
    }

    private void setCommonProperties(Candidate candidate, String[] data) {
        candidate.setCandidateType(Integer.parseInt(data[0]));
        candidate.setFullName(data[1]);
        candidate.setBirthDate(data[2]);
        candidate.setAddress(data[3]);
        candidate.setHometown(data[4]);
        candidate.setPhone(data[5]);
        candidate.setEmail(data[6]);
    }
}