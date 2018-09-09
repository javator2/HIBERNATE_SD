package com.sda.hibernate;

import com.sda.hibernate.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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

        Session session = getSesion();

        session.save(book);

        session.close();

    }


}
