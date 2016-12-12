/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPA;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Antonio
 */
@Entity
@Table(name = "amusers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Amusers.findAll", query = "SELECT a FROM Amusers a")
    , @NamedQuery(name = "Amusers.findByIdUser", query = "SELECT a FROM Amusers a WHERE a.idUser = :idUser")
    , @NamedQuery(name = "Amusers.findByNameUser", query = "SELECT a FROM Amusers a WHERE a.nameUser = :nameUser")
    , @NamedQuery(name = "Amusers.findByPasswordUser", query = "SELECT a FROM Amusers a WHERE a.passwordUser = :passwordUser")
    , @NamedQuery(name = "Amusers.findByEmail", query = "SELECT a FROM Amusers a WHERE a.email = :email")})
public class Amusers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_user")
    private Integer idUser;
    @Basic(optional = false)
    @Column(name = "name_user")
    private String nameUser;
    @Basic(optional = false)
    @Column(name = "password_user")
    private String passwordUser;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private List<Amregistry> amregistryList;

    public Amusers() {
    }

    public Amusers(Integer idUser) {
        this.idUser = idUser;
    }

    public Amusers(Integer idUser, String nameUser, String passwordUser, String email) {
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.passwordUser = passwordUser;
        this.email = email;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public List<Amregistry> getAmregistryList() {
        return amregistryList;
    }

    public void setAmregistryList(List<Amregistry> amregistryList) {
        this.amregistryList = amregistryList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Amusers)) {
            return false;
        }
        Amusers other = (Amusers) object;
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JPA.Amusers[ idUser=" + idUser + " ]";
    }
    
}
