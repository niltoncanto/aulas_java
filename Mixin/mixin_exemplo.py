class LoggingMixin:
    def log(self, message):
        print(f"Log: {message}")

class DatabaseConnection:
    def connect(self):
        print("Conectando ao banco de dados...")

class User(DatabaseConnection, LoggingMixin):
    def save(self):
        self.log("Salvando usuário no banco de dados.")
        self.connect()

user = User()
user.save()

