package pl.marta.kopp.persistence;


import pl.marta.kopp.domain.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class BookStorageJpa implements BookStorage {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("eBookPersistence");
    private EntityManager entityManager = factory.createEntityManager();


    public void add(Book book) {
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.getTransaction().commit();

    }

    @Override
    public List getAll() {
        return entityManager.createQuery("FROM Book").getResultList();
    }

    public List getPresentBooks(){
        return entityManager.createQuery("FROM Book b WHERE b.borrow=false",Book.class).getResultList();
    }

    @Override
    public Book getById(long id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    public Boolean isExists(long id) {
        Query query = entityManager.createQuery("FROM Book b WHERE b.id=:id ");
        query.setParameter("id", id);
        return !(query.getResultList().isEmpty());
    }

    @Override
    public Boolean isExists(String isbn) {
        Query query = entityManager.createQuery("FROM Book b WHERE b.isbn=:isbn ");
        query.setParameter("isbn", isbn);
        return !(query.getResultList().isEmpty());
    }

    @Override
    public void delete(long id) {
        entityManager.getTransaction().begin();
        Book book = entityManager.find(Book.class, id);
        entityManager.remove(book);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(Book book) {
        entityManager.getTransaction().begin();
        entityManager.merge(book);
        entityManager.getTransaction().commit();
    }


    @Override
    public void setBorrow(long id,boolean condition) {
        entityManager.getTransaction().begin();
        Book book = entityManager.find(Book.class, id);
        book.setBorrow(condition);
        entityManager.merge(book);
        entityManager.getTransaction().commit();
    }
}