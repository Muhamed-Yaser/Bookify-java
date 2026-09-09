package com.adminPanel.app.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import com.adminPanel.app.model.Author;

@Transactional
public class AuthorDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Author author) {
        Session session = sessionFactory.getCurrentSession();
        session.save(author);
    }

    public Author findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Author.class, id);
    }

    public List<Author> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Author> query = session.createQuery("from Author", Author.class);
        return query.getResultList();
    }

    public void update(Author author) {
        Session session = sessionFactory.getCurrentSession();
        session.update(author);
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Author authorToDelete = session.get(Author.class, id);
        if (authorToDelete != null) {
            session.delete(authorToDelete);
        }
    }
}