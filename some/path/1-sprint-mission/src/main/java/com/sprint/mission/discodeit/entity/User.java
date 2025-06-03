package com.sprint.mission.discodeit.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class User extends BaseEntity {
    private final UUID userId;
    private final List<Channel> channels;
    private final List<Message> messages;
    private String userName;

    @Override
    public String toString() {
        String channelNames = channels.stream()
                .map(Channel::getChannelName)
                .collect(Collectors.joining(", "));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String created = sdf.format(new Date(createdAt));
        String updated = sdf.format(new Date(updatedAt));

        return "User{" +"\n"+
                "userName=" + userName +"\n"+
                "channels=" + channelNames +"\n"+
                "createdAt=" + created +"\n"+
                "updatedAt=" + updated +"\n"+
                '}'+"\n";
    }

    public User(String userName) {
        this.userId = UUID.randomUUID();
        this.userName = userName;
        channels = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public void addChannel(Channel channel) {
        if(!channels.contains(channel)) {
            channels.add(channel);
            channel.addUser(this);
        }
    }
    public void removeChannel(Channel channel) {
        if(channels.contains(channel)) {
            channels.remove(channel);
            channel.removeUser(this);
        }
    }

    public void addMessage(Message message) {
        if(!messages.contains(message)) {
            messages.add(message);
            message.addUser(this);
        }
    }
    public void removeMessage(Message message) {
        if(messages.contains(message)) {
            messages.remove(message);
            message.removeUser(this);
        }
    }
    public void removeAllMessages(){
        messages.clear();
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Message> getmessages() {
        return messages;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public List<Channel> getchannels() {
        return channels;
    }

}
