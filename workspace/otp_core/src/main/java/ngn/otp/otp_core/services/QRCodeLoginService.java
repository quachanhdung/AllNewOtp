package ngn.otp.otp_core.services;

import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ngn.otp.otp_core.models.QRCodeLoginModel;

@Service
public class QRCodeLoginService {

	private  Logger logger = LoggerFactory.getLogger(QRCodeLoginService.class);
	private  HashSet<QRCodeLoginModel> hashSet = new HashSet<>();
	
	public  QRCodeLoginService() {
		logger.info("QRCodeLoginService start ...");
	}

	public  HashSet<QRCodeLoginModel> getListLog(){
		return hashSet;
	}
	
	public boolean insert(QRCodeLoginModel model) {
		logger.info("insert with model");
		if(model.getKey()==null || model.getKey().isEmpty() ) {
			return false;
		}
		hashSet.add(model);
		return true;
		
	}

	public void insert(String key, String userId) {
		logger.info("insert");
		if(key==null || key.isEmpty() || userId==null || userId.isEmpty()) {
			return;
		}
		QRCodeLoginModel model = new QRCodeLoginModel(key, userId);
		hashSet.add(model);
	}

	public boolean insert(String key) {
		logger.info("insert");
		if(key==null || key.isEmpty() ) {
			return false;
		}
		
		QRCodeLoginModel model = new QRCodeLoginModel(key);
		if(hashSet.contains(model)) {
			return false;
		}
		hashSet.add(model);
		return true;
	}
	

	public  QRCodeLoginModel get(String key) {
		logger.info("get");
		logger.info(String.valueOf(hashSet.size()));
		
		
		for(QRCodeLoginModel m : hashSet) {
			if(m.getKey().equals(key)) {
				return m;
			}
		}
		
		return null;
	}

	public  void delete(String key) {
		hashSet.remove(new QRCodeLoginModel(key));
		
	}

	public  QRCodeLoginModel update(QRCodeLoginModel model) {
		logger.info("update");
		
		if(model==null)return null;
		if(model.getKey()==null || model.getKey().isEmpty())return null;
		
		if(hashSet.contains(model)==false) return null;
		
		hashSet.remove(model);
		hashSet.add(model);
		
		logger.info("update done");
		return model;
	}


}
