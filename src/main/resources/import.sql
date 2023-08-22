-- Etiquetas
INSERT INTO tags (name) VALUES ('BEST_SELLER');
INSERT INTO tags (name) VALUES ('SPECIAL_OFFER');
INSERT INTO tags (name) VALUES ('CYBER_DAY');
INSERT INTO tags (name) VALUES ('CYBER_MONDAY');
INSERT INTO tags (name) VALUES ('NEW_ARRIVALS');
INSERT INTO tags (name) VALUES ('POPULAR_LAST_WEEK');
INSERT INTO tags (name) VALUES ('POPULAR_LAST_MONTH');
INSERT INTO tags (name) VALUES ('POPULAR_LAST_YEAR');
INSERT INTO tags (name) VALUES ('ALL_TIME_POPULAR');

-- Editoriales
INSERT INTO publishers (description, name, web_site) VALUES ('Editorial 1', 'Editorial A', 'http://editoriala.com');
INSERT INTO publishers (description, name, web_site) VALUES ('Editorial 2', 'Editorial B', 'http://editorialb.com');
INSERT INTO publishers (description, name, web_site) VALUES ('Una editorial dedicada a la literatura clásica y académica.', 'Academia Literaria', 'http://www.academialiteraria.com');
INSERT INTO publishers (description, name, web_site) VALUES ('Especializados en novelas de ciencia ficción y fantasía.', 'Galaxia Imaginaria', 'http://www.galaxiaimaginaria.net');
INSERT INTO publishers (description, name, web_site) VALUES ('Líderes en publicaciones de libros de cocina y gastronomía.', 'Culinaria Ediciones', 'http://www.culinariaediciones.com');
INSERT INTO publishers (description, name, web_site) VALUES ('Difundiendo conocimientos a través de libros de no ficción.', 'Sabiduría Impresa', 'http://www.sabiduriaimpresa.org');
INSERT INTO publishers (description, name, web_site) VALUES ('Explorando el mundo a través de guías de viaje y aventura.', 'Mundo Viajero Ediciones', 'http://www.mundoviajeroediciones.com');


-- Autores
INSERT INTO authors (biography, birth_date, first_name, last_name, nationality) VALUES ('Biografía de Autor 1', '1990-01-01', 'Autor', 'Uno', 'Nacionalidad 1');
INSERT INTO authors (biography, birth_date, first_name, last_name, nationality) VALUES ('Biografía de Autor 2', '1985-03-15', 'Autor', 'Dos', 'Nacionalidad 2');
INSERT INTO authors (biography, birth_date, first_name, last_name, nationality) VALUES ('Escritor prolífico con una pasión por la poesía y la narrativa corta.', '1980-05-20', 'Luis', 'Martínez', 'Mexicana');
INSERT INTO authors (biography, birth_date, first_name, last_name, nationality) VALUES ('Autora de novelas románticas que cautivan corazones.', '1972-11-10', 'Isabella', 'García', 'Española');
INSERT INTO authors (biography, birth_date, first_name, last_name, nationality) VALUES ('Explorador de lo desconocido a través de relatos de ciencia ficción.', '1995-02-28', 'Alex', 'Johnson', 'Estadounidense');
INSERT INTO authors (biography, birth_date, first_name, last_name, nationality) VALUES ('Escribiendo sobre historia y misterio con un toque de humor.', '1988-07-15', 'Sophie', 'Wong', 'Canadiense');
INSERT INTO authors (biography, birth_date, first_name, last_name, nationality) VALUES ('Narrador de cuentos infantiles que fomentan la imaginación.', '1979-03-03', 'Marta', 'López', 'Argentina');



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
INSERT INTO books (description, isbn, language, pages, publish_date, stock, title, author_id, publisher_id) VALUES ('Descripción del libro 2', '9876543210', 'Inglés', 250, '2022-12-10', 30, 'Libro 2', 2, 2);
INSERT INTO books (description, isbn, language, pages, publish_date, stock, title, author_id, publisher_id) VALUES ('Una historia épica de aventuras y valentía en un mundo mágico.', '9781234567890', 'Español', 350, '2023-05-20', 75, 'La Espada de los Reinos', 3, 3);
INSERT INTO books (description, isbn, language, pages, publish_date, stock, title, author_id, publisher_id) VALUES ('Un romance apasionante ambientado en las calles de París.', '9789876543210', 'Inglés', 300, '2023-02-15', 40, 'Bajo la Torre Eiffel', 4, 7);
INSERT INTO books (description, isbn, language, pages, publish_date, stock, title, author_id, publisher_id) VALUES ('Una odisea galáctica llena de descubrimientos y desafíos.', '9780123456789', 'Inglés', 400, '2022-11-30', 60, 'Viaje a las Estrellas', 5, 4);
INSERT INTO books (description, isbn, language, pages, publish_date, stock, title, author_id, publisher_id) VALUES ('Un misterio histórico que desafiará las mentes curiosas.', '9785432109876', 'Español', 320, '2022-08-10', 25, 'El Enigma del Pasado', 6, 5);
INSERT INTO books (description, isbn, language, pages, publish_date, stock, title, author_id, publisher_id) VALUES ('Cuentos mágicos para niños que sueñan en grande.', '9780918273645', 'Español', 150, '2023-04-05', 80, 'Aventuras en el Bosque Encantado', 7, 6);

-- Relaciones Libro-Género
INSERT INTO book_genres (book_id, genre_id) VALUES (1, 1);
INSERT INTO book_genres (book_id, genre_id) VALUES (1, 2);

-- Libro 1
INSERT INTO book_genres (book_id, genre_id) VALUES (1, 3);
INSERT INTO book_genres (book_id, genre_id) VALUES (1, 6);

-- Libro 2
INSERT INTO book_genres (book_id, genre_id) VALUES (2, 2);
INSERT INTO book_genres (book_id, genre_id) VALUES (2, 8);

-- Libro 3
INSERT INTO book_genres (book_id, genre_id) VALUES (3, 1);
INSERT INTO book_genres (book_id, genre_id) VALUES (3, 5);

-- Libro 4
INSERT INTO book_genres (book_id, genre_id) VALUES (4, 4);
INSERT INTO book_genres (book_id, genre_id) VALUES (4, 12);

-- Libro 5
INSERT INTO book_genres (book_id, genre_id) VALUES (5, 9);
INSERT INTO book_genres (book_id, genre_id) VALUES (5, 13);

-- Libro 6
INSERT INTO book_genres (book_id, genre_id) VALUES (6, 7);
INSERT INTO book_genres (book_id, genre_id) VALUES (6, 16);

-- Libro 7
INSERT INTO book_genres (book_id, genre_id) VALUES (7, 11);
INSERT INTO book_genres (book_id, genre_id) VALUES (7, 14);




-- Relaciones Libro-Etiqueta

-- Libro 1
INSERT INTO book_tags (book_id, tag_id) VALUES (1, 1);
INSERT INTO book_tags (book_id, tag_id) VALUES (1, 2);

-- Libro 2
INSERT INTO book_tags (book_id, tag_id) VALUES (2, 1);
INSERT INTO book_tags (book_id, tag_id) VALUES (2, 3);

-- Libro 3
INSERT INTO book_tags (book_id, tag_id) VALUES (3, 2);
INSERT INTO book_tags (book_id, tag_id) VALUES (3, 4);

-- Libro 4
INSERT INTO book_tags (book_id, tag_id) VALUES (4, 3);
INSERT INTO book_tags (book_id, tag_id) VALUES (4, 5);

-- Libro 5
INSERT INTO book_tags (book_id, tag_id) VALUES (5, 4);
INSERT INTO book_tags (book_id, tag_id) VALUES (5, 6);

-- Libro 6
INSERT INTO book_tags (book_id, tag_id) VALUES (6, 5);
INSERT INTO book_tags (book_id, tag_id) VALUES (6, 7);

-- Libro 7
INSERT INTO book_tags (book_id, tag_id) VALUES (7, 6);
INSERT INTO book_tags (book_id, tag_id) VALUES (7, 8);



-- Usuarios
--INSERT INTO users (last_name, email, first_name, password, username) VALUES ('Apellido 1', 'usuario1@example.com', 'Usuario', 'password1', 'usuario1');
--INSERT INTO users (last_name, email, first_name, password, username) VALUES ('Apellido 2', 'usuario2@example.com', 'Usuario', 'password2', 'usuario2');

-- INSERT INTO users (last_name, email, first_name, password, username) VALUES ('admin', 'admin@gmail.com', 'admin', '1234', 'admin');
