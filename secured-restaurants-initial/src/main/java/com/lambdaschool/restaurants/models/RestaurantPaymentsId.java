package com.lambdaschool.restaurants.models;

import java.io.Serializable;

public class RestaurantPaymentsId implements Serializable
{
    private long restaurant;

    private long payment;

    public RestaurantPaymentsId()
    {
    }

    public long getRestaurant()
    {
        return restaurant;
    }

    public void setRestaurant(long restaurant)
    {
        this.restaurant = restaurant;
    }

    public long getPayment()
    {
        return payment;
    }

    public void setPayment(long payment)
    {
        this.payment = payment;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        RestaurantPaymentsId that = (RestaurantPaymentsId) o;
        return restaurant == that.restaurant &&
            payment == that.payment;
    }

    @Override
    public int hashCode()
    {
        return 37;
    }
}
