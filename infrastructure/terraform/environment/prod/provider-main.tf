############################
## Provider Module - Main ##
############################

terraform {
  required_version = "1.4.6"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.0"
    }

    tls = {
      source  = "hashicorp/tls"
      version = "~> 4.0.4"
    }

    local = {
      source  = "hashicorp/local"
      version = "~> 2.2.3"
    }
  }
}

provider "aws" {
  region  = var.aws_region
  profile = var.aws_profile
}
