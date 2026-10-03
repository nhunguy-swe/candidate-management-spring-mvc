package com.example.quanlyungvien.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "experience_candidates")
@PrimaryKeyJoinColumn(name = "candidate_id")
public class ExperienceCandidate extends Candidate {

    @Column(name = "exp_in_year")
    private double expInYear;

    @Column(name = "pro_skill")
    private String proSkill;

    @Column(name = "last_working_place")
    private String lastWorkingPlace;

    public double getExpInYear() { return expInYear; }
    public void setExpInYear(double expInYear) { this.expInYear = expInYear; }
    public String getProSkill() { return proSkill; }
    public void setProSkill(String proSkill) { this.proSkill = proSkill; }
    public String getLastWorkingPlace() { return lastWorkingPlace; }
    public void setLastWorkingPlace(String lastWorkingPlace) { this.lastWorkingPlace = lastWorkingPlace; }
}
