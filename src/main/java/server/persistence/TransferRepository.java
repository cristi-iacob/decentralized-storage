package server.persistence;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.lang.Nullable;
import server.model.Transfer;


public class TransferRepository extends Repository <Transfer > {
    @Nullable
    public Transfer getTransferByFilenameFromOnlineUsers(String filename) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        Transfer ret = null;

        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery(
                    "select t from Transfer t inner join User u on t.idPeer = u.id " +
                            "where u.online = true and t.informationPackageId = :filename").setMaxResults(1);
            query.setParameter("filename", filename);
            ret = (Transfer) query.uniqueResult();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        session.close();
        return ret;
    }
}
