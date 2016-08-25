package com.yangui.common.emchat;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yangui.common.emchat.api.ChatGroupAPI;
import com.yangui.common.emchat.api.ChatMessageAPI;
import com.yangui.common.emchat.api.ChatRoomAPI;
import com.yangui.common.emchat.api.FileAPI;
import com.yangui.common.emchat.api.IMUserAPI;
import com.yangui.common.emchat.api.SendMessageAPI;
import com.yangui.common.emchat.comm.ClientContext;
import com.yangui.common.emchat.comm.EasemobRestAPIFactory;
import com.yangui.common.emchat.comm.body.IMUserBody;
import com.yangui.common.emchat.comm.wrapper.BodyWrapper;
import com.yangui.common.emchat.comm.wrapper.ResponseWrapper;

public class Main {

	public static void main(String[] args) throws Exception {
		EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
		
		IMUserAPI user = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
		ChatMessageAPI chat = (ChatMessageAPI)factory.newInstance(EasemobRestAPIFactory.MESSAGE_CLASS);
		FileAPI file = (FileAPI)factory.newInstance(EasemobRestAPIFactory.FILE_CLASS);
		SendMessageAPI message = (SendMessageAPI)factory.newInstance(EasemobRestAPIFactory.SEND_MESSAGE_CLASS);
		ChatGroupAPI chatgroup = (ChatGroupAPI)factory.newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
		ChatRoomAPI chatroom = (ChatRoomAPI)factory.newInstance(EasemobRestAPIFactory.CHATROOM_CLASS);

        ResponseWrapper fileResponse = (ResponseWrapper) file.uploadFile(new File("d:/logo.png"));
        String uuid = ((ObjectNode) fileResponse.getResponseBody()).get("entities").get(0).get("uuid").asText();
        String shareSecret = ((ObjectNode) fileResponse.getResponseBody()).get("entities").get(0).get("share-secret").asText();
        InputStream in = (InputStream) ((ResponseWrapper) file.downloadFile(uuid, shareSecret, false)).getResponseBody();
        FileOutputStream fos = new FileOutputStream("d:/logo1.png");
        byte[] buffer = new byte[1024];
        int len1 = 0;
        while ((len1 = in.read(buffer)) != -1) {
            fos.write(buffer, 0, len1);
        }
        fos.close();

      
		// Create a IM user
		BodyWrapper userBody = new IMUserBody("yangui", "123456", "HelloWorld");
		user.createNewIMUserSingle(userBody);
		  /*
		// Create some IM users
		List<IMUserBody> users = new ArrayList<IMUserBody>();
		users.add(new IMUserBody("User002", "123456", null));
		users.add(new IMUserBody("User003", "123456", null));
		BodyWrapper usersBody = new IMUsersBody(users);
		user.createNewIMUserBatch(usersBody);
		
		// Get a IM user
		user.getIMUsersByUserName("User001");
		
		// Get a fake user
		user.getIMUsersByUserName("FakeUser001");
		// Get 12 users
		user.getIMUsersBatch(null, null);
		*/
		
        //创建群组
//        String[] strs={"User002","User003"};
//        BodyWrapper chatGroupBody= new ChatGroupBody("testchatgroup", "server create group", true, 100L, true, "User101",strs);
//        chatgroup.createChatGroup(chatGroupBody);
        
//        BodyWrapper chatRoomBody=new  ChatRoomBody("testChatRoom", "server create chatroom", 100L,"User101",strs);
//        chatroom.createChatRoom(chatRoomBody);
//        Object c =chatroom.getAllChatRooms();
//        System.out.println(c.toString());
        
//		chatgroup.removeSingleUserFromChatGroup("193967983813460412","User003");
//        Object cs=      (Object) chatgroup.getChatGroupUsers("193967983813460412");
//System.out.println(cs.toString());        	
	}

}
