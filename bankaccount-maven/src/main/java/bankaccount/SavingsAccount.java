package bankaccount;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SavingsAccount extends BankAccount {
    

    private final BigDecimal annualInterestRate;

    public SavingsAccount(String accountNumber,
           String ownerName, 
           double initialBalance, 
           double annualInterestRate) {

         // Calls the appropriate BankAccount constructor
        super(accountNumber, ownerName, initialBalance);


        // Validates and stores interest rate.
        if (annualInterestRate < 0)
            throw new IllegalArgumentException("Interest rate cannot be negative.");

        this.annualInterestRate =
            BigDecimal.valueOf(annualInterestRate).setScale(4, RoundingMode.HALF_UP);
}   

    public BigDecimal getAnnualInterestRate() {
        return annualInterestRate;
    }
    // If you misspell the method name or get its parameters wrong, @Override makes the compiler tell you immediately. Otherwise Java will just think it's a brand-new method.
    @Override
    public void applyMonthlyUpdate() {
        BigDecimal monthlyRate =
            annualInterestRate.divide(BigDecimal.valueOf(12),
                              10,                // calculation scale, prevents infinitely long decimals. 10 dp here, before rounding.
                              RoundingMode.HALF_UP);
            
        BigDecimal interest = getBalance().multiply(monthlyRate);

        addToBalance(interest);
    }

    
    
}