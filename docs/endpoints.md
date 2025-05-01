# Endpoints

## api-users

| Endpoint        | Method | Description       |
|-----------------|--------|-------------------|
| /api/users      | GET    | Get all users     |
| /api/users/{id} | GET    | Get user by ID    |
| /api/users      | POST   | Create a new user |
| /api/users/{id} | PUT    | Update user by ID |
| /api/users/{id} | DELETE | Delete user by ID |

## api-books

| Endpoint                               | Method | Description                   |
|----------------------------------------|--------|-------------------------------|
| /api/books                             | GET    | Get all books                 |
| /api/books/{id}/borrow?userId={userId} | PUT    | Borrow a book by ID to a user |
| /api/books/{id}/return                 | PUT    | Return a book by ID           |

