package org.springframework.samples.petclinic.owner;

import com.sun.istack.internal.NotNull;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "city")
public class City {

    @Id
    @Column(name = "city")
    @NotFound(action = NotFoundAction.IGNORE)
    private String city;

    @Column(name = "state")
    @NotFound(action = NotFoundAction.IGNORE)
    private String state;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
