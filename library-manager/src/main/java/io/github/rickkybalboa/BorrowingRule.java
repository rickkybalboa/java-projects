package io.github.rickkybalboa;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface BorrowingRule {


    int getLoanPeriodDays();

    BigDecimal calculateLateFee(LocalDate dueDate, LocalDate returnDate);

    boolean canRenew(int renewalsSoFar);
}

