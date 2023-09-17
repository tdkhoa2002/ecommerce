/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.Pojo;

import java.io.Serializable;
import java.util.Set;
import jakarta.persistence.*;

/**
 *
 * @author Khoa Tran
 */
@Entity
@Table(name = "voucher")
@NamedQueries({
    @NamedQuery(name = "Voucher.findAll", query = "SELECT v FROM Voucher v"),
    @NamedQuery(name = "Voucher.findById", query = "SELECT v FROM Voucher v WHERE v.id = :id"),
    @NamedQuery(name = "Voucher.findByQuantity", query = "SELECT v FROM Voucher v WHERE v.quantity = :quantity"),
    @NamedQuery(name = "Voucher.findByName", query = "SELECT v FROM Voucher v WHERE v.name = :name"),
    @NamedQuery(name = "Voucher.findByCode", query = "SELECT v FROM Voucher v WHERE v.code = :code"),
    @NamedQuery(name = "Voucher.findByValue", query = "SELECT v FROM Voucher v WHERE v.value = :value"),
    @NamedQuery(name = "Voucher.findByIsDeleted", query = "SELECT v FROM Voucher v WHERE v.isDeleted = :isDeleted")})
public class Voucher implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "value")
    private Integer value;
    @Column(name = "is_deleted")
    private Integer isDeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "voucherId")
    private Set<Order1> order1Set;

    public Voucher() {
    }

    public Voucher(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Set<Order1> getOrder1Set() {
        return order1Set;
    }

    public void setOrder1Set(Set<Order1> order1Set) {
        this.order1Set = order1Set;
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
        if (!(object instanceof Voucher)) {
            return false;
        }
        Voucher other = (Voucher) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tdkhoa.ecommerce.Pojo.Voucher[ id=" + id + " ]";
    }
    
}
