package Project_2;
import java.net.*;
import java.io.*;
import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import java.util.Scanner;
import java.security.KeyStore;
import java.security.cert.*;

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
    while(foundUser != true) {
      try {
        // Ask user for username and password
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

      // .... fortsätter här sen
    }


  }
}