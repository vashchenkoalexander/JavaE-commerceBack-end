package com.payoya.diplomaproject.api.repository;

import com.payoya.diplomaproject.api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserId(Long userId);

    //todo create findOrder from db by some option and took all their data

}
