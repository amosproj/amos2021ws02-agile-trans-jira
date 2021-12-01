package de.tuberlin.amos.gr2.service;

import com.atlassian.activeobjects.tx.Transactional;
import de.tuberlin.amos.gr2.entity.Users;

@Transactional
public interface UserService {

    Users add(String name);
    void delete(int ID);

    Users[] getAllUsers();
}
