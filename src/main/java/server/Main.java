package server;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import server.model.Transfer;
import server.model.User;
import server.persistence.Repository;
import server.persistence.TransferRepository;
import server.persistence.UserRepository;


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
