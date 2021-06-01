package com.lambdaschool.restaurants.repos;

import com.lambdaschool.restaurants.models.Payment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PaymentRepository
    extends CrudRepository<Payment, Long>
{
    /**
     * Updates the name of the payment based on the given payment id.
     *
     * @param uname     The username making this change
     * @param paymentid The primary key (long) of the payment to change
     * @param name      The new name (String) of the payment
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE payments SET type = :name, lastmodifiedby = :uname, lastmodifieddate = CURRENT_TIMESTAMP WHERE paymentid = :paymentid",
        nativeQuery = true)
    void updatePaymentName(
        String uname,
        long paymentid,
        String name);
}
