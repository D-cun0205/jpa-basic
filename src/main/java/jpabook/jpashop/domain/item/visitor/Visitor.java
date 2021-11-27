package jpabook.jpashop.domain.item.visitor;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Movie;

public interface Visitor {

    void visit(Book book);
    void visit(Album album);
    void visit(Movie movie);
}
