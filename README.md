# Filmorate
сервис для оценки и рекомендации фильмов.

### &#128736; Стек:
![Java](https://img.shields.io/badge/java-%25?style=for-the-badge&logo=java&color=blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-%252300758F.svg?style=for-the-badge&logo=PostgreSQL&color=lightskyblue)
![Spring](https://img.shields.io/badge/spring-%25?style=for-the-badge&logo=spring&color=lavenderblush)
![Jdbc](https://img.shields.io/badge/Jdbc-%25?style=for-the-badge&color=lavender)
![REST API](https://img.shields.io/badge/REST%20API-%23266999.svg?style=for-the-badge&color=teal)
![JUnit](https://img.shields.io/badge/JUnit-%25?style=for-the-badge&color=crimson)
![Git](https://img.shields.io/badge/Git-%25.svg?style=for-the-badge&logo=git&color=black)

### ✔ Функциональность:
 - добавление/изменение пользователей;
 - получение списка всех пользователей;
 - добавление/изменение фильмов;
 - получение списка всех фильмов с возможностью фильтрации и сортировки;

В командной работе реализовала:
- возможность выводить топ-N фильмов по количеству лайков;
- добавление режиссера фильма;

### 🖥️ Запуск:
1. Склонируйте репозиторий:
   ```sh
      https://github.com/somikaaaA/java-filmorate.git
   ```
2. Откройте проект в IntelliJ IDEA;
3. Запустите проект на устройстве;

## Схема базы данных
![Untitled](https://github.com/user-attachments/assets/06e694f9-7a75-49ec-bfa0-06449355f872)
### `rating`

Содержит информацию о рейтингах Motion Picture Association.

Таблица состоит из полей:

- `rating_id` — первичный ключ идентификатор рейтинга;
- `name` — название рейтинга;

### `film`

Содержит информацию о фильмах.

Таблица состоит из полей:

- первичный ключ `film_id` — идентификатор фильма;
- `name` — название фильма;
- `description` — описание фильма;
- `release_date` — дата выхода;
- `duration` — продолжительность фильма в минутах;
- `rating_id` — идентификатор рейтинга из mpa_rating;

### `genre`

Содержит информацию о жанрах фильмов.

Таблица состоит из полей:

- первичный ключ `genre_id` — идентификатор жанра;
- `name` — название жанра;

### `filmGenre`

Содержит информацию о жанрах фильмов из таблицы film.

Таблица состоит из полей:

- первичный ключ `film_genre_id`
- `film_id` — идентификатор фильма из film;
- `genre_id` — идентификатор жанра из genre;

### `likes`

Содержит информацию о лайках к фильмам из таблицы film.

Таблица состоит из полей:

- первичный ключ `likes_id`
- `film_id` — идентификатор фильма из film;
- `like_user_id` — идентификатор пользователя из user;

В этой таблице составной первичный ключ по полям film_id и user_id

### `user`

Содержит информацию о пользователях.

Таблица состоит из полей:

- первичный ключ `user_id` — идентификатор пользователя;
- `email` — email пользователя [уникальное не нулевое поле];
- `name` — имя пользователя;
- `birthday` — дата рождения;

### `friends`

Содержит информацию о друзьях.

Таблица состоит из полей:

- первичный ключ `friends_id`;
- `user_id` — пользователь, который отправил запрос на добавление в друзья;
- `friend_user_id` — пользователь которому отправлен запрос в друзья;
- `status_id` — id статуса запроса

### `status`

Содержит информацию о статусе запроса.

Таблица состоит из полей:

- первичный ключ `status_id`;
- `name` — наименование статуса
