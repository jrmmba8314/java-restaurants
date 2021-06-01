package com.lambdaschool.restaurants.controllers;

import com.lambdaschool.restaurants.RestaurantsApplicationTests;
import com.lambdaschool.restaurants.models.Payment;
import com.lambdaschool.restaurants.models.Restaurant;
import com.lambdaschool.restaurants.services.PaymentService;
import com.lambdaschool.restaurants.services.RestaurantService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
  Note that here we are using WithUserDetails as we are using the database and thus have access to users
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = RestaurantsApplicationTests.class)
@AutoConfigureMockMvc
@WithUserDetails(value = "admin")
public class RestaurantControllerIntegrationTest
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    PaymentService paymentService;

    @Before
    public void setUp() throws Exception
    {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        List<Restaurant> myList = restaurantService.findAll();
        for (Restaurant r : myList)
        {
            System.out.println(r.getRestaurantid() + " " + r.getName());
        }

        List<Payment> myPay = paymentService.findAll();
        for (Payment p : myPay)
        {
            System.out.println(p.getPaymentid() + " " + p.getType());
        }

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void whenMeasuredResponseTime()
    {
        given().when()
            .get("/restaurants/restaurants")
            .then()
            .time(lessThan(5000L));
    }

    @Test
    public void listAllRestaurants()
    {
        given().when()
            .get("/restaurants/restaurants")
            .then()
            .statusCode(200)
            .and()
            .body(containsString("Apple"));
    }

    @Test
    public void getRestaurantById()
    {
        long aRestaurant = 12;

        given().when()
            .get("/restaurants/restaurant/" + aRestaurant)
            .then()
            .statusCode(200)
            .and()
            .body(containsString("Test"));
    }

    @Test
    public void getRestaurantByIdNotFound()
    {
        long aRestaurant = 7777;

        given().when()
            .get("/restaurants/restaurant/" + aRestaurant)
            .then()
            .statusCode(404)
            .and()
            .body(containsString("Resource"));
    }

    @Test
    public void getRestaurantByName()
    {
        String aRestaurant = "Test Apple";

        given().when()
            .get("/restaurants/restaurant/name/" + aRestaurant)
            .then()
            .statusCode(200)
            .and()
            .body(containsString("Apple"));
    }

    @Test
    public void getRestaurantByNameNotFound()
    {
        String aRestaurant = "Turtle";

        given().when()
            .get("/restaurants/restaurant/name/" + aRestaurant)
            .then()
            .statusCode(404)
            .and()
            .body(containsString("Resource"));
    }

    @Test
    public void listRestaurantNameCity()
    {
        given().when()
            .get("/restaurants/restaurants/name/apple/city/city")
            .then()
            .statusCode(200)
            .and()
            .body(containsString("Apple"));
    }

    @Test
    public void listRestaurantNameLike()
    {
        given().when()
            .get("/restaurants/restaurants/namelike/test")
            .then()
            .statusCode(200)
            .and()
            .body(containsString("Test"));
    }

    @Test
    public void addNewRestaurant() throws Exception
    {
        String jsonInput = "{\n" +
            "    \"name\": \"Good Eats\",\n" +
            "    \"address\": \"123 Main Avenue\",\n" +
            "    \"city\": \"Uptown\",\n" +
            "    \"state\": \"ST\",\n" +
            "    \"telephone\": \"555-777-7777\",\n" +
            "    \"menus\": [\n" +
            "        {\n" +
            "            \"dish\": \"Soda\",\n" +
            "            \"price\": 3.50\n" +
            "        },\n" +
            "        {\n" +
            "            \"dish\": \"Latte\",\n" +
            "            \"price\": 5.00\n" +
            "        }\n" +
            "    ],\n" +
            "    \"payments\": [\n" +
            "        {\n" +
            "            \"payment\" : {\n" +
            "                \"paymentid\":9\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"payment\" : {\n" +
            "                \"paymentid\":10\n" +
            "            }\n" +
            "        }\n" +
            "    ]\n" +
            "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/restaurants/restaurant")
            .content(jsonInput)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(MockMvcResultMatchers.header()
                .exists("location"));
    }

    @Test
    public void updateRestaurant() throws Exception
    {
        String jsonInput = "{\n" +
            "    \"name\": \"Stumps Eatery\",\n" +
            "    \"address\": \"123 Main Avenue\",\n" +
            "    \"city\": \"Uptown\",\n" +
            "    \"state\": \"ST\",\n" +
            "    \"telephone\": \"555-777-7777\",\n" +
            "    \"menus\": [\n" +
            "        {\n" +
            "            \"dish\": \"Soda\",\n" +
            "            \"price\": 3.50\n" +
            "        },\n" +
            "        {\n" +
            "            \"dish\": \"Latte\",\n" +
            "            \"price\": 5.00\n" +
            "        }\n" +
            "    ],\n" +
            "    \"payments\": [\n" +
            "        {\n" +
            "            \"payment\" : {\n" +
            "                \"paymentid\":2\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"payment\" : {\n" +
            "                \"paymentid\":3\n" +
            "            }\n" +
            "        }\n" +
            "    ]\n" +
            "}";

        mockMvc.perform(MockMvcRequestBuilders.put("/restaurants/restaurant/{userid}",
            13)
            .content(jsonInput)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void deleteRestaurantById()
    {
        long aRestaurant = 10;
        given().when()
            .delete("/restaurants/restaurant/" + aRestaurant)
            .then()
            .statusCode(200);
    }

    @Test
    public void deleteRestaurantByIdNotFound()
    {
        long aRestaurant = 7777;
        given().when()
            .delete("/restaurants/restaurant/" + aRestaurant)
            .then()
            .statusCode(404);
    }

}