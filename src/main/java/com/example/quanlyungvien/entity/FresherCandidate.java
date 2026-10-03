package com.example.quanlyungvien.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fresher_candidates")
@PrimaryKeyJoinColumn(name = "candidate_id")
public class FresherCandidate extends Candidate {

    @Column(name = "graduation_date")
    private String graduationDate;  

    @Column(name = "graduation_rank")
    private String graduationRank;  

    @Column(name = "education")
    private String education;  

    public String getGraduationDate() { return graduationDate; }
    public void setGraduationDate(String graduationDate) { this.graduationDate = graduationDate; }
    public String getGraduationRank() { return graduationRank; }
    public void setGraduationRank(String graduationRank) { this.graduationRank = graduationRank; }
    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }
}
