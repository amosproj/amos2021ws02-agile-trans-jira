package de.tuberlin.amos.gr2.entity;

import net.java.ao.Entity;
import net.java.ao.ManyToMany;
import net.java.ao.schema.Table;

//if the table name is long than 30 characters, we should use @table to customize the table name

@Table("CUSTOM_NAME")
public interface NameLongerThanThirtyCharacters extends Entity{

    String getName();
    void setName(String name);
}
