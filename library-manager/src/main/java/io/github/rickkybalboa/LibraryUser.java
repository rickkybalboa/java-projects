package io.github.rickkybalboa;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Logic for creating and tracking states of Library Users. 
// Should implement: UserID, Name, Items Borrowed

public class LibraryUser {
    
    private final String userId;
    private final String userName;
    protected int numberItemsBorrowed = 0;
    protected BigDecimal lateFeeAccrued = BigDecimal.valueOf(0.00);

    LibraryUser(String userID, String userName) {
        this.userId = userID;
        this.userName = userName;
    }
}
