package example.cominheritanceexample.inheritanceexample;

class TextMessage extends BaseMessage {
    private final String text;

    public TextMessage(String text, User author) {
        super(author);
        this.text = text;
    }

    @Override
    public String getMessageString() {
        return  super.getDate().toString() + "\n" + author.toString() + ": " + text + "\n";
    }
}