package de.hsmannheim.igt.exercise.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "WAREHOUSE")
@Indexed(index = "indexes/warehouses")
public class Warehouse implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String W_ID;

    @ManyToOne
    @JoinColumn(name = "D_ID")
    private District W_DISTRICT;
    @OneToMany(orphanRemoval = true,cascade = CascadeType.MERGE)
    @OrderColumn(name = "ST_ID")
    private List<Stock> w_Stock;

    @Column
    private String W_CITY;
    @Column
    private String W_NAME;


    @Override
    public String toString() {
        return "Warehouse{" +
                "W_ID=" + W_ID +
                ", W_DISTRICT=" + W_DISTRICT +
                ", W_ITEMS=" + w_Stock +
                ", W_CITY='" + W_CITY + '\'' +
                ", W_NAME='" + W_NAME + '\'' +
                '}';
    }

    public District getW_DISTRICT() {
        return W_DISTRICT;
    }

    public void setW_DISTRICT(District w_DISTRICT) {
        W_DISTRICT = w_DISTRICT;
    }

    public List<Stock> getW_ITEMS() {
        return w_Stock;
    }

    public void setW_ITEMS(List<Stock> w_Stock) {
        this.w_Stock = w_Stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return Objects.equals(W_ID, warehouse.W_ID) &&
                Objects.equals(W_DISTRICT, warehouse.W_DISTRICT) &&
                Objects.equals(W_CITY, warehouse.W_CITY) &&
                Objects.equals(W_NAME, warehouse.W_NAME);
    }

    @Override
    public int hashCode() {

        return Objects.hash(W_ID, W_DISTRICT, W_CITY, W_NAME);
    }

    public Warehouse() {
    }

    public void addInventory(Stock stock) {
        if (this.w_Stock == null) {
            this.w_Stock = new ArrayList<>();
        }
        this.w_Stock.add(stock);
    }

    public String getW_ID() {
        return W_ID;
    }

    public void setW_ID(String w_ID) {
        W_ID = w_ID;
    }

    public District getW_District() {
        return W_DISTRICT;
    }

    public void setW_District(District w_district) {
        W_DISTRICT = w_district;
    }

    public String getW_CITY() {
        return W_CITY;
    }

    public void setW_CITY(String w_CITY) {
        W_CITY = w_CITY;
    }

    public String getW_NAME() {
        return W_NAME;
    }

    public void setW_NAME(String w_NAME) {
        W_NAME = w_NAME;
    }

    public boolean hasAllItemsInStock(OrderLine orderLine) {
        boolean[] instock = new boolean[orderLine.getO_ITEMS().size()];
        List<Stock> stock = this.getW_ITEMS();
        for (int i = 0; i < orderLine.getO_ITEMS().size(); i++) {
            for (Stock s : stock) {
                if (s.getST_ITEM_ID().equals(orderLine.getO_ITEMS().get(i).getST_ITEM_ID())) {
                    if (s.getST_ITEM_AMOUNT() >= orderLine.getO_ITEMS().get(i).getST_ITEM_AMOUNT()) {
                        instock[i] = true;
                    }
                }
            }
        }
        for (boolean b : instock) {
            if (b == false) {
                return false;
            }
        }
        return true;
    }

    public List<Stock> orderAllItems(Stock stock) {
        boolean existing = false;
        List<Stock> tempStock = new ArrayList<>();
        for (Stock i : getW_ITEMS()) {
            if (i.getST_ITEM_ID().equals(stock.getST_ITEM_ID())) {
                if (i.getST_ITEM_AMOUNT() <= stock.getST_ITEM_AMOUNT()) {
                    Stock temp = new Stock();
                    temp.setST_ITEM_AMOUNT(i.getST_ITEM_AMOUNT() + stock.getST_ITEM_AMOUNT());
                    temp.setST_ITEM_ID(i.getST_ITEM_ID());
                    tempStock.add(temp);
                }
                existing = true;
                break;
            }
        }
        if (!existing) {
            Stock i = new Stock();
            i.setST_ITEM_AMOUNT(stock.getST_ITEM_AMOUNT());
            i.setST_ITEM_ID(stock.getST_ITEM_ID());
            tempStock.add(i);
        }
        return tempStock;
    }
}
