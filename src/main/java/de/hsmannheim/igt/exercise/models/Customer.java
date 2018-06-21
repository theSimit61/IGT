package de.hsmannheim.igt.exercise.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "CUSTOMER")
@Indexed(index="indexes/customers")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String C_ID;
    @Column
    private String C_NAME;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(C_ID, customer.C_ID);
    }

    @Override
    public int hashCode() {

        return Objects.hash(C_ID);
    }

    @ManyToOne
    @JoinColumn(name="D_ID")
    private District C_DISTRICT;

    public Customer() {
    }

    @Override
    public String toString() {
        return "Customer{" +
                "C_ID=" + C_ID +
                ", C_NAME='" + C_NAME + '\'' +
                ", C_DISTRICT=" + C_DISTRICT +
                '}';
    }

    public String getC_ID() {
        return C_ID;
    }

    public void setC_ID(String c_ID) {
        C_ID = c_ID;
    }

    public String getC_NAME() {
        return C_NAME;
    }

    public void setC_NAME(String c_NAME) {
        C_NAME = c_NAME;
    }

    public District getC_DISTRICT() {
        return C_DISTRICT;
    }

    public void setC_DISTRICT(District c_DISTRICT) {
        C_DISTRICT = c_DISTRICT;
    }
}
