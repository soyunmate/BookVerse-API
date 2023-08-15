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
INSERT INTO genres (description, icon, name) VALUES ('Fantasy Genre Description', 'icon_fantasy.png', 'FANTASY');
INSERT INTO genres (description, icon, name) VALUES ('Science Fiction Genre Description', 'icon_scifi.png', 'SCI_FI');
INSERT INTO genres (description, icon, name) VALUES ('Romance Genre Description', 'icon_romance.png', 'ROMANCE');
INSERT INTO genres (description, icon, name) VALUES ('Mystery Genre Description', 'icon_mystery.png', 'MYSTERY');
INSERT INTO genres (description, icon, name) VALUES ('Thriller Genre Description', 'icon_thriller.png', 'THRILLER');
INSERT INTO genres (description, icon, name) VALUES ('Horror Genre Description', 'icon_horror.png', 'HORROR');
INSERT INTO genres (description, icon, name) VALUES ('Historical Fiction Genre Description', 'icon_historical.png', 'HISTORICAL_FICTION');
INSERT INTO genres (description, icon, name) VALUES ('Biography Genre Description', 'icon_biography.png', 'BIOGRAPHY');
INSERT INTO genres (description, icon, name) VALUES ('Crime Genre Description', 'icon_crime.png', 'CRIME');
INSERT INTO genres (description, icon, name) VALUES ('Comedy Genre Description', 'icon_comedy.png', 'COMEDY');
INSERT INTO genres (description, icon, name) VALUES ('Drama Genre Description', 'icon_drama.png', 'DRAMA');
INSERT INTO genres (description, icon, name) VALUES ('Poetry Genre Description', 'icon_poetry.png', 'POETRY');
INSERT INTO genres (description, icon, name) VALUES ('Science Genre Description', 'icon_science.png', 'SCIENCE');
INSERT INTO genres (description, icon, name) VALUES ('Self-Help Genre Description', 'icon_selfhelp.png', 'SELF_HELP');
INSERT INTO genres (description, icon, name) VALUES ('Young Adult Genre Description', 'icon_youngadult.png', 'YOUNG_ADULT');
INSERT INTO genres (description, icon, name) VALUES ('Children''s Genre Description', 'icon_forkids.png', 'FOR_KIDS');


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
