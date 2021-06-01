package com.lambdaschool.restaurants.repos;

import com.lambdaschool.restaurants.models.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * The CRUD repository connecting Restaurant to the rest of the application
 */
public interface RestaurantRepository
    extends CrudRepository<Restaurant, Long>
{
    /**
     * Find a restaurant based off over restaurant name
     *
     * @param name the name (String) of restaurant you seek
     * @return the first restaurant object with the name you seek
     */
    Restaurant findByName(String name);

    /**
     * Find all restaurants whose name contains a given substring ignoring case
     *
     * @param name the substring of the names (String) you seek
     * @return List of restaurants whose name contain the given substring ignoring case
     */
    List<Restaurant> findByNameContainingIgnoreCase(String name);

    /**
     * Find all restaurants whose name contains a given substring and is located in cities containing a given substring ignoring case
     *
     * @param likename the substring of the names (String) you seek
     * @param likecity the substring of the cities (String) you seek
     * @return List of restaurants whose name contains a given substring and is located in cities containing a given substring ignoring case
     */
    List<Restaurant> findByNameContainingIgnoreCaseAndCityContainingIgnoreCase(
        String likename,
        String likecity);
}
