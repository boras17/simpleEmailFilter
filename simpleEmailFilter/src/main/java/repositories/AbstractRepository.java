package repositories;

import hibernate.HibernateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AbstractRepository<T> {
    private final Class<T> tClass;
    private final Logger logger;

    public AbstractRepository(Class<T> tClass) {
        this.tClass = tClass;
        this.logger = LogManager.getLogger("AbstractRepositoryLogger");
    }

    public void save(T object){
        Transaction transaction = null;
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(object);
            transaction.commit();
            boolean loggerInfoEnabled = this.logger.isInfoEnabled();
            if(loggerInfoEnabled){
                this.logger.info("saving new entity entity class {} entity package: {}", object.getClass().getName(),object.getClass().getPackage().getName());
            }
        }catch (Exception e){
            if(transaction!=null){
                transaction.rollback();
            }
            if (this.logger.isErrorEnabled()) {
                this.logger.error("Error occurred. method={}, invocationParameter={}, errorMessage={}, throwable={}",
                        "save", object, e.getMessage(), e.getCause());
            }else{
                System.out.println("Error log level does not work ");
                e.printStackTrace();
            }
        }
    }

    public void update(T object) {
        Transaction transaction = null;
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(object);
            transaction.commit();
        }catch (Exception e){
            if(transaction!=null){
                transaction.rollback();
            }
            if (this.logger.isErrorEnabled()) {
                this.logger.error("Error occurred. method={}, invocationParameter={}, errorMessage={}, throwable={}",
                        "update", object, e.getMessage(), e.getCause());
            }
        }
    }

    public Optional<T> findById(int id){
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            T saved = session.find(this.tClass, id);
            transaction.commit();
            return Optional.ofNullable(saved);
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            if (this.logger.isErrorEnabled()) {
                this.logger.error("Error occurred. method={}, invocationParameter={}, errorMessage={}, throwable={}",
                        "findById", id, e.getMessage(), e.getCause());
            }
        }
        return Optional.empty();
    }
    public boolean deleteById(int id){
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            T persistentInstance = session.load(this.tClass, id);
            if (persistentInstance != null) {
                session.delete(persistentInstance);
                transaction.commit();
                return true;
            }
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            if (this.logger.isErrorEnabled()) {
                this.logger.error("Error occurred. method={}, invocationParameter={}, errorMessage={}, throwable={}",
                        "findById", id, e.getMessage(), e.getCause());
            }
        }
        return false;
    }
    public List<T> getAll(){
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(this.tClass);
            Root<T> root = criteriaQuery.from(this.tClass);

            CriteriaQuery<T> fetched = criteriaQuery.select(root);
            TypedQuery<T> typedQuery = session.createQuery(fetched);
            List<T> resultList = typedQuery.getResultList();
            transaction.commit();
            return resultList;
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            if (this.logger.isErrorEnabled()) {
                this.logger.error("Error occurred. method={}, errorMessage={}, throwable={}",
                        "getAll", e.getMessage(), e.getCause());
            }
        }
        return Collections.emptyList();
    }
    List<T> getAllByCriteria(CriteriaQuery<T> criteria){
        Transaction transaction = null;
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            TypedQuery<T> typedQuery = session.createQuery(criteria);
            List<T> resultList = typedQuery.getResultList();
            transaction.commit();
            return resultList;
        }catch (Exception e){
            if(transaction!= null){
                transaction.rollback();
            }
            if (this.logger.isErrorEnabled()) {
                this.logger.error("Error occurred. method={}, parameter={} errorMessage={}, throwable={}",
                        "getAll(criteria)",criteria, e.getMessage(), e.getCause());
            }
        }
        return Collections.emptyList();
    }
}
