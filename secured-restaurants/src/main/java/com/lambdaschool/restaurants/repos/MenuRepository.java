package com.lambdaschool.restaurants.repos;

import com.lambdaschool.restaurants.models.Menu;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * The CRUD Repository connecting menu items to the rest of the application
 */
public interface MenuRepository
    extends CrudRepository<Menu, Long>
{
    /**
     * Return a list of menu items based on the given restaurant name
     *
     * @param name The restaurant name of whose menu items you seek
     * @return A list of menu items
     */
    List<Menu> findAllByRestaurant_Name(String name);
}
