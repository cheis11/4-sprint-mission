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
    public Optional<Channel> getChannel(UUID channelId) {
        return data.stream()
                .filter(channel -> channel.getChannelId().equals(channelId))
                .findFirst();
    }

    @Override
    public List<Channel> getChannelContains(String channelName) {
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
    public List<User> getUserInChannel(Channel channel) {
        return data.stream()
                .filter(c -> c.getChannelId().equals(channel.getChannelId()))
                .flatMap(c -> c.getChannelsUsers().stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> getMessagesToChannel(Channel channel) {
        return data.stream()
                .filter(c -> c.getChannelId().equals(channel.getChannelId()))
                .flatMap(c -> c.getChannelMessages().stream())
                .collect(Collectors.toList());
    }


    @Override
    public void updateChannel(UUID ChannelId, String updatedText) {
        data.stream()
                .filter(channel -> channel.getChannelId().equals(ChannelId))
                .findFirst()
                .ifPresent(channel -> {
                    channel.setChannelName(updatedText);
                    channel.setUpdatedAt();
                });
    }

    @Override
    public void addUser(Channel channel, User user) {
        if(!channel.getChannelsUsers().contains(user)) {
            channel.getChannelsUsers().add(user);
            user.getUsersChannels().add(channel);
            channel.setUpdatedAt();
            System.out.println(user.getUserName() + "님이 " + channel.getChannelName() + " 채널에 참가하셨습니다.");
        }
    }

    @Override
    public void removeUser(Channel channel, User user) {
        if(channel.getChannelsUsers().contains(user)) {
            channel.getChannelsUsers().remove(user);
            user.getUsersChannels().remove(channel);
            channel.setUpdatedAt();
            System.out.println(user.getUserName() + "님이 " + channel.getChannelName() + " 채널에서 퇴장하셨습니다.");
        }
    }

    @Override
    public void deleteChannel(Channel channel) {
        data.remove(channel);
    }
}
