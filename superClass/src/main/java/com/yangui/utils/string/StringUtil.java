package com.yangui.utils.string;

import java.util.List;

import com.yangui.entity.BaseEntity;

/**
 * 字符串工具类
 * @author yan gui
 * @param <T>
 */
public class StringUtil<T> {


	private static final BaseEntity T = null;

	/**
	 * 根据传入的实体集合返回Ids
	 * @param 实体集合
	 * @return Json格式下里面的数组Ids
	 */
	public String getIDsToJsonArray(List<T> entities){
		StringBuffer stringBuffer=new StringBuffer();
		if(entities.get(0)!=null && entities.get(0)  instanceof BaseEntity){
			for (int i = 0; i < entities.size(); i++) {
				if(i==0){
					stringBuffer.append("['");
					stringBuffer.append(((BaseEntity) entities.get(i)).getId()+"',");
				}
				else if(i==entities.size()-1){
					stringBuffer.append("'"+((BaseEntity) entities.get(i)).getId()+"']");
				}else{
					stringBuffer.append("'"+((BaseEntity) entities.get(i)).getId()+"',");
				}
			}
		}
		String ids=stringBuffer.toString();
		return ids;
	}
}
