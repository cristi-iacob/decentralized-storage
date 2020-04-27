package files;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

public class FilesUtils {
    private final String path = "/home/cristi/fisiere_licenta/";

    public byte[] multipartToBytes(MultipartFile multipartFile) throws Exception {
        return multipartFile.getBytes();
    }

    public void writeUploadFileToDisk(MultipartFile file) throws Exception {
        File outputFile = new File(path + file.getOriginalFilename());
        outputFile.createNewFile();
        FileOutputStream outStream = new FileOutputStream(outputFile);
        outStream.write(multipartToBytes(file));
        outStream.close();
    }
}
