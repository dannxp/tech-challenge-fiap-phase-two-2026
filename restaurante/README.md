# Sistema de Gestão de Restaurantes

Sistema compartilhado para gestão de restaurantes, permitindo cadastro de tipos de usuário, usuários, restaurantes e itens de cardápio.

## Arquitetura

O projeto segue os princípios da **Clean Architecture**, organizado em camadas:

```
└── src/
    ├── main/java/com/example/sistema/restaurante/
    │   ├── domain/          # Entidades e interfaces de repositório
    │   │   ├── entity/      # Modelos de domínio (TipoUsuario, Usuario, Restaurante, ItemCardapio)
    │   │   └── repository/  # Interfaces de repositório (Spring Data JDBC)
    │   ├── application/     # Casos de uso da aplicação
    │   │   ├── dto/         # Objetos de transferência de dados (request/response)
    │   │   └── service/     # Serviços com lógica de negócio
    │   ├── interfaces/      # Adaptadores de interface
    │   │   └── controller/  # Controladores REST
    │   └── infrastructure/  # Infraestrutura e configurações
    │       └── config/      # Exception handler, configurações globais
    └── test/
        └── java/com/example/sistema/restaurante/
            ├── application/service/   # Testes unitários dos serviços
            └── interfaces/controller/ # Testes de integração dos controllers
```

## Tecnologias

- **Java 21**
- **Spring Boot 4.1.0**
- **Spring Data JDBC**
- **PostgreSQL 16**
- **Docker & Docker Compose**
- **Maven**
- **JaCoCo** (cobertura de testes)
- **JUnit 5 + Mockito**

## Pré-requisitos

- Docker e Docker Compose
- OU Java 21 + Maven + PostgreSQL

## Execução com Docker Compose

```bash
# Construir e iniciar os containers
docker-compose up --build

# A aplicação estará disponível em http://localhost:8080
```

## Execução local (sem Docker)

### Profile dev (H2 em memória — recomendado para desenvolvimento)

```bash
cd restaurante
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

A aplicação usará H2 em memória (esquema recriado a cada inicialização). Console H2 disponível em `http://localhost:8080/h2-console`.

### Profile default (PostgreSQL)

```bash
# Certifique-se de ter PostgreSQL rodando em localhost:5432
# com database 'restaurante', usuário 'restaurante' e senha 'restaurante123'

cd restaurante
mvn clean package -DskipTests
mvn spring-boot:run
```

## Endpoints da API

### Tipos de Usuário

| Método | Endpoint                   | Descrição             |
|--------|----------------------------|-----------------------|
| POST   | `/api/tipos-usuario`       | Criar tipo de usuário |
| GET    | `/api/tipos-usuario`       | Listar todos          |
| GET    | `/api/tipos-usuario/{id}`  | Buscar por ID         |
| PUT    | `/api/tipos-usuario/{id}`  | Atualizar             |
| DELETE | `/api/tipos-usuario/{id}`  | Deletar               |

**Request body (POST/PUT):**
```json
{
    "nome": "Dono de Restaurante"
}
```

### Usuários

| Método | Endpoint              | Descrição       |
|--------|-----------------------|-----------------|
| POST   | `/api/usuarios`       | Criar usuário   |
| GET    | `/api/usuarios`       | Listar todos    |
| GET    | `/api/usuarios/{id}`  | Buscar por ID   |
| PUT    | `/api/usuarios/{id}`  | Atualizar       |
| DELETE | `/api/usuarios/{id}`  | Deletar         |

**Request body (POST/PUT):**
```json
{
    "nome": "João Silva",
    "email": "joao@email.com",
    "tipoUsuarioId": 1
}
```

### Restaurantes

| Método | Endpoint                  | Descrição           |
|--------|---------------------------|---------------------|
| POST   | `/api/restaurantes`       | Criar restaurante   |
| GET    | `/api/restaurantes`       | Listar todos        |
| GET    | `/api/restaurantes/{id}`  | Buscar por ID       |
| PUT    | `/api/restaurantes/{id}`  | Atualizar           |
| DELETE | `/api/restaurantes/{id}`  | Deletar             |

**Request body (POST/PUT):**
```json
{
    "nome": "Pizza do João",
    "endereco": "Rua A, 123",
    "tipoCozinha": "Italiana",
    "horarioFuncionamento": "18h - 23h",
    "donoId": 1
}
```

### Itens do Cardápio

| Método | Endpoint                                   | Descrição                    |
|--------|--------------------------------------------|------------------------------|
| POST   | `/api/restaurantes/{restauranteId}/itens`  | Criar item do cardápio       |
| GET    | `/api/restaurantes/{restauranteId}/itens`  | Listar itens do restaurante  |
| GET    | `/api/itens-cardapio`                      | Listar todos os itens        |
| GET    | `/api/itens-cardapio/{id}`                 | Buscar item por ID           |
| PUT    | `/api/itens-cardapio/{id}`                 | Atualizar item               |
| DELETE | `/api/itens-cardapio/{id}`                 | Deletar item                 |

**Request body (POST/PUT):**
```json
{
    "nome": "Pizza Margherita",
    "descricao": "Mussarela, tomate e manjericão",
    "preco": 45.00,
    "disponivelApenasLocal": true,
    "fotoPath": "/fotos/pizza-margherita.jpg"
}
```

## Testes

```bash
# Executar todos os testes com relatório de cobertura
mvn clean verify

# Relatório JaCoCo: target/site/jacoco/index.html
```

## Estrutura do Banco de Dados

```
tipo_usuario (id, nome)
    ↑
usuario (id, nome, email, tipo_usuario_id)
    ↑
restaurante (id, nome, endereco, tipo_cozinha, horario_funcionamento, dono_id)
    ↑
item_cardapio (id, restaurante_id, nome, descricao, preco, disponivel_apenas_local, foto_path)
```
