#Primeira Refatoração: Injeção de dependências via parâmetros
#Passamos a configuração como argumento explícito nas funções.

# email_sender.py
import smtplib
from email.mime.text import MIMEText

def create_email(subject, body, recipient, sender_email):
    # Criação da mensagem de e-mail
    message = MIMEText(body)
    message['Subject'] = subject
    message['From'] = sender_email
    message['To'] = recipient
    return message

def send_email(subject, body, recipient, smtp_config):
    # Criação da mensagem
    msg = create_email(subject, body, recipient, smtp_config['user'])

    # Envio do e-mail usando configuração recebida como parâmetro
    with smtplib.SMTP(smtp_config['server'], smtp_config['port']) as server:
        server.starttls()
        server.login(smtp_config['user'], smtp_config['password'])
        server.send_message(msg)

""" 
Notas sobre essa refatoração:
- config não é mais importado diretamente.
- O send_email recebe um dicionário smtp_config contendo os dados.
- Agora o envio pode ser facilmente testado com diferentes configurações sem alterar o módulo. 
"""