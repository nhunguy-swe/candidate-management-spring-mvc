package com.example.quanlyungvien.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "intern_candidates")
@PrimaryKeyJoinColumn(name = "candidate_id")
public class InternCandidate extends Candidate {

    @Column(name = "major")
    private String major;

    @Column(name = "semester")
    private int semester;

    @Column(name = "university_name")
    private String universityName;

    @Column(name = "expected_graduation_date")
    private String expectedGraduationDate;

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }
    public String getUniversityName() { return universityName; }
    public void setUniversityName(String universityName) { this.universityName = universityName; }
    public String getExpectedGraduationDate() { return expectedGraduationDate; }
    public void setExpectedGraduationDate(String expectedGraduationDate) { this.expectedGraduationDate = expectedGraduationDate; }
}