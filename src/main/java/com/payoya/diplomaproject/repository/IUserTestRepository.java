package com.payoya.diplomaproject.repository;

import com.payoya.diplomaproject.entity.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserTestRepository extends JpaRepository<UserTest, Long> {
}
