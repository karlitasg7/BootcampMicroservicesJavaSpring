# Running the Application

## Run with Maven

1. Clone the repository

2. Build the api-users:
   ```bash
    cd api-users    
    mvn clean install
   ```
3. Build the api-books:
   ```bash
    cd api-books    
    mvn clean install
   ```

4. Run the api-users:
   ```bash
    cd api-users 
    mvn spring-boot:run
    ```

5. Run the api-books:
   ```bash
    cd api-books 
    mvn spring-boot:run
    ```

6. Access the application
    - api-users: `http://localhost:8080/swagger-ui/index.html`
    - api-books: `http://localhost:8081/swagger-ui/index.html`

7. You can test the endpoints from swagger or use Postman or any API testing tool to interact with the API endpoints.

## Run with docker

1. Clone the repository

2. Build the api-users:
   ```bash
    cd api-users    
    mvn clean install
   ```
3. Build the api-books:
   ```bash
    cd api-books    
    mvn clean install
   ```
4. Build the image for api-users:
   ```bash
    cd api-users    
    docker build -t api-users .
   ```

5. Build the image for api-books:
   ```bash
    cd api-books    
    docker build -t api-books .
   ```

6. Run the api-users:
   ```bash
    docker run -p 8080:8080 api-users
   ```

7. Run the api-books:
   ```bash
    docker run -p 8081:8081 api-books
   ```

## Run with docker-compose

1. Clone the repository

2. Build the api-users:
   ```bash
    cd api-users    
    mvn clean install
   ```

3. Build the api-books:
   ```bash
    cd api-books    
    mvn clean install
   ```

4. Build the image for api-users:
   ```bash
    cd api-users    
    docker build -t api-users .
   ```

5. Build the image for api-books:
   ```bash
    cd api-books    
    docker build -t api-books .
   ```

6. Run the application with docker-compose:
   ```bash
    docker-compose up
   ```
## Run with docker-compose with images from docker Hub

1. Run the application with docker-compose:
   ```bash
    cd docker    
    docker-compose up
   ```
