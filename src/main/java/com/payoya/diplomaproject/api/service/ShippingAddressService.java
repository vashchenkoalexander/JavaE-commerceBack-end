package com.payoya.diplomaproject.api.service;

import com.payoya.diplomaproject.api.entity.ShippingAddress;
import com.payoya.diplomaproject.api.entity.User;
import com.payoya.diplomaproject.api.repository.IShippingAddressRepository;
import com.payoya.diplomaproject.api.repository.IUserRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

@Service
public class ShippingAddressService {

    private IShippingAddressRepository shippingAddressRepository;
    private IUserRepository userRepository;


    public ShippingAddressService(IShippingAddressRepository shippingAddressRepository, IUserRepository userRepository) {
        this.shippingAddressRepository = shippingAddressRepository;
        this.userRepository = userRepository;
    }

    @PostAuthorize("returnObject.user.username == principal.username")
    public ShippingAddress createNewAddress(ShippingAddress shippingAddress, Long userId){
        User user = userRepository.findById(userId).orElse(null);

        if(user == null){
            throw new IllegalStateException("this user doesn't exist");
        }

        shippingAddress.setUser(user);

        return shippingAddressRepository.save(shippingAddress);

    }

}
