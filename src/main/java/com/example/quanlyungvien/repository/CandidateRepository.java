package com.example.quanlyungvien.repository;

import com.example.quanlyungvien.entity.Candidate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CandidateRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Candidate candidate) {
        Session session = sessionFactory.getCurrentSession();
        session.save(candidate);
    }
}