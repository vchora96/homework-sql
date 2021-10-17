# homework-sql
Для локального тестирования можно использовать docker-compose.

Для локального подняти БД используйте команду в терминале docker-compose up. Пример конфигурационного файла:
```
version: '3.3'
services:
postgres:
image: postgres:11
restart: always
container_name: otus_db
ports:
- 5432:5432
environment:
POSTGRES_DB: otus_db
POSTGRES_USER: postgres1
POSTGRES_PASSWORD: postgres1
command: postgres -N 100
```