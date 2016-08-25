package com.yangui.test.env;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class TestEnv {

	public static void main(String[] args) {
		Map map = System.getenv();  
		Iterator it = map.entrySet().iterator();  
		while(it.hasNext())  
		{  
		    Entry entry = (Entry)it.next();  
		    System.out.print(entry.getKey()+"=");  
		    System.out.println(entry.getValue());  
		}  
	}
}
