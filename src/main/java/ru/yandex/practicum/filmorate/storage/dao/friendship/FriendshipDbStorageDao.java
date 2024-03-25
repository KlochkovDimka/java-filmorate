package ru.yandex.practicum.filmorate.storage.dao.friendship;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendshipDbStorageDao {

    List<User> findFriendsUserById(Long userId);

    User addInListFriendsById(Long userId, Long friendId, int statusId);

    void deleteOfListFriendsById(Long userId, Long friendId);

    List<User> findListCommonFriendsById(Long userId, Long friendId);
}
