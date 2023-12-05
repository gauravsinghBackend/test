//package com.example.LoginPage.OneTimePassword.OTPcontroller;
//
//import com.example.LoginPage.Encryption.TokenManager;
//import com.example.LoginPage.Models.User;
//import com.example.LoginPage.OnBoarding.LastState;
//import com.example.LoginPage.OneTimePassword.DTO.OtpValidation;
//import com.example.LoginPage.OneTimePassword.FailedToLoginException;
//import com.example.LoginPage.OneTimePassword.OTPservice.OtpServiceImpl;
//import com.example.LoginPage.LoginSignUp.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//@CrossOrigin(origins = "*")
//@RestController
//public class VerifyOTP {
//    @Autowired
//    private OtpServiceImpl otpServiceImpl;
//    @Autowired
//    private UserRepository userRepository;
//    public ResponseEntity<GlobalResponse> validateOtp(@RequestBody OtpValidation otpValidation) throws Exception {
////        boolean otpEntityOptional = plivoService.ValidateOtpService(otpValidation.getPhone(),otpValidation.getOtp());
////        boolean otpEntityOptional=true;
//        GlobalResponse.VerifyResponse verifyResponse=new GlobalResponse.VerifyResponse();
//        GlobalResponse globalResponse=new GlobalResponse();
//        TokenManager tokenManager=new TokenManager();
//        try {
//            boolean otpEntityOptional = otpServiceImpl.ValidateOtpService(otpValidation.getPhone(),otpValidation.getOtp());
//            if (otpEntityOptional) {
//                //Check if user Exists or not :
//                User user = userRepository.findByPhone(otpValidation.getPhone());
//                if (user.getLastState().equals(LastState.HAVEKIDS))
//                {
//                    verifyResponse.setAlreadyUser(false);
//                    verifyResponse.setOnboarded(true);
//                    String generatedToken = tokenManager.generateToken(user.getId());
//                    verifyResponse.setToken(generatedToken);
//                }
//                else if (user != null) {
//                    //it will be true if user Exists in database:
//                    // Case 1 : Onboarding Completed
//                    // Case 2: Still OnBoarding
//                    //TODO page will get Opened
//                    //Phone Number Mst Also Exist in Database
//                    verifyResponse.setAlreadyUser(true);
//                    String generatedToken = tokenManager.generateToken(user.getId());
//                    verifyResponse.setToken(generatedToken);
//                } else {
//                    //User Landing First Time  save The user
//                    User saveUser = new User();
//                    saveUser.setPhone(otpValidation.getPhone());
//                    User savedUser = userRepository.save(saveUser);
//                    String generatedToken = tokenManager.generateToken(savedUser.getId());
//                    verifyResponse.setToken(generatedToken);
//                    verifyResponse.setAlreadyUser(false);
//                    //Not required: Just for safety: ByDefault false
//                    //Return A page where he left:
//                }
//                verifyResponse.setValidOtp(true);
//                verifyResponse.setUser(user);
////                globalResponse.setMesaage("otp verified successfully");
//                globalResponse.setMeassage("otp verified successfully");
//                globalResponse.setStatus(VerifyOtpEnum.SUCCESS);
//                globalResponse.setVerifyResponse(verifyResponse);
//                return ResponseEntity.ok(globalResponse);
//            } else {
//                verifyResponse.setValidOtp(false);
//                globalResponse.setMeassage("otp failed to verify");
//                globalResponse.setStatus(VerifyOtpEnum.FAILED);
////                verifyResponse.setMesaage("otp failed to verify");
//                return ResponseEntity.ok(globalResponse);
//            }
//        } catch (FailedToLoginException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(globalResponse);
//        } catch (Exception e) {
//            // Handle other exceptions
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(globalResponse);
//        }
//    }
//}
