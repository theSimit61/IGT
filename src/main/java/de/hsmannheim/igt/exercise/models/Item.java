package de.hsmannheim.igt.exercise.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "ITEM")
@Indexed(index="indexes/items")
public class Item implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String I_ID;

    @Column (unique=true)
    private String I_NAME;


    @Override
    public String toString() {
        return "Item{" +
                "I_ID=" + I_ID +
                ", I_NAME='" + I_NAME + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(I_NAME, item.I_NAME);
    }

    @Override
    public int hashCode() {

        return Objects.hash(I_NAME);
    }

    public String getI_ID() {
        return I_ID;
    }

    public void setI_ID(String i_ID) {
        I_ID = i_ID;
    }

    public String getI_NAME() {
        return I_NAME;
    }

    public void setI_NAME(String i_NAME) {
        I_NAME = i_NAME;
    }

    public Item() {
    }
}
