-- Etiquetas
INSERT INTO tags (name) VALUES ('BEST_SELLER');
INSERT INTO tags (name) VALUES ('SPECIAL_OFFER');

-- Editoriales
INSERT INTO publishers (description, name, web_site) VALUES ('Editorial 1', 'Editorial A', 'http://editoriala.com');
INSERT INTO publishers (description, name, web_site) VALUES ('Editorial 2', 'Editorial B', 'http://editorialb.com');

-- Autores
INSERT INTO authors (biography, birth_date, first_name, last_name, nationality) VALUES ('Biografía de Autor 1', '1990-01-01', 'Autor', 'Uno', 'Nacionalidad 1');
INSERT INTO authors (biography, birth_date, first_name, last_name, nationality) VALUES ('Biografía de Autor 2', '1985-03-15', 'Autor', 'Dos', 'Nacionalidad 2');

-- Géneros
INSERT INTO genres (description, icon, name) VALUES ('Género 1', 'icono1.png', 'FANTASY');
INSERT INTO genres (description, icon, name) VALUES ('Género 2', 'icono2.png', 'SCI_FI');

-- Libros
INSERT INTO books (description, isbn, language, pages, publish_date, stock, title, author_id, publisher_id) VALUES ('Descripción del libro 1', '1234567890', 'Español', 300, '2023-01-15', 50, 'Libro 1', 1, 1);
INSERT INTO books (description, isbn, language, pages, publish_date, stock, title, author_id, publisher_id) VALUES ('Descripción del libro 2', '9876543210', 'Inglés', 250, '2022-12-10', 30, 'Libro 2', 2, 1);

-- Relaciones Autor-Libro
INSERT INTO author_books (author_id, book_id) VALUES (1, 1);
INSERT INTO author_books (author_id, book_id) VALUES (2, 2);

-- Relaciones Libro-Género
INSERT INTO book_genres (book_id, genre_id) VALUES (1, 1);
INSERT INTO book_genres (book_id, genre_id) VALUES (1, 2);
INSERT INTO book_genres (book_id, genre_id) VALUES (2, 2);



-- Relaciones Libro-Etiqueta
INSERT INTO book_tags (book_id, tag_id) VALUES (1, 1);
INSERT INTO book_tags (book_id, tag_id) VALUES (1, 2);
INSERT INTO book_tags (book_id, tag_id) VALUES (2, 1);



-- Usuarios
INSERT INTO users (last_name, email, first_name, password, username) VALUES ('Apellido 1', 'usuario1@example.com', 'Usuario', 'password1', 'usuario1');
INSERT INTO users (last_name, email, first_name, password, username) VALUES ('Apellido 2', 'usuario2@example.com', 'Usuario', 'password2', 'usuario2');
