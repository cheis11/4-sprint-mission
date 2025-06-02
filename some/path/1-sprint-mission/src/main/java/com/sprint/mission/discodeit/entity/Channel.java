package com.sprint.mission.discodeit.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Channel {
    private final UUID channelId;
    private final Long createdAt;
    private final List<User> channelsUsers;
    private final List<Message> channelsMessages;
    private Long updatedAt;
    private String channelName;


    @Override
    public String toString() {
        String userNames = channelsUsers.stream()
                .map(User::getUserName)
                .collect(Collectors.joining(", "));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String created = sdf.format(new Date(createdAt));
        String updated = sdf.format(new Date(updatedAt));

        return "Channel{" +"\n"+
                "channelName=" + channelName+"\n"+
                "channelsUsers=" + userNames +"\n"+
                "createdAt=" + created +"\n"+
                "updatedAt=" + updated +"\n"+
                '}'+"\n";
    }

    public Channel(String channelName, User user) {
        channelId = UUID.randomUUID();
        this.channelName = channelName;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        channelsUsers = new ArrayList<>();
        channelsUsers.add(user);
        channelsMessages = new ArrayList<>();
        user.getUsersChannels().add(this);
    }


    public void setUpdatedAt() {
        this.updatedAt = System.currentTimeMillis();
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public List<Message> getChannelMessages() {
        return channelsMessages;
    }

    public UUID getChannelId() {
        return channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public List<User> getChannelsUsers() {
        return channelsUsers;
    }

}
