package com.yangui.test.localHost;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHostTest {      
    public static void main(String[] args) throws UnknownHostException {      
    	InetAddress localHost = InetAddress.getLocalHost();
		String hostAddress = localHost.getHostAddress();
		System.out.println(hostAddress); 
    }      
     
} 
