
MERGE INTO genre AS g --MERGE сливаем данные из одной таблицы в другую
USING (VALUES         --создает временную таблицу source, кот.содержит одно поле genre_name
('Комедия'),
('Драма'),
('Мультфильм'),
('Триллер'),
('Документальный'),
('Боевик')
)AS source(genre_name)
ON g.name = source.genre_name --условие соединения, которое определяет, как сливаются записи
WHEN NOT MATCHED THEN -- указывает, что делать, если записи из `source` не находят совпадения в таблице `genre`
INSERT (name) VALUES (source.genre_name); --в колонку `name` таблицы `genre` вставлять данные genre_name из табл. source


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