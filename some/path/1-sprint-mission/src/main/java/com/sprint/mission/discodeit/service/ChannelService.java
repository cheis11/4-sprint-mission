package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelService {
    public Channel createChannel(Channel channel);//채널 생성
    public List<Channel> getChannels();//모든 채널 출력
    public Optional<Channel> getChannel(UUID channelId);//특정 채널 출력
    public List<Channel> getChannelContains(String channelName);//특정 이름이 포함되어있는 채널 출력
    public List<Message> getMessagesByUserInChannel(Channel channel, User user);//채널에서 특정 유저가 쓴 메세지 확인
    public List<User> getUserInChannel(Channel channel);
    public List<Message> getMessagesToChannel(Channel channel);
    public void updateChannel(UUID ChannelId, String updatedText);//채널 이름 업데이트
    public void addUser(Channel channel, User user);//채널에 유저 참가
    public void removeUser(Channel channel, User user);//채널에서 유저 추방
    public void deleteChannel(Channel channel);//채널 삭제
}