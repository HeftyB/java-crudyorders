package local.heftyb.javaorders.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@JsonIgnoreProperties(value = {"hasvalueforordamount", "hasvalueforadvanceamount"})
public class Order
{
    // ordnum, ordamount, advanceamount, custcode, orderdescription

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ordnum;

    @Transient
    public boolean hasvalueforordamount = false;
    @Column(nullable = false)
    private double  ordamount;

    @Transient
    public boolean hasvalueforadvanceamount = false;
    private double advanceamount;


    private String orderdescription;


    @ManyToMany()
    @JoinTable(name = "orderspayments",
        joinColumns = @JoinColumn(name = "ordnum"),
        inverseJoinColumns = @JoinColumn(name = "paymentid"))
    @JsonIgnoreProperties(value = "orders")
    List<Payment> payments = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "custcode",
        nullable = false)
    @JsonIgnoreProperties(value = "orders")
    private Customer customer;


    public Order()
    {
    }

    public Order(
        double ordamount,
        double advanceamount,
        Customer customer,
        String orderdescription)
    {
        this.ordamount = ordamount;
        this.advanceamount = advanceamount;
        this.customer = customer;
        this.orderdescription = orderdescription;
    }

    public long getOrdnum()
    {
        return ordnum;
    }

    public void setOrdnum(long ordnum)
    {
        hasvalueforordamount = true;
        this.ordnum = ordnum;
    }

    public double getOrdamount()
    {
        return ordamount;
    }

    public void setOrdamount(double ordamount)
    {
        this.ordamount = ordamount;
    }

    public double getAdvanceamount()
    {
        return advanceamount;
    }

    public void setAdvanceamount(double advanceamount)
    {
        hasvalueforadvanceamount = true;
        this.advanceamount = advanceamount;
    }

    public String getOrderdescription()
    {
        return orderdescription;
    }

    public void setOrderdescription(String orderdescription)
    {
        this.orderdescription = orderdescription;
    }

    public List<Payment> getPayments()
    {
        return payments;
    }

    public void setPayments(List<Payment> payments)
    {
        this.payments = payments;
    }

    public void addPayments (Payment payment)
    {
        payments.add(payment);
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    @Override
    public String toString()
    {
        return "Order{" +
            "ordnum=" + ordnum +
            ", ordamount=" + ordamount +
            ", advanceamount=" + advanceamount +
            ", orderdescription='" + orderdescription + '\'' +
            ", payments=" + payments +
            ", customer=" + customer +
            '}';
    }
}
