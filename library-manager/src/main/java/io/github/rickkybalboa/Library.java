package io.github.rickkybalboa;

import java.time.LocalDate;
//import java.util.List;

import io.github.rickkybalboa.LibraryItem.BorrowStatus;

public class Library{

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
