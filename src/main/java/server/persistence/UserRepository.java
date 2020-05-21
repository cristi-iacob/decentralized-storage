package server.persistence;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import server.model.User;

import java.util.List;

public class UserRepository extends Repository <User> {
    public UserRepository() {
        setMyClass(User.class);
    }

    public User getUserByEmail(String email) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        User ret = null;

        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from User where email=:email");
            query.setParameter("email", email);
            ret = (User) query.uniqueResult();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
            return ret;
        }
    }

    public List<User> getAllOnlineUsers() {
        Session session = HibernateUtils.getSessionFactory().openSession();

        try {
            return session.createQuery("select user from User user where user.online=true", User.class).getResultList();
        } catch (HibernateException e) {

        } finally {
            session.close();
        }

        return null;
    }
}
