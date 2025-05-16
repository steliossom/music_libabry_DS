//Somatopoulos Stylianos - 321/2021061
import java.io.*;
import java.net.*;
import java.util.List;

// Server that handles client requests for managing a music library.
public class MusicLibraryServer {
    public static void main(String[] args) {
                // Initialize the music library.
        MusicLibrary library = new MusicLibrary();
        try (ServerSocket serverSocket = new ServerSocket(5000)) { // Open server socket on port 5000
            System.out.println("Server started...");

            while (true) { // Keep listening for client connections
                try (Socket socket = serverSocket.accept();  // Accept client connection
                     ObjectInputStream input = new ObjectInputStream(socket.getInputStream());   // Input stream
                     ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) { // Output stream

                     // Read client command
                    String command = (String) input.readObject();
                    // Execute corresponding operation based on command
                    if (command.equals("ADD")) { 
                        Album album = (Album) input.readObject();  // Read album data
                        List<Song> songs = (List<Song>) input.readObject(); // Read list of songs
                        output.writeObject(library.addAlbum(album, songs)); // Add album and return response
                    } else if (command.equals("GET")) {
                        String title = (String) input.readObject(); // Read album title
                        output.writeObject(library.getAlbum(title)); // Retrieve album and return response
                    } else if (command.equals("DELETE")) {
                        String title = (String) input.readObject(); // Read album title
                        output.writeObject(library.deleteAlbum(title)); // Delete album and return response
                    } else if (command.equals("UPDATE")) {
                        Album album = (Album) input.readObject(); // Read updated album data
                        output.writeObject(library.updateAlbum(album)); // Update album and return response
                    }
                } catch (Exception e) {
                }
            }
        } catch (IOException e) {
        }
    }
}
