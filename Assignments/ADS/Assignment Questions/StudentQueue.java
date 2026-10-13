import java.util.ArrayList;
import java.util.Scanner;

public class StudentQueue {

    public static void main(String[] args) {

        ArrayList<Integer> queue = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        int choice;

        do {
            System.out.println("\n--- Student Queue ---");
            System.out.println("1. Add Student");
            System.out.println("2. Submit Assignment");
            System.out.println("3. Search Student");
            System.out.println("4. Display Queue");
            System.out.println("5. Count Students");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter Student ID: ");
                    int id = sc.nextInt();

                    queue.add(id);

                    System.out.println("Student added.");
                    break;

                case 2:
                    if (queue.isEmpty()) {
                        System.out.println("Queue is empty.");
                    } else {
                        int student = queue.remove(0);
                        System.out.println("Student " + student + " submitted.");
                    }
                    break;

                case 3:
                    System.out.print("Enter Student ID to search: ");
                    int searchId = sc.nextInt();

                    if (queue.contains(searchId)) {
                        System.out.println("Student " + searchId + " is waiting.");
                    } else {
                        System.out.println("Student " + searchId + " is not waiting.");
                    }
                    break;

                case 4:
                    System.out.println("Current Queue: " + queue);
                    break;

                case 5:
                    System.out.println("Current number of students: " + queue.size());
                    break;

                case 6:
                    System.out.println("Program ended.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 6);

        sc.close();
    }
}