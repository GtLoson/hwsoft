package com.hwsoft.util.file;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import com.hwsoft.util.string.StringUtils;

/**
 * FTP工具类
 * @author tzh
 *
 */
public class FTPUtil {

	public static void main(String[] args) {
		try {
        FTPClient client = getFTPClient("123.57.51.29","file","123456");
        System.out.println("client---"+client);
    }catch (Exception e){
        e.printStackTrace();
    }
  }
	
	
	/**
     * 获取ftp链接
     *
     * @return ftpClient
	 * @throws IOException 
     * */
    private static FTPClient getFTPClient(String server, String username, String password) throws IOException{
    	FTPClient ftpClient = null;
        try {
        	ftpClient = new FTPClient();
 
            ftpClient.connect(server);
            ftpClient.login(username, password);
        } catch (SocketException e) {
            throw new SocketException(e.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        return ftpClient;
    }
     
    /**
     *  关闭ftpClient链接
     * 
     *  @param ftpClient 要关闭的ftpClient对象
     * 
     * */
    private static void closeFTPClient(FTPClient ftpClient){
        try {
            try{
                ftpClient.logout();
            }finally{
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	 /**
     * 上传文件
     *
	 * @throws IOException
     * */
    public static boolean putFile( String server, String username, String password , InputStream instream, String fileName, String uploadDir) throws IOException {
//        InputStream instream = null;
        boolean result = false;
        try{
        	FTPClient ftpClient = getFTPClient(server, username, password);
            try{
            	ftpClient.setFileType(FTP.BINARY_FILE_TYPE);  
            	if(!StringUtils.isEmpty(uploadDir)){
            		ftpClient.makeDirectory(uploadDir);
            		ftpClient.changeWorkingDirectory(uploadDir);
            	}
//                instream = new BufferedInputStream(new FileInputStream(f));
                ftpClient.enterLocalPassiveMode();
                result = ftpClient.storeFile(fileName, instream);
            }finally{
                if(instream!=null){
                    instream.close();
                }
                closeFTPClient(ftpClient);
            }
        }catch(IOException e){
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
         
        return result;
    }
    
    /**
     * 删除文件
     * @param filepath filepath是带有上传目录的path, 后台系统上传的文件, 在数据库中已经存有上传目录, 所以就不用再加.
     * @return
     * @throws IOException
     */
    public static boolean deleteFile(String server, String username, String password , String filepath) throws IOException{
    	boolean result = false;
    	FTPClient ftpClient = getFTPClient(server, username, password);
    	try {
    		
			result = ftpClient.deleteFile(filepath);
		} catch (IOException e) {
          e.printStackTrace();
			throw new IOException(e.getMessage());
		} finally{
            closeFTPClient(ftpClient);
        }
    	return result;
    }

}
