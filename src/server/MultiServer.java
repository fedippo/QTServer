package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {
    private final int PORT;

    public static void main(String[] args) {
        new MultiServer(8080);
    }

    public MultiServer(int port){
        this.PORT = port;
        run();
    }

    public void run() {
        ServerSocket serverSocket = null;
        try {
            // Istanzia la ServerSocket in attesa sulla porta
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server avviato sulla porta " + PORT);

            while (true) {
                // Pone in attesa di richieste di connessione
                Socket socket = serverSocket.accept();
                System.out.println("Nuovo client connesso: " + socket.getInetAddress());

                try {
                    // Ad ogni nuova richiesta istanzia ServerOneClient
                    new ServerOneClient(socket);
                } catch (IOException e) {
                    System.err.println("Errore nell'inizializzazione del client: " + e.getMessage());
                    socket.close(); // Chiude la socket se il thread non può partire
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nel server: " + e.getMessage());
        } finally {
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                System.err.println("Errore nella chiusura del server: " + e.getMessage());
            }
        }
    }

}
