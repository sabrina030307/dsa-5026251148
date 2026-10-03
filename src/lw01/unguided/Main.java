// package lw01.unguided;

// public class Main {
// import java.io.File;
// import java.io.FileNotFoundException;
// import java.util.Scanner;

// public class Main {
//     public static void main(String[] args) throws FileNotFoundException {

//         Scanner scanner = new Scanner(new File("rentals.txt"));

//         int totalRecords = scanner.nextInt();

//         Rental[] rentals = new Rental[totalRecords];
//         int[] units = new int[totalRecords];

//         for (int i = 0; i < totalRecords; i++) {

//             String type = scanner.next();
//             String id = scanner.next();
//             int days = scanner.nextInt();
//             units[i] = scanner.nextInt();

//             if (type.equals("LAPTOP")) {
//                 rentals[i] = new LaptopRental(id, days);
//             } else {
//                 rentals[i] = new ProjectorRental(id, days);
//             }
//         }

//         scanner.close();

//         for (int i = 0; i < totalRecords; i++) {
//             System.out.println(
//                 rentals[i].getId()
//                 + " | "
//                 + rentals[i].label()
//                 + " | "
//                 + rentals[i].calculateCharge(units[i])
//             );
//         }
//     }
// }