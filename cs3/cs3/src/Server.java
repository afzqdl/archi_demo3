import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 9999;
    private static List<Contact> contacts = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

                // 创建线程来处理客户端请求
                Thread thread = new RequestHandler(clientSocket);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized List<Contact> getContacts() {
        return contacts;
    }

    public static synchronized void addContact(Contact contact) {
        contacts.add(contact);
    }

    public static synchronized void deleteContact(String name) {
        contacts.removeIf(contact -> contact.getName().equals(name));
    }

    public static synchronized void updateContact(String name, Contact newContact) {
        for (Contact contact : contacts) {
            if (contact.getName().equals(name)) {
                contact.setPhone(newContact.getPhone());
                contact.setEmail(newContact.getEmail());
                break;
            }
        }
    }
}

class RequestHandler extends Thread {
    private final Socket clientSocket;

    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            Object requestObject = in.readObject();
            if (requestObject instanceof Request) {
                Request request = (Request) requestObject;
                Response response = handleRequest(request);
                out.writeObject(response);
                out.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Response handleRequest(Request request) {
        String command = request.getCommand();
        if (command.equals("view")) {
            List<Contact> contacts = Server.getContacts();
            return new Response(contacts);
        } else if (command.equals("add")) {
            Contact contact = request.getContact();
            Server.addContact(contact);
            return new Response("Contact added successfully.");
        } else if (command.equals("delete")) {
            String name = request.getName();
            Server.deleteContact(name);
            return new Response("Contact deleted successfully.");
        } else if (command.equals("update")) {
            String name = request.getName();
            Contact newContact = request.getContact();
            Server.updateContact(name, newContact);
            return new Response("Contact updated successfully.");
        } else {
            return new Response("Invalid command.");
        }
    }
}

class Request implements java.io.Serializable {
    private String command;
    private String name;
    private Contact contact;

    public Request(String command, String name, Contact contact) {
        this.command = command;
        this.name = name;
        this.contact = contact;
    }

    public String getCommand() {
        return command;
    }

    public String getName() {
        return name;
    }

    public Contact getContact() {
        return contact;
    }
}

class Response implements java.io.Serializable {
    private Object result;

    public Response(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }
}

class Contact implements java.io.Serializable {
    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phone + ", Email: " + email;
    }
}