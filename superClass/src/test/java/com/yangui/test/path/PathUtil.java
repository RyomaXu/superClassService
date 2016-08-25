package com.yangui.test.path;


import javax.servlet.http.HttpServletRequest;

/**
 * @描述 ： 获取各种相对路径的工具类
 * @作者 ：小M
 * @博客 ： http://www.cnblogs.com/xiaoMzjm/
 * @时间 ： 2014/07/30
 */
public class PathUtil{

     /**
     * 获取服务的url基本地址
     * @param request    请求
     * @return           例如：http://localhost:8080/test/ ， 其中test为项目名
     */
    public static String getServerPath(HttpServletRequest request){
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
          
        return basePath;
    }
    /**
     * 获取带目录的url地址
     * @param request    请求
     * @param folderName 文件夹名 ，例如：DownLoadFile
     * @return           例如：http://localhost:8080/test/DownLoadFile
     */
    public static String getServerPath(HttpServletRequest request,String folderName){
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
        return basePath+folderName;
    }
    /**
     * 获取服务器的根路径
     * @param request    请求
     * @return           例如：/test ， 其中test为项目名
     */
    public static String getServerContextPath(HttpServletRequest request){
        String path = request.getContextPath();
        return path;
    }
      
    
    /**
     * 获取SRC目录下的文件的路径，带文件夹
     * @param folderName    
     * @return                例如：/F:/myEclipse2013WokeSpace/TestByServlet/WebRoot/WEB-INF/classes/test.txt
     */
    public String getSRCPath(String folderName){
        String path = this.getClass().getClassLoader().getResource(folderName).getPath();
        return path;
    }
}