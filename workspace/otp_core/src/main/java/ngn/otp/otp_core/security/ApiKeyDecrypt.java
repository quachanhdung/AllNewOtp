package ngn.otp.otp_core.security;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ApiKeyDecrypt {
	
	public static String decrypt(String base64Encoded, String key, String iv) throws Exception {
        byte[] keyBytes = key.getBytes();
        byte[] ivBytes = iv.getBytes();

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] encryptedBytes = Base64.getDecoder().decode(base64Encoded);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes);
    }
	public static void main(String[] args) {
        String base64EncodedString = "kgw5KynWd9MAGht7waCZsFI0eTumLPxLVUEnRVtEXjypXNkpQ3vHEtPvWK5llC4M"
        		+ "Df4bvl0BsbedKaGFsnOPJOd8kpaTcW7R04FDsZZMOAwlyKwguh0LVEsi3qQnDJM3"
        		+ "BkVeBjH4F2WYga6D3TGqKljEJSl7QyluPpq0+Rp0S+HYx07sp4Wjynvaxb4oRB14";
        String key = "01234567890123456789012345678901";
        String iv = "0123456789012345";

        try {
            String decryptedString = decrypt(base64EncodedString, key, iv);
            System.out.println("Decrypted String: " + decryptedString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
