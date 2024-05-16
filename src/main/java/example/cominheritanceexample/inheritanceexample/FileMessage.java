package example.cominheritanceexample.inheritanceexample;


class FileMessage extends BaseMessage {
    private final String filePath;

    public FileMessage(String filePath, User author) {
        super(author);
        this.filePath = filePath;
    }

    @Override
    public String getMessageString() {
        return super.getDate().toString() + "\n" + author.toString() + ": " + filePath + "\n";
    }
}