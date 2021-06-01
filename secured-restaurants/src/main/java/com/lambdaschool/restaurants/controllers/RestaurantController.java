package com.lambdaschool.restaurants.controllers;

import com.lambdaschool.restaurants.models.Restaurant;
import com.lambdaschool.restaurants.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * The entry point for clients to access restaurant data
 */
@RestController
@RequestMapping("/restaurants")
public class RestaurantController
{
    @Autowired
    private RestaurantService restaurantService;

    /**
     * Returns a list of all restaurants
     * <br>Example: <a href="http://localhost:2019/restaurants/restaurants">http://localhost:2019/restaurants/restaurants</a>
     *
     * @return JSON list of all restaurants with a status of OK
     * @see RestaurantService#findAll() RestaurantService.findAll()
     */
    @GetMapping(value = "/restaurants",
        produces = {"application/json"})
    public ResponseEntity<?> listAllRestaurants()
    {
        List<Restaurant> myRestaurants = restaurantService.findAll();
        return new ResponseEntity<>(myRestaurants,
            HttpStatus.OK);
    }

    /**
     * Returns a single restaurant based off a restaurant id number
     * <br>Example: <a href="http://localhost:2019/restaurants/restaurant/7">http://localhost:2019/restaurants/restaurant/7</a>
     *
     * @param restaurantId The primary key of the restaurant you seek
     * @return JSON object of the restaurant you seek
     * @see RestaurantService#findRestaurantById(long) RestaurantService.findRestaurantById(long)
     */
    @GetMapping(value = "/restaurant/{restaurantId}",
        produces = {"application/json"})
    public ResponseEntity<?> getRestaurantById(
        @PathVariable
            Long restaurantId)
    {
        Restaurant r = restaurantService.findRestaurantById(restaurantId);
        return new ResponseEntity<>(r,
            HttpStatus.OK);
    }

    /**
     * Return a restaurant object based on a given name
     * <br>Example: <a href="http://localhost:2019/restuarants/restaurant/name/Eagle%20Cafe">http://localhost:2019/restuarants/restaurant/name/Eagle%20Cafe</a>
     *
     * @param name the name of restaurant (String) you seek
     * @return JSON object of the restaurant you seek
     * @see RestaurantService#findRestaurantByName(String) RestaurantService.findRestaurantByName(String)
     */
    @GetMapping(value = "/restaurant/name/{name}",
        produces = {"application/json"})
    public ResponseEntity<?> getRestaurantByName(
        @PathVariable
            String name)
    {
        Restaurant r = restaurantService.findRestaurantByName(name);
        return new ResponseEntity<>(r,
            HttpStatus.OK);
    }

    /**
     * Return a list of restaurant objects whose name is like the given substring and is in the city whose name is like the given String
     * <br>Example: <a href="http://localhost:2019/restaurants/restaurants/name/cafe/city/town"></a>
     *
     * @param name The substring of the name of the restaurants you seek
     * @param city The substring of the city of the restaurants you seek
     * @return A JSON list of restaurants you seek
     * @see RestaurantService#findNameCity(String, String) RestaurantService.findNameCity(String, String)
     */
    @GetMapping(value = "/restaurants/name/{name}/city/{city}",
        produces = {"application/json"})
    public ResponseEntity<?> listRestaurantNameCity(
        @PathVariable
            String name,
        @PathVariable
            String city)
    {
        List<Restaurant> myRestaurants = restaurantService.findNameCity(name,
            city);
        return new ResponseEntity<>(myRestaurants,
            HttpStatus.OK);
    }


    /**
     * Returns a list of restaurants whose name contains the given substring
     * <br>Example: <a href="http://localhost:2019/restaurants/restaurants/namelike/cafe">http://localhost:2019/restaurants/restaurants/namelike/cafe</a>
     *
     * @param name Substring of the restaurant name for which you seek
     * @return A JSON list of restaurants you seek
     * @see RestaurantService#findRestaurantByNameLike(String) RestaurantService.findRestaurantByNameLike(String)
     */
    @GetMapping(value = "/restaurants/namelike/{name}",
        produces = {"application/json"})
    public ResponseEntity<?> listRestaurantNameLike(
        @PathVariable
            String name)
    {
        List<Restaurant> myRestaurants = restaurantService.findRestaurantByNameLike(name);
        return new ResponseEntity<>(myRestaurants,
            HttpStatus.OK);
    }

    /**
     * Given a complete restaurant Object, create a new restaurant record and accompanying menu items records
     * and payment restaurant records.
     * <br> Example: <a href="http://localhost:2019/restaurants/restaurant">http://localhost:2019/restaurants/restaurant</a>
     *
     * @param newRestaurant A complete new restaurant to add including menu items and payments.
     *                      payments must already exist.
     * @return A location header with the URI to the newly created restaurant and a status of CREATED
     * @throws URISyntaxException Exception if something does not work in creating the location header
     * @see RestaurantService#save(Restaurant) RestaurantService.save(Restaurant)
     */
    @PostMapping(value = "/restaurant",
        consumes = {"application/json"},
        produces = {"application/json"})
    public ResponseEntity<?> addNewRestaurant(
        @Valid
        @RequestBody
            Restaurant newRestaurant) throws
                                      URISyntaxException
    {
        newRestaurant = restaurantService.save(newRestaurant);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newRestaurantURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{restaurantid}")
            .buildAndExpand(newRestaurant.getRestaurantid())
            .toUri();
        responseHeaders.setLocation(newRestaurantURI);

        return new ResponseEntity<>(null,
            responseHeaders,
            HttpStatus.CREATED);
    }

    /**
     * Given a complete Restaurant Object
     * Given the restaurant id, primary key, is in the restaurant table,
     * replace the restaurant record, restaurant payment combinations and menu items records.
     * <br> Example: <a href="http://localhost:2019/restaurants/restaurants/15">http://localhost:2019/restaurants/restaurant/15</a>
     *
     * @param updateRestaurant A complete Restaurant including all menu items and payments to be used to
     *                         replace the restaurant. Payments must already exist.
     * @param restaurantid     The primary key of the restaurant you wish to replace.
     * @return status of OK
     * @see RestaurantService#save(Restaurant)
     */
    @PutMapping(value = "/restaurant/{restaurantid}")
    public ResponseEntity<?> updateRestaurant(
        @RequestBody
            Restaurant updateRestaurant,
        @PathVariable
            long restaurantid)
    {
        updateRestaurant.setRestaurantid(restaurantid);
        restaurantService.save(updateRestaurant);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Deletes a given restaurant along with associated menu items and payments
     * <br>Example: <a href="http://localhost:2019/restaurants/restaurant/14">http://localhost:2019/restaurant/restaurant/14</a>
     *
     * @param restaurantid the primary key of the restaurant you wish to delete
     * @return Status of OK
     */
    @DeleteMapping("/restaurant/{restaurantid}")
    public ResponseEntity<?> deleteRestaurantById(
        @PathVariable
            long restaurantid)
    {
        restaurantService.delete(restaurantid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

