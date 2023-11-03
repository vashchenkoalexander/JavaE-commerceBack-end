package com.payoya.diplomaproject.api.repository;

import com.payoya.diplomaproject.api.entity.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
}
