import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 9999;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Connected to server.");

            Business business = new Business(socket, out, in, scanner);
            business.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}