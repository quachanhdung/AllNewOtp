package ngn.otp.otpadmin.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;


import ngn.otp.otpadmin.commons.ServiceResponseBody;
import ngn.otp.otpadmin.utils.PropUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



@Service
public class AuthenService {
	
	private PropUtil prop ;
	
	
	AuthenService(PropUtil prop){
		this.prop=prop;
	}

	public ServiceResponseBody authen(String username, String password) throws IOException {
		System.out.println("=====AuthenService: authen()=====");
		String url = prop.get("otpCore.url")+"/user/login";
		System.out.println(url);
		
		if(url.isEmpty()) return  new ServiceResponseBody();
		
		Gson gson = new Gson();
		OkHttpClient client = new OkHttpClient().newBuilder()
				.build();
		MediaType mediaType  = MediaType.get("application/json; charset=utf-8");
		
		String apiKey = prop.get("otpCore.x-api-key");

		Map<String, String> body = new HashMap<>();
		body.put("username", username);
		body.put("password", password);
		String jsonBody = gson.toJson(body);

		RequestBody requestBody = RequestBody.create(mediaType,jsonBody);

		Request request = new Request.Builder()
				.url(url)
				.method("POST", requestBody)
				.addHeader("X-API-KEY", apiKey)
				.build();
		Response response = client.newCall(request).execute();
		return gson.fromJson(response.body().string(), ServiceResponseBody.class);
	}
}
