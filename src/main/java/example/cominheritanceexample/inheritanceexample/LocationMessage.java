package example.cominheritanceexample.inheritanceexample;

class LocationMessage extends BaseMessage {
    private final float longitude;
    private final float latitude;

    public LocationMessage(float latitude, float longitude, User author) {
        super(author);
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String getMessageString() {
        return super.getDate().toString() + "\n" + author.toString() + ": " +
                "Location: " + this.longitude + ", " + this.latitude + "\n";
    }
}