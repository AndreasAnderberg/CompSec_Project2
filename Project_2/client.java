package Project_2;
import java.net.*;
import java.io.*;
import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.*;
import java.util.Scanner;
import java.util.*;


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

    // Set the truststore using name and location
    System.setProperty("javax.net.ssl.trustStore", "clienttruststore");

    // Initilize varibles
    // collection of ciphers, protocol versions, trusted certificates, and other TLS options. 
    SSLSocketFactory factory = null;
    SSLContext sslContext = null; 
    KeyManagerFactory keyManagerFactory = null;
    KeyStore keyStore = null;
    TrustManagerFactory trustManagerFactory = null;
    Scanner scan = new Scanner(System.in);


    // Initilize input varibles 
    boolean foundUser = false;
    FileInputStream inputStream = null;
    String password; 
    String username = null;

    // Loop until input is correct username and password
    // Using time-out
    //e.g "patien1", "nurse1", "doctor1", "GA1"
    // Each clientkeystore has CN=id number and O = role
    // CN = (10 numbers)
    // O = "patient", "doctor", "nurse", "GA"
    while(foundUser != true) {
      try {
        System.out.println("Username: ");
        username = scan.nextLine();

        System.out.println("Password: ");
        password = scan.nextLine();

        //Check if such keystore exists
        inputStream = new FileInputStream("./clientkeystore/"+ username);
        foundUser = true;

      } catch (FileNotFoundException e) {
        foundUser = false;
        System.out.println("Username or password is wrong. ");
      }
    }

    // Try initalize connection with server using username and password
    try {
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
      SSLSocket client = (SSLSocket) factory.createSocket("localhost", PORT);

      // Set up client and start SSL handshake
      client.setUseClientMode(true);
      client.startHandshake();
      System.out.print("Handshake started....");
      System.out.print("Client: "+client);

      // Set up input output streams using NetworkUtility
      PrintWriter  toServer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true); // Used to write data to the server over the network
      BufferedReader fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
      NetworkUtility nu = new NetworkUtility(toServer, fromServer); 

    } catch (Exception e) {
      e.printStackTrace();
    }


  }
}