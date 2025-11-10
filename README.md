# 📚 Sistema de Livraria

Este projeto é um **sistema simples de gerenciamento de uma livraria**, desenvolvido em **Java** com **Maven**.  
Ele permite **cadastrar autores e livros**, realizar **empréstimos e devoluções**, e consultar o estado atual da biblioteca pelo **console interativo**.

---

## 🧩 Funcionalidades principais

- 👤 **Cadastro de autores**
- 📖 **Cadastro de livros**
- 🔍 **Busca de livros por título**
- 🧾 **Listagem de livros disponíveis e emprestados**
- 📅 **Registro de empréstimos e devoluções**
- 🟢 **Marcação automática de disponibilidade**
- 💬 **Mensagens de sucesso e validação de erros**
- 💾 Dados iniciais (autores e livros) adicionados automaticamente

---

## ⚙️ Estrutura do projeto

```
src/
 └── main/
      └── java/
           └── br/
                └── com/
                     └── livraria/
                          ├── Main.java
                          └── model/
                               ├── Autor.java
                               ├── Livro.java
                               ├── Emprestimo.java
                               └── Biblioteca.java
```

---

## 🚀 Como executar

### 1️⃣ Clonar o repositório
```bash
git clone https://github.com/seuusuario/SistemaLivraria.git
cd SistemaLivraria
```

### 2️⃣ Compilar o projeto
Se estiver usando Maven:
```bash
mvn clean compile
```

### 3️⃣ Executar o programa
```bash
mvn exec:java -Dexec.mainClass="br.com.livraria.Main"
```
ou, no IntelliJ, apenas **rodar a classe `Main.java`**.

---

## 🧠 Como usar

O sistema exibe um menu interativo no console:

```
=== Sistema de Livraria ===
1 - Cadastrar novo autor
2 - Cadastrar novo livro
3 - Ver autores cadastrados
4 - Ver livros disponíveis
5 - Ver todos os livros
6 - Buscar livros por título
7 - Emprestar um livro
8 - Devolver um livro
9 - Ver empréstimos
0 - Sair
```

👉 Ao emprestar um livro:
- o sistema solicita o nome do cliente;
- registra o empréstimo;
- marca o livro como **indisponível**;
- exibe uma mensagem de sucesso.

Ao devolver:
- o sistema atualiza a data de devolução;
- torna o livro **disponível novamente**.

---

## 🧑‍💻 Tecnologias utilizadas
- ☕ **Java 17**
- 🧱 **Maven**
- 🧩 **Paradigma Orientado a Objetos**
- 🖥️ **Entrada e saída pelo console (Scanner)**

---

## 🏗️ Regras de negócio principais
- Cada livro possui um autor.
- Um livro não pode ser emprestado se já estiver emprestado.
- Ao devolver, o livro volta a ficar disponível.
- Empréstimos e devoluções exibem mensagens de confirmação.

---

## 💡 Exemplo de uso

```
📚 Livros disponíveis para empréstimo:
1 - Dom Casmurro (Machado de Assis)
2 - A Hora da Estrela (Clarice Lispector)

Digite o ID do livro para emprestar: 1
Digite o nome do cliente: Maria da Silva
✅ Empréstimo realizado com sucesso!
```

---

## 🏷️ Licença
Este projeto é livre para uso acadêmico e aprendizado.

---

Desenvolvido por **Marcelo Tedesco de Miranda** 💙
