package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.util.Assert;

import com.example.demo.entity.RegistrationDetails;
import com.example.demo.service.RegistrationDetailsService;
import com.example.demo.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationDetailsControllerTest {
	
	
	@InjectMocks
    private RegistrationDetailsController myController;

    @Mock
    private RegistrationDetailsService registrationDetailsService;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Test
    public void testSaveDetail() {
        // Create a mock RegistrationDetails object
        RegistrationDetails detail = new RegistrationDetails();
        detail.setEmail("rajasekhar5486@gmail.com");
        detail.setOtp(1234); // assuming OTP is set here for the test

        // Mock userService behavior (assuming checkEmailExistance is called indirectly)
        Mockito.when(registrationDetailsService.saveRegistrationDetails(detail)).thenReturn("User registered successfully");

        // Mock registrationDetailsService behavior
        List<RegistrationDetails> saved = new ArrayList<>();
        saved.add(detail); // add the detail to the list for testing
        Mockito.when(registrationDetailsService.getRegistrationDetailsByEmail(detail.getEmail())).thenReturn(saved);

        // Call the controller method
        String viewName = myController.saveDetail(detail, model);

        // Verify that the correct view name is returned
        assertEquals("registrationStatus", viewName);

        // Verify interactions
        Mockito.verify(registrationDetailsService).saveRegistrationDetails(detail);
        Mockito.verify(registrationDetailsService).getRegistrationDetailsByEmail(detail.getEmail());
        // Verify that the model attribute is added with the correct value
        Mockito.verify(model, Mockito.never()).addAttribute(Mockito.eq("otpMismatch"), Mockito.anyString());
    }
    @Test
    public void testGetDetails() {
        // Create a mock RegistrationDetails list
        List<RegistrationDetails> userDetailsList = new ArrayList<>();
        // Add some dummy data to the list
        RegistrationDetails user1 = new RegistrationDetails();
        user1.setEmail("user1@example.com");
        user1.setFirstname("John");
        user1.setLastname("Doe");
        // Add more users as needed

        userDetailsList.add(user1);
        // Add more users as needed

        // Mock registrationDetailsService behavior
        when(registrationDetailsService.getRegistrationDetails()).thenReturn(userDetailsList);

        // Call the controller method
        String viewName = myController.getDetails(model);
        System.out.println(viewName);

        // Verify that the correct view name is returned
        assertEquals("details", viewName);

        // Verify interactions
        Mockito.verify(registrationDetailsService).getRegistrationDetails();
        // Verify that the model attribute is added with the correct value
        Mockito.verify(model).addAttribute("users", userDetailsList);
    }

    @Test
    public void testFindDetailsByEmail() {
        // Create a mock RegistrationDetails list
        List<RegistrationDetails> userDetailsList = new ArrayList<>();
        // Add some dummy data to the list
        RegistrationDetails user1 = new RegistrationDetails();
        user1.setEmail("user1@example.com");
        user1.setFirstname("John");
        user1.setLastname("Doe");
    
        // Add more users as needed

        userDetailsList.add(user1);
        // Add more users as needed

        // Mock registrationDetailsService behavior
        when(registrationDetailsService.getRegistrationDetailsByEmail("user1@example.com")).thenReturn(userDetailsList);

        // Call the controller method
        List<RegistrationDetails> result = myController.findDetailsByEmail("user1@example.com");

        // Verify interactions
        Mockito.verify(registrationDetailsService).getRegistrationDetailsByEmail("user1@example.com");
        // Verify that the correct user list is returned
        assertEquals(userDetailsList, result);
    }

}







//private MockMvc mockMvc;
//
//@MockBean
//private RegistrationDetailsService userService;
//
//private RegistrationDetails user;
//
//@BeforeEach
//public void setup() {
//	RegistrationDetails user= new RegistrationDetails();
////	user.setId(1);
//	user.setFirstname("Test User");
//	user.setEmail("test@example.com");
//	user.setPassword("password");
//	user.setBloodGroup("A+");
//
//	user.setLastname("B");
//    }
//
//@Test
//public void registerUserTest() throws Exception {
//
//	when(userService.saveRegistrationDetails(any(RegistrationDetails.class))).thenReturn("User registered successfully");
//	
//	mockMvc.perform(post("/registerUser")
//			.contentType(MediaType.APPLICATION_JSON)
//
//			.content("{\"name\":\"Test User\",\"email\":\"test@example.com\",\"password\":\"password\"}"))
//
//	.andExpect(status().isOk())
//	.andExpect(content().string("User registered successfully"));
//    }

