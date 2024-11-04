
# ArgosApp

**ArgosApp** é um aplicativo Android que facilita o gerenciamento de clientes e produtos, permitindo operações de CRUD (criação, leitura, atualização e exclusão) com sincronização local. Desenvolvido em Kotlin e Jetpack Compose, ele utiliza o Retrofit para comunicação com uma API e DataStore para armazenamento local de dados.

## Recursos

- **CRUD de Clientes e Produtos**: Adicione, edite, veja detalhes e exclua clientes e produtos.
- **DataStore para Persistência Local**: Dados são salvos localmente utilizando DataStore.
- **API Retrofit**: Comunicação com backend utilizando Retrofit para chamadas de rede.
- **Tratamento de Exceções**: Feedback de erros e informações exibidos ao usuário.
- **Interface com Jetpack Compose**: Interface moderna e reativa com Jetpack Compose.

## Tecnologias Utilizadas

- **Linguagem**: Kotlin
- **Interface de Usuário**: Jetpack Compose
- **Network**: Retrofit com suporte para desabilitar SSL temporariamente (ambiente de desenvolvimento)
- **Persistência de Dados**: DataStore para armazenamento local
- **Serialização**: Gson para conversão JSON
- **AsyncStorage**: Compartilhamento de preferências para dados rápidos, utilizando `SharedPreferences`.

## Estrutura do Projeto

### 1. DTO (Data Transfer Objects)

- **ClienteDto** e **ProdutoDto**: Classes para receber e enviar dados entre a API e a aplicação. Incluem anotações `@SerializedName` para facilitar a conversão JSON.

### 2. Modelos

- **Cliente** e **Produto**: Representam os modelos de dados de clientes e produtos, sendo a base para operações internas da aplicação.

### 3. Rede (Retrofit e OkHttp)

- **ArgosApi**: Interface Retrofit para realizar as operações de rede de Clientes e Produtos.
- **UnsafeRetrofitInstance**: Configurações de Retrofit e OkHttp, permitindo ignorar temporariamente a verificação SSL.

### 4. Repositórios

- **ClienteRepository** e **ProdutoRepository**: Manipulam a lógica de negócios para operações CRUD e persistência local no DataStore.

### 5. Armazenamento Local

- **AsyncStorageHelper**: Classe que utiliza `SharedPreferences` para armazenar informações rápidas e persistentes no dispositivo.

### 6. Telas (UI)

- **HomeScreen**: Tela inicial com botões para acessar o gerenciamento de Clientes e Produtos.
- **ClientesScreen** e **ProdutosScreen**: Listagens dos Clientes e Produtos, com botões para adicionar, editar e deletar itens.
- **AddClienteScreen** e **AddProdutoScreen**: Formulários para adicionar novos Clientes e Produtos.
- **EditClienteScreen** e **EditProdutoScreen**: Formulários para editar informações de Clientes e Produtos existentes.
- **DetalheClienteScreen** e **DetalheProdutoScreen**: Exibem informações detalhadas de Clientes e Produtos.

### 7. Navegação

- **AppNavigation**: Configuração da navegação utilizando `NavHost` e `NavHostController`.

## Estrutura de Pastas

```
.
├── data
│   ├── dto
│   ├── models
│   ├── network
│   └── repository
├── ui
│   └── screens
└── storage
```

## Exemplo de Uso

### Adicionar um Cliente

1. Acesse a tela de clientes.
2. Clique em "Adicionar Cliente".
3. Preencha os campos e clique em "Salvar Cliente".

### Excluir um Produto

1. Acesse a tela de produtos.
2. Clique em "Deletar" no item que deseja excluir.
3. Confirme a operação para remover o item.

## Diagrama UML

![ArgosMobileUml (1)](https://github.com/user-attachments/assets/7c2b5c95-9a10-491f-924a-966cd3daef56)

