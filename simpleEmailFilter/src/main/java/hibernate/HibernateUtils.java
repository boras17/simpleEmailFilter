package hibernate;

import models.Attachment;
import models.MessageSaved;
import models.filters.EmailFilter;
import models.filters.FilteredEmail;
import models.saves.SavingStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtils {
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null){
            try{
                Configuration configuration = new Configuration();
                Properties hibernateConfigProperties = new Properties();
                hibernateConfigProperties.put(Environment.DRIVER, "org.h2.Driver");
                hibernateConfigProperties.put(Environment.URL, "jdbc:h2:mem:test;DB_CLOSE_ON_EXIT=FALSE");
                hibernateConfigProperties.put(Environment.USER, "sa");
                hibernateConfigProperties.put(Environment.PASS, "root");
                hibernateConfigProperties.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
                hibernateConfigProperties.put(Environment.SHOW_SQL, "true");
                hibernateConfigProperties.put(Environment.FORMAT_SQL, "true");
                hibernateConfigProperties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                hibernateConfigProperties.put(Environment.HBM2DDL_AUTO, "update");
                hibernateConfigProperties.put(Environment.POOL_SIZE,1);

                configuration.setProperties(hibernateConfigProperties);

                configuration.addAnnotatedClass(MessageSaved.class);
                configuration.addAnnotatedClass(EmailFilter.class);
                configuration.addAnnotatedClass(FilteredEmail.class);
                configuration.addAnnotatedClass(SavingStrategy.class);
                configuration.addAnnotatedClass(Attachment.class);
                configuration.addAnnotatedClass(MessageSaved.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            }catch (Exception e){
                Logger logger = LogManager.getLogger("HibernateUtils");
                if(logger.isErrorEnabled()) {
                    logger.error("Error occurred during creating Session factory message={} throwable={}", e.getMessage(), e.getCause());
                }
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
