package client.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ClientController {
    private String userToken;
    private String email;
    private final String pathToUploaded = "/home/cristi/scindo/";

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addFileToUploaded(String fileName) {
        try {
            Files.write(Paths.get(pathToUploaded + email + "/" + "uploaded.txt"), (fileName + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
