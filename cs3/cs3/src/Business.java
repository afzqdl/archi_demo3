import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;


public class Business {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 9999;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Scanner scanner;

    public Business(Socket socket, ObjectOutputStream out, ObjectInputStream in, Scanner scanner) {
        this.scanner = scanner;
        this.socket = socket;
        this.out = out;
        this.in = in;
    }
    public void run() {
        try {

            while (true) {
                System.out.println("1. View contacts");
                System.out.println("2. Add contact");
                System.out.println("3. Delete contact");
                System.out.println("4. Update contact");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline character

                if (choice == 1) {
                    Request request = new Request("view", null, null);
                    out.writeObject(request);
                    out.flush();

                    Response response = (Response) in.readObject();
                    List<Contact> contacts = (List<Contact>) response.getResult();
                    System.out.println("Contacts:");
                    for (Contact contact : contacts) {
                        System.out.println(contact);
                    }
                } else if (choice == 2) {
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phone = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();

                    Contact contact = new Contact(name, phone, email);
                    Request request = new Request("add", null, contact);
                    out.writeObject(request);
                    out.flush();

                    Response response = (Response) in.readObject();
                    System.out.println(response.getResult());
                } else if (choice == 3) {
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();

                    Request request = new Request("delete", name, null);
                    out.writeObject(request);
                    out.flush();

                    Response response = (Response) in.readObject();
                    System.out.println(response.getResult());
                } else if (choice == 4) {
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter new phone number: ");
                    String phone = scanner.nextLine();
                    System.out.print("Enter new email: ");
                    String email = scanner.nextLine();

                    Contact newContact = new Contact(null, phone, email);
                    Request request = new Request("update", name, newContact);
                    out.writeObject(request);
                    out.flush();

                    Response response = (Response) in.readObject();
                    System.out.println(response.getResult());
                } else if (choice == 5) {
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
