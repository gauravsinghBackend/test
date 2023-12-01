package com.example.LoginPage.LoginSignUp;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.LoginPage.Encryption.TokenData;
import com.example.LoginPage.Encryption.TokenManager;
import com.example.LoginPage.LoginSignUp.DTO.SignupDto;
import com.example.LoginPage.LoginSignUp.DTO.UserDto;
import com.example.LoginPage.Models.ParentRole;
import com.example.LoginPage.Models.User;
import com.example.LoginPage.OnBoarding.LastState;
import com.example.LoginPage.OnBoarding.PregnantChildEnum;
import com.example.LoginPage.OneTimePassword.OTPcontroller.PlivoController;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {LoginController.class})
@ExtendWith(SpringExtension.class)
class LoginControllerDiffblueTest {
    @Autowired
    private LoginController loginController;

    @MockBean
    private PlivoController plivoController;

    @MockBean
    private TokenManager tokenManager;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserServiceImpl userServiceImpl;

    /**
     * Method under test: {@link LoginController#getUser()}
     */
    @Test
    void testGetUser() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user");
        MockMvcBuilders.standaloneSetup(loginController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("UserExists"));
    }

    /**
     * Method under test: {@link LoginController#getUser()}
     */
    @Test
    void testGetUser2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user", "Uri Variables");
        MockMvcBuilders.standaloneSetup(loginController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("UserExists"));
    }

    /**
     * Method under test: {@link LoginController#login(UserDto)}
     */
    @Test
    void testLogin() throws Exception {
        User user = new User();
        user.setDate("2020-03-01");
        user.setDueDate("2020-03-01");
        user.setEmail("jane.doe@example.org");
        user.setFirstPregnancy(true);
        user.setHaveKids(true);
        user.setId(1L);
        user.setLastState(LastState.SIGNUP);
        user.setName("Name");
        user.setParentrole(ParentRole.FATHER);
        user.setPhone("6625550144");
        user.setPregnantorchild(PregnantChildEnum.AREPREGNANT);
        user.setRoles(new ArrayList<>());
        when(userServiceImpl.authenticateUser(Mockito.<String>any())).thenReturn(user);
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/login");
        postResult.characterEncoding("https://example.org/example");

        UserDto userDto = new UserDto();
        userDto.setPhone("6625550144");
        String content = (new ObjectMapper()).writeValueAsString(userDto);
        MockHttpServletRequestBuilder requestBuilder = postResult.contentType(MediaType.APPLICATION_JSON).content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(loginController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }

    /**
     * Method under test: {@link LoginController#registration(String, SignupDto)}
     */
    @Test
    void testRegistration() throws Exception {
        TokenData tokenData = new TokenData();
        tokenData.setUserId(1L);
        when(tokenManager.decryptToken(Mockito.<String>any())).thenReturn(tokenData);

        User user = new User();
        user.setDate("2020-03-01");
        user.setDueDate("2020-03-01");
        user.setEmail("jane.doe@example.org");
        user.setFirstPregnancy(true);
        user.setHaveKids(true);
        user.setId(1L);
        user.setLastState(LastState.SIGNUP);
        user.setName("Name");
        user.setParentrole(ParentRole.FATHER);
        user.setPhone("6625550144");
        user.setPregnantorchild(PregnantChildEnum.AREPREGNANT);
        user.setRoles(new ArrayList<>());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        doNothing().when(userServiceImpl).saveUser(Mockito.<Optional<User>>any(), Mockito.<SignupDto>any());

        SignupDto signupDto = new SignupDto();
        signupDto.setDate("2020-03-01");
        signupDto.setEmail("jane.doe@example.org");
        signupDto.setName("Name");
        signupDto.setParentrole("Parentrole");
        String content = (new ObjectMapper()).writeValueAsString(signupDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/signup")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(loginController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "<SignUpResponseDto><meassage>User signed in successfully</meassage><signUpUpdate>SUCCESS</signUpUpdate"
                                        + "></SignUpResponseDto>"));
    }

    /**
     * Method under test: {@link LoginController#registration(String, SignupDto)}
     */
    @Test
    void testRegistration2() throws Exception {
        TokenData tokenData = new TokenData();
        tokenData.setUserId(1L);
        when(tokenManager.decryptToken(Mockito.<String>any())).thenReturn(tokenData);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(null);
        doNothing().when(userServiceImpl).saveUser(Mockito.<Optional<User>>any(), Mockito.<SignupDto>any());

        SignupDto signupDto = new SignupDto();
        signupDto.setDate("2020-03-01");
        signupDto.setEmail("jane.doe@example.org");
        signupDto.setName("Name");
        signupDto.setParentrole("Parentrole");
        String content = (new ObjectMapper()).writeValueAsString(signupDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/signup")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(loginController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "<SignUpResponseDto><meassage>user registration failed</meassage><signUpUpdate>FAILED</signUpUpdate><"
                                        + "/SignUpResponseDto>"));
    }

    /**
     * Method under test: {@link LoginController#registration(String, SignupDto)}
     */
    @Test
    void testRegistration3() throws Exception {
        TokenData tokenData = new TokenData();
        tokenData.setUserId(1L);
        when(tokenManager.decryptToken(Mockito.<String>any())).thenReturn(tokenData);
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        doNothing().when(userServiceImpl).saveUser(Mockito.<Optional<User>>any(), Mockito.<SignupDto>any());

        SignupDto signupDto = new SignupDto();
        signupDto.setDate("2020-03-01");
        signupDto.setEmail("jane.doe@example.org");
        signupDto.setName("Name");
        signupDto.setParentrole("Parentrole");
        String content = (new ObjectMapper()).writeValueAsString(signupDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/signup")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(loginController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "<SignUpResponseDto><meassage>User is Invalid or Null</meassage><signUpUpdate/></SignUpResponseDto>"));
    }

    /**
     * Method under test: {@link LoginController#registration(String, SignupDto)}
     */
    @Test
    void testRegistration4() throws Exception {
        TokenData tokenData = new TokenData();
        tokenData.setUserId(1L);
        when(tokenManager.decryptToken(Mockito.<String>any())).thenReturn(tokenData);

        User user = new User();
        user.setDate("2020-03-01");
        user.setDueDate("2020-03-01");
        user.setEmail("jane.doe@example.org");
        user.setFirstPregnancy(true);
        user.setHaveKids(true);
        user.setId(1L);
        user.setLastState(LastState.SIGNUP);
        user.setName("Name");
        user.setParentrole(ParentRole.FATHER);
        user.setPhone("6625550144");
        user.setPregnantorchild(PregnantChildEnum.AREPREGNANT);
        user.setRoles(new ArrayList<>());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        doNothing().when(userServiceImpl).saveUser(Mockito.<Optional<User>>any(), Mockito.<SignupDto>any());

        SignupDto signupDto = new SignupDto();
        signupDto.setDate("2020-03-01");
        signupDto.setEmail("jane.doe@example.org");
        signupDto.setName("Name");
        signupDto.setParentrole("Parentrole");
        String content = (new ObjectMapper()).writeValueAsString(signupDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/signup")
                .header("Authorization", 42)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(loginController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "<SignUpResponseDto><meassage>user registration failed</meassage><signUpUpdate>FAILED</signUpUpdate><"
                                        + "/SignUpResponseDto>"));
    }

    /**
     * Method under test: {@link LoginController#testController()}
     */
    @Test
    void testTestController() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/test-controller");
        MockMvcBuilders.standaloneSetup(loginController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Test-Controller reached"));
    }

    /**
     * Method under test: {@link LoginController#testController()}
     */
    @Test
    void testTestController2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/test-controller");
        requestBuilder.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(loginController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Test-Controller reached"));
    }
}
