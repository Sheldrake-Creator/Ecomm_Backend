package com.repository;

import com.exception.RepositoryException;
import com.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.user.id =:userId AND (o.orderStatus ='PENDING' OR o.orderStatus ='PLACED' OR o.orderStatus = 'CONFIRMED' OR o.orderStatus = 'SHIPPED' OR o.orderStatus = 'DELIVERED')")
    public Optional<List<Order>> getUsersOrders(@Param("userId") Long userId) throws RepositoryException;
}
