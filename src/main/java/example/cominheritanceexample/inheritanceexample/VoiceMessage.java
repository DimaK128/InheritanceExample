package example.cominheritanceexample.inheritanceexample;

//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

//import java.io.File;


public class VoiceMessage extends BaseMessage {
    private final String audioFilePath;
    private final Duration duration;

    public VoiceMessage(String audioFilePath, javafx.util.Duration duration, User author) {
        super(author);
        this.audioFilePath = audioFilePath;
        this.duration = duration;
    }

    // public void playAudio() {
    //   Media media = new Media(new File(audioFilePath).toURI().toString());
    //    MediaPlayer mediaPlayer = new MediaPlayer(media);
    //     mediaPlayer.play();
    //  }

    @Override
    public String getMessageString() {
        return super.getDate().toString() + "\n" + author.toString() + ": Voice Message\nDuration: " + duration.toSeconds() + " seconds\n";
    }
}