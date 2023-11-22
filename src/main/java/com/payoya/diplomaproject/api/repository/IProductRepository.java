package com.payoya.diplomaproject.api.repository;

import com.payoya.diplomaproject.api.entity.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByTagsContainsIgnoreCase(String tags);

    List<Product> findAllByTagsContainsIgnoreCaseOrBodyTitleContainingIgnoreCaseOrTitleContainingIgnoreCaseOrderByPrice
            (@NotNull(message = "tags must be present") String tags, @NotNull(message = "bodyTitle must be present") @Size(max = 20000) String bodyTitle, @NotNull(message = "title must be present") String title);

}
