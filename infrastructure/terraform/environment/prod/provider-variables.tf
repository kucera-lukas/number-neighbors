#####################################
## AWS Provider Module - Variables ##
#####################################

# AWS connection & authentication

variable "aws_region" {
  type        = string
  description = "AWS region"
  default     = "eu-central-1"
}

variable "aws_profile" {
  type        = string
  description = "AWS profile"
  default     = "terraform"
}
