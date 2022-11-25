# Application Definition
app_name        = "number-neighbors"
app_environment = "prod"

# Network
vpc_cidr           = "10.11.0.0/16"
public_subnet_cidr = "10.11.1.0/24"

# AWS Settings
aws_region = "eu-central-1"
aws_profile = "terraform"

# Linux Virtual Machine
linux_instance_type               = "t2.micro"
linux_associate_public_ip_address = true
linux_root_volume_size            = 8
linux_root_volume_type            = "gp2"
