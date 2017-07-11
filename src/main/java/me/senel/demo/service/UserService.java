package me.senel.demo.service;


import me.senel.demo.model.User;
import me.senel.demo.service.exception.*;

import java.util.List;

public interface UserService {

    public User getUser(String id) throws UserNotFoundExeption, DataAccessException;

    public void save(User user) throws DataSaveException;

    public User update(String userEmail, String firstName, String lastName, String email, String accessToken,
                       Long dateOfBirthMillis) throws DataAccessException, UserNotFoundExeption, DataSaveException;

    public List<User> getAll() throws  DataAccessException;

    public User register(String firstName, String lastName, String password, String email, Long dateOfBirthMillis)
            throws BadArgumentException, AlreadyExistsException, DataSaveException;

    public User getByEmail(String email) throws DataAccessException, UserNotFoundExeption;

    public void delete(String userId) throws DataAccessException, UserNotFoundExeption, DataSaveException;

}
