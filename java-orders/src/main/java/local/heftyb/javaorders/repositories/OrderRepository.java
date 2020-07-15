package local.heftyb.javaorders.repositories;

import local.heftyb.javaorders.models.Order;
import local.heftyb.javaorders.views.OutstandingAdvance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long>
{
    @Query(value = "SELECT o.ordnum, o.advanceamount, c.custname FROM orders o LEFT JOIN customers c ON o.custcode = c.custcode WHERE o.advanceamount > 0 ORDER BY o.advanceamount DESC", nativeQuery = true)
    List<OutstandingAdvance> findAllOutstandingAdvance();
}
