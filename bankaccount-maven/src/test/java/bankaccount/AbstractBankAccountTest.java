package bankaccount;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractBankAccountTest<T extends BankAccount> {
    
    // Abstract factory method. Each concrete class overrides this method.
    protected abstract T createAccountWith(BigDecimal initialBalance);

    @Test
    void deposit_increases_balance() {
        T account = createAccountWith(new BigDecimal("100.00"));

        account.deposit(50.00);

        assertEquals(new BigDecimal("150.00"),account.getBalance());
    }

    @Test
    void withdraw_decreases_balance() {
        T account = createAccountWith(new BigDecimal("150.00"));
        BigDecimal initialBalance = account.getBalance();
        BigDecimal withdrawAmt = new BigDecimal("50.00");
        account.withdraw(50.00);
        BigDecimal newBalance = initialBalance.subtract(withdrawAmt);

        assertEquals(newBalance, account.getBalance());
    }

    @Test
    // We use assertThrows to verify that calling deposit(-10.00) correctly throws an
    // IllegalArgumentException. The second argument is a lambda expression: () -> acct.deposit(-10.00)
    // This creates a small "executable" block of code that JUnit will run. Instead of letting the
    // exception crash the test immediately, JUnit captures it and checks that it is the expected type.

    void deposit_rejects_negative_amount() {
        T account = createAccountWith(new BigDecimal("100.00"));

        assertThrows(IllegalArgumentException.class, () -> account.deposit(-10.00) );
    }


    /* constructor_rejects_null_name

        balance_never_goes_negative_without_overdraft */

}
