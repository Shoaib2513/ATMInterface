package SummerTraining;
import java.util.*;
import java.io.*;

public class ATMInterface {

    private static int userPIN;
    private static double accountBalance = 1000.00;
    private static final String PIN_FILE = "userpin.txt";
    private static List<String> expenseHistory = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int attempts = 0;
        int maxAttempts = 3;

        // Load PIN from file
        loadPIN();

        System.out.println("Welcome to Simple ATM!");

        // Login with PIN
        while (attempts < maxAttempts) {
            System.out.print("Enter your 4-digit PIN: ");
            int enteredPIN = scanner.nextInt();

            if (enteredPIN == userPIN) {
                showMenu(scanner);
                break;
            } else {
                attempts++;
                System.out.println("Incorrect PIN. Attempts left: " + (maxAttempts - attempts));
            }
        }

        if (attempts == maxAttempts) {
            System.out.println("Too many failed attempts. Card blocked.");
        }

        scanner.close();
    }

    // ATM Menu
    public static void showMenu(Scanner scanner) {
        int choice;

        do {
            System.out.println("\n========= ATM Menu =========");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. View Expenses");
            System.out.println("5. Change PIN");
            System.out.println("6. Exit");
            System.out.print("Choose an option (1-6): ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    depositMoney(scanner);
                    break;
                case 3:
                    withdrawMoney(scanner);
                    break;
                case 4:
                    viewExpenses();
                    break;
                case 5:
                    changePIN(scanner);
                    break;
                case 6:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        } while (choice != 6);
    }

    public static void checkBalance() {
        System.out.printf("Your current balance is: ₹%.2f\n", accountBalance);
    }

    public static void depositMoney(Scanner scanner) {
        System.out.print("Enter amount to deposit: ₹");
        double amount = scanner.nextDouble();

        if (amount > 0) {
            accountBalance += amount;
            System.out.printf("₹%.2f deposited successfully.\n", amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public static void withdrawMoney(Scanner scanner) {
        System.out.print("Enter amount to withdraw: ₹");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        if (amount > 0 && amount <= accountBalance) {
            System.out.print("Enter purpose of withdrawal (e.g., shopping, medicine, bills): ");
            String purpose = scanner.nextLine();

            accountBalance -= amount;
            String record = String.format("₹%.2f spent on %s", amount, purpose);
            expenseHistory.add(record);

            System.out.println(record + " - Withdrawal successful.");
        } else {
            System.out.println("Invalid amount or insufficient balance.");
        }
    }

    public static void viewExpenses() {
        System.out.println("\n===== Expense History =====");
        if (expenseHistory.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            for (String expense : expenseHistory) {
                System.out.println(expense);
            }
        }
    }

    public static void changePIN(Scanner scanner) {
        System.out.print("Enter current PIN: ");
        int currentPIN = scanner.nextInt();

        if (currentPIN == userPIN) {
            System.out.print("Enter new 4-digit PIN: ");
            int newPIN = scanner.nextInt();

            if (newPIN >= 1000 && newPIN <= 9999) {
                userPIN = newPIN;
                savePIN(); // Save the new PIN to the file
                System.out.println("PIN changed successfully.");
            } else {
                System.out.println("Invalid PIN format. Must be 4 digits.");
            }
        } else {
            System.out.println("Incorrect current PIN.");
        }
    }

    // Load PIN from file
    public static void loadPIN() {
        try {
            File file = new File(PIN_FILE);
            if (!file.exists()) {
                // If file doesn't exist, create with default PIN
                FileWriter writer = new FileWriter(PIN_FILE);
                writer.write("1234");
                writer.close();
                userPIN = 1234;
            } else {
                Scanner fileScanner = new Scanner(file);
                if (fileScanner.hasNextInt()) {
                    userPIN = fileScanner.nextInt();
                }
                fileScanner.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading PIN file: " + e.getMessage());
            userPIN = 1234; // Fallback
        }
    }

    // Save new PIN to file
    public static void savePIN() {
        try {
            FileWriter writer = new FileWriter(PIN_FILE);
            writer.write(String.valueOf(userPIN));
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving PIN: " + e.getMessage());
        }
    }
}