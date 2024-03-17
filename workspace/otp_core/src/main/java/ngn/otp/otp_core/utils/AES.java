package ngn.otp.otp_core.utils;
import java.security.MessageDigest;
import java.util.Base64;
 
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
 
public class AES {
 
    private  SecretKeySpec secretKey;
  
 
    private void setKey(String myKey) 
    {
        try {
        	secretKey = new SecretKeySpec(myKey.getBytes(),"AES");        	
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        
    }
 
    public String encrypt(String strToEncrypt, String secret) 
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
           
        } 
        catch (Exception e) 
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
 
    public String decrypt(String strToDecrypt, String secret) 
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));

        } 
        catch (Exception e) 
        {
            System.out.println("Error while decrypting: " + e.toString());
            return strToDecrypt;
        }
       
    }
    
    public static void main(String args[]) {
    	AES t = new AES();
    	System.out.println("hello");
    	String text = "quachanhdung";
    	String salt = "FB53653255A14D95B832B324C94CD1F5";
    	String encrypt = t.encrypt(text, salt);
    	String decrypt = t.decrypt(encrypt, salt);
    	System.out.println(encrypt);
    	System.out.println(decrypt);

    }
}