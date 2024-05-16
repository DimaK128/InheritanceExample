package example.cominheritanceexample.inheritanceexample;

import java.util.ArrayList;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Chat {
    private static final String CHATS_FILE = "chats/chats";
    private final String chatName;
    private final String chatFileName;
    private final String usersFileName;
    private final ArrayList<BaseMessage> messages;

    public Chat(String chatName) throws ExceptionInInitializerError, Exception {
        this.chatName = chatName;
        this.chatFileName = "chats/" + chatName;
        this.usersFileName = "chats/" + chatName + "-users";
        this.messages = new ArrayList<>();

        try {
            ArrayList<String> chats = getChatNames();
            if (chats.contains(chatName)) {
                return;
            }

            createFile(this.usersFileName);
            createFile(this.chatFileName);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new Exception("Failed to create chats the chats.");
        }

        try {
            FileWriter writer = new FileWriter(CHATS_FILE, true);
            writer.write(chatName + "\n");
            writer.close();
            System.out.println("Data appended to the file.");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new Exception("Failed to create chats the chats.");
        }
    }

    @Override
    public String toString() {
        return chatName;
    }

    private void createFile(String filename) throws Exception {
        File file = new File(filename);

        try {
            if (file.createNewFile()) {
                System.out.println("File created successfully: " + file.getAbsolutePath());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            throw new Exception("An error occurred: " + e.getMessage());
        }
    }

    public void addElement(User user) throws Exception {
        try {
            FileWriter writer = new FileWriter(this.usersFileName, true);
            writer.write(user.toString() + "\n");
            writer.close();
            System.out.println("Data appended to the file.");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new Exception("An error occurred adding the user to the chats");
        }
    }

    public void addElement(BaseMessage message) throws Exception {
        messages.add(message);
        try {
            FileWriter writer = new FileWriter(this.chatFileName, true); // true flag for append mode
            writer.write(message.getMessageString() + "\n");
            writer.close();
            System.out.println("Data appended to the file.");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new Exception("An error occurred adding the message to the chats");
        }
    }

    public ArrayList<User> getUsers() throws Exception {
        ArrayList<User> users = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(this.usersFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                users.add(new User(line));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return users;
    }

    public String getMessages() throws IOException {
        return new String(Files.readAllBytes(Paths.get(this.chatFileName)));
    }

    public static ArrayList<String> getChatNames() throws Exception {
        ArrayList<String> chats = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CHATS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                chats.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading chats names file: " + e.getMessage());
            throw new Exception("Failed to get chats");
        }
        return chats;
    }

    public static ArrayList<Chat> listAllChats() throws Exception {
        ArrayList<Chat> chats = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CHATS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                chats.add(new Chat(line));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new Exception("Failed to get chats");
        }
        return chats;
    }
}