import os

# Prompt user for number of keystores to create
num_keystores = int(input("How many keystores do you want to create? "))

# 1. Creating CA certificate
os.system(
    "openssl req -CAcreateserial -x509 -sha256 -nodes -newkey rsa:2048 -keyout CA.key -out CAcert.crt"
)
# 2. Create truststore named "clienttruststore" that contains the CA certificate
os.system(
    "keytool -importcert -file CAcert.crt -keystore clienttruststore -alias clienttruststore_alias -storepass password -noprompt -dname \"CN=clienttruststore\""
)
# 3. For each person, generate a keypair and a certificate signing request   (CSR) using their unique information (CN = id number, O = role).
for i in range(num_keystores):
    # 3.1 Prompt user for idnumber and role
    idnumber = input(
        f"Enter idnumber for keystore (10 numbers) {i+1}: "
    )  # 10 numbers
    role = input(
        f"Enter role for keystore (nurse, doctor, patient, ga) {i+1}: "
    )
    division = input(
        f"Enter division for keystore (1, 2, 3) {i+1}: "
    )
    password = "psw"+idnumber

    # View idnumber role password
    print("idnumber: " + idnumber)
    print("role: " + role)
    print("password: " + password)
    print("division: " + division)

    # 3.2 Create keystore: the keypass and storepass is same for testing only
    os.system(
        f"keytool -genkey -alias {idnumber} -keystore {idnumber} -keyalg RSA -storepass {password} -keypass {password} -dname \"CN={idnumber}, O={role}, S={division}\""
    )

    # 3.3 Create Certificate Signing Request (CSR) for keys in clientkeystore
    os.system(
        f"keytool -certreq -keystore {idnumber} -file {idnumber}.csr -alias {idnumber} -storepass {password}"
    )

    # 3.4 Sign CSR (creates an certificate for the client)
    os.system(
        f"openssl x509 -req -in {idnumber}.csr -CA CAcert.crt -CAkey CA.key -out {idnumber}_signed.crt"
    )

    # 3.5 Import certificate chain into clientkeystore (first CA.cert then signed keypair)
    os.system(
        f"keytool -importcert -noprompt -file CAcert.crt -keystore {idnumber} -alias {idnumber}_new -storepass {password}"
    )
    os.system(
        f"keytool -importcert -trustcacerts -file {idnumber}_signed.crt -keystore {idnumber} -alias {idnumber} -storepass {password}"
    )
    os.system(
        f"keytool -list -v -keystore {idnumber} -storepass {password}"
    )


# 4 Create the same for the server-side
os.system(
    f"keytool -genkey -noprompt -keystore serverkeystore -keyalg RSA -dname \"CN=MyServer\" -alias serverkeystore_alias_new -storepass password -keypass password"
)
os.system(
    f"keytool -certreq -keystore serverkeystore -file server.csr -alias serverkeystore_alias_new -storepass password"
)
os.system(
    f"openssl x509 -req  -in server.csr -CA CAcert.crt -CAkey CA.key -out server_signed.crt"
)
os.system(
    f"keytool -importcert -noprompt -file CAcert.crt -keystore serverkeystore -alias serverkeystore_alias -storepass password"
)
os.system(
    f"keytool -importcert -trustcacerts -file server_signed.crt -keystore serverkeystore -alias serverkeystore_alias_new -storepass password"
)
os.system(
    f"keytool -list -v -keystore serverkeystore -storepass password"
)
os.system(
    f"keytool -importcert -file CAcert.crt -keystore servertruststore -alias servertruststore_alias -storepass password -noprompt"
)

print("Bye!!!")
