package fr.istic.sir.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A House.
 */
@Entity
@Table(name = "house")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class House implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "size")
    private Integer size;
    
    @Column(name = "nb_room")
    private Integer nbRoom;
    
    @OneToMany(mappedBy = "house")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Person> inhabitants = new HashSet<>();

    @OneToMany(mappedBy = "house")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Device> devices = new HashSet<>();

    @OneToMany(mappedBy = "house")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Heater> heaters = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }
    
    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getNbRoom() {
        return nbRoom;
    }
    
    public void setNbRoom(Integer nbRoom) {
        this.nbRoom = nbRoom;
    }

    public Set<Person> getInhabitants() {
        return inhabitants;
    }

    public void setInhabitants(Set<Person> persons) {
        this.inhabitants = persons;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    public Set<Heater> getHeaters() {
        return heaters;
    }

    public void setHeaters(Set<Heater> heaters) {
        this.heaters = heaters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        House house = (House) o;
        if(house.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, house.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "House{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", size='" + size + "'" +
            ", nbRoom='" + nbRoom + "'" +
            '}';
    }
}
