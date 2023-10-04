/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.Pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
import jakarta.persistence.*;

/**
 *
 * @author Khoa Tran
 */
@Entity
@Table(name = "shop")
@NamedQueries({
    @NamedQuery(name = "Shop.findAll", query = "SELECT s FROM Shop s"),
    @NamedQuery(name = "Shop.findById", query = "SELECT s FROM Shop s WHERE s.id = :id"),
    @NamedQuery(name = "Shop.findByName", query = "SELECT s FROM Shop s WHERE s.name = :name"),
    @NamedQuery(name = "Shop.findByAddress", query = "SELECT s FROM Shop s WHERE s.address = :address"),
    @NamedQuery(name = "Shop.findByImageUrl", query = "SELECT s FROM Shop s WHERE s.imageUrl = :imageUrl"),
    @NamedQuery(name = "Shop.findByStatus", query = "SELECT s FROM Shop s WHERE s.status = :status")})
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "address")
    private String address;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "status")
    private Integer status;
    @JsonIgnore
    @OneToMany(mappedBy = "shopId")
    private Set<Product> productSet;
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shopId")
    private Set<Orderdetail> orderdetailSet;

    public Shop() {
    }

    public Shop(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<Product> getProductSet() {
        return productSet;
    }

    public void setProductSet(Set<Product> productSet) {
        this.productSet = productSet;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Set<Orderdetail> getOrderdetailSet() {
        return orderdetailSet;
    }

    public void setOrderdetailSet(Set<Orderdetail> orderdetailSet) {
        this.orderdetailSet = orderdetailSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Shop)) {
            return false;
        }
        Shop other = (Shop) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tdkhoa.ecommerce.Pojo.Shop[ id=" + id + " ]";
    }
    
}
