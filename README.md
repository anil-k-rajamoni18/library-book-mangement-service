# library-book-mangement-service
### Tech Stack
- Backend: Java,Spring 6.x, SpringBoot 3.x 
- Dabase: H2 (In Memory Database)
- Surity: Basic Auth implemeted using spring security
- Deployment: Amazon EKS (elastic kubernetes service)


### How to run
- Clone the repository `https://github.com/anil-k-rajamoni18/library-book-mangement-service.git`

- Run the application using `gradlew bootRun`

### API ENDPOINTS
- GET / : Health check
- POST /api/v1/books/add : Create a single new book
- POST /api/v1/books/add : Create a multiple books at a time
- GET /api/v1/books/{id} : Get a book by id
- GET /api/v1/books : Get all books
- GET /api/v1/books/search?title={title} : Search books by title

### Security 
- Spring Basic Auth
