package com.kp.merchantonboardingservice.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "merchants")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    private String status;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "merchant", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "merchant"})
    private List<Product> products;

    public Merchant() {

    }

    public Merchant(int id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
