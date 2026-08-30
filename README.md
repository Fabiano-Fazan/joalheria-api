# 💎 Joalheria API

API REST desenvolvida com **Java e Spring Boot** para gerenciamento de uma joalheria.

O projeto contempla cadastro e consulta de produtos, clientes, pedidos e movimentações de estoque, além de autenticação com Google, controle de acesso por perfil e upload de imagens com Cloudinary.

## 🎯 Objetivo

Projeto desenvolvido com foco em estudos de desenvolvimento Back-end com Java, aplicando conceitos além de um CRUD tradicional, como autenticação, transações, controle de estoque, concorrência e integrações externas.

## 🧠 Conceitos praticados

* APIs REST
* Programação Orientada a Objetos
* DTOs
* JPA / Hibernate
* Relacionamentos entre entidades
* Transações
* Controle de concorrência
* Spring Security
* OAuth2
* JWT
* Bean Validation
* Paginação
* Tratamento de exceções
* Integração com serviços externos
* Docker
* PostgreSQL

## 🚀 Tecnologias

* Java 17
* Spring Boot 4
* Spring Web MVC
* Spring Data JPA
* Spring Security
* OAuth2 / JWT
* PostgreSQL
* Hibernate
* Cloudinary
* Docker
* Gradle
* Lombok
* SpringDoc OpenAPI

## 📌 Funcionalidades

* Cadastro, edição e exclusão de produtos
* Listagem paginada de produtos
* Filtro por nome e categoria
* Produtos em destaque
* Upload de imagens com Cloudinary
* Cadastro e gerenciamento de clientes
* Criação e cancelamento de pedidos
* Controle de estoque
* Registro de movimentações
* Validação de estoque disponível
* Controle de concorrência com `PESSIMISTIC_WRITE`
* Autenticação com Google OAuth2
* Controle de acesso por perfil `ADMIN` e `CLIENTE`
* Tratamento global de exceções
* Documentação da API com OpenAPI

## 🏗️ Estrutura do projeto

```text
src/main/java/com/joalheria/api

├── configuracao
├── controller
├── domain
├── dto
├── event
├── exception
├── model
├── repositoy
├── service
└── JoalheriaApiApplication
```

## 🔒 Concorrência no estoque

Para evitar inconsistências em alterações simultâneas de estoque, o projeto utiliza lock pessimista:

```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
Optional<Produtos> findWithLockById(UUID id);
```

Isso evita que duas requisições atualizem a quantidade do mesmo produto ao mesmo tempo de forma incorreta.

## 🐳 Executando o projeto

Clone o repositório:

```bash
git clone https://github.com/Fabiano-Fazan/joalheria-api.git
cd joalheria-api
```

Com Docker:

```bash
docker compose up --build
```

Ou localmente no Windows:

```powershell
.\gradlew.bat bootRun
```

A aplicação utiliza por padrão:

```text
http://localhost:8084
```
