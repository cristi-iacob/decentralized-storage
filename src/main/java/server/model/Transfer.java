package server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Transfer")
public class Transfer implements Serializable, HasId {
    @Id
    @Column(name = "idTransfer", unique = true)
    private int id;

    @Column(name = "idUser")
    private int idUser;

    @Column(name = "idPeer")
    private int idPeer;

    @Column(name = "informationPackageId")
    private String informationPackageId;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdPeer() {
        return idPeer;
    }

    public void setIdPeer(int idPeer) {
        this.idPeer = idPeer;
    }

    public String getInformationPackageId() {
        return informationPackageId;
    }

    public void setInformationPackageId(String informationPackageId) {
        this.informationPackageId = informationPackageId;
    }
}
