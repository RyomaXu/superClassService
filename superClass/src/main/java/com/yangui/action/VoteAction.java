package com.yangui.action;



import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yangui.common.Constants;
import com.yangui.entity.Group;
import com.yangui.entity.Option;
import com.yangui.entity.OptionStatistic;
import com.yangui.entity.User;
import com.yangui.entity.Vote;
import com.yangui.service.impl.GroupService;
import com.yangui.service.impl.OptionService;
import com.yangui.service.impl.OptionStatisticService;
import com.yangui.service.impl.UserService;
import com.yangui.service.impl.VoteService;

@ParentPackage("dataJsonDefault")
@Service
public class VoteAction {
	//交互数据
	private String id;//用户Id
	private String page;//页数
	private String  json;

	//单个Json
	private Map<String, Object> dataMap=new HashMap<String, Object>();
	//Json数组
	private List<Object> dataList=new ArrayList<Object>();


	@Autowired
	private UserService userService;
	@Autowired
	private GroupService  groupService;
	@Autowired
	private VoteService  voteService;
	@Autowired
	private OptionService  optionService;
	@Autowired
	private OptionStatisticService optionStatisticService;


	/**
	 * 发布投票
	 * @return
	 * @throws JSONException 
	 */
	@Action(value="/vote/publish",results={
			@Result(name="success",params={"root","dataMap"},type="json"),
			@Result(name="error",location="/error.jsp")})
	public String publish() throws JSONException{
		// dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.clear();
		User user=null;
		Group group=null;
		Vote vote=new Vote();
		if(id!=null){
			user=userService.getOne(Integer.parseInt(id));
		}
		if(user!=null){
			group=groupService.getGroupBySecret(user.getInstitute());
		}
		if(group!=null){
			//保存投票
			JSONObject jsonObject=new JSONObject(json);
			List<User> users=new ArrayList<User>();
			users.add(user);
			vote.setVoteTitle((String)jsonObject.get("votedName"));
			vote.setIsMulty((Boolean)jsonObject.get("isMulty"));
			Timestamp timestamp=new Timestamp((Long)jsonObject.get("upToDateMillis"));
			vote.setEndTime(timestamp);
			vote.setUsers(users);
			vote.setGroup(group);
			
			JSONArray jsonArray=(JSONArray) jsonObject.get("item");
			List<JSONObject> item=new ArrayList<JSONObject>();
			List<Option> options=new ArrayList<Option>();
			for (int i = 0; i <jsonArray.length(); i++) {
				item.add((JSONObject) jsonArray.get(i));
			}
			for (int i = 0; i < item.size(); i++) {
				Option option=new Option();
				option.setItemName((String)item.get(i).get("itemName"));
				option.setSupporterNum((Integer)item.get(i).get( "supporterNum"));
				option.setIsSelected((Boolean)item.get(i).get( "isSelected"));
				option.setVote(vote);
				options.add(option);
			}
			
			vote.setOptions(options);
			voteService.insert(vote);



			
			
			List<Vote> votes=new ArrayList<Vote>();
			votes.add(vote);
			user.setVote(vote);
			userService.update(user);
			group.setVotes(votes);
			groupService.update(group);
			dataMap.put("msg","成功创建投票");
		}else{
			dataMap.put("errorMsg","创建投票失败");
		}
		return "success";
	}
	
	
	
	/**
	 * 用户投票
	 * @return
	 * @throws JSONException 
	 * @throws NumberFormatException 
	 */
	@Action(value="/vote/uservote",results={
			@Result(name="success",params={"root","dataMap"},type="json"),
			@Result(name="error",location="/error.jsp")})
	public String uservote() throws NumberFormatException, JSONException{
		// dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataMap.clear();
		JSONObject jsonObject=new JSONObject(json);
		User user=null;
		Group group=null;
		List<OptionStatistic> optionStatistics=new ArrayList<OptionStatistic>();
		if(id!=null){
			user=userService.getOne(Integer.parseInt(id));
		}
		if(user!=null){
			group=groupService.getGroupBySecret(user.getInstitute());
		}
		if(group!=null){
			Vote vote=voteService.getOne((Integer) jsonObject.get("id"));
			
			JSONArray jsonArray=(JSONArray) jsonObject.get("item");
			List<JSONObject> item=new ArrayList<JSONObject>();
			List<Option> options=new ArrayList<Option>();
			for (int i = 0; i <jsonArray.length(); i++) {
				item.add((JSONObject) jsonArray.get(i));
			}
			for (int i = 0; i < item.size(); i++) {
				Option option=optionService.getOne((Integer) item.get(i).get("id"));
				option.setIsSelected((Boolean)item.get(i).get( "isSelected"));
				
				//保存选项统计
				if((Boolean)item.get(i).get( "isSelected")){
					OptionStatistic optionStatistic=new OptionStatistic();
					optionStatistic.setOptionId(option.getId());
					optionStatistic.setUserId(user.getId());
					optionStatistic.setVote(vote);
					optionStatistics.add(optionStatistic);
				}
				
				
				
				options.add(option);
				optionService.update(option);
			}
			
			
			//保存投票
			vote.setOptions(options);
			vote.setOptionStatistics(optionStatistics);
			voteService.update(vote);
			
			
			List<Vote> votes=new ArrayList<Vote>();
			votes.add(vote);
			user.setVote(vote);
			userService.update(user);
			group.setVotes(votes);
			groupService.update(group);
			dataMap.put("msg","成功投票");
		}else{
			dataMap.put("errorMsg","投票失败");
		}
		return "success";
	}




	/**
	 * 回显过期投票
	 * @return
	 */
	@Action(value="/vote/shownotnew",results={
			@Result(name="success",params={"root","dataList"},type="json"),
			@Result(name="error",location="/error.jsp")})
	public String shownotnew(){
		// dataList中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataList.clear();
		User user=null;
		Group group=null;
		List<Vote> votes=new ArrayList<Vote>();
		if(id!=null){
			user=userService.getOne(Integer.parseInt(id));
		}
		if(user!=null){
			group=groupService.getGroupBySecret(user.getInstitute());
		}
		if(group!=null && page!=null){
			votes=voteService.findVotesForPage(false,group.getId(), (Integer.parseInt(page)-1)*Constants.ACTION_VOTE_PAGELIMIT, Constants.ACTION_VOTE_PAGELIMIT);
			for (int i = 0; i < votes.size(); i++) {
				Vote vote=votes.get(i);
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("votedName", vote.getVoteTitle());
				map.put("isMulty", vote.getIsMulty());
				map.put("id", vote.getId());
				map.put("isMeVoted", optionStatisticService.isCurrentUserVoted(user.getId(), vote.getId()));
				
				List<Option> options=vote.getOptions();
				List<Object> optionList=new ArrayList<Object>();
				for (int j = 0; j < options.size(); j++) {
					Option option=options.get(j);
					Map<String, Object> optionMap=new HashMap<String, Object>();
					optionMap.put("itemName", option.getItemName());
					optionMap.put("supporterNum", option.getSupporterNum());
					optionMap.put("isSelected", option.getIsSelected());
					optionMap.put("id", option.getId());
					optionList.add(optionMap);
				}
				map.put("item",optionList);
				
				//查找该投票所有用户
				List<User> users=userService.findUsersForVote(vote.getId());
				List<Object> userList=new ArrayList<Object>();
				for (int z = 0; z < users.size(); z++) {
					User u=users.get(z);
					Map<String, Object> userMap=new HashMap<String, Object>();
					userMap.put("id", u.getId());
					userMap.put("userName", u.getNickName());
					userMap.put("avatorPath",Constants.ACTION_PHOTO_URLNOID+u.getId());
					userList.add(userMap);
				}
				map.put("votedUsers",userList);
				
				dataList.add(map);
				
			}
		}
		return "success";
	}
	
	/**
	 * 回显最新投票
	 * @return
	 */
	@Action(value="/vote/shownew",results={
			@Result(name="success",params={"root","dataList"},type="json"),
			@Result(name="error",location="/error.jsp")})
	public String shownew(){
		// dataList中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		dataList.clear();

		User user=null;
		Group group=null;
		List<Vote> votes=new ArrayList<Vote>();
		if(id!=null){
			user=userService.getOne(Integer.parseInt(id));
		}
		if(user!=null){
			group=groupService.getGroupBySecret(user.getInstitute());
		}
		if(group!=null && page!=null){
			votes=voteService.findVotesForPage(true,group.getId(), (Integer.parseInt(page)-1)*Constants.ACTION_VOTE_PAGELIMIT, Constants.ACTION_VOTE_PAGELIMIT);
			for (int i = 0; i < votes.size(); i++) {
				Vote vote=votes.get(i);
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("votedName", vote.getVoteTitle());
				map.put("isMulty", vote.getIsMulty());
				map.put("id", vote.getId());
				map.put("isMeVoted", optionStatisticService.isCurrentUserVoted(user.getId(), vote.getId()));
				
				List<Option> options=vote.getOptions();
				List<Object> optionList=new ArrayList<Object>();
				for (int j = 0; j < options.size(); j++) {
					Option option=options.get(j);
					Map<String, Object> optionMap=new HashMap<String, Object>();
					optionMap.put("itemName", option.getItemName());
					optionMap.put("supporterNum", option.getSupporterNum());
					optionMap.put("isSelected", option.getIsSelected());
					optionMap.put("id", option.getId());
					optionList.add(optionMap);
				}
				map.put("item",optionList);
				
				//查找该投票所有用户
				List<User> users=userService.findUsersForVote(vote.getId());
				List<Object> userList=new ArrayList<Object>();
				for (int z = 0; z < users.size(); z++) {
					User u=users.get(z);
					Map<String, Object> userMap=new HashMap<String, Object>();
					userMap.put("id", u.getId());
					userMap.put("userName", u.getNickName());
					userMap.put("avatorPath",Constants.ACTION_PHOTO_URLNOID+u.getId());
					userList.add(userMap);
				}
				map.put("votedUsers",userList);
				dataList.add(map);
			}
		}
		return "success";
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getPage() {
		return page;
	}




	public void setPage(String page) {
		this.page = page;
	}
	
	public String getJson() {
		return json;
	}



	public void setJson(String json) {
		this.json = json;
	}



	//使json有效
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}


	public List<Object> getDataList() {
		return dataList;
	}




	public void setDataList(List<Object> dataList) {
		this.dataList = dataList;
	}






}
