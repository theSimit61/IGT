package de.hsmannheim.igt.exercise.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "Stock")
@Indexed(index="indexes/inventories")
public class Stock implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String ST_ID;

    @JoinColumn(name="I_ID")
    @ManyToOne
    private Item ST_ITEM_ID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return
                Objects.equals(ST_ID, stock.ST_ID) &&
                Objects.equals(ST_ITEM_ID, stock.ST_ITEM_ID);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ST_ID, ST_ITEM_ID);
    }

    @Column
    private int ST_ITEM_AMOUNT;


    @Override
    public String toString() {
        return "Stock{" +
                "ST_ID=" + ST_ID +
                ", ST_ITEM_ID=" + ST_ITEM_ID +
                ", ST_ITEM_AMOUNT=" + ST_ITEM_AMOUNT +
                '}';
    }

    public String getST_ID() {
        return ST_ID;
    }

    public void setST_ID(String ST_ID) {
        this.ST_ID = ST_ID;
    }

    public Item getST_ITEM_ID() {
        return ST_ITEM_ID;
    }

    public void setST_ITEM_ID(Item ST_ITEM_ID) {
        this.ST_ITEM_ID = ST_ITEM_ID;
    }

    public int getST_ITEM_AMOUNT() {
        return ST_ITEM_AMOUNT;
    }

    public void setST_ITEM_AMOUNT(int ST_ITEM_AMOUNT) {
        this.ST_ITEM_AMOUNT = ST_ITEM_AMOUNT;
    }

    public Stock() {
    }
}
