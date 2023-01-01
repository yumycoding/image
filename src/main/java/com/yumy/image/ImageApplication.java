package com.yumy.image;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

import static com.yumy.image.constants.FileConstant.USER_FOLDER;

@SpringBootApplication
public class ImageApplication {


    public static void main(String[] args) {
        SpringApplication.run(ImageApplication.class, args);

    new File(USER_FOLDER).mkdirs();
    }

}
