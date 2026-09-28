package lw2.Prelab.Unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        LinkedList<String[]> requests = new LinkedList<>();
        LinkedList<String[]> books = new LinkedList<>();
        LinkedList<String[]> members = new LinkedList<>();
        Queue<String[]> queue = new LinkedList<>();
        Stack<String[]> failed = new Stack<>();
  
        final int MAX_BORROW = 2;

        Scanner scanner = new Scanner(
            new File("src/lw2/Prelab/Unguided/borrowing.txt")
        );

        while (scanner.hasNextLine()) {
             
            String line = scanner.nextLine();
            String[] data = line.split("\\s+");

            requests.add(data);

            String name = data[0];

            boolean found = false;

            for (String[] member : members) {
                if (member[0].equals(name)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                members.add(new String[]{name, "0"});
            }
        }

        scanner.close();

        books.add(new String[]{"Kalkulus", "2"});
        books.add(new String[]{"Fisika", "1"});
        books.add(new String[]{"Statistika", "2"});

        queue.addAll(requests);

        LinkedList<String[]> successful = new LinkedList<>();

        while (!queue.isEmpty()) {

            String[] request = queue.poll();

            String name = request[0];
            String bookTitle = request[1];

            String[] book = null;
            String[] member = null;

            for (String[] b : books) {
                if (b[0].equals(bookTitle)) {
                    book = b;
                    break;
                }
            }

            for (String[] m : members) {
                if (m[0].equals(name)) {
                    member = m;
                    break;
                }
            }

            int stock = Integer.parseInt(book[1]);
            int borrowed = Integer.parseInt(member[1]);

            if (stock > 0 && borrowed < MAX_BORROW) {

                stock--;
                borrowed++;

                book[1] = String.valueOf(stock);
                member[1] = String.valueOf(borrowed);
                successful.add(request);

            } else {

                failed.push(request);
            }
        }
        System.out.println("=== Successfully Processed Requests ===");

        for (String[] request : successful) {
            System.out.println(request[0] + " " + request[1]);
        }

        System.out.println();
        System.out.println("=== Remaining Book Stock ===");

        for (String[] book : books) {
            System.out.println(book[0] + " : " + book[1]);
        }

        System.out.println();
        System.out.println("=== Failed Requests ===");

        while (!failed.isEmpty()) {

            String[] request = failed.pop();

            System.out.println(request[0] + " " + request[1]);
        }
    }
}



