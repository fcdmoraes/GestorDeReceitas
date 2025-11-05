# 🧪 Projeto Final — API REST com Spring Boot

## 📘 Descrição

Este projeto tem como objetivo a construção de uma **API REST utilizando o framework Spring Boot**.  
A aplicação permite o **cadastro, listagem, atualização e exclusão** de registros em uma base de dados, implementando as operações básicas de um CRUD completo.

> 📌 **Tema:** Sistema de gerenciamento de receitas culinárias  
> O projeto permite cadastrar **receitas**, **categorias** e **ingredientes**, possibilitando o relacionamento entre eles.

---

## 🔗 Endpoints

A API contém os seguintes endpoints principais:

| Método | Rota | Descrição |
|--------|---------------------------|-------------------------------------------|
| `GET` | `/receitas` | Retorna todas as receitas |
| `GET` | `/receitas/{id}` | Retorna uma receita pelo ID |
| `POST` | `/receitas` | Cadastra uma nova receita |
| `PUT` | `/receitas/{id}` | Atualiza completamente uma receita |
| `PATCH` | `/receitas/{id}` | Atualiza parcialmente uma receita |
| `DELETE` | `/receitas/{id}` | Exclui uma receita |

> ⚠️ Endpoints semelhantes foram criados para **categorias** e **ingredientes**.

---

## 💾 Tecnologias Utilizadas

- ☕ **Java 17+**
- 🌱 **Spring Boot**
- 🧱 **Spring Data JPA**
- 🗄️ **H2 Database** (banco em memória)
- 📜 **Swagger UI** (documentação dos endpoints)
- 🔐 **Spring Security** (opcional — pode ser desativado)

---

## ⚙️ Como Executar o Projeto

### 🧩 Pré-requisitos
- Java 17+ instalado
- Maven configurado
- IDE de sua preferência (IntelliJ, Eclipse, VS Code etc.)

