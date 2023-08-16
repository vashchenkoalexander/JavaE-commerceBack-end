package com.payoya.diplomaproject.api.repository;

import com.payoya.diplomaproject.api.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByUsername(String username);
}
