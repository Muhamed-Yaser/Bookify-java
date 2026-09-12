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

    // Returns page number "page" (1-based) with "pageSize" authors per page
    public List<Author> findPage(int page, int pageSize) {
        Session session = sessionFactory.getCurrentSession();
        Query<Author> query = session.createQuery("from Author order by id", Author.class);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public long countAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery("select count(a) from Author a", Long.class);
        return query.getSingleResult();
    }

    public void update(Author author) {
        Session session = sessionFactory.getCurrentSession();
        session.update(author);
    }

    // Returns true if this author is still linked to one or more books
    public boolean hasBooks(int id) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "select count(b) from Book b join b.authors a where a.id = :id";
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("id", id);
        return query.getSingleResult() > 0;
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Author authorToDelete = session.get(Author.class, id);
        if (authorToDelete != null) {
            session.delete(authorToDelete);
        }
    }
}
