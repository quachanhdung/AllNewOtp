package ngn.otp.otp_core.services;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import ngn.otp.otp_core.ApplicationContextProvider;
import ngn.otp.otp_core.utils.PropUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
@Service
public class SmsVnptService {
	
	private static final Logger logger = LogManager.getLogger(SmsVnptService.class);
	private PropUtil prop = ApplicationContextProvider.getContext().getBean(PropUtil.class);
	
	public  String sendSMS(String recipent, String content) {
		logger.info("=====SMSUtilVNPT::sendSMS()=====");
		logger.info(recipent);
		String result = "";

		String smsurl = prop.get("sms.url");
		String smsusername = prop.get("sms.username");
		String smspassword = prop.get("sms.password");
		String smsbindmode = prop.get("sms.bindmode");
		String smsbrandname = prop.get("sms.smsbrandname");
		
		try {
			SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String otpcode = "";
			makeRequest(smsurl,smsusername,smspassword,recipent,content,smsbrandname,1);
			logger.info("Gửi mã otp qua sms thành công    "+recipent);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Gửi mã otp qua sms thất bại    "+recipent);
			
		}
		return result;
	}
	
	public static void makeRequest(String url, String username, String password, String phoneNumber, String message, String brandName, int loaitin) {
		logger.info("=====SMSUtilVNPT::makeRequest()=====");
		logger.info("userid: "+username);
		logger.info("userid: "+message);
		
		if(phoneNumber.startsWith("0")==true) {
			phoneNumber=phoneNumber.substring(1);
		}
		
		if(phoneNumber.startsWith("84")==false) {
			phoneNumber="84"+phoneNumber;
		}
		
		logger.info("phone: "+phoneNumber);
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/json");	
				phoneNumber = "\""+phoneNumber+"\"";
				message = "\""+message+"\"";
				String bodyString ="{"
						+ "  \"RQST\": {"
						+ "    \"name\": \"send_sms_list\","
						+ "    \"REQID\": \"123456\","
						+ "    \"LABELID\": \"191102\","
						+ "    \"CONTRACTTYPEID\": \"1\","
						+ "    \"CONTRACTID\": \"15416\","
						+ "    \"TEMPLATEID\": \"1275979\","
						+ "    \"PARAMS\": ["
						+ "      {"
						+ "        \"NUM\": 1,"
						+ "        \"CONTENT\": "+message
						+ "      }"
						+ "    ],"
						+ "    \"SCHEDULETIME\": \"\","
						+ "    \"MOBILELIST\": "+phoneNumber+","
						+ "    \"ISTELCOSUB\": \"0\","
						+ "    \"AGENTID\": \"199\","
						+ "    \"APIUSER\": \"thanhuyhcmws\","
						+ "    \"APIPASS\": \"889426P@ss!@#\","
						+ "    \"USERNAME\": \"VPTUTPHCM\","
						+ "    \"DATACODING\": \"0\","
						+ "    \"SALEORDERID\": \"\","
						+ "    \"PACKAGEID\": \"\""
						+ "  }"
						+ "}";
				logger.info(bodyString);
				RequestBody body = RequestBody.create(mediaType, bodyString);
				Request request = new Request.Builder()
				  .url("http://113.185.0.35:8888/smsbn/api")
				  .method("POST", body)
				  .addHeader("Content-Type", "application/json")
//				  .addHeader("Cookie", "JSESSIONID=9329813137D10D4C648507CFE8D6C330")
				  .build();
				try {
					Response response = client.newCall(request).execute();
					logger.info("Send smd success");
					logger.info(response.body().string());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    }

}
