package com.repository;

import com.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT a FROM Address a WHERE a.user.userId=:userId")
    Optional<Address> findAddressByUserId(@Param("userId") Long userId);

    @Query("DELETE FROM Address a WHERE a.addressId=:addressId")
    void deleteAddressById(@Param("addressId") Long addressId);
}
