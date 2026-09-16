package com.adminPanel.app.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.adminPanel.app.model.Author;

public class AuthorDAO {

    private SessionFactory sessionFactory;

    public AuthorDAO() {
    }

    public AuthorDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Author author) {
        Session session = sessionFactory.getCurrentSession();
        session.save(author);
    }

    public Author findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return (Author) session.get(Author.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Author> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Author").list();
    }

    public void update(Author author) {
        Session session = sessionFactory.getCurrentSession();
        session.update(author);
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Author authorToDelete = (Author) session.get(Author.class, id);
        if (authorToDelete != null) {
            session.delete(authorToDelete);
        }
    }
}