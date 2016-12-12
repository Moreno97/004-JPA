/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPA;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Antonio
 */
@Entity
@Table(name = "amregistry")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Amregistry.findAll", query = "SELECT a FROM Amregistry a")
    , @NamedQuery(name = "Amregistry.findByIdPlay", query = "SELECT a FROM Amregistry a WHERE a.idPlay = :idPlay")
    , @NamedQuery(name = "Amregistry.findByStartGame", query = "SELECT a FROM Amregistry a WHERE a.startGame = :startGame")
    , @NamedQuery(name = "Amregistry.findByEndGame", query = "SELECT a FROM Amregistry a WHERE a.endGame = :endGame")
    , @NamedQuery(name = "Amregistry.findBySpeed", query = "SELECT a FROM Amregistry a WHERE a.speed = :speed")})
public class Amregistry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_play")
    private Integer idPlay;
    @Basic(optional = false)
    @Column(name = "start_game")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startGame;
    @Basic(optional = false)
    @Column(name = "end_game")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endGame;
    @Basic(optional = false)
    @Column(name = "speed")
    private float speed;
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    private Amusers idUser;

    public Amregistry() {
    }

    public Amregistry(Integer idPlay) {
        this.idPlay = idPlay;
    }

    public Amregistry(Integer idPlay, Date startGame, Date endGame, float speed) {
        this.idPlay = idPlay;
        this.startGame = startGame;
        this.endGame = endGame;
        this.speed = speed;
    }

    public Integer getIdPlay() {
        return idPlay;
    }

    public void setIdPlay(Integer idPlay) {
        this.idPlay = idPlay;
    }

    public Date getStartGame() {
        return startGame;
    }

    public void setStartGame(Date startGame) {
        this.startGame = startGame;
    }

    public Date getEndGame() {
        return endGame;
    }

    public void setEndGame(Date endGame) {
        this.endGame = endGame;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Amusers getIdUser() {
        return idUser;
    }

    public void setIdUser(Amusers idUser) {
        this.idUser = idUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlay != null ? idPlay.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Amregistry)) {
            return false;
        }
        Amregistry other = (Amregistry) object;
        if ((this.idPlay == null && other.idPlay != null) || (this.idPlay != null && !this.idPlay.equals(other.idPlay))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JPA.Amregistry[ idPlay=" + idPlay + " ]";
    }
    
}
