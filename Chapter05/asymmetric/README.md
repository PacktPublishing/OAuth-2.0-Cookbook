# Using keytool to generate key pair

## Generate a keypair
keytool -genkeypair -alias asym -keyalg RSA -keypass abc123 -keystore keystore.jks -storepass abc123

## Extract the public key
keytool -list -rfc --keystore keystore.jks | openssl x509 -inform pem -pubkey

