package bankaccount;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SavingsAccountTest extends AbstractBankAccountTest<SavingsAccount> {

    @Override
    protected SavingsAccount createAccountWith(BigDecimal initialBalance) {
        return new SavingsAccount(
                "001",
                "Alice",
                initialBalance.doubleValue(),
                0.05
        );
    }

    @Test
    @DisplayName("Constructor should store interest rate rounded to 4 decimal places")
    void constructorStoresRoundedInterestRate() {
        SavingsAccount acct = new SavingsAccount(
                "001", "Alice", 100.00, 0.123456);

        BigDecimal expected = new BigDecimal("0.1235");   // rounded HALF_UP to 4dp
        assertEquals(expected, acct.getAnnualInterestRate(),
                "Interest rate should be rounded to 4 decimal places");
    }

    @Test
    @DisplayName("Constructor should reject negative interest rate")
    void constructorRejectsNegativeInterestRate() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SavingsAccount("002", "Bob", 50.00, -0.01);
        });
    }

    @Test
    @DisplayName("applyMonthlyUpdate should correctly add one month of interest")
    void monthlyUpdateAddsCorrectInterest() {

        // Initial data
        SavingsAccount acct = new SavingsAccount(
                "003", "Charlie", 1200.00, 0.12);  // 12% annual interest

        // Monthly rate = 0.12 / 12 = 0.01 (1%)
        // Monthly interest on 1200 = 12.00
        BigDecimal expected = new BigDecimal("1212.00");

        acct.applyMonthlyUpdate();   // APPLY INTEREST

        assertEquals(
                0, expected.compareTo(acct.getBalance()),
                "Balance should increase by exactly 1% (monthly interest)");
    }

    @Test
    @DisplayName("Monthly interest calculation uses correct precision and rounding")
    void monthlyInterestPrecisionTest() {

        // Use awkward interest rate to test precision & rounding path
        SavingsAccount acct = new SavingsAccount(
                "004", "Dana", 1000.00, 0.0754321);

        /*
            annual = 0.0754 (rounded to 4dp)
            monthly = annual / 12
                    = 0.0754 / 12
                    = 0.0062833333... (HALF_UP to 10dp) = 0.0062833333

            interest = 1000 * monthly = 6.28333333
            new balance = 1006.28333333
         */

        acct.applyMonthlyUpdate();

        BigDecimal expected = new BigDecimal("1006.28");

        assertEquals(
                0, expected.compareTo(acct.getBalance()),
                "Balance should be rounded to 2dp.");
    }
}
