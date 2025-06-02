package com.sprint.mission.discodeit.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class User {
    private final UUID userId;
    private final Long createdAt;
    private final List<Channel> usersChannels;
    private final List<Message> usersMessages;
    private Long updatedAt;
    private String userName;

    @Override
    public String toString() {
        String channelNames = usersChannels.stream()
                .map(Channel::getChannelName)
                .collect(Collectors.joining(", "));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String created = sdf.format(new Date(createdAt));
        String updated = sdf.format(new Date(updatedAt));

        return "User{" +"\n"+
                "userName=" + userName +"\n"+
                "UsersChannels=" + channelNames +"\n"+
                "createdAt=" + created +"\n"+
                "updatedAt=" + updated +"\n"+
                '}'+"\n";
    }

    public User(String userName) {
        this.userId = UUID.randomUUID();
        this.userName = userName;
        createdAt = System.currentTimeMillis();
        updatedAt = System.currentTimeMillis();
        usersChannels = new ArrayList<>();
        usersMessages = new ArrayList<>();
    }


    public void setUpdatedAt() {
        this.updatedAt = System.currentTimeMillis();;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public List<Message> getUsersMessages() {
        return usersMessages;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public List<Channel> getUsersChannels() {
        return usersChannels;
    }

}
