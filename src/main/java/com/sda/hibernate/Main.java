package com.sda.hibernate;

import com.sda.hibernate.entity.Book;
import com.sda.hibernate.entity.Category;
import com.sda.hibernate.entity.Publisher;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class Main {

    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            sessionFactory = configuration.buildSessionFactory();
        }catch (Throwable ex){
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Session getSesion() {
        return sessionFactory.openSession();
    }

    public static void main(String[] args) {

        Book book = new Book();
        book.setAuthor("Jan");
        book.setIsbn("3452-2678");
        book.setTitle("Nowe");


        Book book1 = new Book("nowa ksiazka", "2324-2345", "Jan Brzechwa");
        Book book2 = new Book("Proframowanie", "2324-2356", "Frank Tesla");
        Book book3 = new Book("Php junior", "isbn" , "John Smieszny");

        Category category = new Category("Programowanie");
        Category category1 = new Category("Programowanie JAVA");

        Session session = getSesion();

        Transaction tx = session.getTransaction();

        tx.begin();
        session.save(book);
        session.save(book1);
        session.save(book3);
        tx.commit();

        tx.begin();
        session.save(book2);
        tx.commit();

        tx.begin();
        session.save(category);
        session.save(category1);
        tx.commit();

        List<Book> bookList =
                session.createQuery("from " + Book.class.getName()).list();
        for(Book b: bookList){
            System.out.println(b.getAuthor() + " " + b.getTitle());
        }

//        CriteriaBuilder builder = session.getCriteriaBuilder();
//        CriteriaBuilder<Book> bookCriteriaBuilder = builder.createQuery(Book.class).;
//        for (Book b : bookCriteriaBuilder){
//            System.out.println(b.getAuthor());
//        }


//        Book book3 = session.byId(Book.class).getReference(2);
//        System.out.println(book3);
//
//        tx.begin();
//        session.delete(book3);
//        tx.commit();

        Book book4 = new Book("test", "test", "test");
        book4.setCategory(category);

        tx.begin();
        session.save(book4);
        tx.commit();

        Publisher publisher = new Publisher("PWN", "ul.Kosciuszki 168","Warszawa");
        Publisher publisher1 = new Publisher("Nowa Era", "ul.17-eg Stycznia", "Krakow");
        Book book5 = new Book("JAVA od podstaw", "7679-2442", "Maciej Tchurz");
        book5.setPublisher(publisher);
        book1.setPublisher(publisher);
        book2.setPublisher(publisher1);
        book3.setPublisher(publisher1);
        book4.setPublisher(publisher1);
        book4.setCategory(category1);

        tx.begin();
        session.save(book1);
        session.save(book2);
        session.save(book3);
        session.save(book4);
        session.save(book5);
        tx.commit();



        session.close();



    }


}
