Como compilar e executar (JDK 17+):
----------------------------------
1) Compile todos os arquivos .java:
   javac *.java

2) Execute o programa principal:
   java Main

O programa criará/atualizará um arquivo 'log.txt' no diretório atual,
registrando cada chamada a play() no ProxyVideo.

Estrutura dos arquivos:
- Video.java      -> Interface do serviço (contrato)
- RealVideo.java  -> Implementação "pesada" (carregamento custoso)
- ProxyVideo.java -> Proxy com inicialização tardia (lazy) + logging
- Main.java       -> Simulação do uso do padrão Proxy
