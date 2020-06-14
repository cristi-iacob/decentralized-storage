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

    public User getUserByEmailAndPassword(String email, String password) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        User ret = null;

        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from User where email=:email and password=:password");
            query.setParameter("email", email);
            query.setParameter("password", password);
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

    public List<User> getUsersSortedByTimezone(int timezone) {
        Session session = HibernateUtils.getSessionFactory().openSession();

        try {
            Query query = session.createQuery("select user from User user where user.online=true order by abs(user.timezone - :timezone)", User.class);
            query.setParameter("timezone", timezone);
            return query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public void allonline() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("update User set online=1 where 1=1");
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public void alloffline() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("update User set online=0 where 1=1");
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }
}
