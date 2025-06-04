package com.sprint.mission.discodeit.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Channel extends BaseEntity {
    private final UUID channelId;
    private List<User> users;
    private List<Message> messages;
    private String channelName;
    private ChannelState state;

    @Override
    public String toString() {
        String userNames = users.stream()
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
        users = new ArrayList<>();
        users.add(user);
        messages = new ArrayList<>();
        user.getChannels().add(this);
    }

    public void addUser(User user) {
        if(!users.contains(user)) {
            users.add(user);
            user.addChannel(this);
        }
    }
    public void removeUser(User user) {
        if(users.contains(user)) {
            users.remove(user);
            user.removeChannel(this);
        }
    }

    public void addMessage(Message message) {
        if(!messages.contains(message)) {
            messages.add(message);
            message.addChannel(this);
        }
    }
    public void removeMessage(Message message) {
        if(messages.contains(message)) {
            messages.remove(message);
            message.removeChannel(this);
        }
    }
    public void activateChannelState(){
        state = ChannelState.activated;
    }
    public void deactivateChannelState(){
        state = ChannelState.deactivated;
    }
    public void deleteChannelState(){
        state = ChannelState.deleted;
    }


    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public ChannelState getState() {
        return state;
    }

    public List<Message> getChannelMessages() {
        return messages;
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

    public List<User> getUsers() {
        return users;
    }

}
