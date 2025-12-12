# 🅿️ Totem de Estacionamento – Java + JavaFX

Este projeto implementa um **Totem de Estacionamento** completo, simulando o funcionamento real de um sistema de autoatendimento para pagamento de tickets, desenvolvido para a disciplina de Programação I na universidade Feevale.  
A aplicação foi desenvolvida em **Java 17**, utilizando **JavaFX**, **FXML**, e orientação a objetos com foco em clareza e organização.

---

### ✔ Cadastro automático de veículos
O sistema simula periodicamente a entrada de novos veículos, criando Tickets automaticamente.

### ✔ Contagem de tempo em tempo real
Cada ticket tem contagem contínua de horas e minutos, atualizada por threads dedicadas.

### ✔ Busca de placa
O usuário digita a placa e o sistema:
- Valida a existência
- Mostra o ticket correspondente
- Impede pagamento duplicado

### ✔ Pagamentos
O sistema oferece três métodos:

| Tipo     | Regra |
|---------|--------|
| **Pix** | Desconto de 3% |
| **Débito** | Valor simples |
| **Crédito** | 2% de juros por parcela acima da 1ª |

### ✔ Recibo automático
Depois do pagamento, a aplicação gera um **recibo completo**, exibido ao usuário.

### ✔ Logs do sistema
Todas as ações importantes são registradas e exibidas nas telas.

### ✔ Navegação entre telas
O projeto contém quatro telas:

1. **Tela Inicial** – Digitação da placa  
2. **Tela de Pagamento** – Escolha do método  
3. **Recibo** – Resumo completo  
4. **Tela de Sucesso** – Confirmação final  

Após a tela 4, o sistema retorna automaticamente à tela 1.

---

##  Estrutura do Projeto

### **Principais classes**

#### **App**
Gerencia:
- inicialização do JavaFX
- troca de telas
- carregamento de arquivos FXML

#### **Dados**
Classe utilitária **estática**, responsável por armazenar:
- Ticket sendo pago
- Última placa digitada
- Lista de estacionamento
- Lista de logs
- Recibo final

Serve como "memória global" entre controladores.

#### **Ticket**
Representa um veículo estacionado.  
Guarda:
- placa  
- horas/minutos  
- status  
- cálculo de valor  
- controle do tempo

#### **ListaEstacionamento**
Armazena todos os Tickets ativos utilizando `LinkedList<Ticket>`.

---

## Sistema de Pagamentos

Utiliza **herança + polimorfismo**:

### `Pagamento` (abstrata)
Define:
- valor base
- método abstrato `processarPagamento()`
- interface `Comprovante`

### Subclasses
- **Pix** → aplica desconto  
- **Debito** → valor direto  
- **Credito** → juros conforme parcelas  

---

## Telas (Controllers)

### **PrimaryController**
- Exibe lista de veículos
- Aceita placa digitada
- Valida ticket
- Inicia simulação de entrada de veículos e passagem de tempo

### **SecondaryController**
- Recupera ticket selecionado
- Calcula valor conforme forma de pagamento
- Gera log

### **TertiaryController**
- Exibe recibo
- Mostra lista de logs
- Mostra lista de veículos atualizada

### **QuaternaryController**
- Mensagem de sucesso
- Retorno automático à tela inicial

---

## Threads Utilizadas

O sistema usa duas threads para simulação:

- **Entrada de veículos aleatórios**
- **Passagem de tempo para todos os tickets**

Ambas atualizam a interface via `Platform.runLater()` (padrão JavaFX).

---

## Tecnologias Utilizadas

- **Java 17**
- **JavaFX**
- **FXML**
- **Scene Builder**
- **Collections (LinkedList, ObservableList)**
- **Programação concorrente (Thread)**

---

##  Como executar

1. Instale:
   - JDK 17+
   - JavaFX SDK

2. Configure o módulo JavaFX na sua IDE (IntelliJ ou Eclipse).

3. Rode a classe:
