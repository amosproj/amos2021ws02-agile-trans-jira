package de.tuberlin.amos.gr2.entity;

import net.java.ao.Entity;
import net.java.ao.OneToMany;

public interface Teams extends Entity {

    String getTeamLeader();
    void setTeamLeader(String team_leader);

    @OneToMany
    Users[] getUsers();

}
