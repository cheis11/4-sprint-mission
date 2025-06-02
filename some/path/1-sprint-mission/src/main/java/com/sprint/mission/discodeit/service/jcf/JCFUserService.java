package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class JCFUserService implements UserService {
    private final List<User> data;

    public JCFUserService() {
        this.data = new ArrayList<>();
    }

    @Override
    public User addUser(User user) {
        data.add(user);
        return user;
    }

    @Override
    public List<User> getUsers() {
        return data.stream()
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> getUser(UUID UserId) {
        return data.stream()
                .filter(user -> user.getUserId().equals(UserId))
                .findFirst();
    }

    @Override
    public List<User> getUserContains(String UserName) {
        return data.stream()
                .filter(user -> user.getUserName().contains(UserName))
                .collect(Collectors.toList());
    }

    @Override
    public void updateUser(UUID userId, String updatedText) {
        data.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .ifPresent(user -> {
                    user.setUserName(updatedText);
                    user.setUpdatedAt();
                });
    }

    @Override
    public void deleteUser(User user) {
        data.remove(user);
    }

    @Override
    public List<Message> getMessageToUser(User user) {
        return user.getUsersMessages();
    }
}
