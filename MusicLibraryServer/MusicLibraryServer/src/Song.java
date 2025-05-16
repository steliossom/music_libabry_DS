// Somatopoulos Stylianos - 321/2021061
import java.io.*;

// The Song class represents a song with a title, artist, and duration.
class Song implements Serializable {
    private final String title;   // The title of the song
    private final String artist;  // The artist who performed the song
    private final int duration;   // Duration of the song in seconds

    // Constructor to initialize song details
    public Song(String title, String artist, int duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }


    // Overriding toString method to return a formatted string representation of the song
    @Override
    public String toString() {
        return title + " - " + artist + " (" + duration + " sec)";
    }
}
