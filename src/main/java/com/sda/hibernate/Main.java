package com.sda.hibernate;

import com.sda.hibernate.entity.Author;
import com.sda.hibernate.entity.Book;
import com.sda.hibernate.entity.Category;
import com.sda.hibernate.entity.Publisher;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        Author author = new Author("Jan", "Kowalski");
        Author author1 = new Author("Mariusz", "Tchorz");
        Author author2 = new Author("John", "Malecki");



        Book book = new Book();
        book.setIsbn("3452-2678");
        book.setTitle("Nowe");

        Set<Author> authorsSet = new HashSet<Author>();
        authorsSet.add(author);
        authorsSet.add(author2);




        Book book1 = new Book("nowa ksiazka", "2324-2345");
        book1.setAuthors(authorsSet);
        Book book2 = new Book("Proframowanie", "2324-2356");
        Book book3 = new Book("Php junior", "isbn" );

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
            System.out.println(b.getTitle());
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

        Book book4 = new Book("test", "test");
        book4.setCategory(category);

        tx.begin();
        session.save(book4);
        tx.commit();

        Publisher publisher = new Publisher("PWN", "ul.Kosciuszki 168","Warszawa");
        Publisher publisher1 = new Publisher("Nowa Era", "ul.17-eg Stycznia", "Krakow");

        Book book5 = new Book("JAVA od podstaw", "7679-2442");
        book5.setPublisher(publisher);
        book5.setAuthors(new HashSet<Author>(Arrays.asList(new Author("Mariusz", "Kot"))));

        book1.setPublisher(publisher);

        book2.setAuthors(new HashSet<Author>(Arrays.asList(new Author("Tadeusz", "Mroz"))));
        book2.setPublisher(publisher1);

        book3.setPublisher(publisher1);
        book3.setAuthors(new HashSet<Author>(Arrays.asList(new Author("John", "Pat"))));

        book4.setPublisher(publisher1);
        book4.setCategory(category1);
        book4.setAuthors(new HashSet<Author>(Arrays.asList(new Author("Mariusz", "Kot"))));


        tx.begin();
        session.save(book1);
        session.save(book2);
        session.save(book3);
        session.save(book4);
        session.save(book5);
        tx.commit();

        List<Book> books =
                session.createQuery("FROM " + Book.class.getName()).list();

        for (Book b: books){
            System.out.println("Tytul " + b.getTitle());

            for (Author a: b.getAuthors()){
                System.out.println("Author: " + a.getName());
            }
        }

        session.close();



    }


}
