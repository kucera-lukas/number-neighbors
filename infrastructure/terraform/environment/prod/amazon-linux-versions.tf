##############################################
# Get latest Amazon Linux AMI with Terraform #
##############################################

data "aws_ami" "amazon-linux-2-kernel-5" {
  most_recent = true
  owners      = ["amazon"]

  filter {
    name   = "name"
    values = ["amzn2-ami-kernel-5*"]
  }
}
