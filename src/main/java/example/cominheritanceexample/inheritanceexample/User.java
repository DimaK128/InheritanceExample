package example.cominheritanceexample.inheritanceexample;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

class User {
    private final String nickname;

    public User(String nickname) throws Exception {
        this.nickname = nickname;

        try {
            ArrayList<String> users = getUsers();
            for (String user : users) {
                if (Objects.equals(user, nickname)) {
                    return;
                }
            }
            saveUserToFile(nickname);
        }  catch (Exception e) {
            System.err.println(e.getMessage());
            throw new Exception("Failed to init the user");
        }
    }

    @Override
    public String toString() {
        return nickname;
    }

    private ArrayList<String> getUsers() throws Exception {
        ArrayList<String> chats = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("users/users"))) {
            String line;
            while ((line = br.readLine()) != null) {
                chats.add(line);
            }
        } catch (IOException e) {
            throw new Exception("Failed to get users.", e);
        }
        return chats;
    }

    private static void saveUserToFile(String nickname) throws Exception {
        try {
            FileWriter writer = new FileWriter("users/users", true);
            writer.write(nickname + "\n");
            writer.close();
            System.out.println("Data appended to the file.");
        } catch (IOException e) {
            throw new Exception("Failed ot save the user", e);
        }
    }
}