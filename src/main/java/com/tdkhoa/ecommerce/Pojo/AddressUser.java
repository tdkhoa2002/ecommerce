/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.ecommerce.Pojo;

import java.io.Serializable;
import jakarta.persistence.*;

/**
 *
 * @author Khoa Tran
 */
@Entity
@Table(name = "address_user")
@NamedQueries({
    @NamedQuery(name = "AddressUser.findAll", query = "SELECT a FROM AddressUser a"),
    @NamedQuery(name = "AddressUser.findById", query = "SELECT a FROM AddressUser a WHERE a.addressUserPK.id = :id"),
    @NamedQuery(name = "AddressUser.findByAddressId", query = "SELECT a FROM AddressUser a WHERE a.addressUserPK.addressId = :addressId"),
    @NamedQuery(name = "AddressUser.findByUserId", query = "SELECT a FROM AddressUser a WHERE a.addressUserPK.userId = :userId")})
public class AddressUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AddressUserPK addressUserPK;
    @JoinColumn(name = "address_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Address address;
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public AddressUser() {
    }

    public AddressUser(AddressUserPK addressUserPK) {
        this.addressUserPK = addressUserPK;
    }

    public AddressUser(int id, int addressId, int userId) {
        this.addressUserPK = new AddressUserPK(id, addressId, userId);
    }

    public AddressUserPK getAddressUserPK() {
        return addressUserPK;
    }

    public void setAddressUserPK(AddressUserPK addressUserPK) {
        this.addressUserPK = addressUserPK;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (addressUserPK != null ? addressUserPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AddressUser)) {
            return false;
        }
        AddressUser other = (AddressUser) object;
        if ((this.addressUserPK == null && other.addressUserPK != null) || (this.addressUserPK != null && !this.addressUserPK.equals(other.addressUserPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tdkhoa.ecommerce.Pojo.AddressUser[ addressUserPK=" + addressUserPK + " ]";
    }
    
}
