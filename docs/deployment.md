# AWS EC2 Cloud Deployment Guide

This guide details deploying the Dockerized **MicroWatch** platform to an AWS EC2 instance.

## 1. AWS EC2 Setup
1. Launch an AWS EC2 instance running Ubuntu 22.04 LTS (t3.medium recommended).
2. Configure Security Group Inbound Rules:
   - Port 22 (SSH)
   - Port 8080 (API Gateway & WebSockets)
   - Port 5173 / 80 (React Dashboard)

## 2. Docker Installation on EC2
```bash
sudo apt update && sudo apt install -y docker.io docker-compose-v2 git
sudo systemctl enable --now docker
sudo usermod -aG docker $USER
```

## 3. Clone Repository & Launch Platform
```bash
git clone https://github.com/your-username/microwatch.git
cd microwatch

# Create production environment configuration
cp .env.example .env

# Launch all microservices and database
docker compose up -d --build
```

## 4. Verification
Check container status:
```bash
docker compose ps
```
View logs:
```bash
docker compose logs -f
```
Access dashboard at `http://<EC2_PUBLIC_IP>:5173`.
