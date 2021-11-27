package jpabook.jpashop.domain.item.visitor;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Movie;

public class PrintVisitor implements Visitor {

    @Override
    public void visit(Book book) {
        System.out.println("book.class = " + book.getClass());
        System.out.println("[PrintVisitor] [제목:" + book.getName() + " 저자:" + book.getAuthor() + "]");
    }

    @Override
    public void visit(Album album) {

    }

    @Override
    public void visit(Movie movie) {

    }
}
