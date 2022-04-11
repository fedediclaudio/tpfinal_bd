package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController
{
    private final ItemService itemService;
    @Autowired
    public ItemController(ItemService itemService)
    {
        this.itemService = itemService;
    }

    //////  POST

    //TODO: End Point para Funcionalidad 1
    //agrega un Item a una Order ya creada.
    @PostMapping(value = "/new")
    public void addItem(@RequestBody Item newItem)
    {
        this.itemService.addItem(newItem);
    }

    //////  GET

    @GetMapping("/all")
    public List<Item> getAll()
    {
        return this.itemService.getAll();
    }

    @GetMapping("/id/{id}")
    public Optional<Item> getItemById(@PathVariable Long id)
    {
        return this.itemService.getItemById(id);
    }
}
