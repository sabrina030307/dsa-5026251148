package lw2.prelab;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[]args) throws FileNotFoundException{
        LinkedList<String[]> transactions = new LinkedList<>();
        LinkedList<String[]> customer = new LinkedList<>();
        
        Scanner Scanner = new Scanner(new File("src/lw2/prelab/transactions.txt"));

    while  (Scanner.hasNextLine()) {
    String line = Scanner.nextLine();
    String[]data = line.split("\\s+");

    transactions.add(data);
    
    String name = data[0];

    boolean found = false;

    for (String[] cust : customer) {
        if (cust[0].equals(name)) {
            found = true;
            break;

        }
    }
  
    if (!found) {
                customer.add(new String[]{name, "0"});
            }
        }

        Scanner.close();

       
        Queue<String[]> queue = new LinkedList<>();

        for (String[] transaction : transactions) {
            queue.offer(transaction);
        }

        
        Stack<String[]> failedTransaction = new Stack<>();

        
        while (!queue.isEmpty()) {

            String[] currentTransaction = queue.poll();

            String name = currentTransaction[0];
            String type = currentTransaction[1];
            int amount = Integer.parseInt(currentTransaction[2]);

            
            String[] customerData = null;

            for (String[] cust : customer) {
                if (cust[0].equals(name)) {
                    customerData = cust;
                    break;
                }
            }

            int balance = Integer.parseInt(customerData[1]);

            // DEPOSIT
            if (type.equals("DEPOSIT")) {

                balance += amount;
                customerData[1] = String.valueOf(balance);

            }

            // WITHDRAW
            else if (type.equals("WITHDRAW")) {

                if (amount > balance) {

                    // Withdrawal gagal → masuk Stack
                    failedTransaction.push(currentTransaction);

                } else {

                    balance -= amount;
                    customerData[1] = String.valueOf(balance);
                }
            }
        }

        
        System.out.println("=== Final Balances ===");

        for (String[] cust : customer) {
            System.out.println(
                cust[0] + " : " + cust[1]
            );
        }

        
        System.out.println();

        System.out.println("=== Failed Transactios ===");

        while (!failedTransaction.isEmpty()) {

            String[] failed = failedTransaction.pop();

            System.out.println(
                failed[0] + " " +
                failed[1] + " " +
                failed[2]
            );
        }
    }
}