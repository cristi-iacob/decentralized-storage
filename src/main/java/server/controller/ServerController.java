package server.controller;

import org.springframework.web.multipart.MultipartFile;
import server.service.TransferService;

import java.io.File;

public class ServerController {
    private TransferService transferService;

    public ServerController() {
        transferService = new TransferService();
    }

    public String uploadContent(MultipartFile file, String client) {
        return transferService.saveTransfersForFile(file, client);
    }

    public File downloadContent(String client, String filename) throws Exception{
        return transferService.restoreFile(client, filename);
    }
}
