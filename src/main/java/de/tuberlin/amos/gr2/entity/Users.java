package de.tuberlin.amos.gr2.entity;

import net.java.ao.Entity;
import net.java.ao.OneToMany;
import net.java.ao.OneToOne;

import java.util.Calendar;

// in H2 DATABASE, the table name is AO_(hash)_USERS
public interface Users extends Entity{

    String getName();
    void setName(String name);

    @OneToMany
    Requests[] getRequests();

    @OneToOne
    PersonalCalendar getPersonalCalendar();

}
