package de.tuberlin.amos.gr2.service.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import de.tuberlin.amos.gr2.entity.Users;
import de.tuberlin.amos.gr2.service.UserService;
import net.java.ao.Query;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class UserServiceImpl implements UserService {

    @ComponentImport
    private final ActiveObjects ao;

    @Inject
    public UserServiceImpl(ActiveObjects ao)
    {
        this.ao = ao;
    }

    @Override
    public Users add(String name) {
        final Users user = ao.create(Users.class);
        user.setName(name);
        user.save();
        return user;
    }

    @Override
    public void delete(int ID) {
        Users[] users = this.ao.find(
                Users.class,
                Query.select().where("ID = ?", ID)
        );

        for (Users user : users) {
            this.ao.delete(users);
        }

    }

    @Override
    public Users[] getAllUsers() {
        Users[] users = this.ao.find(
                Users.class,
                Query.select()
        );

        return users;
    }
}
