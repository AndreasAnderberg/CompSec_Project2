package Project_2.server;

import java.io.*;
import javax.net.ssl.*;
import java.security.KeyStore;

/*
 * This example shows how to set up a key manager to perform server
 * authentication.
 */

public class server2 {
  private static final int PORT = 9876;

  public static void main(String[] args) throws Exception {
    SSLServerSocket serverSocket = null;

    System.setProperty("javax.net.ssl.trustStore", "./server/servertruststore");


    // Initialize variables
    SSLContext sslContext = null;
    KeyManagerFactory keyManagerFactory = null;
    KeyStore keyStore = null;

    try {
      // Set up the SSL context with the server's key and certificate
      sslContext = SSLContext.getInstance("TLSv1.2");
      keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
      keyStore = KeyStore.getInstance("JKS");
      char[] keyPassphrase = "password".toCharArray(); // password for the server keystore
      keyStore.load(new FileInputStream("serverkeystore"), keyPassphrase);
      keyManagerFactory.init(keyStore, keyPassphrase);
      sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

      // Create the SSL server socket
      SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
      serverSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(PORT);

      // Require client authentication
      serverSocket.setNeedClientAuth(true);

      // Wait for clients to connect
      System.out.println("Waiting for clients to connect...");
      SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
      System.out.println("Client connected: " + clientSocket);

      // Set up input and output streams
      PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      // Start reading and writing messages
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        System.out.println("Received from client: " + inputLine);
        out.println("Server received: " + inputLine);
        if (inputLine.equals("quit"))
          break;
      }

      // Close input and output streams and the client socket
      out.close();
      in.close();
      clientSocket.close();

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (serverSocket != null) {
        try {
          serverSocket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
