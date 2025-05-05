# main.py (ou controller, ou serviço que usa o envio de email)
from refactoring_2 import EmailService
from config import smtp_config

# Instanciando a classe com a configuração injetada
email_service = EmailService(smtp_config)

# Enviando o e-mail
email_service.send_email(
    subject="Bem-vindo!",
    body="Olá, seja bem-vindo ao nosso sistema.",
    recipient="usuario@example.com"
)
