package local.heftyb.javaorders.services;

import local.heftyb.javaorders.models.Order;
import local.heftyb.javaorders.views.OutstandingAdvance;

import java.util.List;

public interface OrderService
{
    Order findOrderById(long id);

    List<OutstandingAdvance> findAllOutstandingAdvance();

    Order save(Order order);

    void delete(long id);
}
