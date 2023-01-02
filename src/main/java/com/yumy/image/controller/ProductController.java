package com.yumy.image.controller;

import com.yumy.image.exception.NotAnImageFileException;
import com.yumy.image.model.Product;
import com.yumy.image.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.yumy.image.constants.FileConstant.FORWARD_SLASH;
import static com.yumy.image.constants.FileConstant.USER_FOLDER;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@AllArgsConstructor
@Log4j2
public class ProductController {


    private final ProductService productService;
    StorageServiceImplementation storageService;

    @PostMapping("/")
    public ResponseEntity<Product> registerNewProduct(@Valid @RequestBody Product product) {
        Product saveProduct = productService.saveProduct(product);
        return ResponseEntity.ok(saveProduct);
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> findAllProducts() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProduct(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(productService.findProduct(id));
    }

    @GetMapping(path = "/image/{product-name}/{fileName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@PathVariable("product-name") String username, @PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(USER_FOLDER + username + FORWARD_SLASH + fileName));
    }

    @PostMapping(path = "/updateProfileImage",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Product> updateProfileImage(@RequestParam Integer productId,
                                                     @RequestParam(value = "productImage") MultipartFile productImage) throws IOException, NotAnImageFileException {
        var result = productService.updateProfileImage(productId, productImage);
        return new ResponseEntity<>(result, OK);
    }


}
