package ngn.otp.otp_core.services;

import java.io.IOException;

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
public class SmsService {
	private final Logger logger = LogManager.getLogger(SmsService.class);
	private final MediaType JSON = MediaType.get("application/json; charset=utf-8");
	private PropUtil prop = ApplicationContextProvider.getContext().getBean(PropUtil.class);

	public  String sendSms(String recipent, String content) {
		logger.info("===SMSUtil::sendSms()===");
		logger.info(recipent+" "+content);
		String smsUrl = prop.get("sms.url");
		String smsUsername = prop.get("sms.username");
		String smsPassword = prop.get("sms.password");
		String smsBindmode = prop.get("sms.bindmode");
		String smsBrandname = prop.get("sms.smsbrandname");

		String result = "";
		String url1 = smsUrl+"login.jsp?"+"userName="+smsUsername+"&password="+smsPassword+"&bindMode="+smsBindmode;	
		logger.info(url1);
		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(JSON, "");
		Request request = new Request.Builder()
				.url(url1)
				.get()
				.addHeader("Connection","close")
				.build();
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response response) throws IOException {
				logger.info(response.toString());
				
				String sid = "";
				sid = response.body().string();
				sid = sid.split(":")[1].split(",")[0];
				sid = sid.substring(1, sid.length()-1);
				smsSending(smsUrl, sid, smsBrandname, recipent, content);
				response.body().close();
				response.close();
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				logger.error(arg1.toString());

			}
		});

		return result;

	}
	
	
	private  String smsSending(String smsUrl, String sid, String sender, String recipent, String content) {
		logger.info("===SMSUtil::smsSending()===");
		
		smsUrl = smsUrl+"send_2.jsp?enCoding=ALPHA_UCS2&sid="+sid+"&sender="+sender+"&recipient="+recipent+"&content="+content;
		logger.info(smsUrl);
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url(smsUrl)
				.get()
				.build();
		String result ="";
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onResponse(Call arg0, Response response) throws IOException {
				logger.info(response.toString());
				response.toString();
				response.body().close();
				response.close();
			}
			
			@Override
			public void onFailure(Call arg0, IOException arg1) {
				logger.error("sending sms to "+recipent+" error: ");
				logger.error(arg1.toString());
			}
		});
		
		return result;
	}
	
}
