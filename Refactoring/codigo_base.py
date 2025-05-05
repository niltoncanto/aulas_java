# config.py
SMTP_SERVER = "smtp.example.com"
SMTP_PORT = 587
SMTP_USER = "noreply@example.com"
SMTP_PASSWORD = "password123"

# email_sender.py
import smtplib
from email.mime.text import MIMEText
import config

def create_email(subject, body, recipient):
    # Criação da mensagem de e-mail
    message = MIMEText(body)
    message['Subject'] = subject
    message['From'] = config.SMTP_USER
    message['To'] = recipient
    return message

def send_email(subject, body, recipient):
    # Criação da mensagem
    msg = create_email(subject, body, recipient)

    # Envio do e-mail usando configuração global
    with smtplib.SMTP(config.SMTP_SERVER, config.SMTP_PORT) as server:
        server.starttls()
        server.login(config.SMTP_USER, config.SMTP_PASSWORD)
        server.send_message(msg)
