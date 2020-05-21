package files;

import files.split.BitwiseSplitManager;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FilesUtils {
    public static byte[] multipartToBytes(MultipartFile multipartFile) throws Exception {
        return multipartFile.getBytes();
    }

    public static void writeUploadFileToDisk(List<Byte> file, String path, String filename) throws Exception {
        File outputFile = new File(path + filename);
        outputFile.getParentFile().mkdirs();
        outputFile.createNewFile();
        FileOutputStream outStream = new FileOutputStream(outputFile);
        outStream.write(byteListToArray(file));
        outStream.close();
    }

    private static byte[] byteListToArray(List<Byte> file) {
        byte[] ret = new byte[file.size()];

        for (int i = 0; i < file.size(); ++ i) {
            ret[i] = file.get(i);
        }

        return ret;
    }

    private static List<Byte> byteArrayToList(byte[] bytes) {
        List < Byte > retList = new ArrayList<>();

        for (int i = 0; i < bytes.length; ++ i) {
            retList.add(bytes[i]);
        }

        return retList;
    }

    public static List<Byte> readFileFromDisk(String user, String filename) {
        try {
            File inputFile = new File("/home/cristi/fisiere_licenta/" + user + "/" + filename);
            return byteArrayToList(Files.readAllBytes(inputFile.toPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static int computeFileSize(List<Byte>[] parts) {
        int ret = 0;

        for (int i = 0; i < parts.length; ++ i) {
            ret += parts[i].size();
        }

        return ret;
    }

    private static byte[] byteClassToPrimitiveByte(Byte[] bytes) {
        byte[] ret = new byte[bytes.length];

        for (int i = 0; i < bytes.length; ++ i) {
            ret[i] = bytes[i];
        }

        return ret;
    }

    public static File fileFromParts(List<Byte>[] parts, String filename) {
        BitwiseSplitManager splitManager = new BitwiseSplitManager(parts.length);

        try {
            File file = new File("/tmp/" + filename + ".tmp");
            file.deleteOnExit();
            FileOutputStream fileOutputStream = new FileOutputStream("/tmp/" + filename + ".tmp");
            byte[] bytes = byteClassToPrimitiveByte(splitManager.revertSplit(parts));
            fileOutputStream.write(bytes);
            fileOutputStream.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
