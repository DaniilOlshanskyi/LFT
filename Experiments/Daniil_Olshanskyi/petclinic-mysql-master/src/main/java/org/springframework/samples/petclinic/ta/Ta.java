package org.springframework.samples.petclinic.ta;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ta")
public class Ta {

    @Id
    @Column(name = "ta_id")
    private int ta_id;

    @Column(name = "ta_name")
    private String ta_name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ta_id")
    private List<Team> teamList;

    public int getTa_id() {
        return ta_id;
    }

    public void setTa_id(int ta_id) {
        this.ta_id = ta_id;
    }

    public String getTa_name() {
        return ta_name;
    }

    public void setTa_name(String ta_name) {
        this.ta_name = ta_name;
    }

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }
}
