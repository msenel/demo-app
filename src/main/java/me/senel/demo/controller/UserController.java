package me.senel.demo.controller;


import com.fasterxml.jackson.annotation.JsonView;
import me.senel.demo.model.User;
import me.senel.demo.service.UserService;
import me.senel.demo.service.exception.*;
import me.senel.demo.util.json.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@RestController
@RequestMapping("api")
public class UserController extends AbstractController {


    private static final Logger logger = LoggerFactory
            .getLogger(UserController.class);


    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/me", method = RequestMethod.GET)
    @JsonView(View.Public.class)
    public ResponseEntity<User> getOwnInformation() {
        User user = null;
        try {
            user = userService.getByEmail(getPrincipal());
        } catch (DataAccessException ex) {
            logger.error("Unable to fetch user with email {}", getPrincipal());
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UserNotFoundExeption ex) {
            logger.error("User with email {} does not exist", getPrincipal());
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "register", method = RequestMethod.PUT)
    public ResponseEntity<User> registerUser(
            @RequestParam(value = "firstName", required = true) String firstName,
            @RequestParam(value = "lastName", required = true) String lastName,
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "dateOfBirthMillis", required = false) Long dateOfBirthMillis
    ) {
        User user = null;

        try {
            user = userService.register(firstName, lastName, password, email, dateOfBirthMillis);
        } catch (BadArgumentException e) {
            logger.error("At least on parameter is missing or in wrong format. Parameters: {}, {}, {}",
                    firstName, lastName, email);
            return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
        } catch (AlreadyExistsException e) {
            logger.error("Given email address already exists: {}", e);
            return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
        } catch (DataSaveException e) {
            logger.error("Unable to create new user {}", e);
            return new ResponseEntity<User>(user, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public ResponseEntity<User> updateUser(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "dateOfBirthMillis", required = false) Long dateOfBirthMillis
    ) {
        User user = null;

        try {
            user = userService.update(getPrincipal(), firstName, lastName, email, password, dateOfBirthMillis);
        } catch (DataAccessException ex) {
            logger.error("Unable to fetch user with email {}", getPrincipal());
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UserNotFoundExeption ex) {
            logger.error("User with email {} does not exist", getPrincipal());
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        } catch (DataSaveException ex) {
            logger.error("Unable to update user {}", ex);
            return new ResponseEntity<User>(user, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @JsonView(View.Public.class)
    public ResponseEntity<User> getUser(@PathVariable(value = "id") String id) {
        User user = null;
        try {
            user = userService.getUser(id);
        } catch (DataAccessException ex) {
            logger.error("Unable to fetch user with id {}", id);
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UserNotFoundExeption ex) {
            logger.error("User with id {} does not exist",id);
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }


    @RequestMapping(value = "/admin/user", method = RequestMethod.GET)
    @JsonView(View.Private.class)
    public ResponseEntity<List<User>> getAll() {

        List<User> userList = null;

        try {
            userList = userService.getAll();
        } catch (DataAccessException ex) {
            logger.error("Unable to fetch users");
            return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        if (userList == null || userList.size() == 0) {
            logger.error("No user exists");
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        }


        return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
    }


    @RequestMapping(value = "/admin/user", method = RequestMethod.DELETE)
    @JsonView(View.Private.class)
    public ResponseEntity<Void> deleteUser(
            @RequestParam(value = "id", required = false) String id
    ) {

        try {
            userService.delete(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (DataAccessException ex) {
            logger.error("Unable to fetch user with id {}", id);
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UserNotFoundExeption ex) {
            logger.error("User with email {} does not exist", getPrincipal());
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } catch (DataSaveException ex) {
            logger.error("Unable to delete user {}", ex);
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @RequestMapping(value = "/admin/user/update", method = RequestMethod.POST)
    public ResponseEntity<User> updateSpecifiedUser(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "dateOfBirthMillis", required = false) Long dateOfBirthMillis
    ) {
        User user = null;

        try {
            user = userService.update(id, firstName, lastName, email, password, dateOfBirthMillis);
        } catch (DataAccessException ex) {
            logger.error("Unable to fetch user with email {}", id);
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UserNotFoundExeption ex) {
            logger.error("User with email {} does not exist", id);
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        } catch (DataSaveException ex) {
            logger.error("Unable to update user {}", ex);
            return new ResponseEntity<User>(user, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }


    @RequestMapping(value = "/admin/register", method = RequestMethod.PUT)
    public ResponseEntity<User> addUser(
            @RequestParam(value = "firstName", required = true) String firstName,
            @RequestParam(value = "lastName", required = true) String lastName,
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "dateOfBirthMillis", required = false) Long dateOfBirthMillis
    ) {
        User user = null;

        try {
            user = userService.register(firstName, lastName, password, email, dateOfBirthMillis);
        } catch (BadArgumentException e) {
            logger.error("At least on parameter is missing or in wrong format. Parameters: {}, {}, {}",
                    firstName, lastName, email);
            return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
        } catch (AlreadyExistsException e) {
            logger.error("Given email address already exists: {}", e);
            return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
        } catch (DataSaveException e) {
            logger.error("Unable to create new user {}", e);
            return new ResponseEntity<User>(user, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

}

