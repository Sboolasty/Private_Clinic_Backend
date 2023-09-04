package com.example.private_clinic_backend.repository;

import com.example.private_clinic_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByEmail(String email);
    User findUserByIdNumber(String idNumber);
}

