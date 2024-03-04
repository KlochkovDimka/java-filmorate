# java-filmorate
Template repository for Filmorate project.
</br>
</br>
</br>
</br>
![](src/main/resources/SchemaBD.markdown)
# Описание схемы:
 1. user: 
<!-- TOC -->
Содержит данные о пользователях. Таблица включает в себя следующие поля:
* Первичный ключ user_id - идентификатор пользователя;
* email - электронная почта пользователя;
* name - имя пользователя;
* login - логин пользователя;
* birthday - дата рождения пользователя
<!-- TOC -->
 2. film:
<!-- TOC -->
Содержит данные о фильмах. Таблица включает в себя следующие поля:
* Первичный ключ film_id - идентификатор фильма;
* Внешний ключ genre_id (ссылает к таблице genre_film) - идентификатор жанра;
* Внешний ключ rating_id (ссылает к таблице rating_film) - идентификатор рейтинга;
* Внешний ключ user_id (ссылает к таблице user_id) - идентификатор пользователя !!!!
* name - название фильма
* description - описание фильма
* release_data - дата релиза
* duration - продолжительность фильма
<!-- TOC -->
3. friendship: 
<!-- TOC -->
* Первичный ключ user_id (ссылается к таблице user) - идентификатор пользователя;
* Внешний ключ friend_id (ссылается к таблице user) - идентификатор друга(пользователя);
* Внешний ключ status_id - идентификатор статуса 
<!-- TOC -->
4. status_friendship:
<!-- TOC -->
* Первичный ключ status_id - идентификатор статуса 
* name - имя идентификатора 
<!-- TOC -->
5. genre_film:
<!-- TOC -->
* Первичный ключ genre_id - идентификатор жанра
* name - название жанра
<!-- TOC -->
6. rating_film:
<!-- TOC -->
* Первичный ключ rating_id - идентификатор рейтинга
* name - название рейтинга
<!-- TOC -->
7. likes_film:
<!-- TOC -->
* Первичный ключ like_film_id - идентификатор лайка фильму
* Внешний ключ film_id (ссылается на таблицу film) - идентификатор фильма
* Внешний ключ user_id (ссылается на таблицу user) - идентификатор пользователя
<!-- TOC -->
# Примеры запросов к БД:

1. Получение фильма по идентификатору</br>

SELECT* FROM film </br>
WHERE film_id = 1;

2. Получение популярных фильмов </br>

SELECT f.name AS film_name, </br>
       COUNT(lf.user_id) AS count_like </br> 
FROM likes_films AS lf </br>
LEFT JOIN film AS f ON f.film_id = lf.film_id </br>
GROUP BY film_name </br>
ORDER BY count_like DESC </br>
LIMIT 10;

3. Получение пользователя по идентификатору </br>

SELECT* FROM user </br>
WHERE user_id = 1;

4. Получение списка друзей конкретного пользователя </br>

SELECT friend_id </br>
FROM friendship </br>
WHERE user_id = 1 </br>
AND status_id = 'CONFIRMED'; </br>

5. Получение списка общих друзей </br>

SELECT friend_id </br>
FROM friendship </br>
WHERE user_id = 1 </br>
AND user_id = 2 </br> 
HAVING COUNT(friend_id)>1;




   