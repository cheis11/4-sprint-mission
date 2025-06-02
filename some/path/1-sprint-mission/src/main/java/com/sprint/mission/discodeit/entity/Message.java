package com.sprint.mission.discodeit.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Message {
    private final UUID messageId;
    private final User user;
    private final Channel channel;
    private final Long createdAt;
    private Long updatedAt;
    private String contents;


    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String created = sdf.format(new Date(createdAt));
        String updated = sdf.format(new Date(updatedAt));

        return "Message{" +"\n"+
                "messageContents=" + contents +"\n"+
                "userName=" + user.getUserName() +"\n"+
                "channelName=" + channel.getChannelName() +"\n"+
                "createdAt=" + created +"\n"+
                "updatedAt=" + updated +"\n"+
                '}'+"\n";
    }

    public Message(String messageContents, User user, Channel channel) {
        messageId = UUID.randomUUID();
        this.contents = messageContents;
        this.user = user;
        this.channel = channel;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        user.getUsersMessages().add(this);
        channel.getChannelMessages().add(this);
    }


    public void setUpdatedAt() {
        this.updatedAt = System.currentTimeMillis();
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public UUID getMessageId() {
        return messageId;
    }

    public String getContent() {
        return contents;
    }

    public User getUser() {
        return user;
    }

    public Channel getChannel() {
        return channel;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public Long getCreatedAt() {
        return createdAt;
    }
}
