package server;

import data.Data;
import database.DatabaseConnectionException;
import database.EmptySetException;
import database.NoValueException;
import mining.ClusteringRadiusException;
import mining.EmptyDatasetException;
import mining.QTMiner;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;

public class ServerOneClient extends Thread {
    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private QTMiner kmeans;
    private Data data;

    public ServerOneClient(Socket s) throws IOException {
        this.socket = s;

        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());

        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object codeObject = in.readObject();
                if (!(codeObject instanceof Integer)) {continue;}

                int code = (Integer) codeObject;

                switch (code) {
                    case 0:
                        storeTableFromDb();
                        break;
                    case 1:
                        learningFromDbTable();
                        break;
                    case 2:
                        storeClusterInFile();
                        break;
                    case 3:
                        learningFromFile();
                        break;
                    default:
                        out.writeObject("KO: Comando sconosciuto");
                }
            }
        }catch (IOException | ClassNotFoundException e){
            System.err.println("Errore di comunicazione : " + e.getMessage());
        }finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.err.println("Errore chiusura socket.");
            }
        }
    }

    public void storeTableFromDb() throws IOException, ClassNotFoundException{
        String tabName = (String) in.readObject();
        try {
            this.data = new Data(tabName);
            out.writeObject("OK");
        } catch ( NoValueException | DatabaseConnectionException | SQLException | EmptySetException e) {
            out.writeObject("KO: " + e.getMessage());
        }
    }

    public void learningFromDbTable() throws IOException, ClassNotFoundException{
        if (this.data == null) {
            out.writeObject("KO: Data not loaded");
            return;
        }
        try {
            double r = (Double) in.readObject();
            this.kmeans = new QTMiner(r);
            out.writeObject("OK");
            out.writeObject(kmeans.compute(data));
            out.writeObject(kmeans.getC().toString(data));
        }catch (ClusteringRadiusException | EmptyDatasetException e){
            out.writeObject("KO: " + e.getMessage());
        }
    }

    public void storeClusterInFile() throws IOException, ClassNotFoundException {
        String FileName = (String) in.readObject();
        try {
            kmeans.salva(FileName);
            out.writeObject("OK");
        }catch (Exception e){
            out.writeObject("KO: " + e.getMessage());
        }
    }

    public void learningFromFile() throws IOException, ClassNotFoundException {
        String FileName = (String) in.readObject();
        try {
            this.kmeans = new QTMiner(FileName);
            out.writeObject("OK");
            out.writeObject(kmeans.getC().toString());
        }catch (Exception e){
            out.writeObject("KO: " + e.getMessage());
        }
    }
}
