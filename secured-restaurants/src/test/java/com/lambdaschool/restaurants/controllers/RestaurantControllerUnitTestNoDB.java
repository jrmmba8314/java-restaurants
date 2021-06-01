package com.lambdaschool.restaurants.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.restaurants.RestaurantsApplicationTests;
import com.lambdaschool.restaurants.models.Menu;
import com.lambdaschool.restaurants.models.Payment;
import com.lambdaschool.restaurants.models.Restaurant;
import com.lambdaschool.restaurants.models.RestaurantPayments;
import com.lambdaschool.restaurants.services.RestaurantService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
  Note that here we are using WithMockUser as we are NOT using the database
  and thus DO NOT have access to users so need to Mock one up!
 */

@RunWith(SpringRunner.class)
@WithMockUser(username = "admin",
    roles = {"USER", "ADMIN"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = RestaurantsApplicationTests.class,
    properties = {
        "command.line.runner.enabled=false"})
@AutoConfigureMockMvc
public class RestaurantControllerUnitTestNoDB
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    private List<Restaurant> restaurantList;

    @Before
    public void setUp() throws Exception
    {
        restaurantList = new ArrayList<>();

        Payment payType1 = new Payment("Credit Card");
        Payment payType2 = new Payment("Cash");
        Payment payType3 = new Payment("Bitcoins");

        payType1.setPaymentid(1);
        payType2.setPaymentid(2);
        payType3.setPaymentid(3);

        // Restaurant String name, String address, String city, String state, String telephone
        String rest1Name = "Test Apple";
        Restaurant r1 = new Restaurant(rest1Name,
            "123 Main Street",
            "City",
            "ST",
            "555-555-1234");
        r1.setRestaurantid(10);

        r1.getPayments()
            .add(new RestaurantPayments(r1,
                payType1));
        r1.getPayments()
            .add(new RestaurantPayments(r1,
                payType2));
        r1.getPayments()
            .add(new RestaurantPayments(r1,
                payType3));

        r1.getMenus()
            .add(new Menu("Mac and Cheese",
                6.95,
                r1));
        r1.getMenus()
            .get(0)
            .setMenuId(11);

        r1.getMenus()
            .add(new Menu("Lasagna",
                8.50,
                r1));
        r1.getMenus()
            .get(1)
            .setMenuId(12);

        r1.getMenus()
            .add(new Menu("Meatloaf",
                7.77,
                r1));
        r1.getMenus()
            .get(2)
            .setMenuId(13);

        r1.getMenus()
            .add(new Menu("Tacos",
                8.49,
                r1));
        r1.getMenus()
            .get(3)
            .setMenuId(14);

        r1.getMenus()
            .add(new Menu("Chef Salad",
                12.50,
                r1));
        r1.getMenus()
            .get(4)
            .setMenuId(15);

        restaurantList.add(r1);

        String rest2Name = "Test Eagle Cafe";
        Restaurant r2 = new Restaurant(rest2Name,
            "321 Uptown Drive",
            "Town",
            "ST",
            "555-555-5555");
        r2.setRestaurantid(20);
        r2.getPayments()
            .add(new RestaurantPayments(r2,
                payType2));
        r2.getMenus()
            .add(new Menu("Tacos",
                10.49,
                r2));
        r2.getMenus()
            .get(0)
            .setMenuId(21);
        r2.getMenus()
            .add(new Menu("Barbacoa",
                12.75,
                r2));
        r2.getMenus()
            .get(1)
            .setMenuId(22);

        restaurantList.add(r2);

        String rest3Name = "Test Number 1 Eats";
        Restaurant r3 = new Restaurant(rest3Name,
            "565 Side Avenue",
            "Village",
            "ST",
            "555-123-1555");
        r3.setRestaurantid(30);
        r3.getPayments()
            .add(new RestaurantPayments(r3,
                payType1));
        r3.getPayments()
            .add(new RestaurantPayments(r3,
                payType3));
        r3.getMenus()
            .add(new Menu("Pizza",
                15.15,
                r3));
        r3.getMenus()
            .get(0)
            .setMenuId(31);

        restaurantList.add(r3);

        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void listAllRestaurants() throws Exception
    {
        String apiUrl = "/restaurants/restaurants";
        Mockito.when(restaurantService.findAll())
            .thenReturn(restaurantList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn();
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(restaurantList);

        System.out.println(er);
        assertEquals(er,
            tr);
    }

    @Test
    public void getRestaurantById() throws Exception
    {
        String apiUrl = "/restaurants/restaurant/10";
        Mockito.when(restaurantService.findRestaurantById(10))
            .thenReturn(restaurantList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn();
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(restaurantList.get(0));

        System.out.println(tr);
        assertEquals(er,
            tr);
    }

    @Test
    public void getRestaurantByIdNotFound() throws Exception
    {
        String apiUrl = "/restaurants/restaurant/100";
        Mockito.when(restaurantService.findRestaurantById(100))
            .thenReturn(null);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn();
        String tr = r.getResponse()
            .getContentAsString();

        String er = "";

        System.out.println(er);
        assertEquals(er,
            tr);
    }

    @Test
    public void getRestaurantByName() throws Exception
    {
        String apiUrl = "/restaurants/restaurant/name/Test Eagle Cafe";

        Mockito.when(restaurantService.findRestaurantByName("Test Eagle Cafe"))
            .thenReturn(restaurantList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn(); // this could throw an exception
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(restaurantList.get(0));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        Assert.assertEquals("Rest API Returns List",
            er,
            tr);
    }

    @Test
    public void getRestaurantByNameNotFound() throws Exception
    {
        String apiUrl = "/restaurants/restaurant/name/Turtle";

        Mockito.when(restaurantService.findRestaurantByName("Turtle"))
            .thenReturn(null);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn();
        String tr = r.getResponse()
            .getContentAsString();

        String er = "";

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals(er,
            tr);
    }

    @Test
    public void listRestaurantNameCity() throws Exception
    {
        String apiUrl = "/restaurants/restaurants/name/cafe/city/town";

        Mockito.when(restaurantService.findNameCity("cafe",
            "town"))
            .thenReturn(restaurantList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn(); // this could throw an exception
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(restaurantList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        Assert.assertEquals("Rest API Returns List",
            er,
            tr);
    }

    @Test
    public void listRestaurantNameLike() throws Exception
    {
        String apiUrl = "/restaurants/restaurants/namelike/cafe";

        Mockito.when(restaurantService.findRestaurantByNameLike("cafe"))
            .thenReturn(restaurantList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn(); // this could throw an exception
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(restaurantList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        Assert.assertEquals("Rest API Returns List",
            er,
            tr);
    }

    @Test
    public void addNewRestaurant() throws Exception
    {
        String apiUrl = "/restaurants/restaurant";

        String rest3Name = "Test Mitchell's Cafe";
        Restaurant r3 = new Restaurant(rest3Name,
            "565 Side Avenue",
            "Village",
            "ST",
            "555-123-1555");
        r3.setRestaurantid(40);

        Payment payType1 = new Payment("Turtle");
        payType1.setPaymentid(1);

        r3.getPayments()
            .add(new RestaurantPayments(r3,
                payType1));
        r3.getMenus()
            .add(new Menu("Pizza",
                15.15,
                r3));
        r3.getMenus()
            .get(0)
            .setMenuId(41);

        ObjectMapper mapper = new ObjectMapper();
        String restaurantString = mapper.writeValueAsString(r3);

        Mockito.when(restaurantService.save(any(Restaurant.class)))
            .thenReturn(r3);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(restaurantString);

        mockMvc.perform(rb)
            .andExpect(status().isCreated())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateRestaurant() throws Exception
    {
        String apiUrl = "/restaurants/restaurant/{restaurantid}";
        String rest2Name = "Test BarnBarn's Place";
        Restaurant r2 = new Restaurant(rest2Name,
            "321 Uptown Drive",
            "Town",
            "ST",
            "555-555-5555");
        r2.setRestaurantid(13);
        Payment pay1 = new Payment("Unknown1");
        pay1.setPaymentid(1);
        Payment pay2 = new Payment("Unknown2");
        pay2.setPaymentid(2);

        r2.getPayments()
            .add(new RestaurantPayments(r2,
                pay1));
        r2.getPayments()
            .add(new RestaurantPayments(r2,
                pay2));
        r2.getMenus()
            .add(new Menu("Carrots",
                10.49,
                r2));
        r2.getMenus()
            .add(new Menu("Hay",
                12.75,
                r2));

        Mockito.when(restaurantService.update(r2,
            10L))
            .thenReturn(r2);
        ObjectMapper mapper = new ObjectMapper();
        String restaurantString = mapper.writeValueAsString(r2);

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl,
            10L)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(restaurantString);

        mockMvc.perform(rb)
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteRestaurantById() throws Exception
    {
        String apiUrl = "/restaurants/restaurant/{restaurantid}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl,
            "13")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb)
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }
}