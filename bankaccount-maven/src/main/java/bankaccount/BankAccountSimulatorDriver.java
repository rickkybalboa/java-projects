package bankaccount;

import java.util.Scanner;

/**
 * The {@code BankAccountSimulatorDriver} class serves as the user-interface layer
 * for the BankAccount application. Its purpose is to manage all console-based
 * interaction with the user while delegating business logic to the
 * {@link BankAccount} model class.
 *
 * <p>This class follows a clear separation of concerns:
 *
 * <ul>
 *   <li><strong>User interaction:</strong> The driver handles all input prompts,
 *       menu navigation, and error messages.</li>
 *   <li><strong>Model delegation:</strong> All account operations (deposit,
 *       withdrawal, balance display) are performed through the public methods of
 *       {@code BankAccount}, keeping the UI logic and business logic separate.</li>
 *   <li><strong>Helper methods:</strong> Input validation and transaction
 *       workflows are moved into private helper methods (such as
 *       {@code handleDeposit} and {@code handleWithdrawal}) to reduce duplication
 *       and keep the {@code main} method readable.</li>
 *   <li><strong>Main loop:</strong> A structured {@code while(!exit)} loop drives
 *       the menu system, repeatedly prompting the user and delegating actions.</li>
 * </ul>
 *
 * <p>Overall, the structure of this class improves clarity, maintainability, and
 * separation of responsibilities. It allows the driver to focus on input and
 * output while the {@code BankAccount} class remains responsible for enforcing
 * banking rules and maintaining object state.
 */

/*
 * BankAccountSimulatorDriver
 * ---------------------------
 * This driver class is responsible for handling all user interaction for the
 * BankAccount project. It follows a clear separation of concerns:
 *
 * 1. The BankAccount class contains the data and business logic (the model).
 *    This driver does not modify that logic; it only calls its public methods.
 *
 * 2. Input handling and validation are separated into helper methods 
 *    (e.g., readMenuChoice, handleDeposit, handleWithdrawal) to avoid 
 *    duplicated code and to keep the main loop easy to read.
 *
 * 3. The main loop (`while(!exit)`) controls the application's flow:
 *       - displaying the menu,
 *       - validating user choices,
 *       - delegating actions to helper methods.
 *
 * 4. Each operation (deposit, withdrawal, etc.) is encapsulated in its own
 *    method so that error handling, parsing, and messaging remain consistent
 *    and isolated from the menu logic.
 *
 * Overall, this structure improves readability, avoids repetition, and keeps
 * the driver focused on user interaction rather than business rules.
 */


public class BankAccountSimulatorDriver {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {

            BankAccount account = new SavingsAccount("001", "Mr A",1000,0.12);

            boolean exit = false;

            while (!exit) {
                int choice = readMenuChoice(sc);

                switch (choice) {
                    case 1 -> handleDeposit(sc, account);
                    case 2 -> handleWithdrawal(sc, account);
                    case 3 -> System.out.println(account);
                    case 4 -> exit = true;
                    // default is not necessary due to validation, but you could add:
                    // default -> System.out.println("Unexpected option.");
                }
            }

            System.out.println("Thank you for using BANK.");
        }
    }

    private static int readMenuChoice(Scanner sc) {
        while (true) {
            System.out.println("""
                    
                    Please select one of the following options:
                    1. Deposit Funds
                    2. Withdraw Funds
                    3. Print Account Details
                    4. Exit""");

            String input = sc.nextLine();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 4) {
                    return choice;
                } else {
                    System.out.println("Please enter a number between 1 and 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void handleDeposit(Scanner sc, BankAccount account) {
        while (true) {
            System.out.println("Please enter deposit amount: ");
            String input = sc.nextLine();

            try {
                double amount = Double.parseDouble(input);
                account.deposit(amount);
                System.out.println("Deposit of £" + amount + " successful.");
                return; // success → leave method
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Deposit failed: " + e.getMessage());
            }
        }
    }

    private static void handleWithdrawal(Scanner sc, BankAccount account) {
        while (true) {
            System.out.println("Please enter withdrawal amount or type 'cancel' to cancel withdrawal: ");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("cancel")) {
                System.out.println("Withdrawal cancelled.");
                return;
            }

            try {
                double amount = Double.parseDouble(input);
                account.withdraw(amount);
                System.out.println("Withdrawal of £" + amount + " successful.");
                return; // success → leave method
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Withdrawal failed: " + e.getMessage());
            }
        }
    }
}