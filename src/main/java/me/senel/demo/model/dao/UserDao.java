package me.senel.demo.model.dao;


import me.senel.demo.model.User;

import java.util.List;

public interface UserDao  extends GenericDao<User> {

    public User getByEmail(String email);

    public List<User> getAll();

}
