package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.controller.ServerController;
import server.exceptions.userexceptions.BadTokenException;
import server.exceptions.userexceptions.UserException;
import server.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;

@RestController
@EnableAutoConfiguration
public class Server {
    ServerController serverController = new ServerController();
    UserService userService = new UserService();
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("client") String client,
                                             HttpServletRequest request) {
        try {
            String ret = serverController.uploadContent(file, client);
            if (ret != null) {
                return new ResponseEntity<>(ret, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("There are no available peers.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/download")
    public @ResponseBody byte[]
    downloadFile(@RequestParam("client") String client,
                 @RequestParam("filename") String filename) {
        try {
            File file = serverController.downloadContent(client, filename);
            byte[] ret = Files.readAllBytes(file.toPath());
            return ret;
        } catch (Exception e) {
            return e.getMessage().getBytes();
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestParam("username") String username,
                                        @RequestParam("password") String password,
                                        HttpServletRequest request) {
        try {
            String loginResult = userService.login(username, password);

            if (loginResult == null) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            } else if (loginResult.equals("-1")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(loginResult, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Object> register(@RequestParam("username") String username,
                                           @RequestParam("password") String password,
                                           HttpServletRequest request) {
        try {
            userService.register(username, password);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>("There is another user with the given email.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Object> logout(@RequestParam("username") String username){
                                        // @RequestParam("token") String token) {
        try {
            userService.logout(username, "");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BadTokenException e) {
            return new ResponseEntity<>("Bad token.", HttpStatus.BAD_REQUEST);
        } catch (UserException e) {
            return new ResponseEntity<>("User already logged out.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/allonline")
    public void allonline() {
        userService.allonline();
    }

    @GetMapping(value = "/alloffline")
    public void alloffline() {
        userService.alloffline();
    }

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}
