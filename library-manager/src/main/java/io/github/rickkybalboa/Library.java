package io.github.rickkybalboa;

import java.time.LocalDate;
import java.util.TreeMap;
import java.util.ArrayList;
//import java.util.List;
import java.util.Map;

import io.github.rickkybalboa.LibraryItem.BorrowStatus;

public abstract class Library{

/* private List<LibraryItem> allItems;
private List<LibraryUser> allUsers;
private List<LibraryItem> borrowedItems;
 */
 /* public BorrowStatus borrowItem(LibraryItem item) {
    if (item.isBorrowed) {
        return BorrowStatus.ALREADY_BORROWED;      
    } else {
    item.isBorrowed = true;
    item.dateLastBorrowed = LocalDate.now();
    item.setDueDate();
    return BorrowStatus.BORROWED;
    }
 } */

    private static Map<String, LibraryItem> itemsById = new TreeMap<>();
    
   /*  public static boolean addItem(LibraryItem newLibItem) {
        if (allLibItems.containsKey(newLibItem.getId())) {
            throw new IllegalStateException("Item already exists");
        }

        allLibItems.put(newLibItem.getId(),newLibItem);

        String itemType = newLibItem.getItemType();

        if(newLibItem.getClass() == Book.class) {
        //    addBook(newLibItem);
        }
        switch(itemType) {
            //case "BOOK" -> addBook(newLibItem);
            case "DVD" ->  allMagazines.put(newLibItem.getId(),newLibItem);
            case "MAGAZINE" -> allMagazines.put(newLibItem.getId(), newLibItem);
        }
        return true;
    } */

    

    public BorrowStatus returnItem(LibraryItem item) {
    if (!item.isBorrowed) {
        return BorrowStatus.NO_CHANGE;
    }
    else {
    item.isBorrowed = false;
    item.dateDue = null;   
    item.dateLastReturned = LocalDate.now(); 
    return BorrowStatus.RETURNED;
    }
}

    public String printItemInfo(LibraryItem item) {
        return item.toString();
    }
}
