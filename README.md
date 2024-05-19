# Simulador Pix

Este projeto é uma simulação do sistema Pix, um método de pagamento popular no Brasil. Ele consiste em dois microserviços: `ms-conta` e `ms-extrato`.

## Tecnologias Utilizadas

- Java
- Spring Boot
- Maven
- Python
- PostgreSQL
- MongoDB
- Kafka
- SOLID Principles
- Testes Unitários
- Testes de Integração
- CQRS (Command Query Responsibility Segregation)
- Comunicação Assíncrona

## Serviços

### ms-conta

Este serviço é responsável por gerenciar as informações da conta. Ele usa PostgreSQL como seu banco de dados e se comunica com o serviço `ms-extrato` via Kafka.

### ms-extrato

Este serviço é responsável por gerenciar os registros de transações. Ele usa MongoDB como seu banco de dados e recebe mensagens do serviço `ms-conta` via Kafka.

## Configuração

Para executar este projeto, você precisa ter o Docker e o Docker Compose instalados em sua máquina. Uma vez que você tenha instalado, você pode iniciar o projeto executando o seguinte comando no diretório raiz do projeto:

```bash
docker-compose up -d
```

Isso iniciará todos os serviços definidos no arquivo ``docker-compose.yml``.


## Testes 

Os testes unitários são escritos usando JUnit e Mockito. Você pode executar os testes executando o seguinte comando no diretório raiz de cada microserviço:

```bash
mvn test
```

## Endpoints

Na raiz do projeto existe uma collection do Postman com todos os endpoints disponíveis. Você pode importá-la no Postman e testa-los.

### ms-conta
- `POST /api/conta`: Cria uma nova conta.
- `GET /api/conta/{idConta}`: Retorna as informações da conta com o ID especificado.
- `PUT /api/conta/{idConta}/add-chave-pix`: Adiciona chave pix na conta com o ID especificado.


### ms-extrato

- `GET /api/extrato/{idConta}`: Retorna o extrato da conta com o ID especificado.