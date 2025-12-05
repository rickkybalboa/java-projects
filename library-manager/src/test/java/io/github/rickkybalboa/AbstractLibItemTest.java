package io.github.rickkybalboa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
//import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

import java.time.*;


public abstract class AbstractLibItemTest<T extends LibraryItem> {

// Abstract factory method. Each concrete class overrides this method.
    protected abstract T createItemWith(String id);

    @Test
    void borrowSetsCorrectDueDate() {
        T libItem = createItemWith("001");
        libItem.borrow();
        String expectedDate = LocalDate.now().plusDays(libItem.borrowingRule.getLoanPeriodDays()).toString();
        String actualDate = libItem.getDueDate();

        assertEquals(expectedDate, actualDate,
            "Borrow function should set correct Due Date");
    }

    @Test
    void renewalUpdatesCounter(){
        T libItem = createItemWith("001");
        libItem.borrow();
        libItem.renew();
        libItem.renew();

        assertEquals(2, libItem.renewalsSoFar,
            "Renewals should update renewal counter"    
        );
    }

    @Test
    @DisplayName("Renewal should be rejected if renewal limit exceeded")
    void renewalRejectsExcessRenewal(){
        T libItem = createItemWith("001");
        libItem.renewalsSoFar = libItem.borrowingRule.getRenewalLimit();

        assertEquals(false, libItem.renew(),
    "Renewal should be rejected if over renewal limit.");
    }
}


