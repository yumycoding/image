package com.yumy.image.controller;

import com.yumy.image.exception.NotAnImageFileException;
import com.yumy.image.model.User;
import com.yumy.image.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
public class UserController {


    private final UserService userService;


    @PostMapping("/")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        User saveUser = userService.saveUser(user);
        return ResponseEntity.ok(saveUser);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> findAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUser(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(userService.findUser(id));
    }

    @GetMapping(path = "/image/{username}/{fileName}", produces = IMAGE_JPEG_VALUE)

    public byte[] getProfileImage(@PathVariable("username") String username, @PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(USER_FOLDER + username + FORWARD_SLASH + fileName));
    }

    @PostMapping("/updateProfileImage")
    public ResponseEntity<User> updateProfileImage(@RequestBody User user, @RequestParam(value = "profileImage") MultipartFile profileImage) throws IOException, NotAnImageFileException {
        var result = userService.updateProfileImage(user, profileImage);
        return new ResponseEntity<>(result, OK);
    }


}
