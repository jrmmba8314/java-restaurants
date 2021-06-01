package com.lambdaschool.restaurants;

import com.lambdaschool.restaurants.models.*;
import com.lambdaschool.restaurants.repos.PaymentRepository;
import com.lambdaschool.restaurants.repos.RestaurantRepository;
import com.lambdaschool.restaurants.services.RoleService;
import com.lambdaschool.restaurants.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class SeedData
    implements CommandLineRunner
{
    @Autowired
    private PaymentRepository payrepos;

    @Autowired
    private RestaurantRepository restaurantrepos;

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Transactional
    @Override
    public void run(String[] args) throws
                                   Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);

        // admin, data, user
        User u1 = new User("admin",
            "password");
        u1.getRoles()
            .add(new UserRoles(u1,
                r1));
        u1.getRoles()
            .add(new UserRoles(u1,
                r2));
        u1.getRoles()
            .add(new UserRoles(u1,
                r3));

        userService.save(u1);

        // data, user
        User u2 = new User("cinnamon",
            "1234567");
        u2.getRoles()
            .add(new UserRoles(u2,
                r2));
        u2.getRoles()
            .add(new UserRoles(u2,
                r3));
        userService.save(u2);

        // user
        User u3 = new User("barnbarn",
            "ILuvM4th!");
        u3.getRoles()
            .add(new UserRoles(u3,
                r2));
        userService.save(u3);

        User u4 = new User("puttat",
            "password");
        u4.getRoles()
            .add(new UserRoles(u4,
                r2));
        userService.save(u4);

        User u5 = new User("misskitty",
            "password");
        u5.getRoles()
            .add(new UserRoles(u5,
                r2));
        userService.save(u5);

        Payment payType1 = new Payment("Credit Card");
        Payment payType2 = new Payment("Cash");
        Payment payType3 = new Payment("Mobile Pay");

        payrepos.save(payType1);
        payrepos.save(payType2);
        payrepos.save(payType3);

        // Restaurant String name, String address, String city, String state, String telephone
        String rest1Name = "Apple";
        Restaurant rest1 = new Restaurant(rest1Name,
            "123 Main Street",
            "City",
            "ST",
            "555-555-1234");
        rest1.getPayments()
            .add(new RestaurantPayments(rest1,
                payType1));
        rest1.getPayments()
            .add(new RestaurantPayments(rest1,
                payType2));
        rest1.getPayments()
            .add(new RestaurantPayments(rest1,
                payType3));
        rest1.getMenus()
            .add(new Menu("Mac and Cheese",
                6.95,
                rest1));
        rest1.getMenus()
            .add(new Menu("Lasagna",
                8.50,
                rest1));
        rest1.getMenus()
            .add(new Menu("Meatloaf",
                7.77,
                rest1));
        rest1.getMenus()
            .add(new Menu("Tacos",
                8.49,
                rest1));
        rest1.getMenus()
            .add(new Menu("Chef Salad",
                12.50,
                rest1));

        restaurantrepos.save(rest1);

        String rest2Name = "Eagle Cafe";
        Restaurant rest2 = new Restaurant(rest2Name,
            "321 Uptown Drive",
            "Town",
            "ST",
            "555-555-5555");
        rest2.getPayments()
            .add(new RestaurantPayments(rest2,
                payType2));
        rest2.getMenus()
            .add(new Menu("Tacos",
                10.49,
                rest2));
        rest2.getMenus()
            .add(new Menu("Barbacoa",
                12.75,
                rest2));

        restaurantrepos.save(rest2);

        String rest3Name = "Number 1 Eats";
        Restaurant rest3 = new Restaurant(rest3Name,
            "565 Side Avenue",
            "Village",
            "ST",
            "555-123-1555");
        rest3.getPayments()
            .add(new RestaurantPayments(rest3,
                payType1));
        rest3.getPayments()
            .add(new RestaurantPayments(rest3,
                payType3));
        rest3.getMenus()
            .add(new Menu("Pizza",
                15.15,
                rest3));

        restaurantrepos.save(rest3);
    }
}
