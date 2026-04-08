# BookIt - Sistema de reservas de citas

Aplicacion web full stack para gestionar servicios y citas de clientes.
Incluye:
- `frontend` en Angular (servido con Nginx)
- `backend` en Spring Boot
- `db` PostgreSQL con persistencia por volumen

## Imagenes Docker Hub

- Frontend: `DOCKERHUB_USER/bookit-frontend:latest`
- Backend: `DOCKERHUB_USER/bookit-backend:latest`

Reemplaza `DOCKERHUB_USER` por tu usuario real de Docker Hub al publicar.

## Ejecucion

```bash
docker compose up -d
```

## URLs

- Frontend: http://localhost:8080
- Backend health: http://localhost:3000/health
- Backend API base: http://localhost:3000

## Variables de entorno

Puedes copiar `.env.example` a `.env` y ajustar valores:

- `DOCKERHUB_USER`
- `POSTGRES_DB`
- `POSTGRES_USER`
- `POSTGRES_PASSWORD`

## Endpoints minimos implementados

### Servicios
- `GET /services`

### Citas
- `GET /appointments`
- `POST /appointments`
- `PATCH /appointments/:id/status`
- `DELETE /appointments/:id`

### Salud
- `GET /health`

## Publicacion de imagenes (antes de entregar)

```bash
docker compose build
docker push DOCKERHUB_USER/bookit-frontend:latest
docker push DOCKERHUB_USER/bookit-backend:latest
```

## Validacion recomendada

```bash
docker compose down -v
docker compose up -d
docker compose ps
docker compose logs
```
