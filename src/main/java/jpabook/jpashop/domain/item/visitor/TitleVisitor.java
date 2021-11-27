package jpabook.jpashop.domain.item.visitor;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Movie;

public class TitleVisitor implements Visitor {

    private String title;

    public String getTitle() {
        return title;
    }

    @Override
    public void visit(Book book) {
        title = "[제목:" + book.getName() + " 저자:" + book.getAuthor() + "]";
    }

    @Override
    public void visit(Album album) {

    }

    @Override
    public void visit(Movie movie) {

    }
}
