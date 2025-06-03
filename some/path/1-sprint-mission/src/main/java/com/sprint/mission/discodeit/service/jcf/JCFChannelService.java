package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;
import java.util.stream.Collectors;

public class JCFChannelService implements ChannelService {
    private final List<Channel> data ;

    public JCFChannelService() {
        this.data = new ArrayList<>();
    }

    @Override
    public Channel createChannel(Channel channel) {
        data.add(channel);
        return channel;
    }

    @Override
    public List<Channel> getChannels() {
        return data.stream()
                .collect(Collectors.toList());
    }
//optional로 바꾸기
    @Override
    public Optional<Channel> findChannelById(UUID channelId) {
        return data.stream()
                .filter(channel -> channel.getChannelId().equals(channelId))
                .findFirst();
    }

    @Override
    public List<Channel> findChannelsByNameContains(String channelName) {
        return data.stream()
                .filter(channel -> channel.getChannelName().contains(channelName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> getMessagesByUserInChannel(Channel channel, User user) {
        return channel.getChannelMessages().stream()
                .filter(message -> message.getUser().equals(user))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getUsersInChannel(Channel channel) {
        return data.stream()
                .filter(c -> c.getChannelId().equals(channel.getChannelId()))
                .flatMap(c -> c.getUsers().stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> getMessagesInChannel(Channel channel) {
        return data.stream()
                .filter(c -> c.getChannelId().equals(channel.getChannelId()))
                .flatMap(c -> c.getChannelMessages().stream())
                .collect(Collectors.toList());
    }


    @Override
    public void updateChannelName(UUID ChannelId, String updatedText) {
        data.stream()
                .filter(channel -> channel.getChannelId().equals(ChannelId))
                .findFirst()
                .ifPresent(channel -> {
                    channel.setChannelName(updatedText);
                    channel.setUpdatedAt();
                });
    }
    //void로 바꿀것
    @Override
    public void joinUser(Channel channel, User user) {
        channel.addUser(user);
    }

    @Override
    public void leaveUser(Channel channel, User user) {
        channel.removeUser(user);
    }

    @Override
    public void deleteChannel(Channel channel) {
        data.remove(channel);
    }
}
