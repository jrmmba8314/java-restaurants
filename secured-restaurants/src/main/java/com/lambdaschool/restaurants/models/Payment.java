package com.lambdaschool.restaurants.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * The entity allowing interaction with the payments table
 */
@Entity
@Table(name = "payments")
public class Payment
    extends Auditable
{
    /**
     * The primary key of this payment type
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long paymentid;

    /**
     * The name of this payment type
     */
    @Column(nullable = false,
        unique = true)
    private String type;

    /**
     * Part of the join relationship between payments and restaurants
     * connects payments to the payment restaurant combination
     */
    @OneToMany(mappedBy = "payment",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnoreProperties(value = "payment",
        allowSetters = true)
    private Set<RestaurantPayments> restaurants = new HashSet<>();

    /**
     * The default constructor required by the JPA
     */
    public Payment()
    {
    }

    /**
     * Given the name of a payment type, create a new payment. Restaurants get added later
     *
     * @param type the name of the payment type
     */
    public Payment(String type)
    {
        this.type = type;
    }

    /**
     * Getter for the primary key of this payment type
     *
     * @return the primary key (long) of this payment type
     */
    public long getPaymentid()
    {
        return paymentid;
    }

    /**
     * Setter for the primary key of this payment type
     *
     * @param paymentid the new primary key (long) of this payment type
     */
    public void setPaymentid(long paymentid)
    {
        this.paymentid = paymentid;
    }

    /**
     * Getter for the name of this payment type
     *
     * @return the name (String) of this payment type
     */
    public String getType()
    {
        return type;
    }

    /**
     * Setter for the name of this payment type
     *
     * @param type the new name (String) of this payment type
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * Getter for the list of restaurants using this payment type
     *
     * @return the list of restaurants using this payment type
     */
    public Set<RestaurantPayments> getRestaurants()
    {
        return restaurants;
    }

    /**
     * Setter for the list of restaurants using this paymen type
     *
     * @param restaurantPayments the new list of restaurants using this payment type
     */
    public void setRestaurants(Set<RestaurantPayments> restaurantPayments)
    {
        this.restaurants = restaurantPayments;
    }
}