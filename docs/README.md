# Apuntes de la Configuracion del Proyecto

### Base de Datos en MySQL usando Docker
```
docker run -it -p 3316:3306 -e MYSQL_ROOT_PASSWORD=secreto -e MYSQL_DATABASE=fact -e MYSQL_USER=fact -e MYSQL_PASSWORD=secreto --name fact mysql:8.0.23
```
