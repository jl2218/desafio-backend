# Desafio para vaga Back-end

## Descrição do Projeto
Este projeto é uma API REST para gerenciar um estacionamento de carros e motos.

## Tecnologias Utilizadas
- Java
- Spring Boot

## Funcionalidades
- CRUD de Estabelecimentos
- CRUD de Veículos
- CRUD de Clientes
- Controle de entrada e saída de veículos

## Rotas da API
- `POST /establishment/register` - Cria um novo estabelecimento
- `GET /establishment/list` - Lista todos os estabelecimentos
- `PUT /establishment/update` - Atualiza um estabelecimento
- `DELETE /establishment/delete?id=` - Deleta um estabelecimento pelo ID
- `POST /vehicle/register` - Cria um novo veículo
- `GET /vehicle/list` - Lista todos os veículos
- `PUT /vehicle/update` - Atualiza um veículo pelo ID
- `DELETE /vehicle/delete?id=` - Deleta um veículo pelo ID
- `POST /vehicle/control/entry?establishmentDocument=&vehiclePlate=` - Registra uma entrada de um veículo no estabelcimento selecionado
- `POST /vehicle/control/exit?establishmentDocument=&vehiclePlate=` - Registra uma saída de um veículo no estabelcimento selecionado
- `GET /vehicle/control/summary?establishmentDocument=` - Cria o sumário das entradas e saídas do período total
- `GET /vehicle/control/summary/perHour?establishmentDocument=` - Cria o sumário das entradas e saídas da última hora
- `POST /vehicle/control/entry/customer?establishmentDocument=&customerDocument=` - Registra uma entrada de um veículo de um cliente no estabelcimento selecionado
- `POST /vehicle/control/exit/customer?establishmentDocument=&customerDocument=` - Registra uma saída de um veículo de um cliente no estabelcimento selecionado
- `POST /customer/register` - Cria um novo cliente
- `GET /customer/list` - Lista todos os clientes
- `PUT /customer/update` - Atualiza um cliente
- `DELETE /customer/delete?id=` - Deleta um cliente pelo ID

## Autenticação
Este projeto utiliza JWT para autenticação. Para criar um usuário, use a rota `POST /auth/register`e para autenticar, use a rota `POST /auth/login`.

## Documentação
Este projeto contém documentação. Para acessar, utilize a rota `/swagger-ui/index.html`

## Docker
Este projeto está conteinerizado. Para acessar, utilize o link <https://hub.docker.com/repository/docker/joaopedrofaria/myrepo/general>