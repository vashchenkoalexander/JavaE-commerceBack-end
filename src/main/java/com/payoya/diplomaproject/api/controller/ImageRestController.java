package com.payoya.diplomaproject.api.controller;

import com.payoya.diplomaproject.api.entity.Image;
import com.payoya.diplomaproject.api.service.ImageService;
import jakarta.annotation.Nullable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/v1/image")
public class ImageRestController {

    private final ImageService imageService;

    public ImageRestController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam String description,
                                              @RequestParam @Nullable Long productId) {
        return imageService.saveImage(file, description, productId);
    }

    @GetMapping("/all")
    public List<Image> getAllImages() {
        return imageService.getAllImages();
    }

    @GetMapping(value = "/get/{path}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> getImageByPath(@PathVariable String path){
        return imageService.getImageByPath(path);
    }

}
