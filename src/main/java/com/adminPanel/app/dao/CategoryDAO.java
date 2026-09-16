package com.adminPanel.app.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import com.adminPanel.app.model.Category;

@Transactional
public class CategoryDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Category category) {
        Session session = sessionFactory.getCurrentSession();
        session.save(category);
    }

    public Category findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Category.class, id);
    }

    public List<Category> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Category> query = session.createQuery("from Category", Category.class);
        return query.getResultList();
    }

    // Returns page number "page" (1-based) with "pageSize" categories per page
    public List<Category> findPage(int page, int pageSize) {
        Session session = sessionFactory.getCurrentSession();
        Query<Category> query = session.createQuery("from Category order by id", Category.class);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public long countAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery("select count(c) from Category c", Long.class);
        return query.getSingleResult();
    }

    public void update(Category category) {
        Session session = sessionFactory.getCurrentSession();
        session.update(category);
    }

    // Returns true if this category is still linked to one or more books
    public boolean hasBooks(int id) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "select count(b) from Book b where b.category.id = :id";
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("id", id);
        return query.getSingleResult() > 0;
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Category categoryToDelete = session.get(Category.class, id);
        if (categoryToDelete != null) {
            session.delete(categoryToDelete);
        }
    }
}
