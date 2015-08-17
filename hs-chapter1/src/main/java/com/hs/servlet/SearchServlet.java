package com.hs.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
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

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      Logger logger = LoggerFactory.getLogger(SearchServlet.class);

      String searchString = request.getParameter("searchString");
      logger.info("Received searchString [" + searchString + "]");

      Session session = StartupDataLoader.openSession();
      FullTextSession fullTextSession = Search.getFullTextSession(session);

      fullTextSession.beginTransaction();
      
      QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Item.class).get();
      org.apache.lucene.search.Query luceneQuery = queryBuilder.keyword().onFields("name", "description").matching(searchString).createQuery();

      org.hibernate.Query hibernateQuery = fullTextSession.createFullTextQuery(luceneQuery, Item.class);
      List<Item> items = hibernateQuery.list();
      logger.info("Found " + items.size() + " search results");
      request.setAttribute("items", items);
      
      fullTextSession.getTransaction().commit();
      session.close();
      
      // pass the request object to the jsp/jstl view for rendering
      getServletContext().getRequestDispatcher("/WEB-INF/pages/search.jsp").forward(request, response);
   }

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doPost(request, response);
   }

}
