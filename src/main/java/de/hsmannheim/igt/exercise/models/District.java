package de.hsmannheim.igt.exercise.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "DISTRICT")
@Indexed(index="indexes/districts")

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class District implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String D_ID;
    @Column
    private String D_CITY;
    @Column (unique=true)
    private String D_NAME;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        District district = (District) o;
        return Objects.equals(D_ID, district.D_ID) &&
                Objects.equals(D_CITY, district.D_CITY) &&
                Objects.equals(D_NAME, district.D_NAME);
    }

    @Override
    public int hashCode() {

        return Objects.hash(D_ID, D_CITY, D_NAME);
    }

    public District() {
    }

    public String getD_ID() {
        return D_ID;
    }

    public void setD_ID(String d_ID) {
        D_ID = d_ID;
    }

    public String getD_CITY() {
        return D_CITY;
    }

    public void setD_CITY(String d_CITY) {
        D_CITY = d_CITY;
    }

    @Override
    public String toString() {
        return "District{" +
                "D_ID=" + D_ID +
                ", D_CITY='" + D_CITY + '\'' +
                ", D_NAME='" + D_NAME + '\'' +
                '}';
    }

    public String getD_NAME() {
        return D_NAME;
    }

    public void setD_NAME(String d_NAME) {
        D_NAME = d_NAME;
    }

}
