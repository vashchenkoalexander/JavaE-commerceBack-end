package com.payoya.diplomaproject.api.service;

import com.payoya.diplomaproject.api.entity.Image;
import com.payoya.diplomaproject.api.entity.Product;
import com.payoya.diplomaproject.api.repository.IImageRepository;
import com.payoya.diplomaproject.api.repository.IProductRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import com.google.gson.Gson;
import java.util.List;

@Service
public class ImageService {

    private final IImageRepository imageRepository;
    private final IProductRepository productRepository;

    private final Gson gson = new Gson();


    public ImageService(IImageRepository imageRepository, IProductRepository productRepository) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<Object> saveImage(MultipartFile file, String description, Long productId) {



        if(!file.isEmpty()){

            try{

                String path = "D:\\Main\\ProjectDiploma\\images\\" + file.getOriginalFilename();

                //check if this file already exist in project local directory. Aboard to save
                if(imageRepository.findImageByImagePath(path) != null){

                    String errorMessage = "this image alredy exist. You cannot add one more similar to him." +
                            " But you can find this image by GET request to: " +
                            "/api/v1/image/get/" + file.getOriginalFilename();

                    return new ResponseEntity<>(gson.toJson(errorMessage), HttpStatus.BAD_REQUEST);
                }

                Image image = new Image();
                byte[] bytes = file.getBytes();

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path));
                stream.write(bytes);
                stream.close();

                image.setImagePath(path);
                image.setDescription(description);

                if(productId != null){
                    Product product = productRepository.findById(productId).orElse(null);
                    image.setProduct(product);
                }
                imageRepository.save(image);

                return new ResponseEntity<>("Image Saved with this path" + path, HttpStatus.OK);

            } catch (IOException e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Image is empty", HttpStatus.BAD_REQUEST);
        }
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public ResponseEntity<byte[]> getImageByPath(String path){

        try {

            String iternalPath = "D:\\Main\\ProjectDiploma\\images\\" + path;
            System.err.println(iternalPath);

            // Read the image from the specified path
            BufferedImage image = ImageIO.read(new File(iternalPath));

            // Convert the image to a byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] imageData = baos.toByteArray();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.IMAGE_PNG);

            return new ResponseEntity<>(imageData, httpHeaders, HttpStatus.OK);

        } catch (IOException e) {
            e.getMessage();
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    }

}
