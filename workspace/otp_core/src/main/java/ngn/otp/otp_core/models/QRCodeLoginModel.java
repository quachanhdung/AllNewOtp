package ngn.otp.otp_core.models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class QRCodeLoginModel implements Serializable{

	private static final long serialVersionUID = 1L;

	private String key;
	private String userId;
	private String info;

	public QRCodeLoginModel() {

	}

	public QRCodeLoginModel(String key) {
		this.key = key;
		this.userId="";

	}
	
	public QRCodeLoginModel(String key, String userId) {
		this.key = key;
		this.userId = userId;
		
	}
	
	public QRCodeLoginModel(String key, String userId, String info) {
		super();
		this.key = key;
		this.userId = userId;
		this.info = info;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QRCodeLoginModel other = (QRCodeLoginModel) obj;
		return Objects.equals(key, other.key);
	}

	@Override
	public int hashCode() {
		return Objects.hash(key);
	}
	
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public static void main(String args[]) {
//		HashSet<QRCodeLoginModel> h = new HashSet<>();
//
//		QRCodeLoginModel m1 = new QRCodeLoginModel("abc");
//		QRCodeLoginModel m2 = new QRCodeLoginModel("abc","aaa");
//		
//		h.add(m1);
//
//		
//		h.remove(m1);
//		h.add(m2);
//		System.out.println("contains: "+h.contains(m1));
//
//
//
//		for(QRCodeLoginModel m : h) {
//			System.out.println(m.key+" - "+m.userId);
//		}


		
	}

}
