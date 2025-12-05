package io.github.rickkybalboa;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class BookBorrowingRule implements BorrowingRule {

    int renewalLimit = 2;
    int loanPeriod = 21;

    @Override
    public int getLoanPeriodDays() {
        return loanPeriod;
    }
    
    @Override
    public int getRenewalLimit() {
        return renewalLimit;
    }

    // Delete this?
    public void setRenewalLimit(int newLimit) {
        renewalLimit = newLimit;
    }

    @Override
    public BigDecimal calculateLateFee(LocalDate dueDate, LocalDate returnDate) {

        if(!returnDate.isAfter(dueDate)) {
            throw new IllegalStateException("Return is not late.");
        }

        var late = ChronoUnit.DAYS.between(returnDate, dueDate);
        BigDecimal lateFee = Helpers.roundToScale(BigDecimal.valueOf(late*0.25),2 );
        return lateFee;
    }

    @Override
    public boolean canRenew(int renewalsSoFar) {
        if (renewalsSoFar >= renewalLimit) {
            return false;
        } else {
            return true;
        }
    }

    
}
