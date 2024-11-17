
MERGE INTO genre AS g
USING (VALUES
('Комедия'),
('Драма'),
('Мультфильм'),
('Триллер'),
('Документальный'),
('Боевик')
)AS source(genre_name)
ON g.name = source.genre_name
WHEN NOT MATCHED THEN
INSERT (name) VALUES (source.genre_name);


MERGE INTO rating AS r
USING (VALUES
('G'),
('PG'),
('PG-13'),
('R'),
('NC-17')
)AS source(rating_name)
ON r.name = source.rating_name
WHEN NOT MATCHED THEN
INSERT (name) VALUES (source.rating_name);


MERGE INTO status AS s
USING (VALUES
('Done'),
('Not done')
)AS source(status_name)
ON s.name = source.status_name
WHEN NOT MATCHED THEN
INSERT (name) VALUES (source.status_name);