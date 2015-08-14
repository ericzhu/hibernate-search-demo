package com.hs.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hs.domain.Item;

@WebListener
public class StartupDataLoader implements ServletContextListener {

   Logger logger = LoggerFactory.getLogger(StartupDataLoader.class);

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
      if (sessionFactory != null && !sessionFactory.isClosed()) {
         sessionFactory.close();
      }
   }

   @Override
   public void contextInitialized(ServletContextEvent event) {
      Session session = openSession();
      session.beginTransaction();
      loadTestData(session);
      session.getTransaction().commit();
      session.close();
   }

   private void loadTestData(Session session) {
      Item item1 = new Item("The Cloud", "cloud.jpg",
         "The Cloud is a place of magic and wonder.  Businesses run smoothly in the Cloud.  Developers no longer need system administrators, or food and water for that matter.  You can watch television on your tablet device from the comfort of your own sofa, without having to look up at the television.  Download the Cloud app, from the Cloud, and harness this awesome power today!");
      session.save(item1);
      logger.info("Persisting " + item1.getName());

      Item item2 = new Item("Sales Closer", "pointing.jpg",
         "A high-powered productivity app for high-powered sales professionals.  Track your high-powered leads, and manage your high-powered schedule.  When you are out on the town doing high-powered networking, you want to show your high-powered sales prospects that you are high-powered too.");
      session.save(item2);
      logger.info("Persisting " + item2.getName());

      Item item3 = new Item("World Tournament Football", "ball.jpg",
         "This game app offers all the excitement of football (soccer), except that it's played on a touch screen rather than your feet.  So there isn't any of the kicking, or the running, or any of the physical exercise at all.  Other than that, it's pretty much the same.");
      session.save(item3);
      logger.info("Persisting " + item3.getName());

      Item item4 = new Item("Yet Another Crystal Game", "brilliant.jpg",
         "A dazzling game app, in which you connect crystals of the same color to make them go away.  It's sort of like Tetris.  Actually, it's sort of like the other dozen or so other games today where you connect crystals of the same color.");
      session.save(item4);
      logger.info("Persisting " + item4.getName());

      Item item5 = new Item("Pencil Sharpener", "pencil.jpg",
         "Sharpen your pencils, by sticking them into your phone's Bluetooth plug and pushing a button.  This app really pushes your phone's hardware to its limits!");
      session.save(item5);
      logger.info("Persisting " + item5.getName());

      Item item6 = new Item("Stapler Tracker", "stapler.jpg",
         "Is someone always taking your stapler?  It's a common problem in many office spaces.  This business productivity app will help you manage your stapler at all times, so that you will never have to deal with a \"case of the Mondays\" again.");
      session.save(item6);
      logger.info("Persisting " + item6.getName());

      Item item7 = new Item("Frustrated Flamingos", "flamingo.jpg",
         "A fun little game app, where you throw large birds around for no apparent reason.  Why else do you think they're so frustrated?");
      session.save(item7);
      logger.info("Persisting " + item7.getName());

      Item item8 = new Item("Grype Video Conferencing", "laptop.jpg",
         "Make free local and international calls, with video, using this app and your home Internet connection.  Better yet, make free calls using your employer's Internet connection!");
      session.save(item8);
      logger.info("Persisting " + item8.getName());

      Item item9 = new Item("E-Book Reader", "book.jpg",
         "Read books on your computer, or on the go from your mobile device with this powerful e-reader app.  We recommend \"Hibernate Search by Example\", from Packt Publishing.");
      session.save(item9);
      logger.info("Persisting " + item9.getName());

      Item item10 = new Item("Dome Web Browser", "orangeswirls.jpg",
         "This amazing app allows us to track all of your online activity.  We can figure out where you live, what you had for breakfast this morning, or what your closest secrets are.  The app also includes a web browser.");
      session.save(item10);
      logger.info("Persisting " + item10.getName());

      Item item11 = new Item("Athena Internet Radio", "jamming.jpg",
         "Listen to your favorite songs on streaming Internet radio!  When you like a song, this app will play more songs similar to that one.  Or at least it plays more songs... to be honest, sometimes they're not all that similar.  :(");
      session.save(item11);
      logger.info("Persisting " + item11.getName());

      Item item12 = new Item("Map Journey", "compass.jpg",
         "Do you need directions to help you reach a destination?  This GPS app will definitely produce enough turn-by-turn directions to get you there!  Eventually.");
      session.save(item12);
      logger.info("Persisting " + item12.getName());
   }

}
