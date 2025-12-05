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
    protected BorrowingRule borrowingRule;
    protected int renewalsSoFar = 0;


    protected LibraryItem(String id,
        String title,
        ItemType itemType,
        BorrowingRule borrowingRule    
    ) {

        this.id = id;
        this.title = title;
        this.itemType = itemType;
        this.borrowingRule = borrowingRule;
        
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getItemType() {
        return itemType.toString();
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

    public boolean borrow() {
        if(isBorrowed) return false;

        LocalDate todayDate = LocalDate.now();
        dateDue = todayDate.plusDays(borrowingRule.getLoanPeriodDays());
        isBorrowed = true;
        return true;
    }

    public boolean returnItem() {
        if(!isBorrowed) return false;

        dateDue = null;
        isBorrowed = false;
        return true;
    }

      public boolean renew() {
        if (!isBorrowed) return false;

        if(!borrowingRule.canRenew(renewalsSoFar)) {
            return false;
        }
        LocalDate todayDate = LocalDate.now();
        dateDue = todayDate.plusDays(borrowingRule.getLoanPeriodDays());
        renewalsSoFar++;
        return true;
    }

    @Override
    public String toString() {
        return "=====Item Information====" + 
	    	   "\nItem ID: " + id +
	           "\nItem Title: " + title +
               "\nItem Type:" + getItemType() +
               "\nBorrow Status" + getBorrowStatus() +
               "\nDue Date:" + getDueDate() +
			   "\n=========================";
    }


    // abstract methods for loan duration, fees, renew rules.

    public abstract void setLateFee();

  


}


