# java-filmorate
Template repository for Filmorate project.
</br>
</br>
</br>
</br>
![](src/main/resources/SchemaBD.markdown)
# Описание схемы:
 1. users: 
<!-- TOC -->
Содержит данные о пользователях. Таблица включает в себя следующие поля:
* Первичный ключ id - идентификатор пользователя;
* email - электронная почта пользователя;
* name - имя пользователя;
* login - логин пользователя;
* birthday - дата рождения пользователя
<!-- TOC -->
 2. films:
<!-- TOC -->
Содержит данные о фильмах. Таблица включает в себя следующие поля:
* Первичный ключ id - идентификатор фильма;
* Внешний ключ mpa_id (ссылает к таблице mpa) - идентификатор рейтинга;
* name - название фильма
* description - описание фильма
* release_data - дата релиза
* duration - продолжительность фильма
<!-- TOC -->
3. friendship: 
<!-- TOC -->
* Первичный ключ user_id (ссылается к таблице user) - идентификатор пользователя;
* Внешний ключ friend_id (ссылается к таблице user) - идентификатор друга(пользователя);
* Внешний ключ status_id (ссылается к таблице status_friendship)- идентификатор статуса 
<!-- TOC -->
4. status_friendship:
<!-- TOC -->
* Первичный ключ status_id - идентификатор статуса 
* name - имя идентификатора 
<!-- TOC -->
5. genres:
<!-- TOC -->
* Первичный ключ id - идентификатор жанра
* name - название жанра
<!-- TOC -->
6. mpa:
<!-- TOC -->
* Первичный ключ mpa_id - идентификатор рейтинга
* name - название рейтинга
<!-- TOC -->
7. likes_films:
<!-- TOC -->
* Внешний ключ film_id (ссылается на таблицу films) - идентификатор фильма
* Внешний ключ user_id (ссылается на таблицу users) - идентификатор пользователя
<!-- TOC -->
8. genres_films:
<!-- TOC -->
* Внешний ключ film_id (ссылается на таблицу films) - идентификатор фильма
* Внешний ключ genres_id (ссылается на таблицу genres) - идентификатор пользователя
<!-- TOC -->
# Примеры запросов к БД:

1. Получение фильма по идентификатору</br>

SELECT* FROM FILMS </br>
WHERE ID=?

2. Получение популярных фильмов </br>

SELECT f.* </br>
FROM LIKES_FILMS lf </br>
INNER JOIN FILMS f ON f.ID = lf.FILM_ID </br>
GROUP BY f.ID </br>
ORDER BY COUNT(lf.USER_ID) DESC </br>
LIMIT ?;

3. Получение пользователя по идентификатору </br>

SELECT* FROM USERS </br>
WHERE id = ?

4. Получение списка друзей конкретного пользователя </br>

SELECT u.* </br>
FROM FRIENDSHIP f </br>
INNER JOIN USERS u ON f.FRIENDS_ID = u.ID </br>
WHERE f.USER_ID = ? AND f.STATUS_ID = 1;</br>

5. Получение списка общих друзей </br>

SELECT u.* </br>
FROM FRIENDSHIP f </br>
INNER JOIN USERS u ON u.ID = f.FRIENDS_ID </br>
WHERE F.USER_ID = ? OR  F.USER_ID = ? </br>
GROUP BY F.FRIENDS_ID </br>
HAVING COUNT(F.FRIENDS_ID)>1;</br>




   