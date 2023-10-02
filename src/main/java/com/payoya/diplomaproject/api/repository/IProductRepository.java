package com.payoya.diplomaproject.api.repository;

import com.payoya.diplomaproject.api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByTagsContainsIgnoreCase(String tags);

}
