package io.github.rickkybalboa;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class BookBorrowingRule implements BorrowingRule {

    @Override
    public int getLoanPeriodDays() {
        return 21;
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
        if (renewalsSoFar > 2) {
            return false;
        } else {
            return true;
        }
    }

    
}
