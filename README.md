# Dos Servicios

Proyecto con dos microservicios Spring Boot y un frontend Angular:

- `student-service`: servicio maestro para gestionar estudiantes.
- `enrollment-service`: servicio transaccional para gestionar matriculas.
- `frond`: frontend Angular que consume ambos servicios.

## Requisitos

Instala antes de ejecutar:

- Java JDK 21
- Maven
- Node.js y npm
- Docker Desktop, opcional si quieres ejecutar con Docker

## Puertos

| Aplicacion | Puerto local | URL |
| --- | --- | --- |
| student-service | 8090 | `http://localhost:8090` |
| enrollment-service | 8095 | `http://localhost:8095` |
| frontend Angular | 4200 | `http://localhost:4200` |

## Ejecutar todo en local

Esta es la forma recomendada para probar el proyecto completo. Debes abrir tres terminales.

### 1. Levantar student-service

```bash
cd student-service
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

El servicio queda disponible en:

```text
http://localhost:8090
```

Consola H2:

```text
http://localhost:8090/h2-console
```

Datos de H2:

```text
JDBC URL: jdbc:h2:mem:studentdb
User: sa
Password: password
```

### 2. Levantar enrollment-service

En otra terminal:

```bash
cd enrollment-service
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

El servicio queda disponible en:

```text
http://localhost:8095
```

Consola H2:

```text
http://localhost:8095/h2-console
```

Datos de H2:

```text
JDBC URL: jdbc:h2:mem:enrollmentdb
User: sa
Password: password
```

`enrollment-service` consulta estudiantes en `http://localhost:8090`, por eso primero debe estar levantado `student-service`.

### 3. Levantar el frontend

En otra terminal:

```bash
cd frond
npm install
npm start
```

Abre el navegador en:

```text
http://localhost:4200
```

## Flujo de prueba recomendado

1. Levanta `student-service`.
2. Levanta `enrollment-service`.
3. Levanta `frond`.
4. En el frontend, crea primero un estudiante.
5. Luego crea una matricula usando un estudiante existente.

## Probar con cURL

Crear estudiante:

```bash
curl -X POST http://localhost:8090/v1/api/student/register \
  -H "Content-Type: application/json" \
  -d "{\"dni\":\"12345678\",\"firstName\":\"Aixa\",\"lastName\":\"Gonzales\",\"promotion\":\"2026-I\",\"date\":\"2026-07-02\"}"
```

Listar estudiantes:

```bash
curl -X GET http://localhost:8090/v1/api/student/list
```

Crear matricula:

```bash
curl -X POST http://localhost:8095/v1/api/enrollment/register \
  -H "Content-Type: application/json" \
  -d "{\"studentId\":1,\"courseName\":\"Programacion I\",\"enrollmentDate\":\"2026-07-02\",\"status\":\"ACTIVA\"}"
```

Listar matriculas:

```bash
curl -X GET http://localhost:8095/v1/api/enrollment/list
```

Ver detalle de matricula con estudiante:

```bash
curl -X GET http://localhost:8095/v1/api/enrollment/detail/1
```

## Ejecutar con Docker

Cada microservicio tiene su propio `docker-compose.yml`.

### student-service con Docker

```bash
cd student-service
docker compose up -d --build
```

URLs:

- API directa: `http://localhost:8091`
- Nginx: `http://localhost:8092`
- MySQL: `localhost:3306`

Detener:

```bash
docker compose down
```

### enrollment-service con Docker

```bash
cd enrollment-service
docker compose up -d --build
```

URLs:

- API directa: `http://localhost:8095`
- Nginx: `http://localhost:8096`
- MySQL: `localhost:3308`

Detener:

```bash
docker compose down
```

Nota: el `docker-compose.yml` de `enrollment-service` usa `STUDENT_SERVICE_URL=http://host.docker.internal:8090`. Para que funcione sin cambios, levanta `student-service` localmente en el puerto `8090`. Si levantas `student-service` con Docker, cambia esa variable a `http://host.docker.internal:8091`.

## Comandos utiles

Ver estado de contenedores:

```bash
docker compose ps
```

Ver logs:

```bash
docker compose logs -f
```

Compilar frontend:

```bash
cd frond
npm run build
```

Compilar un microservicio:

```bash
mvn clean package
```

## Estructura del repositorio

```text
Dos-servicios/
|-- student-service/
|-- enrollment-service/
|-- frond/
|-- README.md
```
