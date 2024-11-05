import java.util.*;
import java.io.*;

class Expense {
    private String date;
    private double amount;
    private String category;
    private String description;

    public Expense(String date, double amount, String category, String description) {
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return date + " | " + category + " | " + amount + " | " + description;
    }
}

class ExpenseTracker {
    private List<Expense> expenses;

    public ExpenseTracker() {
        expenses = new ArrayList<>();
    }

    public void addExpense(String date, double amount, String category, String description) {
        expenses.add(new Expense(date, amount, category, description));
    }

    public void viewSummary() {
        double totalIncome = 0, totalExpenses = 0;
        for (Expense exp : expenses) {
            if (exp.getAmount() > 0) totalIncome += exp.getAmount();
            else totalExpenses += Math.abs(exp.getAmount());
        }
        System.out.println("Total Income: " + totalIncome);
        System.out.println("Total Expenses: " + totalExpenses);
        System.out.println("Balance: " + (totalIncome - totalExpenses));
    }

    public void saveToFile(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        for (Expense exp : expenses) {
            writer.write(exp.toString() + "\n");
        }
        writer.close();
    }

    public void loadFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(" | ");
            addExpense(data[0], Double.parseDouble(data[1]), data[2], data[3]);
        }
        reader.close();
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ExpenseTracker tracker = new ExpenseTracker();
        int choice;

        do {
            System.out.println("1. Add Expense");
            System.out.println("2. View Summary");
            System.out.println("3. Save Expenses to File");
            System.out.println("4. Load Expenses from File");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String date = sc.nextLine();
                    System.out.print("Enter amount: ");
                    double amount = sc.nextDouble();
                    sc.nextLine();  // Consume newline
                    System.out.print("Enter category: ");
                    String category = sc.nextLine();
                    System.out.print("Enter description: ");
                    String description = sc.nextLine();
                    tracker.addExpense(date, amount, category, description);
                    break;
                case 2:
                    tracker.viewSummary();
                    break;
                case 3:
                    try {
                        System.out.print("Enter filename to save: ");
                        String filename = sc.nextLine();
                        tracker.saveToFile(filename);
                        System.out.println("Expenses saved to file.");
                    } catch (IOException e) {
                        System.out.println("Error saving to file: " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        System.out.print("Enter filename to load: ");
                        String filename = sc.nextLine();
                        tracker.loadFromFile(filename);
                        System.out.println("Expenses loaded from file.");
                    } catch (IOException e) {
                        System.out.println("Error loading from file: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);

        sc.close();
    }
}
