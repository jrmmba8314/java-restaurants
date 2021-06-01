package com.lambdaschool.restaurants.services;

import com.lambdaschool.restaurants.models.Menu;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("menuService")
public class MenuServiceImpl implements MenuService
{
    @Override
    public List<Menu> findAll()
    {
        return null;
    }

    @Override
    public Menu findMenuById(long id)
    {
        return null;
    }

    @Override
    public void delete(long id)
    {

    }

    @Override
    public Menu update(
        long menuid,
        Menu menu)
    {
        return null;
    }

    @Override
    public Menu save(
        long restaurantid,
        Menu menu)
    {
        return null;
    }

    @Override
    public List<Menu> findByRestaurantName(String restaurantname)
    {
        return null;
    }
}
