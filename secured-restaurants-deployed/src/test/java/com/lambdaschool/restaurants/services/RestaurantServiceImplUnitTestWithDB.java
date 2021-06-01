package com.lambdaschool.restaurants.services;

import com.lambdaschool.restaurants.RestaurantsApplicationTests;
import com.lambdaschool.restaurants.exceptions.ResourceNotFoundException;
import com.lambdaschool.restaurants.models.Menu;
import com.lambdaschool.restaurants.models.Payment;
import com.lambdaschool.restaurants.models.Restaurant;
import com.lambdaschool.restaurants.models.RestaurantPayments;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

/*
  Since no security checks are done in Restaurant Services,
  nothing special with security needs to be handled in the tests
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestaurantsApplicationTests.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestaurantServiceImplUnitTestWithDB
{
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private PaymentService paymentService;

    @Before
    public void setUp() throws Exception
    {
        // mocks -> fake data
        // stubs -> fake methods
        // Java -> mocks
        MockitoAnnotations.initMocks(this);

        List<Restaurant> myList = restaurantService.findAll();

        for (Restaurant r : myList)
        {
            System.out.println("Restaurant id: " + r.getRestaurantid() + " Restaurant Name: " + r.getName());
        }

        System.out.println();

        List<Payment> myPayList = paymentService.findAll();
        for (Payment p : myPayList)
        {
            System.out.println("Payment id: " + p.getPaymentid() + " Payment Type: " + p.getType());
        }
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void a_findRestaurantByNameLike()
    {
        assertEquals(1,
            restaurantService.findRestaurantByNameLike("eat")
                .size());
    }

    @Test
    public void b_findNameCity()
    {
        assertEquals(1,
            restaurantService.findNameCity("test",
                "city")
                .size());
    }

    @Test
    public void c_findAll()
    {
        System.out.println(restaurantService.findAll());
        assertEquals(3,
            restaurantService.findAll()
                .size());
    }

    @Test
    public void d_findRestaurantById()
    {
        assertEquals("Test Apple",
            restaurantService.findRestaurantById(12)
                .getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void e_findRestaurantByIdNotFound()
    {
        assertEquals("Test Apple",
            restaurantService.findRestaurantById(10000)
                .getName());
    }

    @Test
    public void f_findRestaurantByName()
    {
        assertEquals("Test Apple",
            restaurantService.findRestaurantByName("Test Apple")
                .getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void ff_findRestaurantByNameFailed()
    {
        assertEquals("Lambda",
            restaurantService.findRestaurantByName("Stumps")
                .getName());
    }

    @Test
    public void g_delete()
    {
        restaurantService.delete(12);
        assertEquals(2,
            restaurantService.findAll()
                .size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void gg_deletefailed()
    {
        restaurantService.delete(777);
        assertEquals(2,
            restaurantService.findAll()
                .size());
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
        payType1.setPaymentid(9);

        r3.getPayments()
            .add(new RestaurantPayments(r3,
                payType1));
        r3.getMenus()
            .add(new Menu("Pizza",
                15.15,
                r3));

        Restaurant addRest = restaurantService.save(r3);
        assertNotNull(addRest);
        assertEquals(rest3Name,
            addRest.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void h_savePayTypeNotFound()
    {
        // create a restaurant to save
        String rest3Name = "Test Mitchell's Cafe";
        Restaurant r3 = new Restaurant(rest3Name,
            "565 Side Avenue",
            "Village",
            "ST",
            "555-123-1555");

        Payment payType1 = new Payment("Turtle");
        payType1.setPaymentid(13);

        r3.getPayments()
            .add(new RestaurantPayments(r3,
                payType1));
        r3.getMenus()
            .add(new Menu("Pizza",
                15.15,
                r3));

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
        r2.setRestaurantid(21);
        Payment pay1 = new Payment("Unknown1");
        pay1.setPaymentid(9);
        Payment pay2 = new Payment("Unknown2");
        pay2.setPaymentid(10);

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

        Restaurant addRestaurant = restaurantService.save(r2);

        assertNotNull(addRestaurant);
        assertEquals(rest2Name,
            addRestaurant.getName());
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

        Restaurant addRestaurant = restaurantService.save(r2);

        assertNotNull(addRestaurant);
        assertEquals(rest2Name,
            addRestaurant.getName());
    }

    @Test
    public void i_update()
    {
        String rest2Name = "Test Eagle Cafe";
        Restaurant r2 = new Restaurant(rest2Name,
            "321 Uptown Drive",
            "Town",
            "ST",
            "555-555-5555");
        r2.setRestaurantid(10);
        Payment pay1 = new Payment("Unknown1");
        pay1.setPaymentid(9);
        Payment pay2 = new Payment("Unknown2");
        pay2.setPaymentid(10);

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

        Restaurant addRestaurant = restaurantService.update(r2,
            18);

        assertNotNull(addRestaurant);
        assertEquals(rest2Name,
            addRestaurant.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void i_updatePayTypeNotFound()
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
        pay2.setPaymentid(13);

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

        Restaurant addRestaurant = restaurantService.update(r2,
            777);

        assertNotNull(addRestaurant);
        assertEquals(rest2Name,
            addRestaurant.getName());
    }
}