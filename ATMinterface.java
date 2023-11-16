import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class User {
    private String userId;
    private String pin;

    public User(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
    }

    public boolean authenticate(String inputUserId, String inputPin) {
        return userId.equals(inputUserId) && pin.equals(inputPin);
    }
}

class Account {
    private double balance;
    private List<Transaction> History;

    public Account() {
        balance = 1000.0;
        History = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance = amount + balance;
        History.add(new Transaction("Deposit", amount));
    }

     public void transferto(double amount) {
        
            balance = amount - balance;
            History.add(new Transaction("Transfer ", amount ));
        
        }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance = balance - amount;
            History.add(new Transaction("Withdraw", amount));
            return true;
        }
        return false;
    }

    public boolean withdraw1(double amount) {
        if (balance >= amount) {
            balance = balance - amount;
            History.add(new Transaction("Transfer", amount));
            return true;
        }
        return false;
    }
    public void transfer(Account receiver, double amount) {
        if (withdraw(amount)) {
            receiver.deposit(amount);
            History.add(new Transaction("Transfer to " + receiver.getClass().getSimpleName(), amount));
        }
    }

    public List<Transaction> getTransactionHistory() {
        return History;
    }
}

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String toString() {
        return type + ": Rs." + amount ;
    }
}

public class ATMinterface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        User user = new User("12345", "6789");
        Account account = new Account();

        System.out.print("Enter your user ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter your PIN: ");
        String pin = scanner.nextLine();

        if (user.authenticate(userId, pin)) {
            System.out.println("Welcome to the ATM!");
            boolean Run = true;

            while (Run) {
                System.out.println("\nChoose an option:");
                System.out.println("1. Display Balance");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Transfer");
                System.out.println("5. Transactions History");
                System.out.println("6. Quit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Balance: Rs." + account.getBalance());
                        break;
                    case 2:
                        System.out.print("Enter the deposit amount: Rs.");
                        double depositAmount = scanner.nextDouble();
                        account.deposit(depositAmount);
                        System.out.println("Rs."+depositAmount+" has been sucessfully deposited to your account");
                        break;
                    case 3:
                        System.out.print("Enter the withdrawal amount: Rs.");
                        double withdrawAmount = scanner.nextDouble();
                        if (account.withdraw(withdrawAmount)) {
                            System.out.println("Rs." + withdrawAmount + " withdrawn.");
                        } else {
                            System.out.println("Insufficient balance.");
                        }
                        break;
                    case 4:
                        System.out.print("Enter the recipient's user ID: ");
                        String recipientUserId = scanner.next();
                        System.out.print("Enter the transfer amount: Rs.");
                        double transferAmount = scanner.nextDouble();
                        if (account.withdraw1(transferAmount)){
                        
                        System.out.println("Rs."+transferAmount+" has been Sucessfully transferred to "+recipientUserId);
                        
                        }else {
                            System.out.println("Insufficient balance.");
                        }
                         break;
                    case 5:
                        List<Transaction> history = account.getTransactionHistory();
                        System.out.println("Transaction History:");
                        for (Transaction transaction : history) {
                            System.out.println(transaction);
                        }
                        break;
                    case 6:
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        Run = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Authentication failed. Please try again.");
        }
    }
}