


class Singleton:
    __instance = None  # Variável de classe para armazenar a única instância
    __initialized = False  # Controle para garantir a inicialização apenas uma vez

    def __new__(cls, *args, **kwargs):
        if cls.__instance is None:
            cls.__instance = super(Singleton, cls).__new__(cls)  # Cria a instância única
        return cls.__instance  # Retorna a instância única

    def __init__(self, config=None):
        if not self.__initialized:  # Garante que a inicialização ocorra apenas uma vez
            self.config = config  # Atribui um valor de configuração (se fornecido)
            self.__initialized = True  # Marca como inicializado



