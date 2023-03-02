package Project_2.server.clientHandlers;

import javax.net.ssl.SSLSocket;

public class Nursehandler extends ClientHandler{
    public Nursehandler(SSLSocket client, String id, String division) {
        super(client, "nurse", id, division);
    }
}
