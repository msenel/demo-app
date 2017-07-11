package me.senel.demo.service.impl;

import me.senel.demo.model.User;
import me.senel.demo.model.dao.UserDao;
import me.senel.demo.service.UserService;
import me.senel.demo.service.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory
            .getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;


    @Override
    public User getUser(String id) throws UserNotFoundExeption, DataAccessException {
        User user = null;
        try {
            user = userDao.get(id);
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }

        if (user == null) {
            throw new UserNotFoundExeption(String.format(
                    "User with id %s does not exist", id));
        }

        return user;
    }

    @Override
    public void save(User user) throws DataSaveException {
        try {
            userDao.save(user);
        } catch (Exception ex) {
            throw new DataSaveException(ex.getMessage());
        }

    }

    @Override
    public User update(String userEmail, String firstName, String lastName, String email, String accessToken,
                       Long dateOfBirthMillis) throws DataAccessException, UserNotFoundExeption, DataSaveException {

        User user = getByEmail(userEmail);


        logger.debug("Trying to update user {} with id {}: firstName {}, lastName {}, email {}", user.getEmail(),
                user.getId(), firstName, lastName, email);

        if (!StringUtils.isEmpty(firstName)) {
            user.setFirstName(firstName);
        }

        if (!StringUtils.isEmpty(lastName)) {
            user.setLastName(lastName);
        }

        if (!StringUtils.isEmpty(email)) {
            user.setEmail(email);
        }

        if (!StringUtils.isEmpty(accessToken)) {
            user.setAccessToken(new BCryptPasswordEncoder().encode(accessToken));
        }

        if(dateOfBirthMillis != null) {
            user.setDateOfBirthMillis(dateOfBirthMillis);
        }

        save(user);

        logger.info("User {} with id {} has been updated", user.getEmail(), user.getId());

        return user;
    }

    @Override
    public List<User> getAll() throws DataAccessException {
        return userDao.getAll();
    }

    @Override
    public User register(String firstName, String lastName, String password, String email, Long dateOfBirthMillis)
            throws BadArgumentException, AlreadyExistsException, DataSaveException {

        checkIfUserExists(firstName, lastName, password, email);

        logger.debug("Trying to new create user: firstName {}, lastName {}, email {}", firstName, lastName, email);

        User user = createAndSaveUser(firstName, lastName, password, email, dateOfBirthMillis);

        logger.info("User {} with id {} has been created", user.getEmail(), user.getId());

        return user;
    }

    @Override
    public User getByEmail(String email) throws DataAccessException, UserNotFoundExeption {

        User user = null;

        try {
            user = userDao.getByEmail(email);
        } catch (Exception ex) {
            throw new DataAccessException(ex.getMessage());
        }

        if (user == null) {
            throw new UserNotFoundExeption(String.format(
                    "User with email %s does not exist", email));
        }

        return user;
    }

    @Override
    public void delete(String userId) throws DataAccessException, UserNotFoundExeption, DataSaveException {
        User user = getUser(userId);
        try {
            userDao.delete(user);
        } catch (Exception ex) {
            throw new DataSaveException(ex.getMessage());
        }
    }

    private void checkIfUserExists(String firstName, String lastName, String password, String email)
            throws BadArgumentException, AlreadyExistsException {

        if (StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(email) || !email.contains("@")) {
            throw new BadArgumentException("Some of the required parameters to register a user is missing");
        }

        if (userDao.getByEmail(email) != null) {
            throw new AlreadyExistsException("User with given email address already exists.");
        }
    }

    private User createAndSaveUser(String firstName, String lastName, String password, String email,
                                   long dateOfBirthMillis) throws DataSaveException {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setAccessToken(new BCryptPasswordEncoder().encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setDateOfBirthMillis(dateOfBirthMillis);
        user.setRole(1);

        save(user);

        return user;
    }

}
