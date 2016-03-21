/**
 * 
 */
package com.hwsoft.hessian.common;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tzh
 *
 */
public class ResultVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8805404876293155103L;

	private boolean success;
	
	private String message;
	
	private Map<String, Object> data;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		if(null == data){
			data = new HashMap<String, Object>();
		}
		this.data.putAll(data);
	}
	
	public void addData(String key,Object value){
		if(null == data){
			data = new HashMap<String, Object>();
		}
		data.put(key, value);
	}

    @Override
    public String toString() {
        return "ResultVo{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
