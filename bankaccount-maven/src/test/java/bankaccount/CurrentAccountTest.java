package bankaccount;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CurrentAccountTest extends AbstractBankAccountTest<CurrentAccount> {

    @Override
    protected CurrentAccount createAccountWith(BigDecimal initialBalance) {
        // Default factory used by shared tests in AbstractBankAccountTest
        // (interest rate + overdraft size here are just “sensible defaults”)
        return new CurrentAccount(
                "CA-001",
                "Test User",
                initialBalance.doubleValue(),
                0.05,      // 5% annual interest
                200.00     // £200 overdraft limit
        );
    }

    @Test
    @DisplayName("Constructor should reject negative overdraft limit")
    void constructorRejectsNegativeOverdraftLimit() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CurrentAccount("CA-002", "Bob", 100.00, 0.05, -50.00);
        });
    }

    @Test
    @DisplayName("applyMonthlyUpdate should add one month of interest to positive balance")
    void monthlyUpdateAddsInterestOnPositiveBalance() {
        CurrentAccount acct = new CurrentAccount(
                "CA-003",
                "Charlie",
                1200.00,
                0.12,   // 12% annual → 1% per month
                300.00
        );

        // 1% of 1200 = 12.00 → new balance = 1212.00
        BigDecimal expected = new BigDecimal("1212.00");

        acct.applyMonthlyUpdate();

        assertEquals(0, expected.compareTo(acct.getBalance()),
                "Balance should increase by exactly 1% (monthly interest)");
    }

    @Test
    @DisplayName("Withdraw can dip into overdraft up to the limit")
    void withdrawUsesOverdraftWithinLimit() {
        CurrentAccount acct = new CurrentAccount(
                "CA-004",
                "Dana",
                100.00,
                0.00,
                50.00    // overdraft limit
        );

        // Total available = 100 + 50 = 150, withdrawing 120 is allowed.
        acct.withdraw(120.00);

        BigDecimal expected = new BigDecimal("-20.00");

        assertEquals(0, expected.compareTo(acct.getBalance()),
                "Balance should be -20.00 after dipping into overdraft");
    }

    @Test
    @DisplayName("Withdraw beyond balance + overdraft limit should throw")
    void withdrawBeyondOverdraftThrows() {
        CurrentAccount acct = new CurrentAccount(
                "CA-005",
                "Eve",
                100.00,
                0.00,
                50.00    // total available = 150
        );

        assertThrows(IllegalArgumentException.class,
                () -> acct.withdraw(200.00),
                "Withdrawing more than balance + overdraft should fail");
    }

    @Test
    @DisplayName("Check overdraft and balance are updated correctly after withdrawals")
    void checkOverdraftUpate() {
        CurrentAccount acct = createAccountWith(new BigDecimal("100.00"));
        acct.withdraw(110.00);
        acct.withdraw(20.00);

        assertEquals(new BigDecimal("30.00"), acct.getOverdraftUsed(), 
                "Overdraft used after test transactions should equal 30.00");
        assertEquals(new BigDecimal("-30.00"), acct.getBalance(),
                "Balance after test transactions should equal negative of overdraft (-30.00)");
    }

    /* Implement: 
     * a test for reducing overdraft via deposit;
     * a test for overdraft limits being reached across multiple transactions. 
     */
}
