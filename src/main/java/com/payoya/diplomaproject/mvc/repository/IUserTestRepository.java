package com.payoya.diplomaproject.mvc.repository;

import com.payoya.diplomaproject.mvc.entity.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserTestRepository extends JpaRepository<UserTest, Long> {
}
