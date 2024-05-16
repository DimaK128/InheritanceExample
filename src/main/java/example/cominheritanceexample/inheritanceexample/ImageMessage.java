package example.cominheritanceexample.inheritanceexample;

class ImageMessage extends BaseMessage {
    private final String imageUrl;



    public ImageMessage(String imageUrl, User author) {
        super(author);
        this.imageUrl = imageUrl;

    }


    @Override
    public String getMessageString() {
        return super.getDate().toString() + "\n" + author.toString() + ": " + imageUrl + "\n";
    }
}