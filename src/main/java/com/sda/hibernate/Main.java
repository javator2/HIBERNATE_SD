package com.sda.hibernate;

import com.sda.hibernate.entity.Book;
import com.sda.hibernate.entity.Category;
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

        Category category = new Category("Programowanie");
        Category category1 = new Category("Programowanie JAVA");




        Session session = getSesion();

        Transaction tx = session.getTransaction();

        tx.begin();
        session.save(book);
        session.save(book1);
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


        Book book3 = session.byId(Book.class).getReference(2);
        System.out.println(book3);

        tx.begin();
        session.delete(book3);
        tx.commit();


        session.close();



    }


}
