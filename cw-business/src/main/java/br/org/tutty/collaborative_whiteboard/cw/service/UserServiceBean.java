package br.org.tutty.collaborative_whiteboard.cw.service;

import br.org.tutty.collaborative_whiteboard.UserDao;
import br.org.tutty.collaborative_whiteboard.cw.context.SessionContext;
import cw.entities.User;
import cw.exceptions.DataNotFoundException;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * Created by drferreira on 16/12/14.
 */
@Stateless
@Local(UserService.class)
public class UserServiceBean implements UserService, Serializable {

    @Inject
    private UserDao userDao;
    @Inject
    private SessionContext sessionContext;

    @Override
    public User fetch(String email) throws DataNotFoundException {
        return userDao.fetch(email);
    }

    @Override
    public Boolean isAlreadyRegistered(String email) {
        try {
            fetch(email);
            return Boolean.TRUE;

        } catch (DataNotFoundException e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean hasSomeProject() {
        User user = sessionContext.getLoggedUser().getUser();
        return user.hasSomeProject();
    }

    @Override
    public void create(User user) {
        userDao.persist(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }
}
