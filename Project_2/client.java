package Project_2;
import java.io.*;
import javax.net.ssl.*;
import java.security.KeyStore;
import java.util.Scanner;


/*
 * This example shows how to set up a key manager to perform client
 * authentication.
 *
 * This program assumes that the client is not inside a firewall.
 * The application can be modified to connect to a server outside
 * the firewall by following SSLSocketClientWithTunneling.java.
 */

public class client {

  private static final int PORT = 9876;

  public static void main(String[] args) throws Exception {
    // Initilize varibles
    // collection of ciphers, protocol versions, trusted certificates, and other TLS options. 
    SSLSocketFactory factory = null;
    SSLContext sslContext = null; 
    KeyManagerFactory keyManagerFactory = null;
    KeyStore keyStore = null;
    TrustManagerFactory trustManagerFactory = null;
    Scanner scan = new Scanner(System.in);


    // Initilize input varibles 
    FileInputStream inputStream = null;
    String password = null; 
    String username = null;
    String input = null;
    String serverRespons = "";
    boolean authenticated = false;

    while(!authenticated) {
        
      System.out.println("Username: ");
      username = scan.nextLine();

      System.out.println("Password: ");
      password = scan.nextLine();

      try {
        // Try initialize connection with server using username and password      
        // Set the truststore using name and location
        System.setProperty("javax.net.ssl.trustStore", "clienttruststore");

<<<<<<< HEAD
        inputStream = new FileInputStream("Project_2/clientkeystores/"+username); //clientkeystore funkar
        
        char[] phrase = password.toCharArray();
  
        sslContext = SSLContext.getInstance("TLSv1.2");
        keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyStore = KeyStore.getInstance("JKS");
        trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
  
  
        // Load keystore using given username and password
        keyStore.load(inputStream, phrase); // This line loads the keystore containing the client's certificate and private key, using the given username and password 
        
        keyManagerFactory.init(keyStore, phrase); // This line initializes a key manager that can authenticate the client to the server during the TLS handshake
        trustManagerFactory.init(keyStore); // Responsible for creating trust managers that can verify the server's digital certificate during the TLS handshake
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null); // Security parameters that are used to establish a secure TLS connection.
  
        //Create an SSL socket for the (node) client and connect to the server
        factory = sslContext.getSocketFactory();
        SSLSocket clientSocket = (SSLSocket) factory.createSocket("localhost", PORT);
  
        // Set up client and start SSL handshake
        //client.setUseClientMode(true);
        clientSocket.startHandshake();
  
        // Set up input output streams using NetworkUtility
        PrintWriter  out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true); // Used to write data to the server over the network
        BufferedReader read = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  // Listen to server's messages
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); // Whats written in the console that will turn into a message
=======
        System.out.println("Password: ");
        password = scan.nextLine();

        //Check if such keystore exists
        inputStream = new FileInputStream("Project_2/clientkeystores/"+username);
        foundUser = true;
>>>>>>> e70ade350c71810518b5ebed1c3fe62e4a082e59

      
<<<<<<< HEAD
        // Start read from server and send. End session by writing "quit"
        // Send and read server's first message. Without this we have to initally send a empty message before we can send...
        out.println(" ");
        String serverResponse = read.readLine();
        System.out.println("Response: " + serverResponse);
        String[] info = serverResponse.split(";");
        System.out.println("Received: ");
=======
      keyManagerFactory.init(keyStore, phrase); // This line initializes a key manager that can authenticate the client to the server during the TLS handshake
      trustManagerFactory.init(keyStore); // Responsible for creating trust managers that can verify the server's digital certificate during the TLS handshake
      sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null); // Security parameters that are used to establish a secure TLS connection.

      //Create an SSL socket for the (node) client and connect to the server
      factory = sslContext.getSocketFactory();
      SSLSocket client = (SSLSocket) factory.createSocket("localhost", PORT);

      // Set up client and start SSL handshake
      //client.setUseClientMode(true);
      client.startHandshake();

      // Set up input output streams using NetworkUtility
      PrintWriter  out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true); // Used to write data to the server over the network
      BufferedReader read = new BufferedReader(new InputStreamReader(client.getInputStream()));  // Listen to server's messages
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); // Whats written in the console that will turn into a message


      // Start read from server and send. End session by writing "quit"


      // Send and read server's first message. Without this we have to initally send a empty message before we can send...
      out.println(" ");
      String serverResponse = read.readLine();
      System.out.println("Response: " + serverResponse);
      String[] info = serverResponse.split(";");
      System.out.println("Received: ");
      for (String s : info) {
          System.out.println(s);
      }

      // This part is only implemented for sending and recieving messages. 
      input = in.readLine();
      while(!input.equals("quit")) {
        out.println(input); // print and send the input
        System.out.println("Sent: "+input);

        // Read server's response
        serverRespons = read.readLine();
        info = serverRespons.split(";");
        System.out.println("Received: " + "\n");
>>>>>>> e70ade350c71810518b5ebed1c3fe62e4a082e59
        for (String s : info) {
            System.out.println(s);
        }

<<<<<<< HEAD
        // This part is only implemented for sending and recieving messages. 
        input = "";
        while(!input.equals("quit")) {
          input = in.readLine();
          out.println(input); // print and send the input
          System.out.println("Sent: "+input);

          // Read server's response
          serverRespons = read.readLine();
          info = serverRespons.split(";");
          System.out.println("Received: " + "\n");
          for (String s : info) {
            System.out.println(s);
          }
        }

        // Closes input, output and the socket in order to end the session
        out.close();
        in.close();
        clientSocket.close();

      } catch (Exception e) {
        System.out.print("Authentication failed. Try Again!");
        e.printStackTrace();
=======
        input = in.readLine();
>>>>>>> e70ade350c71810518b5ebed1c3fe62e4a082e59
      }
    }
  }
}