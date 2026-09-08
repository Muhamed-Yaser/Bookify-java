package com.adminPanel.app.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.adminPanel.app.model.Book;

public class BookDAO {

    private SessionFactory sessionFactory;

    public BookDAO() {
    }

    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(book);
    }

    public Book findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return (Book) session.get(Book.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Book> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Book").list();
    }

    public void update(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(book);
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book bookToDelete = (Book) session.get(Book.class, id);
        if (bookToDelete != null) {
            session.delete(bookToDelete);
        }
    }
}