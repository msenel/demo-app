package me.senel.demo.model.dao.impl;

import me.senel.demo.model.User;
import me.senel.demo.model.dao.UserDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {


    @Override
    @Transactional(readOnly = true)
    public User getByEmail(String email) {

            return (User) getSession().getNamedQuery("get.user.by.email")
                    .setString("email", email)
                    .setMaxResults(1)
                    .uniqueResult();

    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return getSession().getNamedQuery("get.all.users")
                .list();
    }

}
