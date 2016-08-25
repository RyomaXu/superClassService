package com.yangui.utils.uuid;

import org.doomdark.uuid.UUIDGenerator;      

public class UuidUtil {
	

	public static String createUUId() {
		String uuid = UUIDGenerator.getInstance()      
                .generateRandomBasedUUID().toString();    
        uuid = uuid.replaceAll("-", "");
        return uuid;
	}      
     
} 
