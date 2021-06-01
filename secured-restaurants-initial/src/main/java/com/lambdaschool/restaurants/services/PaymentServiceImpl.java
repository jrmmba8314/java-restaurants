package com.lambdaschool.restaurants.services;

import com.lambdaschool.restaurants.models.Payment;
import com.lambdaschool.restaurants.models.User;
import com.lambdaschool.restaurants.repos.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService
{
    @Autowired
    PaymentRepository payrepos;

    @Override
    public List<Payment> findAll()
    {
        List<Payment> list = new ArrayList<>();
        /*
         * findAll returns an iterator set.
         * iterate over the iterator set and add each element to an array list.
         */
        payrepos.findAll()
            .iterator()
            .forEachRemaining(list::add);
        return list;    }

    @Override
    public Payment findPaymentById(long id)
    {
        return null;
    }

    @Override
    public Payment save(Payment payment)
    {
        return null;
    }

    @Override
    public Payment findByName(String name)
    {
        return null;
    }

    @Override
    public void delete(long id)
    {

    }

    @Override
    public Payment update(
        long id,
        Payment payment)
    {
        return null;
    }
}
