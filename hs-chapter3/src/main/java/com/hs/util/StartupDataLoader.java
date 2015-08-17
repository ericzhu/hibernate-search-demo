package com.hs.util;

import java.util.Arrays;
import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hs.domain.Category;
import com.hs.domain.CustomerReview;
import com.hs.domain.Item;

@WebListener
public class StartupDataLoader implements ServletContextListener {

   Logger                              logger = LoggerFactory.getLogger(StartupDataLoader.class);

   private static EntityManagerFactory entityManagerFactory;

   public static EntityManager createEntityManager() {
      if (entityManagerFactory == null) {
         entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-search-demo");
      }
      return entityManagerFactory.createEntityManager();
   }

   @Override
   public void contextDestroyed(ServletContextEvent event) {
      if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
         entityManagerFactory.close();
      }
   }

   @Override
   public void contextInitialized(ServletContextEvent event) {
      EntityManager entityManager = createEntityManager();
      entityManager.getTransaction().begin();
      loadTestData(entityManager);
      entityManager.flush();
      entityManager.getTransaction().commit();
      entityManager.close();
   }

   private void loadTestData(EntityManager entityManager) {
      //
      // Create 5 Categorys
      //
      Category xPhone = new Category("Orange", "xPhone", null);
      Category xTablet = new Category("Orange", "xTablet", null);
      Category solarSystem = new Category("Song-Sung", "Solar System Phone", null);
      Category flame = new Category("Jungle", "Flame Book Reader", null);
      Category pc = new Category(null, "Personal Computer", null);

      //
      // Create and persist 12 Items with Categorys and customer reviews
      //
      Item theCloud = new Item(
         "The Cloud",
         "cloud.jpg",
         "The Cloud is a place of magic and wonder.  Businesses run smoothly in the Cloud.  Developers no longer need system administrators, or food and water for that matter.  You can watch television on your tablet Category from the comfort of your own sofa, without having to look up at the television.  Download the Cloud Item, from the Cloud, and harness this awesome power today!");
      theCloud.setCategories(new HashSet<Category>(Arrays.asList(new Category[] { xPhone, xTablet })));
      CustomerReview theCloudReview1 = new CustomerReview("fanboy1984", 5, "This Item makes my xPhone even more stylish and trendy!");
      CustomerReview theCloudReview2 = new CustomerReview("anti.hipster", 1,
         "I don't understand what 'The Cloud' means.  This seems like more of a catchphrase than a new technology or Item...");
      theCloud.setCustomerReviews(new HashSet<CustomerReview>(Arrays.asList(new CustomerReview[] { theCloudReview1, theCloudReview2 })));
      entityManager.persist(theCloud);
      logger.info("Persisting " + theCloud.getName());

      Item salesCloser = new Item(
         "Sales Closer",
         "pointing.jpg",
         "A high-powered productivity Item for high-powered sales professionals.  Track your high-powered leads, and manage your high-powered schedule.  When you are out on the town doing high-powered networking, you want to show your high-powered sales prospects that you are high-powered too.");
      salesCloser.setCategories(new HashSet<Category>(Arrays.asList(new Category[] { xPhone, solarSystem })));
      CustomerReview salesCloserReview = new CustomerReview("ShowMeTheMoney", 5,
         "Great Item!  If you have used 'Sales Commander 2000' before, then this interface will feel familiar.");
      salesCloser.setCustomerReviews(new HashSet<CustomerReview>(Arrays.asList(new CustomerReview[] { salesCloserReview })));
      entityManager.persist(salesCloser);
      logger.info("Persisting " + salesCloser.getName());

      Item football = new Item(
         "World Tournament Football",
         "ball.jpg",
         "This game Item offers all the excitement of football (soccer), except that it's played on a touch screen rather than your feet.  So there isn't any of the kicking, or the running, or any of the physical exercise at all.  Other than that, it's pretty much the same.");
      football.setCategories(new HashSet<Category>(Arrays.asList(new Category[] { xTablet, flame })));
      CustomerReview footballReview = new CustomerReview("RealAmerican", 2,
         "False advertising... I though this was supposed to be football, but it's a SOCCER game instead.");
      football.setCustomerReviews(new HashSet<CustomerReview>(Arrays.asList(new CustomerReview[] { footballReview })));
      entityManager.persist(football);
      logger.info("Persisting " + football.getName());

      Item crystal = new Item(
         "Yet Another Crystal Game",
         "brilliant.jpg",
         "A dazzling game Item, in which you connect crystals of the same color to make them go away.  It's sort of like Tetris.  Actually, it's sort of like the other dozen or so other games today where you connect crystals of the same color.");
      crystal.setCategories(new HashSet<Category>(Arrays.asList(new Category[] { flame, pc })));
      CustomerReview crystalReview = new CustomerReview(
         "YetAnotherGamer",
         3,
         "Why is this only supported on two Categorys?  The other dozen clones of this game are available on all Categorys.  You should really make this Item inactive until more Categorys are supported...");
      crystal.setCustomerReviews(new HashSet<CustomerReview>(Arrays.asList(new CustomerReview[] { crystalReview })));
      crystal.setActive(false);
      entityManager.persist(crystal);
      logger.info("Persisting " + crystal.getName());

      Item pencilSharpener = new Item("Pencil Sharpener", "pencil.jpg",
         "Sharpen your pencils, by sticking them into your phone's Bluetooth plug and pushing a button.  This Item really pushes your phone's hardware to its limits!");
      pencilSharpener.setCategories(new HashSet<Category>(Arrays.asList(new Category[] { xPhone, solarSystem })));
      CustomerReview pencilSharpenerReview1 = new CustomerReview("missing.digits", 1, "Ouch, this Item is a menace!  I should sue.");
      CustomerReview pencilSharpenerReview2 = new CustomerReview("LawyerGuy", 5, "@missing.digits:  Private message me.  Let's talk...");
      pencilSharpener.setCustomerReviews(new HashSet<CustomerReview>(Arrays.asList(new CustomerReview[] { pencilSharpenerReview1, pencilSharpenerReview2 })));
      entityManager.persist(pencilSharpener);
      logger.info("Persisting " + pencilSharpener.getName());

      Item staplerTracker = new Item(
         "Stapler Tracker",
         "stapler.jpg",
         "Is someone always taking your stapler?  It's a common problem in many office spaces.  This business productivity Item will help you manage your stapler at all times, so that you will never have to deal with a \"case of the Mondays\" again.");
      staplerTracker.setCategories(new HashSet<Category>(Arrays.asList(new Category[] { pc })));
      CustomerReview staplerTrackerReview = new CustomerReview("mike.bolton", 3, "'PC LOAD LETTER'?  What does that mean?!?");
      staplerTracker.setCustomerReviews(new HashSet<CustomerReview>(Arrays.asList(new CustomerReview[] { staplerTrackerReview })));
      entityManager.persist(staplerTracker);
      logger.info("Persisting " + staplerTracker.getName());

      Item frustratedFlamingos = new Item("Frustrated Flamingos", "flamingo.jpg",
         "A fun little game Item, where you throw large birds around for no Itemarent reason.  Why else do you think they're so frustrated?");
      frustratedFlamingos.setCategories(new HashSet<Category>(Arrays.asList(new Category[] { xPhone, xTablet, solarSystem, flame, pc })));
      CustomerReview frustratedFlamingosReview = new CustomerReview("BirdSlinger", 4,
         "LOL, I love catapulting the flamingos into the cows!  I hate how the advertisement banner hides part of the view, tho.");
      frustratedFlamingos.setCustomerReviews(new HashSet<CustomerReview>(Arrays.asList(new CustomerReview[] { frustratedFlamingosReview })));
      entityManager.persist(frustratedFlamingos);
      logger.info("Persisting " + frustratedFlamingos.getName());

      Item grype = new Item(
         "Grype Video Conferencing",
         "laptop.jpg",
         "Make free local and international calls, with video, using this Item and your home Internet connection.  Better yet, make free calls using your employer's Internet connection!");
      grype.setCategories(new HashSet<Category>(Arrays.asList(new Category[] { xPhone, xTablet, solarSystem, pc })));
      CustomerReview grypeReview = new CustomerReview("office.casual", 4,
         "I wish they had not added video to this Item in the latest version.  I liked it much more back when I didn't have to get dressed.");
      grype.setCustomerReviews(new HashSet<CustomerReview>(Arrays.asList(new CustomerReview[] { grypeReview })));
      entityManager.persist(grype);
      logger.info("Persisting " + grype.getName());

      Item eReader = new Item(
         "E-Book Reader",
         "book.jpg",
         "Read books on your computer, or on the go from your mobile Category with this powerful e-reader Item.  We recommend \"Hibernate Search by Example\", from Packt Publishing.");
      eReader.setCategories(new HashSet<Category>(Arrays.asList(new Category[] { xPhone, xTablet, solarSystem, flame, pc })));
      CustomerReview eReaderReview = new CustomerReview("StevePerkins", 5,
         "This 'Hibernate Search by Example' book is brilliant!  Thanks for the recommendation!");
      eReader.setCustomerReviews(new HashSet<CustomerReview>(Arrays.asList(new CustomerReview[] { eReaderReview })));
      entityManager.persist(eReader);
      logger.info("Persisting " + eReader.getName());

      Item domeBrowser = new Item(
         "Dome Web Browser",
         "orangeswirls.jpg",
         "This amazing Item allows us to track all of your online activity.  We can figure out where you live, what you had for breakfast this morning, or what your closest secrets are.  The Item also includes a web browser.");
      domeBrowser.setCategories(new HashSet<Category>(Arrays.asList(new Category[] { solarSystem, flame, pc })));
      CustomerReview domeBrowserReview = new CustomerReview("TinFoilHat", 1,
         "I uninstalled this Item.  If the government would fake a moon landing, then they would definately use my browser history to come after me.");
      domeBrowser.setCustomerReviews(new HashSet<CustomerReview>(Arrays.asList(new CustomerReview[] { domeBrowserReview })));
      entityManager.persist(domeBrowser);
      logger.info("Persisting " + domeBrowser.getName());

      Item athenaRadio = new Item(
         "Athena Internet Radio",
         "jamming.jpg",
         "Listen to your favorite songs on streaming Internet radio!  When you like a song, this Item will play more songs similar to that one.  Or at least it plays more songs... to be honest, sometimes they're not all that similar.  :(");
      athenaRadio.setCategories(new HashSet<Category>(Arrays.asList(new Category[] { xPhone, xTablet, solarSystem, flame, pc })));
      CustomerReview athenaRadioReview = new CustomerReview("lskinner", 5, "I requested 'Free Bird', and this Item played 'Free Bird'.  What's not to like?");
      athenaRadio.setCustomerReviews(new HashSet<CustomerReview>(Arrays.asList(new CustomerReview[] { athenaRadioReview })));
      entityManager.persist(athenaRadio);
      logger.info("Persisting " + athenaRadio.getName());

      Item mapJourney = new Item("Map Journey", "compass.jpg",
         "Do you need directions to help you reach a destination?  This GPS Item will definitely produce enough turn-by-turn directions to get you there!  Eventually.");
      mapJourney.setCategories(new HashSet<Category>(Arrays.asList(new Category[] { xPhone, solarSystem, pc })));
      CustomerReview mapJourneyReview = new CustomerReview("LostInSpace", 3, "Not great... but still WAY better than Orange maps.");
      mapJourney.setCustomerReviews(new HashSet<CustomerReview>(Arrays.asList(new CustomerReview[] { mapJourneyReview })));
      entityManager.persist(mapJourney);
      logger.info("Persisting " + mapJourney.getName());
   }

}
