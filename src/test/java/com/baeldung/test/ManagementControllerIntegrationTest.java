package com.baeldung.test;

import com.baeldung.Application;
import com.baeldung.persistence.dao.UserRepository;
import com.baeldung.persistence.model.User;
import com.baeldung.spring.TestDbConfig;
import com.baeldung.spring.TestIntegrationConfig;
import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { Application.class, TestDbConfig.class, TestIntegrationConfig.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ManagementControllerIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${local.server.port}")
    int port;

    private FormAuthConfig formConfig;
    private String MANAGEMENT_URL;

    //

    @BeforeEach
    public void init() {
        User user = userRepository.findByEmail("test@test.com");
        if (user == null) {
            user = new User();
            user.setFirstName("Test");
            user.setLastName("Test");
            user.setPassword(passwordEncoder.encode("test"));
            user.setEmail("test@test.com");
            user.setEnabled(true);
            userRepository.save(user);
        } else {
            user.setPassword(passwordEncoder.encode("test"));
            userRepository.save(user);
        }

        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
        MANAGEMENT_URL = "/management";

        formConfig = new FormAuthConfig("/login", "username", "password");
    }

    @Test
    public void testManagementPage_whenUserHasManagerRole_thenStatusCodeOK() {
        final RequestSpecification request = RestAssured.given().auth().form("manager@test.com", "test", formConfig).redirects().follow(false);

        request.when().get(MANAGEMENT_URL).then().statusCode(200).body(containsString("Management"));
    }


    @Test
    public void testManagementPage_whenUserDoeNotHaveManagerRole_thenStatusCodeOK() {
        final RequestSpecification request = RestAssured.given().auth().form("test@test.com", "test", formConfig).redirects().follow(false);

        request.when().get(MANAGEMENT_URL).then().statusCode(302).body(is(emptyOrNullString()) );
    }

}
