package de.hsmannheim.igt.exercise.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "ORDERLINE")
@Indexed(index="indexes/orderlines")
public class OrderLine implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String O_ID;

    @OneToMany (cascade = {CascadeType.ALL})
    @OrderColumn(name = "I_ID")
    private List<Stock> O_ITEMS;

    @ManyToOne
    @JoinColumn(name="C_ID")
    private Customer O_CUSTOMER;

    @Column
    private boolean O_NEWORDER;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLine orderLine = (OrderLine) o;
        return Objects.equals(O_ID, orderLine.O_ID) &&
                Objects.equals(O_CUSTOMER, orderLine.O_CUSTOMER);
    }

    @Override
    public int hashCode() {
        return Objects.hash(O_ID, O_CUSTOMER);
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "O_ID=" + O_ID +
                ", O_ITEMS=" + O_ITEMS +
                ", O_CUSTOMER=" + O_CUSTOMER +
                ", O_NEWORDER=" + O_NEWORDER +
                '}';
    }

    public String getO_ID() {
        return O_ID;
    }

    public void setO_ID(String o_ID) {
        O_ID = o_ID;
    }

    public List<Stock> getO_ITEMS() {
        return O_ITEMS;
    }

    public void setO_ITEMS(List<Stock> o_ITEMS) {
        O_ITEMS = o_ITEMS;
    }

    public Customer getO_CUSTOMER() {
        return O_CUSTOMER;
    }

    public void setO_CUSTOMER(Customer o_CUSTOMER) {
        O_CUSTOMER = o_CUSTOMER;
    }

    public boolean isO_NEWORDER() {
        return O_NEWORDER;
    }

    public void setO_NEWORDER(boolean o_NEWORDER) {
        O_NEWORDER = o_NEWORDER;
    }
    public OrderLine() {
    }
}
