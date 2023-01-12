package me.hjhng.springdatajdbcpractice.order.entity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.mapstruct.Mapper;

import me.hjhng.springdatajdbcpractice.order.Order;

@Mapper(componentModel = "spring")
public interface OrderEntityMapper {

  OrderEntity toEntity(Order order);

  default OffsetDateTime toOffsetDateTime(LocalDateTime source) {
    if (source == null) {
      return null;
    }

    return OffsetDateTime.of(source, ZoneOffset.UTC);
  }
}
