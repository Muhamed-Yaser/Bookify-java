package com.adminPanel.app.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import com.adminPanel.app.model.Book;

@Transactional // Enables automatic Spring transaction management
public class BookDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    public Book findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }

    public List<Book> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Book> query = session.createQuery("from Book", Book.class);
        return query.getResultList();
    }

    public void update(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.update(book);
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book bookToDelete = session.get(Book.class, id);
        if (bookToDelete != null) {
            session.delete(bookToDelete);
        }
    }
}