package com.pllis.mylog.service;

import okhttp3.*;
import org.springframework.stereotype.Service;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.UUID;

@Service
public class NaverSmsService {

    private final String accessKey = "YOUR_ACCESS_KEY";        // 콘솔에서 발급
    private final String secretKey = "YOUR_SECRET_KEY";        // 콘솔에서 발급
    private final String serviceId = "YOUR_SERVICE_ID";        // 콘솔 > SENS > SMS
    private final String senderPhone = "01012345678";          // 사전 등록된 발신 번호

    public void sendSms(String toPhoneNumber, String content) throws Exception {
        String url = "https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages";
        String timestamp = Long.toString(System.currentTimeMillis());
        String method = "POST";

        String message = new StringBuilder()
                .append(method).append(" ")
                .append("/sms/v2/services/").append(serviceId).append("/messages\n")
                .append(timestamp).append("\n")
                .append(accessKey)
                .toString();

        String signature = makeSignature(message);

        OkHttpClient client = new OkHttpClient();

        String bodyJson = "{"
                + "\"type\":\"SMS\","
                + "\"from\":\"" + senderPhone + "\","
                + "\"content\":\"" + content + "\","
                + "\"messages\":[{\"to\":\"" + toPhoneNumber + "\"}]"
                + "}";

        RequestBody body = RequestBody.create(bodyJson, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("x-ncp-apigw-timestamp", timestamp)
                .addHeader("x-ncp-iam-access-key", accessKey)
                .addHeader("x-ncp-apigw-signature-v2", signature)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println("Response: " + response.body().string());
    }

    private String makeSignature(String message) throws Exception {
        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(rawHmac);
    }
}
