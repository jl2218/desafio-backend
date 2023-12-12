# Desafio para vaga Back-end

## Descrição do Projeto
Este projeto é uma API REST para gerenciar um estacionamento de carros e motos.

## Tecnologias Utilizadas
- Java
- Spring Boot

## Funcionalidades
- CRUD de Estabelecimentos
- CRUD de Veículos
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

## Autenticação
Este projeto utiliza JWT para autenticação. Para criar um usuário, use a rota `POST /auth/register`e para autenticar, use a rota `POST /auth/login`.
