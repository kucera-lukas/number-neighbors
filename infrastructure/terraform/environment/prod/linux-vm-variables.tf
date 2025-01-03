########################################
## Virtual Machine Module - Variables ##
########################################

variable "linux_instance_type" {
  type        = string
  description = "EC2 instance type for Linux Server"
  default     = "t2.micro"
}

variable "linux_associate_public_ip_address" {
  type        = bool
  description = "Associate a public IP address to the EC2 instance"
  default     = true
}

variable "linux_root_volume_size" {
  type        = number
  description = "Volume size of root volume of Linux Server"
}

variable "linux_root_volume_type" {
  type        = string
  description = "Volume type of root volume of Linux Server. Can be standard, gp3, gp2, io1, sc1 or st1"
  default     = "gp2"
}

variable "linux_ec2_user" {
  type        = string
  description = "Linux system user account name"
  default     = "ec2-user"
}

variable "linux_database_name" {
  type        = string
  description = "Name of the PostgreSQL database"
  default     = "numberneighbors"
}

variable "linux_postgres_user" {
  type        = string
  description = "Username of the PostgreSQL role"
  default     = "numberneighborsuser"
}

variable "linux_postgres_password" {
  type        = string
  description = "Password of the PostgreSQL role"
  default     = "numberneighborspassword"
}

variable "linux_domain" {
  type        = string
  description = "Server domain name"
  default     = "api.numberneighbors.lukaskucera.com"
}

variable "linux_certbot_email" {
  type        = string
  description = "Email used for registration and recovery contact of letsencrypt/certbot"
  default     = "lukas.kucera.g@gmail.com"
}

variable "linux_client_url" {
  type        = string
  description = "Uniform Resource Locator of the client website"
  default     = "https://numberneighbors.lukaskucera.com/"
}
