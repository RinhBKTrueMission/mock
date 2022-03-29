package com.example.demo.reponsitory;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {
  @Query(value = "SELECT u FROM Order u WHERE u.orderNum=(Select max(u.orderNum) from u) ")
  Order getMaxOrderNum();

}
