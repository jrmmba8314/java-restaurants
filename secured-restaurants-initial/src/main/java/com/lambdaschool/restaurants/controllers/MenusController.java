package com.lambdaschool.restaurants.controllers;

import com.lambdaschool.restaurants.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The entry point for clients to access menu item data
 */
@RestController
@RequestMapping("/menus")
public class MenusController
{
    /**
     * Using the Menus service to process menu data
     */
    @Autowired
    private MenuService menuService;


}
