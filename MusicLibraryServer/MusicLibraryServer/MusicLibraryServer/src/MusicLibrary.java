import java.util.ArrayList;
import java.util.List;

class MusicLibrary {
    private final List<Album> albums = new ArrayList<>();
    private final List<Song> existingSongs = new ArrayList<>();

    public synchronized String addAlbum(Album album, List<Song> songs) {
        album.getSongs().addAll(songs);
        albums.add(album);
        existingSongs.addAll(songs); // Διόρθωση: Προσθήκη όλων των τραγουδιών σωστά
        return "Album and songs added successfully!";
    }

    public synchronized Album getAlbum(String title) {
        return albums.stream().filter(a -> a.getTitle().equalsIgnoreCase(title)).findFirst().orElse(null);
    }

    public synchronized String deleteAlbum(String title) {
        return albums.removeIf(a -> a.getTitle().equalsIgnoreCase(title)) ? "Album deleted." : "Album not found.";
    }

 public synchronized String updateAlbum(Album updatedAlbum) {
    for (int i = 0; i < albums.size(); i++) {
        Album existingAlbum = albums.get(i);
        
        if (existingAlbum.getTitle().equalsIgnoreCase(updatedAlbum.getTitle()) || existingAlbum.equals(updatedAlbum)) {
            // Αν η λίστα τραγουδιών είναι null, την αρχικοποιούμε
            if (updatedAlbum.getSongs() == null) {
                updatedAlbum.setSongs(new ArrayList<>());
            }

            // Αν δεν έχουν δοθεί νέα τραγούδια, διατηρούμε τα παλιά
            if (updatedAlbum.getSongs().isEmpty()) {
                updatedAlbum.getSongs().addAll(existingAlbum.getSongs());
            }

            // Αντικαθιστούμε το άλμπουμ
            albums.set(i, updatedAlbum);
            return "Album updated successfully!";
        }
    }

    // Αν δε βρεθεί το άλμπουμ με τον παλιό τίτλο, αναζητούμε για αλλαγή τίτλου
    for (int i = 0; i < albums.size(); i++) {
        Album existingAlbum = albums.get(i);

        if (existingAlbum.getSongs().equals(updatedAlbum.getSongs())) {
            // Αφαιρούμε το παλιό άλμπουμ και προσθέτουμε το νέο
            albums.remove(i);
            albums.add(updatedAlbum);
              // Αν δεν έχουν δοθεί νέα τραγούδια, διατηρούμε τα παλιά
            if (updatedAlbum.getSongs().isEmpty()) {
                updatedAlbum.getSongs().addAll(existingAlbum.getSongs());
            }
            return "Album updated successfully!";
        }
    }

    for (Album existingAlbum : albums) {
        if (existingAlbum.getTitle().equalsIgnoreCase(updatedAlbum.getTitle())) {
            // Προσθήκη νέων τραγουδιών χωρίς να αφαιρεθούν τα παλιά
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
    return "Album not found.";
}


    
    public synchronized String addSongToAlbum(String albumTitle, Song song) {
    Album album = getAlbum(albumTitle);
    if (album != null) {
        if (!album.getSongs().contains(song)) { // Αποφυγή διπλών εγγραφών
            album.getSongs().add(song);
            existingSongs.add(song); // Προσθήκη στη συνολική λίστα τραγουδιών
            return "Song added successfully!";
        } else {
            return "Song already exists in the album.";
        }
    }
    return "Album not found.";
}

    public synchronized String addSongsToAlbum(String albumTitle, List<Song> songs) {
        Album album = getAlbum(albumTitle);
        if (album != null) {
            album.getSongs().addAll(songs);
            existingSongs.addAll(songs); // Ενημέρωση και της λίστας των τραγουδιών
            return "Songs added successfully!";
        }
        return "Album not found.";
    }

    public synchronized List<Song> getSongsFromAlbum(String albumTitle) {
        Album album = getAlbum(albumTitle);
        return album != null ? album.getSongs() : null;
    }
}
