package com.example.LoginPage.LoginSignUp;

import static org.mockito.ArgumentMatchers.eq;

import com.example.LoginPage.Encryption.TokenManager;
import com.example.LoginPage.LoginSignUp.DTO.UserDto;
import com.example.LoginPage.OneTimePassword.DTO.OtpResponse;
import com.example.LoginPage.OneTimePassword.DTO.OtpStatus;
import com.example.LoginPage.OneTimePassword.OTPcontroller.OtpController;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.*;

@ContextConfiguration(classes = {LoginController.class})
@ExtendWith(SpringExtension.class)
@WebMvcTest(LoginController.class)
class LoginControllerDiffblueTest {
    @Autowired
    private LoginController loginController;

    @MockBean
    private OtpController plivoController;

    @MockBean
    private TokenManager tokenManager;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private LoginService loginService;
    @Autowired
    private MockMvc mockMvc;

//    @MockBean
//    private UserServiceImpl userService;

    /**
     * Method under test: {@link LoginController#getUser()}
     */

    @Test
    public void testLoginWithPhone() throws Exception {
        // Create a UserDto for the test
        UserDto userDto = new UserDto();
        userDto.setPhone("1234567890");

        // Mock the behavior of your service and controller methods
//        Mockito.when(userServiceImpl.authenticateUser(Mockito.anyString())).thenReturn(new User());
//        SmsRequest smsRequest=new SmsRequest();
//        smsRequest.setPhone(userDto.getPhone());
//        // Mock the response when sendSms is called
//        //It when testing if anyone calls plivocontroller.sendsms
//        //it should return ok with otp send successfully
//        Mockito.when(plivoController.sendSms(eq(smsRequest)))
//                .thenReturn(ResponseEntity.ok("otp send successfully"));

        // Perform the mock MVC request
//        mockMvc.perform(MockMvcRequestBuilders.post("/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(userDto)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("otp send successfully"));
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto))
//                        .header(HttpHeaders.AUTHORIZATION, "")  // Set an empty authorization header
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("otp send successfully")).andReturn();

        // Extract the content of the response
        String resultContent = mvcResult.getResponse().getContentAsString();

        // Create an expected Response object
        OtpResponse expectedResponse = new OtpResponse();
        expectedResponse.setStatus(OtpStatus.SUCCESS);
        expectedResponse.setMessage("otp send successfully");

        // Convert the expected Response object to a JSON string
        String expectedResponseJson = new ObjectMapper().writeValueAsString(expectedResponse);
        assertThat(resultContent).as(" ").isEqualTo(expectedResponseJson);
        // Assert that the actual response content matches the expected response
    }

}
