package de.tuberlin.amos.gr2.entity;

import net.java.ao.Entity;
import net.java.ao.ManyToMany;
import net.java.ao.OneToMany;
import net.java.ao.schema.Default;
import net.java.ao.schema.Indexed;
import net.java.ao.schema.StringLength;
import net.java.ao.schema.Unique;

import java.util.Date;


public interface Requests extends Entity {

    enum Type
    {
        OPEN,
        CLOSED,
        CANCELLED
    }

    @Unique
    @Default("default")
    String getName();
    void setName(String name);

    @StringLength(StringLength.UNLIMITED)
    String getSummary();
    void setSummary(String summary);

    Type getType();
    void setType(Type type);

    @Indexed
    @Default("true")
    boolean getIsActive();
    void setIsActive(boolean isActive);

    Date getOpenDate();
    void setOpenDate(Date date);




}
