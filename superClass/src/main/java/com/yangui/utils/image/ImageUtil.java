package com.yangui.utils.image;

import org.aspectj.bridge.MessageUtil;

public class ImageUtil {
	/* 根据尺寸缩放图片
     * 
     * @author tanjun
     * @date 2013年9月6日  
     * @param path 
     *           源图路径
     * @param width
     *           压缩后宽度
     * @param height
     *          压缩后高度
     * @param type 
     *          1为像素，2为百分比处理，如（像素大小：1024x1024,百分比：50%x50%）
     * @return
     * @throws Exception
     */
//    public static String createThumbnail(String path,int width, int height,String type) throws Exception {
//         
//        IMOperation op = new IMOperation();
//        ConvertCmd cmd = new ConvertCmd(true);
//        String newFileName=null;
//        //文件名前缀
//        String prevFileName=null;
//        try {
//            op.addImage();
//            String raw = "";
//            if("1".equals(type)){
//               //按像素
//               raw = width+"x"+height+"!";
//               prevFileName=width + "x" + height + "_";
//            }else{
//               //按百分比
//               raw = width+"%x"+height+"%";
//               prevFileName=width + "%x" + height + "%_";
//            } 
//            //压缩
//            op.addRawArgs("-thumbnail", raw);
//            //图片质量
//            op.addRawArgs("-quality", "100");
//            op.addImage();
//            //系统类型
//            String osName = System.getProperty("os.name").toLowerCase();
//            if (osName.indexOf("win") != -1) {
//                // linux下不要设置此值，不然会报错
//                cmd.setSearchPath(DspConfigHolder.getProperty("imageMagickPath"));
//            }
//            //读取配置文件：工程路径
//            String filePath = DspConfigHolder.getProjectAbsPath() + path;
//            String fpath[]=filePath.split("/");
//            //原图名称
//            String oldFileName=fpath[fpath.length-1];
//            //压缩图名称
//            String fileName=IdCreater.newId()+getImgSuffix(oldFileName);;
//            //压缩后的新文件名
//            newFileName= prevFileName+ fileName;
//            //新文件路径
//            String newfile=filePath.replace(oldFileName, newFileName);
//            //压缩
//            cmd.run(op, filePath, newfile);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Exception(MessageUtil.getMessage("thumbnailimage.error"));
//        }
//        return DspConfigHolder.getImgUploadPath()+newFileName;
//    }
}
