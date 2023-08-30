package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.Order;

import java.util.List;


public interface OrderRepository extends BaseRepository<Order> {
    List<Order> getUserOrders(int id);
}
