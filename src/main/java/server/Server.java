package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.controller.ServerController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@EnableAutoConfiguration
public class Server {
    ServerController serverController = new ServerController();
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(@RequestParam("file")MultipartFile file,
                                             @RequestParam("client") String client) throws IOException {
        try {
            serverController.uploadContent(file, client);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/download")
    public @ResponseBody byte[]
    downloadFile(@RequestParam("client") String client,
                 @RequestParam("filename") String filename) throws IOException {
        try {
            File file = serverController.downloadContent(client, filename);
            byte[] ret = Files.readAllBytes(file.toPath());
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }


}
