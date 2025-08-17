# WinWin.travel assessment task

## Environment variables
Create `.env` file in the projects's root directory with following variables
- JWT_SECRET = 'secret'
- INTERNAL_TOKEN = 'token'
- POSTGRES_URL = 'url with database name' like auth-db:5432/users
- POSTGRES_USER = 'postgres user'
- POSTGRES_PASSWORD = 'postgres password'

Example:
```
JWT_SECRET=U6Y1mXcVIsZrICduVYBUULxfRAfI785mkxwDAF4cVy89pG7VvVUyQ0LFRLv8V6xt
INTERNAL_TOKEN=ukYuCz57z1Sg3HOoOwlHlvMRAodkV4OMfXueGrikGHH28T8oHWH5ygQzjTWTltgy

POSTGRES_URL=auth-db:5432/users
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgress
```

## Deploy project
Then, run command `docker compose up -d --build` to deploy whole project locally.

## Endpoints
### Registration
```
curl -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d "{"email":"a@a.com","password":"pass"}"
```
You need to send body as in example below:
```json
{
    "email":"2@afw.com",
    "password":"awdawdawdaww"
}
```
In case of success, you will receive response with `201` code with empty body.
### Login
```
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d "{"email":"a@a.com","password":"pass"}"
```
You also need to send body as in example below:
```json
{
    "email":"2@afw.com",
    "password":"awdawdawdaww"
}
```
In case of success, you will receive response with `200` code and token in the body.
You need to store that token and include it in the `Authorization` header with prefix `Bearer ` to access secured endpoint.
In case of failure, you'll got `403` code.
### Secured endpoint
```
curl -X POST http://localhost:8080/api/process -H "Authorization: Bearer <token>" -H "Content-Type: application/json" -d "{"text":"hello"}"
```
You need to send a body like this:
```json
{
    "text":"hello"
}
```
And in case of success, you'll receive transformed text in the response body like this one:
```json
{
    "result": "OLLEH"
}
```