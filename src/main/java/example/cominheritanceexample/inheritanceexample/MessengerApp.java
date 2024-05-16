package example.cominheritanceexample.inheritanceexample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.TextField;


import javafx.util.Duration;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;


public class MessengerApp extends Application {
    private Chat selectedChat;
    private User selectedUser;
    private final TextField inputField = new TextField();
    private final TextArea  messageArea = new TextArea();
    private final TextField chatField = new TextField();
    private final TextField userField = new TextField();
    private final ListView<Chat> chatListView = new ListView<>();
    private final ListView<User> userListView = new ListView<>();
    private final ListView<BaseMessage> messageListView = new ListView<>();




    private final Button sendTextButton = new Button("Send Text");
    private final Button sendVoiceButton = new Button("Send Voice");
    private final Button sendImageButton = new Button("Send Image");
    private final Button sendLocationButton = new Button("Send Location");
    private final Button sendContactButton = new Button("Send Contact");
    private final Button sendFileButton = new Button("Send File");
    private final Button chatButton = new Button("Add Chat");
    private final Button userButton = new Button("Add User");


    private void getUsersForSelectedChat() throws Exception {
        ArrayList<User> users = selectedChat.getUsers();
        userListView.getItems().clear();
        userListView.getItems().addAll(users);
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showChatMessages() throws IOException {
        if (selectedChat != null) {
            messageArea.setText(selectedChat.getMessages());
        }
    }

    private void sendMessage(BaseMessage message) {
        if (selectedChat != null && selectedUser != null) {
            try {
                selectedChat.addElement(message);
                inputField.clear();
                showChatMessages();
            } catch (Exception e) {
                showAlert("An error occurred: " + e, Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Chat or user is not selected", Alert.AlertType.WARNING);
        }
    }

    private void addChat() throws Exception {
        if (chatField.getText().isEmpty()) {
            return;
        }

        try {
            ArrayList<String> chats = Chat.getChatNames();
            for (String chat : chats) {
                if (Objects.equals(chat, chatField.getText())) {
                    return;
                }
            }
        } catch (Exception e) {
            showAlert("An error occurred: " + e, Alert.AlertType.ERROR);
            return;
        }

        Chat newChat = new Chat(chatField.getText());
        chatListView.getItems().addAll(newChat);
        chatField.clear();
    }

    private void addUser() throws Exception {
        if (selectedChat == null) {
            showAlert("Please select a chats to join", Alert.AlertType.WARNING);
            return;
        }
        for (User user : userListView.getItems()) {
            if (Objects.equals(user.toString(), userField.getText()) || Objects.equals(user.toString(), "users/users")) {
                showAlert("User already exists", Alert.AlertType.WARNING);
                userField.clear();
                return;
            }
        }
        User newUser = new User(userField.getText());
        selectedChat.addElement(newUser);
        userListView.getItems().addAll(newUser);
        userField.clear();
    }

    private void initButtons() {
        sendTextButton.setOnAction(_ -> {
            try {
                sendMessage(new TextMessage(inputField.getText(), selectedUser));
            } catch (Exception e) {
                showAlert("An error occurred: " + e, Alert.AlertType.ERROR);
            }
        });


        sendVoiceButton.setOnAction(_ -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Audio File");
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                CompletableFuture<Duration> durationFuture = getAudioDurationAsync(selectedFile);
                durationFuture.thenAccept(duration -> {
                    try {
                        sendMessage(new VoiceMessage(selectedFile.getAbsolutePath(), duration, selectedUser));
                    } catch (Exception e) {
                        showAlert("An error occurred: " + e, Alert.AlertType.ERROR);
                    }
                }).exceptionally(ex -> {
                    showAlert("Failed to retrieve audio duration", Alert.AlertType.ERROR);
                    return null;
                });
            }
        });

        sendImageButton.setOnAction(_ -> {
            if (!inputField.getText().endsWith(".png") || inputField.getText().split(" ").length != 1) {
                showAlert("Should be valid .png file path", Alert.AlertType.WARNING);
                return;
            }
            try {
                sendMessage(new ImageMessage(inputField.getText(), selectedUser));
            } catch (Exception e) {
                showAlert("An error occurred: " + e, Alert.AlertType.ERROR);
            }
        });

        sendLocationButton.setOnAction(_ -> {
            String[] location = inputField.getText().split(" ");
            if (location.length != 2) {
                showAlert("Should be two floats with latitude and longitude", Alert.AlertType.WARNING);
                return;
            }
            try {
                float latitude = Float.parseFloat(location[0]);
                float longitude = Float.parseFloat(location[1]);
                sendMessage(new LocationMessage(latitude, longitude, selectedUser));
            } catch (NumberFormatException e) {
                showAlert("Should be two floats with latitude and longitude", Alert.AlertType.WARNING);
            } catch (Exception e) {
                showAlert("An error occurred: " + e, Alert.AlertType.ERROR);
            }
        });

        sendContactButton.setOnAction(_ -> {
            String[] contact =  inputField.getText().split(" ");
            if (contact.length != 2) {
                showAlert("Contact should be in format: <name> <number>", Alert.AlertType.WARNING);
                return;
            }
            try {
                int number = Integer.parseInt(contact[1]);
                sendMessage(new ContactMessage(contact[0], number, selectedUser));
            } catch (NumberFormatException e) {
                showAlert("Contact should be in format: <name> <number>", Alert.AlertType.WARNING);
            } catch (Exception e) {
                showAlert("An error occurred: " + e, Alert.AlertType.ERROR);
            }
        });

        sendFileButton.setOnAction(_ -> {
            if (inputField.getText().split(" ").length != 1) {
                showAlert("Should be valid file path", Alert.AlertType.WARNING);
                return;
            }
            try {
                sendMessage(new FileMessage(inputField.getText(), selectedUser));
            } catch (Exception e) {
                showAlert("An error occurred: " + e, Alert.AlertType.ERROR);
            }
        });

        userButton.setOnAction(_ -> {
            try {
                addUser();
            } catch (Exception e) {
                showAlert("An error occurred: " + e, Alert.AlertType.ERROR);
            }
        });

        chatButton.setOnAction(_ -> {
            try {
                addChat();
            } catch (Exception e) {
                showAlert("An error occurred: " + e, Alert.AlertType.ERROR);
            }
        });
    }

    private CompletableFuture<Duration> getAudioDurationAsync(File audioFile) {
        CompletableFuture<Duration> future = new CompletableFuture<>();
        Media media = new Media(audioFile.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setOnReady(() -> {
            Duration duration = media.getDuration();
            future.complete(duration);
            mediaPlayer.dispose();
        });

        mediaPlayer.setOnError(() -> {
            future.completeExceptionally(new RuntimeException("Failed to retrieve audio duration"));
            mediaPlayer.dispose();
        });

        mediaPlayer.setOnEndOfMedia(() -> {
            future.completeExceptionally(new RuntimeException("Failed to retrieve audio duration"));
            mediaPlayer.dispose();
        });

        mediaPlayer.setOnPlaying(mediaPlayer::dispose);
        mediaPlayer.play();

        return future;
    }

    private void initViews() {
        userListView.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            selectedUser = newValue;
        });

        try {
            chatListView.getItems().addAll(Chat.listAllChats());
            chatListView.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
                selectedChat = newValue;
                try {
                    getUsersForSelectedChat();
                    showChatMessages();
                } catch (Exception e) {
                    showAlert("An error occurred: " + e, Alert.AlertType.ERROR);
                }
            });

            messageListView.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    BaseMessage selectedMessage = messageListView.getSelectionModel().getSelectedItem();
                    if (selectedMessage != null) {
                        enableEditing(selectedMessage);
                    }
                }
            });

        } catch (Exception e) {
            showAlert("An error occurred: " + e, Alert.AlertType.ERROR);
        }
    }

    private void enableEditing(BaseMessage message) {
        TextField editField = new TextField(message.getMessage());


        editField.setOnAction(event -> {
            message.setMessage(editField.getText());
            messageListView.refresh();
        });

        int selectedIndex = messageListView.getItems().indexOf(message);
        messageListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(BaseMessage item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else if (item.equals(message)) {
                    setGraphic(editField);
                    setText(null);
                } else {
                    setText(item.getMessage());
                    setGraphic(null);
                }
            }
        });
        messageListView.getSelectionModel().select(selectedIndex);
        editField.requestFocus();
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        initViews();
        initButtons();

        HBox inputBox = new HBox(inputField, sendTextButton, sendVoiceButton, sendImageButton, sendLocationButton, sendContactButton, sendFileButton);
        inputBox.setSpacing(10);
        inputBox.setAlignment(Pos.CENTER);
        VBox chatBox = new VBox(new Label("Chats"), chatListView, chatButton, chatField);
        VBox userBox = new VBox(new Label("Users"), userListView, userButton, userField);
        GridPane centerPane = new GridPane();
        centerPane.setPadding(new Insets(10));
        centerPane.setHgap(10);
        centerPane.setVgap(10);
        centerPane.add(chatBox, 0, 0);
        centerPane.add(userBox, 1, 0);
        root.setCenter(centerPane);
        root.setBottom(new VBox(messageArea, inputBox));

        Scene scene = new Scene(root, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Messenger App");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

