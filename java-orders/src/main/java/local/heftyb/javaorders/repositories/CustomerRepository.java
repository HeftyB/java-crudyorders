package local.heftyb.javaorders.repositories;

import local.heftyb.javaorders.models.Customer;
import local.heftyb.javaorders.views.OrderCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long>
{
    List<Customer> findByCustnameContainingIgnoringCase (String name);

    @Query(value = "SELECT c.custname as name, count(o.ordnum) as countorders FROM customers c LEFT JOIN orders o ON c.custcode = o.custcode GROUP BY name ORDER BY countorders desc", nativeQuery = true)
    List<OrderCount> getOrderCounts();
}