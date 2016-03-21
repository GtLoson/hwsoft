package com.hwsoft.util.string;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;


/**
 * 自定义字符串工具
 */
public class StringUtils {
	
	public static final String SPLIT_COMMA = ",";
	
    public static boolean isEmpty(String string) {
        return org.apache.commons.lang.StringUtils.isEmpty(string);
    }
    
    public static String replaceBlank(String str) {
        String dest = "";
        if(!isEmpty(str)) {
//            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//            Matcher m = p.matcher(str);
//            dest = m.replaceAll("");
        	str = str.replaceAll("\\r", "").replaceAll("\\n", "").replaceAll("\\t", "").replaceAll("\"", "'");
        	return str;
        }
        return dest;
    }
    
    public static String idCardDeal(String idCard){
    	if(isEmpty(idCard)){
    		return null;
    	}
    	String start = idCard.substring(0, 4);
    	String end = idCard.substring(idCard.length()-4);
    	return start+"********"+end;
    }
    
    public static String bankCardDeal(String bankCard){
    	if(isEmpty(bankCard)){
    		return null;
    	}
    	String end = bankCard.substring(bankCard.length()-4);
    	return end;
    }
    
    
    public static List<String> split(String str,String pattern) {
    	
    	if(isEmpty(str)){
    		return null;
    	}
    	
    	String[] strArray = str.split(pattern);
    	
    	if(str.isEmpty()){
    		return null;
    	}
    	List<String> result = new ArrayList<String>();
    	for(String string : strArray){
    		result.add(string);
    	}
    	return result;
    }
    
    
    public static void main(String[] args) {  
        // TODO Auto-generated method stub     
//        String content=" <div style=\"LINE-HEIGHT: 30px\">指出：“<table><tr><td>信息\r\n化是</td></tr><tr><td>公安机关的一场警务革命</td></tr></table>，对于这场革命，谁认识早，谁抓得好，谁就赢得主动，占领制高点”。省常委委、省委政法委书籍、省公安厅厅长孟苏铁通知多次强调：“加强信息化建设，是新形势下提升社会管理效能的必由之路，是实现公安工作跨越式发展的有力支撑”；“公安信息化是发展方向，更是前进动力；是工作载体，更是创新平台”；“要紧紧抓住公安信息化建设这个支撑点，在深化应用中全面增强公安机关的核心战斗力”。<p><p><span style=\"font-size:18px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; “工欲善其事，必先利其器。”在当前日益动态的社会治安形势下，我市公安机关要彻底扭转“打不胜打、防不胜防”的被动局面，实现警力不增、效能大增的目标，就必须积极主动适应信息化的发展趋势，加快信息化手段、战法的总结、提炼、推广和应用，坚持向信息化要警力、向科技手段要战斗力，通过信息化行成的强大后台，将广大侦查民警变成以一</span></p></div>";     
////       String txtcontent = content.replaceAll("</?[^>]+>", ""); //剔出<html>的标签  
//        String txtcontent = content.replaceAll("\\r", "").replaceAll("\\n", "").replaceAll("\\t", "").replaceAll("\"", "'");//去除字符串中的空格,回车,换行符,制表符  
//        System.out.println(txtcontent);
//        content = content.replaceAll("\\s*|\t|\r|\n", "");//去除字符串中的空格,回车,换行符,制表符  
//        System.out.println(content);     
         //指出：“信息化是公安机关的一场警务革命，对于这场革命，谁认识早，谁抓得好，谁就赢得主动，占领制高点”。省常委委、省委政法委书籍、省公安厅厅长孟苏铁通知多次强调：“加强信息化建设，是新形势下提升社会管理效能的必由之路，是实现公安工作跨越式发展的有力支撑”；“公安信息化是发展方向，更是前进动力；是工作载体，更是创新平台”；“要紧紧抓住公安信息化建设这个支撑点，在深化应用中全面增强公安机关的核心战斗力”。&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“工欲善其事，必先利其器。”在当前日益动态的社会治安形势下，我市公安机关要彻底扭转“打不胜打、防不胜防”的被动局面，实现警力不增、效能大增的目标，就必须积极主动适应信息化的发展趋势，加快信息化手段、战法的总结、提炼、推广和应用，坚持向信息化要警力、向科技手段要战斗力，通过信息化行成的强大后台，将广大侦查民警变成以一
    	
    	List<String> urls = new ArrayList<String>();
    	urls.add("dddddd");
    	urls.add("sssss");
    	urls.add("aaaaa");
    	System.out.println(new Gson().toJson(urls));
    	
    	String dir = "/a/a/";
    	String uploadDir = dir+(StringUtils.isEmpty(dir) ? "" : (dir.endsWith("/") ? "" : "/"));
    	System.out.println(uploadDir);
   }  
 
}
