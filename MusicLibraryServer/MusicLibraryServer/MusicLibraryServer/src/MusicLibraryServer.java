import java.io.*;
import java.net.*;
import java.util.List;

public class MusicLibraryServer {
    public static void main(String[] args) {
        MusicLibrary library = new MusicLibrary();
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server started...");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                     ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {

                    String command = (String) input.readObject();
                    if (command.equals("ADD")) {
                        Album album = (Album) input.readObject();
                        List<Song> songs = (List<Song>) input.readObject();
                        output.writeObject(library.addAlbum(album, songs));
                    } else if (command.equals("GET")) {
                        String title = (String) input.readObject();
                        output.writeObject(library.getAlbum(title));
                    } else if (command.equals("DELETE")) {
                        String title = (String) input.readObject();
                        output.writeObject(library.deleteAlbum(title));
                    } else if (command.equals("UPDATE")) {
                        Album album = (Album) input.readObject();
                        output.writeObject(library.updateAlbum(album));
                    }
                } catch (Exception e) {
                }
            }
        } catch (IOException e) {
        }
    }
}
