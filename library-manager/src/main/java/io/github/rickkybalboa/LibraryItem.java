package io.github.rickkybalboa;

import java.time.LocalDate;

public abstract class LibraryItem {

    private final String id;
    protected final String title;
    private final ItemType itemType;
    protected boolean isBorrowed;
    protected LocalDate dateLastBorrowed;
    protected LocalDate dateDue;
    protected LocalDate dateLastReturned;
    protected enum BorrowStatus {
        BORROWED, RETURNED, ALREADY_BORROWED, NO_CHANGE
    }


    protected LibraryItem(String id,
        String title,
        ItemType itemType,
        boolean isBorrowed,
        LocalDate dateLastBorrowed,
        LocalDate dateDue,
        LocalDate dateLastReturned) {

        this.id = id;
        this.title = title;
        this.itemType = itemType;
        this.isBorrowed = isBorrowed;
        this.dateLastBorrowed = dateLastBorrowed;
        this.dateDue = dateDue;
    }

    protected LibraryItem(String id, String title, ItemType itemType) {
        this(id,title, itemType, false,null,null, null);
    }

    public String printAllInfo() {
        return toString();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public String getDueDate() {
        if (dateDue == null) {
            return "Item is not due for return (" + getBorrowStatus() + ").";
        }
        else {
            return dateDue.toString();
        }
    }

    public BorrowStatus getBorrowStatus() {
        if (this.isBorrowed) {
            return BorrowStatus.ALREADY_BORROWED;
        }
        else return BorrowStatus.RETURNED;
    }

    @Override
    public String toString() {
        return "=====Item Information====" + 
	    	   "\nItem ID: " + id +
	           "\nItem Title: " + title +
               "\nItem Type:" + getItemType() +
               "\nBorrow Status" + getBorrowStatus() +
	           "\nDate Last Borrowed: " + dateLastBorrowed +
               "\nDate Last Returned:" + dateLastReturned +
               "\nDue Date:" + getDueDate() +
			   "\n=========================";
    }

    public BorrowStatus borrow() {
    if (this.isBorrowed) {
        return BorrowStatus.ALREADY_BORROWED;      
    } else {
    this.isBorrowed = true;
    this.dateLastBorrowed = LocalDate.now();
    this.setDueDate();
    return BorrowStatus.BORROWED;
    }
}

public BorrowStatus returnItem() {
    if (!this.isBorrowed) {
        return BorrowStatus.ALREADY_BORROWED;
    }
    else {
    this.isBorrowed = false;
    this.dateDue = null;   
    this.dateLastReturned = LocalDate.now(); 
    return BorrowStatus.RETURNED;
    }
}

    // abstract methods for loan duration, fees, renew rules.

    public abstract void setDueDate();

    public abstract void setLateFee();

    public abstract void renewRule();


}


