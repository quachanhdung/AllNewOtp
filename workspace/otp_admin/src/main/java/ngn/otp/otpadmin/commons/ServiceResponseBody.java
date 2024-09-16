package ngn.otp.otpadmin.commons;

import java.util.Collections;

public class ServiceResponseBody {
	private int status=-1;
	private String message=null;
	private Object data=null;
	private long count=0;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		if(data==null) {
			return Collections.emptyList();
		}
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	
	
	
	
	
}
