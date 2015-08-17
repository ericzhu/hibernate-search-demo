package com.hs.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hs.domain.Item;
import com.hs.util.StartupDataLoader;

@WebServlet("search")
public class SearchServlet extends HttpServlet {

   private static final long serialVersionUID = 1L;

   @SuppressWarnings("unchecked")
   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      Logger logger = LoggerFactory.getLogger(SearchServlet.class);

      String searchString = req.getParameter("searchString");
      logger.info("Received searchString [" + searchString + "]");
      Session session = StartupDataLoader.openSession();
      FullTextSession fullTextSession = Search.getFullTextSession(session);

      fullTextSession.beginTransaction();

      QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Item.class).get();
      org.apache.lucene.search.Query luceneQuery = queryBuilder.keyword()
         .onFields("name", "description", "categories.name", "customerReviews.comments")
         .matching(searchString)
         .createQuery();

      FullTextQuery hibernateQuery = fullTextSession.createFullTextQuery(luceneQuery, Item.class);
      List<Item> items = hibernateQuery.list();
      logger.info("Found " + items.size() + " items");

      fullTextSession.clear();
      req.setAttribute("items", items);

      fullTextSession.getTransaction().commit();
      session.close();
      // Forward the request object (including the search results) to the JSP/JSTL view for rendering
      getServletContext().getRequestDispatcher("/WEB-INF/pages/search.jsp").forward(req, resp);

   }

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      this.doPost(req, resp);
   }
}
