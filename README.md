# CompSec_Project2 - Medical Records and Secure Connections

## Introduction
The hospital's record management system consists of two different applications, a client and a server. These are connected using transport layer security (TLS) which is a protocol for establishing a secure connection between two entities, for instance, a web browser (client) and a website (server). This way of establishing a connection makes it possible to use the record management system through the open network. Our record management system has a role-based access control meaning that the client whether they are patients, nurses, doctors, or government agency the roles will have different access privileges in order to ensure that sensitive or confidential information is protected and that only authorized users can access and manipulate it.

---


In this Java project we uses SSL/TLS protocol for establishing secure connections between a client and a server. The javax.net.ssl library provides the necessary classes and interfaces for secure socket communication, while the java.security library provides the classes and interfaces required for secure key management. The javax.net library provides a set of networking classes for establishing sockets and connections. Additionally, the project requires the use of a KeyStore, which is a storage facility for cryptographic keys and certificates, and X509Certificate, which is a standard for public key certificates.

##  Instructions

### Login
Each client has their own keystore which contains a unique certification with personal attributes such as role, division and identification number.
Each role and individual specified access and allowed actions according to this schema:

| User Role | Access Control |
| --- | --- |
| Patient | Allowed to read own records |
| Nurse | Read and write to all records associated with the nurse's name and division. Can also read all records associated with the same division. |
| Doctor | Read and write to all records associated with the doctor's name and division. Can also read all records associated with the same division. Can create new records for a patient if treating them, and associate a nurse with the record. |
| Government Agency | Allowed to read and delete all types of records. |

### Usernames and passwords

| Role    | Name       | ID Number   | Division    | Password      |
| ------- | ---------- | -----------| -----------| ------------- |
| Nurse   | nurse1     | 1234567890 | 1          | psw1234567890 |
| Nurse   | nurse2     | 1234567891 | 2          | psw1234567891 |
| Doctor  | doctor1    | 1234567892 | 1          | psw1234567892 |
| Doctor  | doctor2    | 1234567893 | 2          | psw1234567893 |
| Patient | patient1   | 1234567895 | 0          | psw1234567895 |
| Patient | patient2   | 1234567896 | 0          | psw1234567896 | 
| Government agency      | ga1        | 1234567894 | 0          | psw1234567894 |


&nbsp;

_Divisions available are 1, 2 and 3. Division 0 is only added to client for implementation puposes only_

&nbsp;

### Run Client & Server
Run the application and start testning using the following commands in two seperate terminals:
&nbsp;

Terminal 1
&nbsp;

_javac server.java_
&nbsp;

_java server.java_
&nbsp;
&nbsp;
&nbsp;


Terminal 2
&nbsp;

_javac client.java_
&nbsp;

_java client.java_
&nbsp;




