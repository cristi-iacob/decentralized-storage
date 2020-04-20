package server;
import files.FileMaster;
import files.split.BitwiseSplitManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@EnableAutoConfiguration
public class Server {
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(@RequestParam("file")MultipartFile file,
                                             @RequestParam("client") String client) throws IOException {
        try {
            FileMaster fileMaster = new FileMaster();
            BitwiseSplitManager splitter = new BitwiseSplitManager();
            List< Byte > bytes[] = splitter.splitFileBytes(file.getBytes(), 5);
//            Byte[] reverted = splitter.revertSplit(bytes);
//            for (int i = 0; i  < reverted.length; ++ i) {
//                System.out.println(reverted[i]);
//            }
            fileMaster.writeUploadFileToDisk(file);
            return new ResponseEntity<>("yay", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("naaa", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }


}
