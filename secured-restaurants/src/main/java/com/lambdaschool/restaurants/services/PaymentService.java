package com.lambdaschool.restaurants.services;

import com.lambdaschool.restaurants.models.Payment;

import java.util.List;

/**
 * The service that works with the Payment Model.
 */
public interface PaymentService
{
    /**
     * Returns a list of all Payment objects
     *
     * @return list of all Payment object
     */
    List<Payment> findAll();

    /**
     * Return the first Payment matching the given primary key
     *
     * @param id The primary key (long) of the Payment seek
     * @return The Payment object you seek
     */
    Payment findPaymentById(long id);

    /**
     * Given a complete Payment object, saved that Payment object in the database.
     * If a primary key is provided, the record is completely replaced
     * If no primary key is provided, one is automatically generated and the record is added to the database.
     *
     * @param payment the payment object to be saved
     * @return the saved payment object including any automatically generated fields
     */
    Payment save(Payment payment);

    /**
     * Find the first payment object matching the given name
     *
     * @param name The name (String) of the payment you seek
     * @return The payment object matching the given name
     */
    Payment findByName(String name);

    /**
     * Deletes a given payment and associated restaurant payments combinations
     *
     * @param id The payment id of the payment to delete
     */
    void delete(long id);

    /**
     * Updates the name of the given payment
     *
     * @param id      The primary key (long) of the payment you wish to update
     * @param payment The payment object containing the new name
     * @return The complete payment with the new name
     */
    Payment update(
        long id,
        Payment payment);
}
