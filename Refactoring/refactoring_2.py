# Segunda Refatoração: Separar responsabilidades em uma classe
# Criaremos uma classe EmailService para organizar melhor as funções.
# email_service.py
import smtplib
from email.mime.text import MIMEText

class EmailService:
    def __init__(self, smtp_config):
        # Armazena a configuração SMTP recebida
        self.smtp_config = smtp_config

    def create_email(self, subject, body, recipient):
        # Cria uma mensagem de e-mail usando a configuração interna
        message = MIMEText(body)
        message['Subject'] = subject
        message['From'] = self.smtp_config['user']
        message['To'] = recipient
        return message

    def send_email(self, subject, body, recipient):
        # Cria a mensagem
        msg = self.create_email(subject, body, recipient)

        # Envia a mensagem usando o servidor SMTP
        with smtplib.SMTP(self.smtp_config['server'], self.smtp_config['port']) as server:
            server.starttls()
            server.login(self.smtp_config['user'], self.smtp_config['password'])
            server.send_message(msg)
""" 
Notas sobre essa refatoração:
- A responsabilidade agora é concentrada em uma classe.
- O smtp_config fica encapsulado.
- A classe pode ser instanciada com diferentes configurações conforme necessário.
- O código ficou muito mais testável e modular. 
"""