import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.net.*;

// Entry point for the client application
public class MusicLibraryClient {
    public static void main(String[] args) throws IOException {
        // Start the client GUI in a separate thread
        SwingUtilities.invokeLater(() -> new ClientGUI().setVisible(true));
    }
}

// GUI-based client for interacting with the Music Library Server
class ClientGUI extends JFrame {
    private final JTextField titleField, descriptionField, genreField, yearField, songTitleField, songArtistField, songDurationField;
    private final JTextArea resultArea;
    private final List<Song> songsToAdd; // Stores songs before adding them to an album

    public ClientGUI() {
        setTitle("Music Library Client");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        songsToAdd = new ArrayList<>();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        
        // Album input fields
        panel.add(new JLabel("Album Title:"));
        titleField = new JTextField();
        panel.add(titleField);
        
        panel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        panel.add(descriptionField);
        
        panel.add(new JLabel("Genre:"));
        genreField = new JTextField();
        panel.add(genreField);
        
        panel.add(new JLabel("Year:"));
        yearField = new JTextField();
        panel.add(yearField);

        // Song input fields
        panel.add(new JLabel("Song Title:"));
        songTitleField = new JTextField();
        panel.add(songTitleField);

        panel.add(new JLabel("Song Artist:"));
        songArtistField = new JTextField();
        panel.add(songArtistField);

        panel.add(new JLabel("Duration (sec):"));
        songDurationField = new JTextField();
        panel.add(songDurationField);

        // Button to add a song to the list before adding the album
        JButton addSongButton = new JButton("Add Song");
        addSongButton.addActionListener(e -> addSong());
        panel.add(addSongButton);

        // Button to finish adding an album along with its songs
        JButton finishAddingButton = new JButton("Finish Adding Album");
        finishAddingButton.addActionListener(e -> addAlbum());
        panel.add(finishAddingButton);
        
        // Button to retrieve an album
        JButton getButton = new JButton("Get Album");
        getButton.addActionListener(e -> getAlbum());
        panel.add(getButton);
        
        // Button to delete an album
        JButton deleteButton = new JButton("Delete Album");
        deleteButton.addActionListener(e -> deleteAlbum());
        panel.add(deleteButton);
        
        // Button to update an album
        JButton updateButton = new JButton("Update Album");
        updateButton.addActionListener(e -> updateAlbum());
        panel.add(updateButton);

        add(panel, BorderLayout.NORTH);
        
        // Text area to display results or errors
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    // Adds a song to the list before adding it to an album
    private void addSong() {
        String title = songTitleField.getText();
        String artist = songArtistField.getText();
        int duration;
        try {
            duration = Integer.parseInt(songDurationField.getText());
        } catch (NumberFormatException e) {
            resultArea.setText("Invalid duration format");
            return;
        }
        songsToAdd.add(new Song(title, artist, duration));
        resultArea.setText("Song added: " + title);
    }

    // Retrieves an album from the server
    private void getAlbum() {
        String title = titleField.getText();
        try (Socket socket = new Socket("localhost", 5000);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject("GET"); // Send GET command
            out.writeObject(title); // Send album title
            
            Object response = in.readObject();
            if (response instanceof Album) {
                Album album = (Album) response;
                resultArea.setText(album.toString());
            } else {
                resultArea.setText("Album not found");
            }
        } catch (Exception e) {
            resultArea.setText("Error: " + e.getMessage());
        }
    }

    // Sends a request to delete an album
    private void deleteAlbum() {
        String title = titleField.getText();
        try (Socket socket = new Socket("localhost", 5000);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject("DELETE"); // Send DELETE command
            out.writeObject(title); // Send album title
            
            String response = (String) in.readObject();
            resultArea.setText(response);
        } catch (Exception e) {
            resultArea.setText("Error: " + e.getMessage());
        }
    }

    // Sends a request to add an album along with its songs
    private void addAlbum() {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String genre = genreField.getText();
        int year;
        try {
            year = Integer.parseInt(yearField.getText());
        } catch (NumberFormatException e) {
            resultArea.setText("Invalid year format");
            return;
        }
        
        try (Socket socket = new Socket("localhost", 5000);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject("ADD"); // Send ADD command
            out.writeObject(new Album(title, description, genre, year)); // Send album data
            out.writeObject(songsToAdd); // Send list of songs
            
            String response = (String) in.readObject();
            resultArea.setText(response);
            songsToAdd.clear(); // Clear song list after adding
        } catch (Exception e) {
            resultArea.setText("Error: " + e.getMessage());
        }
    }
    
    // Sends a request to update an existing album
    private void updateAlbum() {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String genre = genreField.getText();
        int year;
        try {
            year = Integer.parseInt(yearField.getText());
        } catch (NumberFormatException e) {
            resultArea.setText("Invalid year format");
            return;
        }
        
        try (Socket socket = new Socket("localhost", 5000);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject("UPDATE"); // Send UPDATE command
            out.writeObject(new Album(title, description, genre, year)); // Send album data
            out.writeObject(songsToAdd); // Send list of songs
            
            String response = (String) in.readObject();
            resultArea.setText(response);
        } catch (Exception e) {
            resultArea.setText("An error occurred while updating the album: " + e.getMessage());
        }
    }
}
