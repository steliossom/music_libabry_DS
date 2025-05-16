import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Album implements Serializable {
    private final String title;
    private final String description;
    private final String genre;
    private final int year;
    private List<Song> songs;

    public Album(String title, String description, String genre, int year) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.year = year;
        this.songs = new ArrayList<>();
    }

    public void addSong(Song song) {
        songs.add(song);
    }

  public void setSongs(ArrayList<Song> existingSongs) {
    this.songs = existingSongs;
}


    public List<Song> getSongs() {
        return songs;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Album: " + title + " (" + year + ")\n" +
               "Genre: " + genre + "\n" +
               "Description: " + description + "\n" +
               "Songs: " + songs + "\n";
    }

    
}
