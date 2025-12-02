package bankaccount;

import static bankaccount.Helpers.roundToScale;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrentAccount extends BankAccount {
    
    private BigDecimal overdraftLimit;
    private final BigDecimal annualInterestRate;
    private BigDecimal overdraftUsed = BigDecimal.valueOf(0,2);

    public CurrentAccount(String accountNumber,
           String ownerName, 
           double initialBalance, 
           double annualInterestRate,
           double overdraftLimit) {

        super(accountNumber,ownerName,initialBalance);

         // Validates and stores interest rate.
        if (annualInterestRate < 0)
            throw new IllegalArgumentException("Interest rate cannot be negative.");

        this.annualInterestRate =
            Helpers.roundToScale(annualInterestRate, 4);

        // Validates and stores overdraft limit.
        if (overdraftLimit < 0)
            throw new IllegalArgumentException("Overdraft limit cannot be negative.");

        this.overdraftLimit = roundToScale(overdraftLimit,2 );
    }

    public BigDecimal getAnnualInterestRate() {
        return annualInterestRate;
    }

    @Override
    public void applyMonthlyUpdate() {
        BigDecimal monthlyRate =
            annualInterestRate.divide(BigDecimal.valueOf(12),
                              10,                // calculation scale, prevents infinitely long decimals. 10 dp here, before rounding.
                              RoundingMode.HALF_UP);
            
        BigDecimal interest = getBalance().multiply(monthlyRate);

        addToBalance(interest);
    }


    // Withdrawals over account balance will add to overdraftUsed.
    // If withdrawal greater than allowed balance + overdraft, method will throw exception.
        @Override
        public void withdraw(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");

        BigDecimal amt = roundToScale(amount,2);
        BigDecimal balance = getBalance();

        // Check for sufficient funds:
        BigDecimal available = getAvailableFunds();
        if (amt.compareTo(available) > 0) {
            throw new IllegalArgumentException("Insufficient funds.");
        }

        //Apply the withdrawal:
        BigDecimal newBalance = balance.subtract(amt);

        // If balance below zero, update overdraftUsed:
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
           // BigDecimal newlyUsedOverdraft = newBalance.abs();     // OLD METHOD DID NOT ADD *ONLY* NEW OVERDRAFT USED!
            overdraftUsed = newBalance.abs();  // Overdraft is always |negative balance| so this is all that's needed.
        }

        // Set new balance:
        setBalance(newBalance);
        }
        
        public BigDecimal getAvailableFunds() {
            BigDecimal remainingOverdraft = overdraftLimit.subtract(overdraftUsed);
            BigDecimal fundsAvail = getBalance().add(remainingOverdraft);
            return fundsAvail;
        }

        public BigDecimal getOverdraftUsed() {
            return overdraftUsed;
        }
    }

