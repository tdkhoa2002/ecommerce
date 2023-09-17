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
@Embeddable
public class AddressUserPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Basic(optional = false)
    @Column(name = "address_id")
    private int addressId;
    @Basic(optional = false)
    @Column(name = "user_id")
    private int userId;

    public AddressUserPK() {
    }

    public AddressUserPK(int id, int addressId, int userId) {
        this.id = id;
        this.addressId = addressId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) addressId;
        hash += (int) userId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AddressUserPK)) {
            return false;
        }
        AddressUserPK other = (AddressUserPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.addressId != other.addressId) {
            return false;
        }
        if (this.userId != other.userId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tdkhoa.ecommerce.Pojo.AddressUserPK[ id=" + id + ", addressId=" + addressId + ", userId=" + userId + " ]";
    }
    
}
