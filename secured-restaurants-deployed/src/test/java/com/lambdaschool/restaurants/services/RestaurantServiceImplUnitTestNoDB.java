package com.lambdaschool.restaurants.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.restaurants.RestaurantsApplicationTests;
import com.lambdaschool.restaurants.exceptions.ResourceNotFoundException;
import com.lambdaschool.restaurants.models.Menu;
import com.lambdaschool.restaurants.models.Payment;
import com.lambdaschool.restaurants.models.Restaurant;
import com.lambdaschool.restaurants.models.RestaurantPayments;
import com.lambdaschool.restaurants.repos.PaymentRepository;
import com.lambdaschool.restaurants.repos.RestaurantRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

/*
  Since no security checks are done in Restaurant Services,
  nothing special with security needs to be handled in the tests
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestaurantsApplicationTests.class,
    properties = {
        "command.line.runner.enabled=false"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestaurantServiceImplUnitTestNoDB
{

    // mocks -> fake data
    // stubs -> fake methods
    // Java -> mocks
    @Autowired
    private RestaurantService restaurantService;

    @MockBean
    private RestaurantRepository restrepos;

    @MockBean
    private PaymentRepository payrepos;

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

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void a_findRestaurantByNameLike()
    {
        Mockito.when(restrepos.findByNameContainingIgnoreCase("eat"))
            .thenReturn(restaurantList);

        assertEquals(3,
            restaurantService.findRestaurantByNameLike("eat")
                .size());
    }

    @Test
    public void b_findNameCity()
    {
        Mockito.when(restrepos.findByNameContainingIgnoreCaseAndCityContainingIgnoreCase("test",
            "city"))
            .thenReturn(restaurantList);

        assertEquals(3,
            restaurantService.findNameCity("test",
                "city")
                .size());
    }

    @Test
    public void c_findAll()
    {
        Mockito.when(restrepos.findAll())
            .thenReturn(restaurantList);

        System.out.println(restaurantService.findAll());
        assertEquals(3,
            restaurantService.findAll()
                .size());
    }

    @Test
    public void d_findRestaurantById()
    {
        Mockito.when(restrepos.findById(4L))
            .thenReturn(Optional.of(restaurantList.get(0)));

        assertEquals("Test Apple",
            restaurantService.findRestaurantById(4)
                .getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void e_findRestaurantByIdNotFound()
    {
        Mockito.when(restrepos.findById(10000L))
            .thenThrow(ResourceNotFoundException.class);

        assertEquals("Test Apple",
            restaurantService.findRestaurantById(10000)
                .getName());
    }

    @Test
    public void f_findRestaurantByName()
    {
        Mockito.when(restrepos.findByName("Test Apple"))
            .thenReturn(restaurantList.get(0));

        assertEquals("Test Apple",
            restaurantService.findRestaurantByName("Test Apple")
                .getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void ff_findRestaurantByNameFailed()
    {
        Mockito.when(restrepos.findByName("Lambda"))
            .thenThrow(ResourceNotFoundException.class);

        assertEquals("Lambda",
            restaurantService.findRestaurantByName("Stumps")
                .getName());
    }

    @Test
    public void g_delete()
    {
        Mockito.when(restrepos.findById(4L))
            .thenReturn(Optional.of(restaurantList.get(0)));

        Mockito.doNothing()
            .when(restrepos)
            .deleteById(4L);

        restaurantService.delete(4);
        assertEquals(3,
            restaurantList.size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void gg_deletefailed()
    {
        Mockito.when(restrepos.findById(777L))
            .thenReturn(Optional.empty());

        Mockito.doNothing()
            .when(restrepos)
            .deleteById(777L);

        restaurantService.delete(777);
        assertEquals(3,
            restaurantList.size());
    }

    @Test
    public void h_save()
    {
        // create a restaurant to save
        String rest3Name = "Test Mitchell's Cafe";
        Restaurant r3 = new Restaurant(rest3Name,
            "565 Side Avenue",
            "Village",
            "ST",
            "555-123-1555");

        Payment payType1 = new Payment("Turtle");
        payType1.setPaymentid(1);

        r3.getPayments()
            .add(new RestaurantPayments(r3,
                payType1));
        r3.getMenus()
            .add(new Menu("Pizza",
                15.15,
                r3));

        Mockito.when(restrepos.save(any(Restaurant.class)))
            .thenReturn(r3);
        Mockito.when(payrepos.findById(1L))
            .thenReturn(Optional.of(payType1));

        Restaurant addRest = restaurantService.save(r3);
        assertNotNull(addRest);
        assertEquals(rest3Name,
            addRest.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void ha_savePayTypeNotFound()
    {
        // create a restaurant to save
        String rest3Name = "Test Mitchell's Cafe";
        Restaurant r3 = new Restaurant(rest3Name,
            "565 Side Avenue",
            "Village",
            "ST",
            "555-123-1555");

        Payment payType1 = new Payment("Turtle");
        payType1.setPaymentid(1);

        r3.getPayments()
            .add(new RestaurantPayments(r3,
                payType1));
        r3.getMenus()
            .add(new Menu("Pizza",
                15.15,
                r3));

        Mockito.when(restrepos.save(any(Restaurant.class)))
            .thenReturn(r3);
        Mockito.when(payrepos.findById(1L))
            .thenReturn(Optional.empty());

        Restaurant addRest = restaurantService.save(r3);
        assertNotNull(addRest);
        assertEquals(rest3Name,
            addRest.getName());
    }

    @Test
    public void hh_saveput()
    {
        String rest2Name = "Test Cinammon's Place";
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
            .clear();
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

        Mockito.when(restrepos.findById(13L))
            .thenReturn(Optional.of(r2));
        Mockito.when(payrepos.findById(1L))
            .thenReturn(Optional.of(pay1));
        Mockito.when(payrepos.findById(2L))
            .thenReturn(Optional.of(pay2));

        Mockito.when(restrepos.save(any(Restaurant.class)))
            .thenReturn(r2);

        assertEquals(13L,
            restaurantService.save(r2)
                .getRestaurantid());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void hhh_saveputfailed()
    {
        String rest2Name = "Test BarnBarn's Place";
        Restaurant r2 = new Restaurant(rest2Name,
            "321 Uptown Drive",
            "Town",
            "ST",
            "555-555-5555");
        r2.setRestaurantid(777);
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

        Mockito.when(restrepos.findById(777L))
            .thenThrow(ResourceNotFoundException.class);

        Mockito.when(restrepos.save(any(Restaurant.class)))
            .thenReturn(r2);
        Mockito.when(payrepos.findById(1L))
            .thenReturn(Optional.of(pay1));
        Mockito.when(payrepos.findById(2L))
            .thenReturn(Optional.of(pay2));

        assertEquals(777L,
            restaurantService.save(r2)
                .getRestaurantid());
    }

    @Test
    public void i_update() throws Exception
    {
        String rest2Name = "Test Eagle Cafe";
        Restaurant r2 = new Restaurant(rest2Name,
            "321 Uptown Drive",
            "Town",
            "ST",
            "555-555-5555");
        r2.setRestaurantid(10);
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

        // I need a copy of r2 to send to update so the original r2 is not changed.
        // I am using Jackson to make a clone of the object
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurant r3 = objectMapper
            .readValue(objectMapper.writeValueAsString(r2),
                Restaurant.class);

        Mockito.when(restrepos.findById(10L))
            .thenReturn(Optional.of(r3));
        Mockito.when(payrepos.findById(1L))
            .thenReturn(Optional.of(pay1));
        Mockito.when(payrepos.findById(2L))
            .thenReturn(Optional.of(pay2));

        Mockito.when(restrepos.save(any(Restaurant.class)))
            .thenReturn(r2);

        Restaurant addRestaurant = restaurantService.update(r2,
            10);

        assertNotNull(addRestaurant);
        assertEquals(rest2Name,
            addRestaurant.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void ii_updateNotFound()
    {
        String rest2Name = "Test BarnBarn's Place";
        Restaurant r2 = new Restaurant(rest2Name,
            "321 Uptown Drive",
            "Town",
            "ST",
            "555-555-5555");
        r2.setRestaurantid(777);
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

        Mockito.when(restrepos.findById(777L))
            .thenThrow(ResourceNotFoundException.class);
        Mockito.when(payrepos.findById(1L))
            .thenReturn(Optional.of(pay1));
        Mockito.when(payrepos.findById(2L))
            .thenReturn(Optional.of(pay2));

        Mockito.when(restrepos.save(any(Restaurant.class)))
            .thenReturn(r2);
        Restaurant addRestaurant = restaurantService.update(r2,
            777);

        assertNotNull(addRestaurant);
        assertEquals(rest2Name,
            addRestaurant.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void iii_updatePayTypeNotFound() throws Exception
    {
        String rest2Name = "Test BarnBarn's Place";
        Restaurant r2 = new Restaurant(rest2Name,
            "321 Uptown Drive",
            "Town",
            "ST",
            "555-555-5555");
        r2.setRestaurantid(10);
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

        // I need a copy of r2 to send to update so the original r2 is not changed.
        // I am using Jackson to make a clone of the object
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurant r3 = objectMapper
            .readValue(objectMapper.writeValueAsString(r2),
                Restaurant.class);

        Mockito.when(restrepos.findById(10L))
            .thenReturn(Optional.of(r3));
        Mockito.when(payrepos.findById(1L))
            .thenReturn(Optional.of(pay1));
        Mockito.when(payrepos.findById(2L))
            .thenReturn(Optional.empty());

        Mockito.when(restrepos.save(any(Restaurant.class)))
            .thenReturn(r2);
        Restaurant addRestaurant = restaurantService.update(r2,
            10);

        assertNotNull(addRestaurant);
        assertEquals(rest2Name,
            addRestaurant.getName());
    }
}