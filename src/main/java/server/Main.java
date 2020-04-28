package server;

import server.model.Transfer;
import server.persistence.TransferRepository;

public class Main {
    public static void main(String[] args) {
        TransferRepository repo = new TransferRepository();
        Transfer transfer = new Transfer();
        transfer.setIdUser(1);
        transfer.setIdPeer(2);
        transfer.setInformationPackageId("test");
        repo.add(transfer);
    }
}
