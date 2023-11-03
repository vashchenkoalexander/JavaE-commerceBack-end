package com.payoya.diplomaproject.api.controller;

import com.payoya.diplomaproject.api.entity.ShippingAddress;
import com.payoya.diplomaproject.api.service.ShippingAddressService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shippaddr")
public class ShippingAddressRestController {

    private ShippingAddressService shippingAddressService;

    public ShippingAddressRestController(ShippingAddressService shippingAddressService) {
        this.shippingAddressService = shippingAddressService;
    }

    @PostMapping("/new{userId}")
    public ShippingAddress createNewAddress(@RequestBody ShippingAddress shippingAddress, @PathVariable Long userId){
        return shippingAddressService.createNewAddress(shippingAddress, userId);
    }

}
