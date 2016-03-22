package dao.BookServiceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import dao.BookService;
import entities.Book;
import entities.User;

public class BookServiceImpl implements BookService{
    
//    protected BookServiceImpl(){};
    
    public List<Book> getAvailableBooks(){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("LibraryJPAEM");
        EntityManager entitymanager = emfactory.createEntityManager( );
        List <Book> books = entitymanager.createQuery(
                "SELECT b FROM Book b WHERE b.id NOT IN (SELECT t.book FROM Transaction t WHERE t.dateOfReturn IS NULL)")
                .getResultList(); 
        entitymanager.close( );
        emfactory.close();
        return books;
    }
    
    public Book getBook(int id){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("LibraryJPAEM");     
        EntityManager entitymanager = emfactory.createEntityManager( );
        Book book = (Book)entitymanager.createQuery(
                "SELECT u FROM Book u WHERE u.id = :bookId")
                .setParameter("bookId", id)
                .getSingleResult(); 
        entitymanager.close( );
        emfactory.close( );
        return book;

        }
    public List<Book> getAll(){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("LibraryJPAEM");
        EntityManager entitymanager = emfactory.createEntityManager( );
        List <Book> books = entitymanager.createQuery(
                "SELECT u FROM Book u")
                .getResultList(); 
        entitymanager.close( );
        emfactory.close( );
        return books;

        }

    @Override
    public void createBook(Book book) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("LibraryJPAEM");
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin( );
        entitymanager.persist( book );
        entitymanager.getTransaction( ).commit( );
        entitymanager.close( );
        emfactory.close( );
        
    }

    @Override
    public void deleteBook(int id) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("LibraryJPAEM");
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin( );
        Book book = (Book)entitymanager.createQuery(
                "SELECT u FROM Book u WHERE u.id = :bookId")
                .setParameter("bookId", id)
                .getSingleResult();
        entitymanager.remove( book );
        entitymanager.getTransaction( ).commit( );
        entitymanager.close( );
        emfactory.close( );
        
    }

    @Override
    public void updateBook(Book book) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("LibraryJPAEM");
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin( );
        Book fetchedBook = (Book)entitymanager.createQuery(
                "SELECT u FROM Book u WHERE u.id = :bookId")
                .setParameter("bookId", book.getId())
                .getSingleResult();
        fetchedBook.setAuthor(book.getAuthor());
        fetchedBook.setIsbn(book.getIsbn());
        fetchedBook.setState(book.getState());
        fetchedBook.setTitle(book.getState());
        entitymanager.getTransaction( ).commit( );
        entitymanager.close( );
        emfactory.close( );
    }
    
}
