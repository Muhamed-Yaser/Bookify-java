package com.adminPanel.app.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.adminPanel.app.model.Category;

public class CategoryDAO {

    private SessionFactory sessionFactory;

    public CategoryDAO() {
    }

    public CategoryDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Category category) {
        Session session = sessionFactory.getCurrentSession();
        session.save(category);
    }

    public Category findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return (Category) session.get(Category.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Category> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Category").list();
    }

    public void update(Category category) {
        Session session = sessionFactory.getCurrentSession();
        session.update(category);
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Category categoryToDelete = (Category) session.get(Category.class, id);
        if (categoryToDelete != null) {
            session.delete(categoryToDelete);
        }
    }
}