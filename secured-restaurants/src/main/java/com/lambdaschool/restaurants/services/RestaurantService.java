package com.lambdaschool.restaurants.services;

import com.lambdaschool.restaurants.models.Restaurant;

import java.util.List;

/**
 * The service that works with the restaurant model
 */
public interface RestaurantService
{
    /**
     * Returns a list of all the restaurants
     *
     * @return List of restaurants. If no restaurants, empty list.
     */
    List<Restaurant> findAll();

    /**
     * Returns the restaurant with the given primary key.
     *
     * @param id The primary key (long) of the restaurant you seek.
     * @return The given restaurant or throws an exception if not found.
     */
    Restaurant findRestaurantById(long id);

    /**
     * Returns the restaurant with the given name
     *
     * @param name The full name (String) of the restaurant you seek.
     * @return The restaurant with the given name or throws an exception if not found.
     */
    Restaurant findRestaurantByName(String name);

    /**
     * A list of all restaurants whose name contains the given substring
     *
     * @param name The substring (String) of the name of the Restaurants you seek
     * @return List of restaurants whose name contains the given substring
     */
    List<Restaurant> findRestaurantByNameLike(String name);


    /**
     * A list of all restaurants whose name and city contain the given substrings
     *
     * @param name The substring (String) of the name of the Restaurants you seek
     * @param city The substring (String) of the city of the Restaurants you seek
     * @return List of restaurants whose name and city contain the given substring
     */
    List<Restaurant> findNameCity(
        String name,
        String city);

    /**
     * Deletes the restaurant record, its restaurant and payment combinations, and its menu items from the database based off of the provided primary key
     *
     * @param id The primary key (long) of the restaurant you seek.
     */
    void delete(long id);

    /**
     * Given a complete restaurant object, saves that restaurant object in the database.
     * If a primary key is provided, the record is completely replaced
     * If no primary key is provided, one is automatically generated and the record is added to the database.
     *
     * @param restaurant the restaurant object to be saved
     * @return the saved restaurant object including any automatically generated fields
     */
    Restaurant save(Restaurant restaurant);

    /**
     * Updates the provided fields in the restaurant record referenced by the primary key.
     *
     * @param restaurant just the restaurant fields to be updated.
     * @param id         The primary key (long) of the restaurant to update
     * @return the complete restaurant object that got updated
     */
    Restaurant update(
        Restaurant restaurant,
        long id);
}
