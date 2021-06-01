package com.lambdaschool.restaurants.services;

import com.lambdaschool.restaurants.models.Menu;

import java.util.List;

/**
 * The Service that works with the Menu Items Model
 */
public interface MenuService
{
    /**
     * Returns a list of all restaurants and their menu items
     *
     * @return list of all restaurants and their menu items
     */
    List<Menu> findAll();

    /**
     * Returns the menu item associated with the given id
     *
     * @param id The primary key (long) of the menu item you seek
     * @return The menu item (Menu) you seek
     */
    Menu findMenuById(long id);

    /**
     * Remove the menu item referenced by the given id
     *
     * @param id The primary key (long) of the menu it you seek
     */
    void delete(long id);

    /**
     * Replaces the menu dish and price based on the given primary key
     *
     * @param menuid The primary key (long) of the menu item you seek
     * @param menu   the information to update the menu item. Only new information needs to be sent
     * @return The Menu object that you updated
     */
    Menu update(
        long menuid,
        Menu menu);

    /**
     * Add a new menu item
     *
     * @param restaurantid the restaurantid of the menu item
     * @param menu         the information to update the menu item. Only new information needs to be sent
     * @return the new menu item
     */
    Menu save(
        long restaurantid,
        Menu menu);

    /**
     * Return a list of menu items based on the given restaurant name
     *
     * @param restaurantname The name of the restaurant whose menu items you seek
     * @return A menu items based on the given restaurant name
     */
    List<Menu> findByRestaurantName(String restaurantname);
}
