package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    public User addUser(User user);//유저 가입
    public List<User> getUsers();//모든 유저 출력
    public Optional<User> getUser(UUID UserId);//특정 유저 출력
    public List<User> getUserContains(String UserName);//특정 단어가 들어가있는 유저 출력
    public void updateUser(UUID userId, String updatedText);//유저 이름 수정
    public void deleteUser(User user);//유저 탈퇴
    public List<Message> getMessageToUser(User user);//유저가 쓴 메세지 출력

}