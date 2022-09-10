#!/usr/bin/env bash

for file in rsakey.pem rsapubkey.pem
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
openssl genpkey -out rsakey.pem -algorithm RSA -pkeyopt rsa_keygen_bits:2048

# generate public key
openssl pkey -in rsakey.pem -pubout -out rsapubkey.pem

echo "rsakey.pem and rsapubkey.pem generated successfully."
