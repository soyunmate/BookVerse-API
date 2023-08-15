# BookVerse API (Under Construction)

Welcome to the BookVerse API repository! This API is currently under development and is being built as part of my personal portfolio.

## Notice

Please note that this project is a work in progress. Some features may be incomplete, and there might be bugs or issues that are being actively addressed.
## Getting Started

Follow these steps to set up and run the project locally:

1. Clone this repository: `git clone https://github.com/tuusuario/BookVerse.git`
2. Navigate to the project directory: `cd BookVerse`
3. Install dependencies: `mvn install`
4. Configure the database connection in `application.properties`.
5. Run the application: `mvn spring-boot:run`

The API will start at `http://localhost:8080`.

## API Endpoints
- `GET /api/books/find/{id}`: Find a book.
- `GET /api/books/findAll`: Get a list of all books.
- `POST /api/books/save`: Create a new book.
- `PUT /api/books/update/{id}`: Update a book.
- `DELETE /api/books/delete/{id}`: Delete a book.

