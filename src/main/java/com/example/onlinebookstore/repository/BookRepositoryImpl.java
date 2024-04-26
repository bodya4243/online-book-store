package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.exception.EntityNotFoundException;
import com.example.onlinebookstore.model.Book;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final SessionFactory sessionFactory;

    @Override
    public Book save(Book book) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
            return book;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert book " + book, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Book> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all books", e);
        }
    }
    
    @Override
    public Optional<Book> findById(Long id) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Book book = session.find(Book.class, id);
            transaction.commit();
            return Optional.ofNullable(book);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new EntityNotFoundException("Can't get book by id:" + id, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
