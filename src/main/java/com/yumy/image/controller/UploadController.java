package com.yumy.image.controller;


import com.yumy.image.exception.NotAnImageFileException;
import com.yumy.image.model.Product;
import com.yumy.image.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
//@RequestMapping(path = "products")
@RequiredArgsConstructor
public class UploadController {


    private final ProductService productService;


    @GetMapping("/uploadimage")
    public String displayUploadForm(Model model) {
        model.addAttribute("message","Please upload Image");
        return "index";
    }

    @PostMapping("/upload")
    public String uploadImage(Model model,
                              @RequestParam Integer productId,
                              @RequestParam(value = "productImage") MultipartFile productImage
    ) throws  IOException, NotAnImageFileException {

        Product product = productService.updateProfileImage(productId, productImage);
        model.addAttribute("product", product);

        return "index";
    }

}
