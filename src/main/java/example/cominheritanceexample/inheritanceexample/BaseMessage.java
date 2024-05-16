package example.cominheritanceexample.inheritanceexample;

import java.time.LocalDateTime;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


abstract class BaseMessage {
    protected User author;
    protected LocalDateTime date;
    private StringProperty message; // StringProperty for message content
    private boolean editable; // Flag to indicate if the message is editable


    public BaseMessage(User author) {
        this.author = author;
        this.date = LocalDateTime.now();
        this.message = new SimpleStringProperty(); // Initialize StringProperty
        this.editable = true; // Set editable flag to false by default
    }

    public abstract String getMessageString();

    public LocalDateTime getDate() {
        return date;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getMessage() {
        return message.get();
    }

    public StringProperty messageProperty() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }
}