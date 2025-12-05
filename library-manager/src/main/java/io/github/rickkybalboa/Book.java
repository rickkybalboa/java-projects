package io.github.rickkybalboa;

import java.lang.UnsupportedOperationException;

import java.time.LocalDate;


public class Book extends LibraryItem {

private String author;
private String genre;


    public Book(String id, String author, String title, String genre) {

        super(id, title, ItemType.BOOK, new BookBorrowingRule());

        this.author = author;
        this.genre = genre;
    }


    public void getAuthor() {
        System.out.println(author);
    }

    public void getGenre() {
        System.out.println(genre);
    }


    @Override
    public void setLateFee() {
        throw new UnsupportedOperationException("Not yet implemented.");
    }


 /*    @Override
    public ItemType getItemType() {
        return ItemType.BOOK;
    } */
}

