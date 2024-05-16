package example.cominheritanceexample.inheritanceexample;

class ContactMessage extends BaseMessage {
    private final String name;
    private final int number;

    public ContactMessage(String name, int number, User author) {
        super(author);
        this.name = name;
        this.number = number;
    }

    @Override
    public String getMessageString() {
        return super.getDate().toString() + "\n" + author.toString() + ": " + name + " " + number + "\n";
    }
}