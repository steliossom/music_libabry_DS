import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// The Album class represents a music album with a title, description, genre, year, and a list of songs.
class Album implements Serializable {
    private final String title;
    private final String description;
    private final String genre;
    private final int year;
    private List<Song> songs;

    // Constructor to initialize album details
    public Album(String title, String description, String genre, int year) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.year = year;
        this.songs = new ArrayList<>();
    }

    // Method to add a song to the album
    public void addSong(Song song) {
        songs.add(song);
    }

    // Method to set an existing list of songs to the album
    public void setSongs(ArrayList<Song> existingSongs) {
        this.songs = existingSongs;
    }

    // Method to retrieve the list of songs in the album
    public List<Song> getSongs() {
        return songs;
    }

    // Method to retrieve the title of the album
    public String getTitle() {
        return title;
    }

    // Overriding toString method to return a formatted string representation of the album
    @Override
    public String toString() {
        return "Album: " + title + " (" + year + ")\n" +
               "Genre: " + genre + "\n" +
               "Description: " + description + "\n" +
               "Songs: " + songs + "\n";
    }
}
