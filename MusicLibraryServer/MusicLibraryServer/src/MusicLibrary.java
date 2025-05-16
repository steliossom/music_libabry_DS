//Somatopoulos Stylianos - 321/2021061
import java.util.ArrayList;
import java.util.List;

// Class representing the music library, which manages albums and songs.
class MusicLibrary {
    private final List<Album> albums = new ArrayList<>(); // List of albums in the library
    private final List<Song> existingSongs = new ArrayList<>(); // List of all existing songs

    // Adds an album along with its songs to the library.
    public String addAlbum(Album album, List<Song> songs) {
        album.getSongs().addAll(songs);
        albums.add(album);
        existingSongs.addAll(songs);
        return "Album and songs added successfully!";
    }

    // Retrieves an album by its title.
    public Album getAlbum(String title) {
        return albums.stream().filter(a -> a.getTitle().equalsIgnoreCase(title)).findFirst().orElse(null);
    }

    // Deletes an album by its title.
    public String deleteAlbum(String title) {
        return albums.removeIf(a -> a.getTitle().equalsIgnoreCase(title)) ? "Album deleted." : "Album not found.";
    }

// Updates an existing album in the library with new data.
public String updateAlbum(Album updatedAlbum) {
    // First, search for the album by title or exact match
    for (int i = 0; i < albums.size(); i++) {
        Album existingAlbum = albums.get(i);
        
        // If the title matches or the album is equal, update it
        if (existingAlbum.getTitle().equalsIgnoreCase(updatedAlbum.getTitle()) || existingAlbum.equals(updatedAlbum)) {
            // If the updated album has no song list, initialize it
            if (updatedAlbum.getSongs() == null) {
                updatedAlbum.setSongs(new ArrayList<>());
            }

            // If no new songs are provided, keep the existing songs
            if (updatedAlbum.getSongs().isEmpty()) {
                updatedAlbum.getSongs().addAll(existingAlbum.getSongs());
            }

            // Replace the old album with the updated one
            albums.set(i, updatedAlbum);
            return "Album updated successfully!";
        }
    }

    // If no exact match was found, check for possible title change by matching songs
    for (int i = 0; i < albums.size(); i++) {
        Album existingAlbum = albums.get(i);

        // If the song list matches an existing album, assume a title change
        if (existingAlbum.getSongs().equals(updatedAlbum.getSongs())) {
            // Remove the old album and add the new one with updated details
            albums.remove(i);
            albums.add(updatedAlbum);

            // If no new songs are given, retain the old songs
            if (updatedAlbum.getSongs().isEmpty()) {
                updatedAlbum.getSongs().addAll(existingAlbum.getSongs());
            }
            return "Album updated successfully!";
        }
    }

    // If the album exists by title, but new songs are provided, add them without removing existing ones
    for (Album existingAlbum : albums) {
        if (existingAlbum.getTitle().equalsIgnoreCase(updatedAlbum.getTitle())) {
            // Add new songs only if they are not already present
            if (updatedAlbum.getSongs() != null) {
                for (Song newSong : updatedAlbum.getSongs()) {
                    if (!existingAlbum.getSongs().contains(newSong)) {
                        existingAlbum.getSongs().add(newSong);
                        existingSongs.add(newSong);
                    }
                }
            }
            return "Album updated successfully with new songs!";
        }
    }

    // If no match is found, return a failure message
    return "Album not found.";
}

    
    // Adds a single song to an album.
    public String addSongToAlbum(String albumTitle, Song song) {
        Album album = getAlbum(albumTitle);
        if (album != null) {
            if (!album.getSongs().contains(song)) {
                album.getSongs().add(song);
                existingSongs.add(song);
                return "Song added successfully!";
            } else {
                return "Song already exists in the album.";
            }
        }
        return "Album not found.";
    }

    // Adds multiple songs to an album.
    public String addSongsToAlbum(String albumTitle, List<Song> songs) {
        Album album = getAlbum(albumTitle);
        if (album != null) {
            album.getSongs().addAll(songs);
            existingSongs.addAll(songs);
            return "Songs added successfully!";
        }
        return "Album not found.";
    }

    // Retrieves all songs from a specified album.
    public List<Song> getSongsFromAlbum(String albumTitle) {
        Album album = getAlbum(albumTitle);
        return album != null ? album.getSongs() : null;
    }
}

