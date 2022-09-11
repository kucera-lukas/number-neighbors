#!/usr/bin/env bash

DIR=src/main/resources

KEY=rsakey.pem
PUBKEY=rsapubkey.pem

KEY_FILE=$DIR/$KEY
PUBKEY_FILE=$DIR/$PUBKEY

for file in $KEY_FILE $PUBKEY_FILE
do
    if [ -e "$file" ]; then
        read -p "$file already exists. Would you like to overwrite it? [y/N]" yn
        case $yn in
            [Yy]* ) rm $file;;
            * ) echo "quitting..." && exit 0;;
        esac
    fi
done

# generate private key
openssl genpkey -out $KEY_FILE -algorithm RSA -pkeyopt rsa_keygen_bits:2048

# generate public key
openssl pkey -in $KEY_FILE -pubout -out $PUBKEY_FILE

echo "$KEY and $PUBKEY successfully generated in the $DIR directory"
