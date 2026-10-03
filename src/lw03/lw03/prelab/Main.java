package lw03.lw03.prelab;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        // Problem 1
        List<String> playlist = new ArrayList<>();

        Scanner sc = new Scanner(
            Main.class.getResourceAsStream("playlist.txt")
        );

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] data = line.split(" ");

            if (data[0].equals("ADD")) {
                playlist.add(data[1]);
            } 
            else if (data[0].equals("INSERT")) {
                int index = Integer.parseInt(data[1]);
                playlist.add(index, data[2]);
            } 
            else if (data[0].equals("REMOVE")) {
                playlist.remove(data[1]);
            }
        }

        System.out.println("===== Problem 1 =====");
        System.out.println("Total songs: " + playlist.size());

        for (int i = 0; i < playlist.size(); i++) {
            System.out.println((i + 1) + ": " + playlist.get(i));
        }

        sc.close();


        // Problem 2
        Set<String> participants = new LinkedHashSet<>();

        Scanner sc2 = new Scanner(
            Main.class.getResourceAsStream("participants.txt")
        );

        int duplicate = 0;

        while (sc2.hasNextLine()) {
            String name = sc2.nextLine();

            if (participants.contains(name)) {
                duplicate++;
            } 
            else {
                participants.add(name);
            }
        }

        System.out.println("===== Problem 2 =====");
        System.out.println("Unique participants: " + participants.size());

        int number = 1;

        for (String name : participants) {
            System.out.println(number + ". " + name);
            number++;
        }

        System.out.println("Duplicate registrations: " + duplicate);

        sc2.close();


        // Problem 3
        Map<String, Integer> inventory = new LinkedHashMap<>();

        Scanner sc3 = new Scanner(
            Main.class.getResourceAsStream("inventory.txt")
        );

        int failedSales = 0;

        while (sc3.hasNextLine()) {
            String line = sc3.nextLine();
            String[] data = line.split(" ");

            String type = data[0];
            String product = data[1];
            int quantity = Integer.parseInt(data[2]);

            if (type.equals("ADD")) {

                if (inventory.containsKey(product)) {
                    inventory.put(
                        product,
                        inventory.get(product) + quantity
                    );
                } 
                else {
                    inventory.put(product, quantity);
                }

            } 
            else if (type.equals("SELL")) {

                if (inventory.containsKey(product)
                        && inventory.get(product) >= quantity) {

                    inventory.put(
                        product,
                        inventory.get(product) - quantity
                    );

                } 
                else {
                    failedSales++;
                }
            }
        }

        System.out.println("===== Problem 3 =====");

        for (String product : inventory.keySet()) {
            System.out.println(product + ": " + inventory.get(product));
        }

        System.out.println("Failed sales: " + failedSales);

        sc3.close();
    }
}