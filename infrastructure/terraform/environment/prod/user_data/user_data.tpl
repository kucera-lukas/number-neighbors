#!/bin/bash
sudo su
yum update -y

# docker
amazon-linux-extras install docker
systemctl start docker.service
systemctl enable docker.service
# shellcheck disable=SC2154
usermod -a -G docker "${var.linux_ec2_user}"

# postgres
amazon-linux-extras install postgresql14
yum install postgresql postgresql-server -y
postgresql-setup initdb
systemctl start postgresql
systemctl enable postgresql
su - postgres -c "createdb number-neighbors"

# deploy script
mkdir number-neighbors
cat <<EOF > number-neighbors/deploy.sh
#!/bin/bash

docker run \
    --detach \
    --env-file .env \
    --net host \
    --name number-neighbors \
    $IMAGE_NAME:sha-$SHA
EOF
chmod +x number-neighbors/deploy.sh
