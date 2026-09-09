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

    public void update(Category category) {
        Session session = sessionFactory.getCurrentSession();
        session.update(category);
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Category categoryToDelete = session.get(Category.class, id);
        if (categoryToDelete != null) {
            session.delete(categoryToDelete);
        }
    }
}