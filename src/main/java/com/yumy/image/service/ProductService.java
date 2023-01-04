package com.yumy.image.service;

import com.yumy.image.exception.NotAnImageFileException;
import com.yumy.image.model.Product;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static com.yumy.image.constants.FileConstant.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.MediaType.*;

@Service
@Log4j2
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product findProduct(Integer id) {
        return productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    private void saveProfileImage(Product product, MultipartFile productImage) throws IOException, NotAnImageFileException {

       product.setName(product.getName().replaceAll("\\s", "_"));
        if (productImage != null) {
            if (!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE).contains(productImage.getContentType())) {
                throw new NotAnImageFileException(productImage.getOriginalFilename() + NOT_AN_IMAGE_FILE);
            }
            Path userFolder = Paths.get(USER_FOLDER + product.getName()).toAbsolutePath().normalize();
            if (!Files.exists(userFolder)) {
                Files.createDirectories(userFolder);
                log.info(DIRECTORY_CREATED + userFolder);
            }
            var simplePath = Paths.get(userFolder+"\\" + product.getName() + DOT + JPG_EXTENSION);

            Files.deleteIfExists(simplePath);

            Files.copy(productImage.getInputStream(), userFolder.resolve(product.getName() + DOT + JPG_EXTENSION), REPLACE_EXISTING);

            product.setProfileImageUrl(setProductImageUrl(product.getName()));
            productRepository.save(product);
            log.info(FILE_SAVED_IN_FILE_SYSTEM + productImage.getOriginalFilename());

        }
    }

    private String setProductImageUrl(String productName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(PRODUCT_IMAGE_PATH + productName + FORWARD_SLASH
                + productName + DOT + JPG_EXTENSION).toUriString();
    }

    public Product updateProfileImage(Integer productId, MultipartFile profileImage) throws IOException, NotAnImageFileException {
        Product product = productRepository.findById(productId).orElseThrow(EntityNotFoundException::new);
        saveProfileImage(product, profileImage);
        return product;
    }


}
