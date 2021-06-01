package com.lambdaschool.restaurants.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * The entity allowing interaction with the menus table
 */
@Entity
@Table(name = "menus")
public class Menu
    extends Auditable
{
    /**
     * The primary key (long) of this menu type
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long menuId;

    /**
     * The name of the dish (String) for this menu item
     */
    @Column(nullable = false)
    private String dish;

    /**
     * The cost (double) of this menu item
     */
    private double price;

    /**
     * The restaurant to which this menu item is associated
     */
    @ManyToOne
    @JoinColumn(name = "restaurantid",
        nullable = false)
    @JsonIgnoreProperties(value = "menus",
        allowSetters = true)
    private Restaurant restaurant;

    /**
     * Default constructor as required by the JPA
     */
    public Menu()
    {
    }

    /**
     * Create a menu item using the given parameters
     *
     * @param dish       The name of the dish (String) for this menu item
     * @param price      the cost (double) of this menu item
     * @param restaurant the restaurant (Restaurant) associated with this menu item.
     */
    public Menu(
        String dish,
        double price,
        Restaurant restaurant)
    {
        this.dish = dish;
        this.price = price;
        this.restaurant = restaurant;
    }

    /**
     * Getter for the primary key of this menu item
     *
     * @return the primary key (long) of this menu item
     */
    public long getMenuId()
    {
        return menuId;
    }

    /**
     * Setter for the primary key of this menu item
     *
     * @param menuId the new primary key (long) of this menu item
     */
    public void setMenuId(long menuId)
    {
        this.menuId = menuId;
    }

    /**
     * Getter for the dish name of this menu item
     *
     * @return the dish name (String) of this menu item
     */
    public String getDish()
    {
        return dish;
    }

    /**
     * Setter for the dish name of this menu item
     *
     * @param dish the new dish name (String) of this menu item
     */
    public void setDish(String dish)
    {
        this.dish = dish;
    }

    /**
     * Getter for the price of this menu item
     *
     * @return the price (double) of this menu item
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * Setter for the price of this menu item
     *
     * @param price the new price (double) of this menu item
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /**
     * Getter for the restaurant associated with this menu item
     *
     * @return the restaurant associated with this menu item
     */
    public Restaurant getRestaurant()
    {
        return restaurant;
    }

    /**
     * Setter for the restaurant associated with this menu item
     *
     * @param restaurant the new restaurant associated with this menu item
     */
    public void setRestaurant(Restaurant restaurant)
    {
        this.restaurant = restaurant;
    }
}