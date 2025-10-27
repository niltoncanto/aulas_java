# Sistema de Cadastro de Produtos

Este projeto implementa um sistema simples de cadastro de produtos em Java, utilizando persistência em arquivo de texto.

## Estrutura do Projeto

### 1. Interface IProdutoDAO
Define os métodos para operações CRUD de produtos:
- `adicionar(Produto produto)` - Adiciona um novo produto
- `listarTodos()` - Lista todos os produtos
- `buscarPorNome(String nome)` - Busca um produto pelo nome
- `remover(String nome)` - Remove um produto pelo nome

### 2. Classe Produto
Representa um produto com os seguintes atributos:
- `String nome` - Nome do produto
- `double preco` - Preço do produto
- `int quantidade` - Quantidade em estoque

**Métodos incluídos:**
- Construtor padrão e com parâmetros
- Getters e Setters para todos os atributos
- `toString()` sobrescrito para exibição amigável
- `toFileFormat()` - Converte para formato de arquivo (nome;preco;quantidade)
- `fromFileFormat(String linha)` - Cria produto a partir de linha do arquivo

### 3. Classe ProdutoArquivoDAOImpl
Implementa a interface `IProdutoDAO` utilizando arquivo de texto (`produtos.txt`) para persistência:
- Grava produtos no formato: `nome;preco;quantidade`
- Um produto por linha
- Campos separados por ponto e vírgula
- Lê e carrega dados do arquivo automaticamente

### 4. Classe CadastroProdutosApp
Aplicação principal com menu interativo:

**Menu disponível:**
1. **Adicionar um novo produto** - Solicita nome, preço e quantidade
2. **Listar todos os produtos** - Exibe todos os produtos cadastrados em formato de tabela
3. **Sair** - Encerra a aplicação

## Como Executar

1. Compile todas as classes Java:
```bash
javac *.java
```

2. Execute a aplicação principal:
```bash
java CadastroProdutosApp
```

## Funcionalidades

### Adicionar Produto
- Valida se o nome não está vazio
- Verifica se o preço não é negativo
- Verifica se a quantidade não é negativa
- Não permite produtos duplicados (mesmo nome)

### Listar Produtos
- Exibe todos os produtos em formato de tabela
- Mostra o total de produtos cadastrados
- Informa quando não há produtos cadastrados

### Persistência
- Os dados são salvos automaticamente no arquivo `produtos.txt`
- O arquivo é criado automaticamente quando necessário
- Formato: uma linha por produto com campos separados por `;`

## Exemplo de Uso

```
=== SISTEMA DE CADASTRO DE PRODUTOS ===

┌─────────────────────────────────────┐
│            MENU PRINCIPAL           │
├─────────────────────────────────────┤
│ 1. Adicionar um novo produto       │
│ 2. Listar todos os produtos        │
│ 3. Sair                            │
└─────────────────────────────────────┘
Escolha uma opção: 1

=== ADICIONAR NOVO PRODUTO ===
Nome do produto: Notebook Dell
Preço do produto (R$): 2500.00
Quantidade em estoque: 10
Produto 'Notebook Dell' adicionado com sucesso!
```

## Arquivo de Dados

O arquivo `produtos.txt` será criado automaticamente e terá o seguinte formato:
```
Notebook Dell;2500.00;10
Mouse Logitech;45.50;25
Teclado Mecânico;150.00;15
```

## Tratamento de Erros

- Validação de entrada de dados
- Tratamento de erros de arquivo
- Mensagens informativas para o usuário
- Verificação de produtos duplicados
