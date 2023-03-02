package Project_2.server.clientHandlers;

import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public abstract class Handler implements Runnable{
    protected final SSLSocket client;
    protected final String role;
    protected final String id;
    protected final String division;

    public Handler  (SSLSocket client, String role, String id, String division) {
        this.client = client;
        this.role = role;
        this.id = id;
        this.division = division;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            // Read client message
            System.out.println("Client's role: " + role);

            handleRequests(out, in);

            in.close();
            out.close();
            client.close();
            System.out.println("client disconnected");
        } catch (IOException e) {
            System.out.println("Client died: " + e.getMessage());
            e.printStackTrace();
        }
    }

    protected abstract void handleRequests(PrintWriter out, BufferedReader in) throws IOException;
    public abstract void saveRecord(PrintWriter out, BufferedReader in) throws IOException;
    public abstract void read(PrintWriter out, BufferedReader in) throws IOException;
    public abstract void destroyRecord(PrintWriter out, BufferedReader in) throws IOException;
}
