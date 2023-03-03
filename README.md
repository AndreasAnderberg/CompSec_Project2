# CompSec_Project2 - Medical Records and Secure Connections

## Introduction
This application assumes that a client (patient, doctor, nurse, or government agency) has unique login details which is used for reading, writing and deleting medical records.

According to this schema, patients, doctors, nurses, and government agencies have access to medical records.

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

### Username and password for testing

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


_javac server.java_
_java server.java_
&nbsp;

_javac client.java_
_java client.java_



