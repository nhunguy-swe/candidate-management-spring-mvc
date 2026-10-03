package com.example.quanlyungvien.controller;

import com.example.quanlyungvien.model.ValidationError;
import com.example.quanlyungvien.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/")
    public String showUploadPage() {
        return "upload"; // Lần đầu truy cập vào trang upload file
    }

    @PostMapping("/import")
    public String handleImportFile(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("msgError", "Vui lòng đính kèm tệp tin trước khi thực hiện xử lý!");
            return "upload"; // Lỗi file trống thì giữ ở trang upload
        }

        try {
            // Thực hiện đọc file, lưu bản ghi đúng vào DB, gom bản ghi lỗi vào list
            List<Map<String, Object>> errors = candidateService.processAndSaveFile(file.getInputStream());

            // Đẩy danh sách lỗi sang trang kết quả
            model.addAttribute("errors", errors);

            return "result"; // CHUYỂN TIẾP SANG TRANG result.jsp ĐỂ HIỂN THỊ MA TRẬN LỖI

        } catch (IOException e) {
            model.addAttribute("msgError", "Đã xảy ra lỗi hệ thống trong quá trình đọc luồng dữ liệu file.");
            return "upload";
        }
    }
}
