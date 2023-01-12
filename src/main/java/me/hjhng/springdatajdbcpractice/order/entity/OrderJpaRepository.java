package me.hjhng.springdatajdbcpractice.order.entity;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {

}
