import java.io.*;
import java.util.*;

public class FileEncryptionDecryption {

    static final String INPUT_FILE = "input.txt";
    static final String ENCRYPTED_FILE = "encrypted.txt";
    static final String DECRYPTED_FILE = "decrypted.txt";

    // Encryption
    public static void encryptFile() {

        try {
            FileInputStream input =
                    new FileInputStream(INPUT_FILE);

            FileOutputStream output =
                    new FileOutputStream(ENCRYPTED_FILE);

            int ch;

            while ((ch = input.read()) != -1) {

                // Convert character into non-readable format
                ch = ch + 3;

                output.write(ch);
            }

            input.close();
            output.close();

            System.out.println("File encrypted successfully.");

        } catch (IOException e) {
            System.out.println("Error while encrypting file.");
        }
    }

    // Decryption
    public static void decryptFile() {

        try {
            FileInputStream input =
                    new FileInputStream(ENCRYPTED_FILE);

            FileOutputStream output =
                    new FileOutputStream(DECRYPTED_FILE);

            int ch;

            while ((ch = input.read()) != -1) {

                // Convert encrypted character back
                ch = ch - 3;

                output.write(ch);
            }

            input.close();
            output.close();

            System.out.println("File decrypted successfully.");

        } catch (IOException e) {
            System.out.println("Error while decrypting file.");
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int choice;

        do {
            System.out.println("\n===== FILE ENCRYPTION MENU =====");
            System.out.println("1. Encrypt File");
            System.out.println("2. Decrypt File");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    encryptFile();
                    break;

                case 2:
                    decryptFile();
                    break;

                case 3:
                    System.out.println("Program terminated.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 3);

        sc.close();
    }
}