//package com.example.LoginPage.OneTimePassword.OTPservice;
//
//import com.example.LoginPage.OneTimePassword.OTPcontroller.OtpService;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//
//public class Fast2SMSSender implements OtpService {
//    private String apiKey = "pPMgkBs48Am2WE6UZFadfnzeiuOKbSVTNlj1XHcQ97tYhCoL502PHXRZFCeIrfTj7qlSDGiucUh5a6mQ";
//    private String SenderId="FastSms";
//    private String message = "Hello from Fast2SMS!";
//    private String numbers = "9876543210";  // Replace with the recipient's phone number
//
//    public String sendSms(String to) {
//        try {
//            String url = "https://www.fast2sms.com/dev/bulk";
//            URL obj = new URL(url);
//            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//            con.setRequestMethod("POST");
//            con.setRequestProperty("Authorization", apiKey);
//            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            con.setRequestProperty("Cache-Control", "no-cache");
//            con.setDoOutput(true);
//
//            String data = "sender_id=FSTSMS&message=" + message + "&language=english&route=p&numbers=" + numbers;
//
//            try (OutputStream out = con.getOutputStream()) {
//                byte[] postData = data.getBytes(StandardCharsets.UTF_8);
//                out.write(postData);
//            }
//
//            int responseCode = con.getResponseCode();
//            System.out.println("Response Code: " + responseCode);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
//
