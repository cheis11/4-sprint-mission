package com.sprint.mission.discodeit.factory;


import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;

public class ServiceFactory {
    public static UserService createUserService(){
        return new JCFUserService();
    }
    public static MessageService createMessageService(){
        return new JCFMessageService();
    }
    public static ChannelService createChannelService(){
        return new JCFChannelService();
    }
}
