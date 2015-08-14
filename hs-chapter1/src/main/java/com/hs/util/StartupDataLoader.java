package com.hs.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

@WebListener
public class StartupDataLoader implements ServletContextListener {

   private static SessionFactory sessionFactory;

   public static synchronized Session openSession() {
      if (sessionFactory == null) {
         Configuration configuration = new Configuration();
         configuration.configure();
         ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
         sessionFactory = configuration.buildSessionFactory(serviceRegistry);
      }
      return sessionFactory.openSession();
   }

   @Override
   public void contextDestroyed(ServletContextEvent event) {
      if(sessionFactory != null && !sessionFactory.isClosed()) {
         sessionFactory.close();
      }
   }

   @Override
   public void contextInitialized(ServletContextEvent event) {
      Session session = openSession();
      session.beginTransaction();
      
      session.getTransaction().commit();
      session.close();
   }

}
