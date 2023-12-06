package com.payoya.diplomaproject.api.repository;

import com.payoya.diplomaproject.api.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImageRepository extends JpaRepository<Image, Long> {

    Image findImageByImagePath(String path);

}
