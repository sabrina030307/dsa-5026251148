package lw03.lw03.unguided;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Map<String, Integer> courses = new LinkedHashMap<>();

        Scanner sc = new
         Scanner(Main.class.getResourceAsStream("enrollment.txt"));

        int rejected = 0;

        List<String> checks = new ArrayList<>();

        while (sc.hasNextLine()) {

            String line = sc.nextLine();
            String[] data = line.split(" ");
    
            String operation = data[0];
            String course = data[1];

            if (operation.equals("REGISTER")) {

                int count = Integer.parseInt(data[2]);

                if (count <= 0) {
                    rejected++;
                } else {
                    if (courses.containsKey(course)) {
                        int current = courses.get(course);
                        courses.put(course, current + count);
                    } else {
                        courses.put(course, count);
                    }
                }

            } else if (operation.equals("WITHDRAW")) {
                int count = Integer.parseInt(data[2]);
                if (count <= 0) {
                    rejected++;
                } else if (!courses.containsKey(course)) {
                    rejected++;
                } else {
                    int current = courses.get(course);

                    if (current >= count) {
                        courses.put(course, current - count);
                    } else {
                        rejected++;
                    }
                }

            } else if (operation.equals("CHECK")) {

                if (courses.containsKey(course)) {
                    int current = courses.get(course);
                    checks.add(course + ": " + current + " students");
                } else {
                    checks.add(course + ": Not found");
                }
            }
        }

        sc.close();

        System.out.println("===== Enrollment Checks =====");

        for (String check : checks) {
            System.out.println(check);
        }

        System.out.println();
        System.out.println("===== Final Enrollment =====");

        for (String course : courses.keySet()) {
            System.out.println(course + ": " + courses.get(course) + " students");
        }

        System.out.println();
        System.out.println("Rejected operations: " + rejected);
    } 
    }







