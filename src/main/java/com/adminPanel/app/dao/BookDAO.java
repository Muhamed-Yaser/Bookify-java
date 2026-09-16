package com.adminPanel.app.dao;

import org.hibernate.Hibernate;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import com.adminPanel.app.model.Author;
import com.adminPanel.app.model.Book;
import com.adminPanel.app.model.BookDetails;
import com.adminPanel.app.model.Category;

@Transactional // Enables automatic Spring transaction management
public class BookDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        reattachAssociations(session, book);
        session.save(book);
    }

    // Re-fetches Category and each Author by ID from the current Hibernate session,
    // guaranteeing they are managed entities before the Book is saved/updated.
    // This avoids TransientObjectException regardless of how the objects arrived from the form.
    private void reattachAssociations(Session session, Book book) {
        if (book.getCategory() != null && book.getCategory().getId() != null) {
            book.setCategory(session.get(Category.class, book.getCategory().getId()));
        }
        if (book.getAuthors() != null) {
            List<Author> managedAuthors = new ArrayList<>();
            for (Author a : book.getAuthors()) {
                if (a != null && a.getId() != null) {
                    Author managed = session.get(Author.class, a.getId());
                    if (managed != null) {
                        managedAuthors.add(managed);
                    }
                }
            }
            book.setAuthors(managedAuthors);
        }
    }

    public Book findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);

        // Tell Hibernate to load the lazy data while the DB session is still open
        if (book != null) {
            Hibernate.initialize(book.getAuthors());
            Hibernate.initialize(book.getCategory());
            Hibernate.initialize(book.getBookDetails());
        }

        return book;
    }

    public List<Book> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Book> query = session.createQuery("from Book", Book.class);
        return query.getResultList();
    }

    // Returns page number "page" (1-based) with "pageSize" books per page
    public List<Book> findPage(int page, int pageSize) {
        Session session = sessionFactory.getCurrentSession();
        Query<Book> query = session.createQuery("from Book order by id", Book.class);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public long countAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery("select count(b) from Book b", Long.class);
        return query.getSingleResult();
    }

    // Searches by book title OR ISBN (case-insensitive, partial match), with pagination
    public List<Book> search(String keyword, int page, int pageSize) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "select distinct b from Book b left join b.bookDetails d " +
                "where lower(b.title) like :kw or lower(d.isbn) like :kw " +
                "order by b.id";
        Query<Book> query = session.createQuery(hql, Book.class);
        query.setParameter("kw", "%" + keyword.toLowerCase() + "%");
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public long countSearch(String keyword) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "select count(distinct b) from Book b left join b.bookDetails d " +
                "where lower(b.title) like :kw or lower(d.isbn) like :kw";
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("kw", "%" + keyword.toLowerCase() + "%");
        return query.getSingleResult();
    }

    // Finds a BookDetails row by ISBN, used to enforce ISBN uniqueness with a friendly error message
    public BookDetails findBookDetailsByIsbn(String isbn) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from BookDetails where isbn = :isbn";
        Query<BookDetails> query = session.createQuery(hql, BookDetails.class);
        query.setParameter("isbn", isbn);
        List<BookDetails> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public void update(Book book) {
        Session session = sessionFactory.getCurrentSession();
        reattachAssociations(session, book);
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