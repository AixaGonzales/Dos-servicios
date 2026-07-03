# Front Angular - Dos Servicios

Aplicacion Angular basica para consumir:

- `student-service`: servicio maestro de estudiantes en `http://localhost:8090`
- `enrollment-service`: servicio transaccional de matriculas en `http://localhost:8095`

## Estructura

```text
src/app/
|-- core/
|   |-- models/       # Interfaces Student, Enrollment y EnrollmentDetail
|   |-- services/     # Servicios HTTP para consumir los microservicios
|-- layout/
|   |-- components/   # Layout principal con sidebar
|-- features/
|   |-- estudiantes/  # Pagina de gestion de estudiantes
|   |-- matriculas/   # Pagina de gestion de matriculas
|-- app.routes.ts     # Rutas principales
```

## Ejecutar

Instalar dependencias:

```bash
npm install
```

Levantar Angular:

```bash
npm start
```

Compilar:

```bash
npm run build
```

## Flujo recomendado

1. Levantar `student-service`.
2. Registrar estudiantes desde la pagina **Estudiantes**.
3. Levantar `enrollment-service`.
4. Crear matriculas desde la pagina **Matriculas** seleccionando un estudiante existente.

La pagina de matriculas consume ambos servicios: lista estudiantes desde el maestro y guarda matriculas en el transaccional.
