/*Implemente um sistema simples de saudação para usuários. 
Cada usuário pode ou não ter uma saudação personalizada. 
Caso o usuário não possua saudação, deve-se utilizar o 
padrão de projeto Null Object, evitando o uso de null no código cliente.

Requisitos:
- Crie uma interface Usuario com um método saudacao().
- Implemente duas classes:
  - UsuarioReal: exibe uma mensagem personalizada.
  - UsuarioNulo: exibe uma mensagem genérica, como "Nenhuma saudação configurada."
- No método main, instancie dois usuários: um com saudação real e outro com saudação nula. 
  Ambos devem chamar exibir() sem verificar se a saudação é nula.
*/
