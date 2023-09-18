package com.payoya.diplomaproject.api.repository;

import com.payoya.diplomaproject.api.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long>  {
    Optional<User> findUserByUsername(String username);

    List<User> findAllByFirstName(String firstName, Pageable pageable);
}
