package server.service;

import com.google.gson.Gson;
import files.FilesUtils;
import files.split.BitwiseSplitManager;
import files.split.SplitManager;
import org.springframework.web.multipart.MultipartFile;
import server.model.Transfer;
import server.peers.IPeersFinder;
import server.peers.RandomPeersFinder;
import server.persistence.TransferRepository;
import server.persistence.UserRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TransferService {
    private final int PARTS_NUMBER = 5;
    private final int PEERS_NUMBER = 15;
    private final int COPIES_NUMBER = PEERS_NUMBER / PARTS_NUMBER;
    private SplitManager splitManager;
    private IPeersFinder peersFinder;
    private TransferRepository transferRepository;
    private UserRepository userRepository;

    public TransferService() {
        this.splitManager = new BitwiseSplitManager(PARTS_NUMBER);
        this.peersFinder = new RandomPeersFinder(PEERS_NUMBER);
        this.transferRepository = new TransferRepository();
        this.userRepository = new UserRepository();
    }

    private void saveTransfer(int userId, int peerId, String informationPackageId) {
        Transfer transfer = new Transfer();
        transfer.setIdUser(userId);
        transfer.setIdPeer(peerId);
        transfer.setInformationPackageId(informationPackageId);
        transferRepository.add(transfer);
    }

    public String saveTransfersForFile(MultipartFile file, String username) {
        try {
            List<Byte>[] parts = splitManager.splitFileBytes(file.getBytes());
            ArrayList <Integer> peers =
                    (ArrayList) peersFinder.findPeers(userRepository.getUserByEmail(username).getId());
            String commonFileName = "" + System.currentTimeMillis() + "_" + file.getOriginalFilename()
                    + "_" + username;

            for (int i = 0; i < parts.length; ++ i) {
                for (int j = i * COPIES_NUMBER; j < (i + 1) * COPIES_NUMBER; ++ j) {
                    String packageName = commonFileName + "_" + i;
                    saveTransfer(userRepository.getUserByEmail(username).getId(), peers.get(j), packageName);
                    FilesUtils.writeUploadFileToDisk(parts[i],
                            "/home/cristi/fisiere_licenta/" + userRepository.get(peers.get(j)).getEmail() + "/",
                            packageName);
                }
            }

            ArrayList <String> names = new ArrayList<>();
            for (Integer peer : peers) {
                names.add(userRepository.get(peer).getEmail());
            }
            return commonFileName + "~" + new Gson().toJson(names);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public File restoreFile(String client, String filename) throws Exception {
        List <Byte>[] fileParts = new List[PARTS_NUMBER];

        for (int i = 0; i < PARTS_NUMBER; ++ i) {
            String filenameToSearch = filename + "_" + i;
            Transfer transfer = transferRepository.getTransferByFilenameFromOnlineUsers(filenameToSearch);

            if (transfer == null) {
                throw new Exception("There are no sufficient online peers!");
            }

            fileParts[i] = FilesUtils.readFileFromDisk(userRepository.get(transfer.getIdPeer()).getEmail(), filenameToSearch);
        }

        return FilesUtils.fileFromParts(fileParts, filename);
    }
}
