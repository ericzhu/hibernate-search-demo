package com.hs.servlet;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
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
      Logger logger = LoggerFactory.getLogger(this.getClass());

      String searchString = req.getParameter("searchString") == null ? "" : req.getParameter("searchString").trim();
      String sortField = req.getParameter("sortField") != null ? req.getParameter("sortField").trim() : "relevance";
      int firstResult = req.getParameter("firstResult") != null ? Integer.parseInt(req.getParameter("firstResult")) : 0;
      logger.info("Received searchString [" + searchString + "], sortField [" + sortField + "], and firstResult [" + firstResult + "]");

      // start an entity manager
      EntityManager entityManager = StartupDataLoader.createEntityManager();

      // create a Hibernate Search wrapper around the vanilla entity manager
      FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);

      QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Item.class).get();

      org.apache.lucene.search.Query luceneQuery = null;

      if (searchString.length() > 2 && searchString.startsWith("\"") && searchString.endsWith("\"")) {
         String unquotedSearchString = searchString.substring(1, searchString.length() - 1);
         luceneQuery = queryBuilder.phrase()
            .onField("name")
            .andField("description")
            .andField("categories.name")
            .andField("customerReviews.comments")
            .sentence(unquotedSearchString)
            .createQuery();

      }
      else {
         luceneQuery = queryBuilder.keyword()
            .fuzzy()
            .withThreshold(0.7f)
            .onFields("name", "description", "categories.name", "customerReviews.comments")
            .matching(sortField)
            .createQuery();
      }

      FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Item.class);

      if (sortField.equals("name")) {
         Sort sort = new Sort(new SortField("sort_name", SortField.STRING));
         fullTextQuery.setSort(sort);
      }
      else if (sortField.equals("name-reverse")) {
         Sort sort = new Sort(new SortField("sort_name", SortField.STRING, true));
         fullTextQuery.setSort(sort);
      }

      int resultSize = fullTextQuery.getResultSize();
      fullTextQuery.setFirstResult(firstResult);
      List<Item> items = fullTextQuery.getResultList();

      fullTextEntityManager.clear();

      // Put the search results on the HTTP request object, along with sorting and pagination related paramaters
      req.setAttribute("searchString", searchString);
      req.setAttribute("sortField", sortField);
      req.setAttribute("items", items);
      req.setAttribute("resultSize", resultSize);
      req.setAttribute("firstResult", firstResult);

      // Close and clean up the JPA entity manager
      entityManager.getTransaction().commit();
      entityManager.close();

      // Forward the request object (including the search results) to the JSP/JSTL view for rendering
      getServletContext().getRequestDispatcher("/WEB-INF/pages/search.jsp").forward(req, resp);

   }

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      this.doPost(req, resp);
   }

}
