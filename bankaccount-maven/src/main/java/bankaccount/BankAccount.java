/*
Model a simple bank account system that demonstrates encapsulation, data protection, and controlled access to behavior.
The program should simulate realistic account operations while maintaining a clean separation between internal state and external interaction.

What you’ll see:
How getters/setters act as gates, not just syntax noise — and how small, focused methods define an API boundary.
 */
package bankaccount;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class BankAccount {
	
	private final String accountNumber;  // <-- field
	private final String ownerName;
	protected BigDecimal balance;  // use 'BigDecimal' for money. Using 'double' for currency leads to rounding errors, precision loss, and floating-point drift.
	

	/*
	Keep constructor because subclasses need a way to initialise inherited fields.
	
	Protected: Prevents external code from instantiating BankAccount
	Allows subclasses like SavingsAccount to call it
	Does not allow external code access.
	 */
	protected BankAccount(String accountNumber, String ownerName, double initialBalance){
		if(initialBalance < 0)
			throw new IllegalArgumentException("Initial balance cannot be negative.");
		this.accountNumber = accountNumber;  // <-- field = parameter
		this.ownerName = ownerName;
		this.balance = BigDecimal.valueOf(initialBalance).setScale(2, RoundingMode.HALF_UP);

		
	}
	
	// Constructors should have consistent visibility:
	protected BankAccount(String accountNumber, String ownerName){
		
		this(accountNumber, ownerName, 0);  
		//Without using "this(...)", you'd have to write initialisation logic twice, including the fields. Centralise initiation logic while avoiding duplicate code.
		
	}
	
	
	public void deposit(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");

		// Ensure scaling to 2dp is consistent:
        balance = balance
				.add(BigDecimal.valueOf(amount)
				.setScale(2, RoundingMode.HALF_UP));
    }
	
	public void withdraw(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");

        BigDecimal amt = BigDecimal.valueOf(amount).setScale(2,RoundingMode.HALF_UP);

        if (amt.compareTo(balance) > 0)
            throw new IllegalArgumentException("Insufficient funds.");

        balance = balance.subtract(amt);
    }

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal amount) {
		balance = amount;
	}
	
	@Override
	//Define how the object should describe itself:
	public String toString() {
	    return "====Account Statement====" + 
	    	   "\nNumber: " + accountNumber +
	           "\nOwner Name: " + ownerName +
	           "\nBalance: " + balance.setScale(2, RoundingMode.HALF_UP).toPlainString() +
			   "\n=========================";
	}

	 // For implementation in different account types.
	 /* Making applyMonthlyUpdate() public is not a security risk because public methods define legitimate operations that trusted external code should be able to call. Security in Java is not provided by hiding model operations but by architectural boundaries and invariants inside methods.
	 i.e. Public methods are safe when they represent valid, intentional behaviour of your model. The danger comes from bad design, not visibility.
	*/
	public abstract void applyMonthlyUpdate();



	/**
 	* Safely increases the account balance by the given amount.
 	* Ensures the balance always remains scaled to 2 decimal places.
	*
	* Why is this in the superclass? 
	* Updating the balance safely (with scaling, invariants, and encapsulation) is core behaviour shared by all account types. 
	* Subclasses *shouldn’t manipulate the balance field directly. Also, avoid duplicate code.
	 */
	protected void addToBalance(BigDecimal amount) {
    this.balance = this.balance.add(amount).setScale(2, RoundingMode.HALF_UP);
}


}



