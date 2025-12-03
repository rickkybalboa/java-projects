package io.github.rickkybalboa;

import java.lang.UnsupportedOperationException;

public class Book extends LibraryItem {

//private static final String itemType = "Book"; // Implement enum
private String genre;

    public Book(String id, String title, String genre) {

        super(id, title, ItemType.BOOK);
    }

    public void getGenre() {
        System.out.println(genre);
    }

    @Override
    public void renewRule() {
        // TODO: Implement
        throw new UnsupportedOperationException("Not yet implemented.");       
    }

    @Override
    public void setLateFee() {
        // TODO: Implement
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    public void setDueDate() {

    }


    
}
