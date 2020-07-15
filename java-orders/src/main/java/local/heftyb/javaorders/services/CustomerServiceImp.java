package local.heftyb.javaorders.services;

import local.heftyb.javaorders.models.Customer;
import local.heftyb.javaorders.models.Order;
import local.heftyb.javaorders.models.Payment;
import local.heftyb.javaorders.repositories.CustomerRepository;
import local.heftyb.javaorders.views.OrderCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class CustomerServiceImp implements CustomerService
{
    @Autowired
    private CustomerRepository custrepo;

    @Override
    public List<Customer> findAllCustomers()
    {
        List<Customer> customerList = new ArrayList<>();
        custrepo.findAll().iterator().forEachRemaining(customerList::add);
        return customerList;
    }

    @Override
    public Customer findCustomerById(long id) throws EntityNotFoundException
    {
        return custrepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found"));
    }

    @Override
    public List<Customer> findByNameLike(String name)
    {
        ArrayList<Customer> customerList = (ArrayList<Customer>) custrepo.findByCustnameContainingIgnoringCase(name);
        return customerList;
    }

    @Override
    public List<OrderCount> getOrderCounts()
    {
        return custrepo.getOrderCounts();
    }


    @Transactional
    @Override
    public Customer save(Customer customer)
    {
        Customer newCustomer = new Customer();

        if (customer.getCustcode() != 0)
        {
            custrepo.findById(customer.getCustcode())
                .orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " Not Found!"));
            newCustomer.setCustcode(customer.getCustcode());
        }

        newCustomer.setAgent(customer.getAgent());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setCustname(customer.getCustname());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPhone(customer.getPhone());

        for (Order o :
            customer.getOrders())
        {
            Order newOrder = new Order(o.getOrdamount(), o.getAdvanceamount(),
            newCustomer,
            o.getOrderdescription());
            for (Payment p :
                o.getPayments())
            {
                newOrder.addPayments(p);
            }
            newCustomer.addOrder(newOrder);
        }

        return custrepo.save(newCustomer);
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        if (custrepo.findById(id).isPresent())
        {
            custrepo.deleteById(id);
        } else
        {
            throw new   EntityNotFoundException("Customer " + id + " Not Found!");
        }

    }

    @Transactional
    @Override
    public Customer update(
        Customer customer,
        long id)
    {
        Customer currentCustomer = custrepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " Not Found!"));

        if (customer.getAgent() != null)
        {
            currentCustomer.setAgent(customer.getAgent());
        }

        if (customer.getCustcity() != null)
        {
            currentCustomer.setCustcity(customer.getCustcity());
        }

        if (customer.getCustcountry() != null)
        {
            currentCustomer.setCustcountry(customer.getCustcountry());
        }

        if (customer.getCustname() != null)
        {
            currentCustomer.setCustname(customer.getCustname());
        }

        if (customer.getWorkingarea() != null)
        {
            currentCustomer.setWorkingarea(customer.getWorkingarea());
        }

        if (customer.getGrade() != null)
        {
            currentCustomer.setGrade(customer.getGrade());
        }

        if (customer.hasvalueforopeningamt)
        {
            currentCustomer.setOpeningamt(customer.getOpeningamt());
        }

        if (customer.hasvalueforrecieveamt)
        {
            currentCustomer.setReceiveamt(customer.getReceiveamt());
        }

        if (customer.hasvalueforpaymentamt)
        {
            currentCustomer.setPaymentamt(customer.getPaymentamt());
        }

        if (customer.hasvalueforoutstandingamt)
        {
            currentCustomer.setOutstandingamt(customer.getOutstandingamt());
        }

        if (customer.getPhone() != null)
        {
            currentCustomer.setPhone(customer.getPhone());
        }

        if (customer.getOrders().size() > 0)
        {
            for (Order o :
                customer.getOrders())
            {
                Order newOrder = new Order(o.getOrdamount(),
                    o.getAdvanceamount(),
                    currentCustomer,
                    o.getOrderdescription());
                for (Payment p :
                    o.getPayments())
                {
                    newOrder.addPayments(p);
                }
                currentCustomer.addOrder(newOrder);
            }
        }

        return custrepo.save(currentCustomer);
    }
}
