package com.lambdaschool.restaurants.services;

import com.lambdaschool.restaurants.exceptions.ResourceNotFoundException;
import com.lambdaschool.restaurants.models.Menu;
import com.lambdaschool.restaurants.models.Payment;
import com.lambdaschool.restaurants.models.Restaurant;
import com.lambdaschool.restaurants.models.RestaurantPayments;
import com.lambdaschool.restaurants.repos.PaymentRepository;
import com.lambdaschool.restaurants.repos.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the restaurant service interface
 */
@Transactional
@Service(value = "restaurantService")
public class RestaurantServiceImpl
    implements RestaurantService
{
    /**
     * Connects this service to the restaurants table
     */
    @Autowired
    private RestaurantRepository restrepos;

    /**
     * Connects this service to the payments table
     */
    @Autowired
    private PaymentRepository payrepos;

    /**
     * Connects this service to the auditing service in order to get current user name
     */
    @Autowired
    private UserAuditing userAuditing;

    @Override
    public List<Restaurant> findRestaurantByNameLike(String name)
    {
        List<Restaurant> list = restrepos.findByNameContainingIgnoreCase(name);
        return list;
    }

    @Override
    public List<Restaurant> findNameCity(
        String name,
        String city)
    {
        List<Restaurant> list = restrepos.findByNameContainingIgnoreCaseAndCityContainingIgnoreCase(name,
            city);
        return list;
    }

    @Override
    public List<Restaurant> findAll()
    {
        List<Restaurant> list = new ArrayList<>();
        restrepos.findAll()
            .iterator()
            .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Restaurant findRestaurantById(long id) throws
                                                  ResourceNotFoundException
    {
        return restrepos.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Restaurant id " + id + " not found!"));
    }

    @Override
    public Restaurant findRestaurantByName(String name) throws
                                                        ResourceNotFoundException
    {
        Restaurant restaurant = restrepos.findByName(name);

        if (restaurant == null)
        {
            throw new ResourceNotFoundException("Restaurant " + name + " not found!");
        }

        return restaurant;
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        if (restrepos.findById(id)
            .isPresent())
        {
            restrepos.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException("Restaurant id " + id + " not found!");
        }
    }

    @Transactional
    @Override
    public Restaurant save(Restaurant restaurant)
    {
        Restaurant newRestaurant = new Restaurant();

        if (restaurant.getRestaurantid() != 0)
        {
            restrepos.findById(restaurant.getRestaurantid())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant id " + restaurant.getRestaurantid() + " not found!"));
            newRestaurant.setRestaurantid(restaurant.getRestaurantid());
        }

        newRestaurant.setName(restaurant.getName());
        newRestaurant.setAddress(restaurant.getAddress());
        newRestaurant.setCity(restaurant.getCity());
        newRestaurant.setState(restaurant.getState());
        newRestaurant.setTelephone(restaurant.getTelephone());

        newRestaurant.getPayments()
            .clear();
        for (RestaurantPayments rp : restaurant.getPayments())
        {
            Payment newPayment = payrepos.findById(rp.getPayment()
                .getPaymentid())
                .orElseThrow(() -> new ResourceNotFoundException("Payment id " + rp.getPayment()
                    .getPaymentid() + " not found!"));

            newRestaurant.getPayments()
                .add(new RestaurantPayments(newRestaurant,
                    newPayment));
        }

        newRestaurant.getMenus()
            .clear();
        for (Menu m : restaurant.getMenus())
        {
            newRestaurant.getMenus()
                .add(new Menu(m.getDish(),
                    m.getPrice(),
                    newRestaurant));
        }

        return restrepos.save(newRestaurant);
    }

    @Transactional
    @Override
    public Restaurant update(
        Restaurant restaurant,
        long id)
    {
        Restaurant currentRestaurant = restrepos.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Restaurant " + id + " not found"));

        if (restaurant.getName() != null)
        {
            currentRestaurant.setName(restaurant.getName());
        }

        if (restaurant.getAddress() != null)
        {
            currentRestaurant.setAddress(restaurant.getAddress());
        }

        if (restaurant.getCity() != null)
        {
            currentRestaurant.setCity(restaurant.getCity());
        }

        if (restaurant.getState() != null)
        {
            currentRestaurant.setState(restaurant.getState());
        }

        if (restaurant.getTelephone() != null)
        {
            currentRestaurant.setTelephone(restaurant.getTelephone());
        }

        if (restaurant.getPayments()
            .size() > 0)
        {
            for (RestaurantPayments rp : restaurant.getPayments())
            {
                Payment newPayment = payrepos.findById(rp.getPayment()
                    .getPaymentid())
                    .orElseThrow(() -> new ResourceNotFoundException("Payment id " + rp.getPayment()
                        .getPaymentid() + " not found!"));

                currentRestaurant.getPayments()
                    .add(new RestaurantPayments(currentRestaurant,
                        newPayment));
            }
        }

        System.out.println("*** " + restaurant.getMenus()
            .size());
        System.out.println(restaurant.getMenus());

        if (restaurant.getMenus()
            .size() > 0)
        {
            currentRestaurant.getMenus()
                .clear();
            for (Menu m : restaurant.getMenus())
            {
                currentRestaurant.getMenus()
                    .add(new Menu(m.getDish(),
                        m.getPrice(),
                        currentRestaurant));
            }
        }

        return restrepos.save(currentRestaurant);
    }
}
