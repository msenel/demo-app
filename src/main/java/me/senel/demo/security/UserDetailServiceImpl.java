package me.senel.demo.security;


import me.senel.demo.model.User;
import me.senel.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory
            .getLogger(UserDetailServiceImpl.class);

    @Autowired
    private UserService userService;

    /**
     * Returns a populated {@link UserDetails} object. The username is first
     * retrieved from the database and then mapped to a {@link UserDetails}
     * object.
     */
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        try {
            logger.debug("Trying to authenticate user: {}", username);

            User user = userService.getByEmail(username);
            if (user == null) {
                logger.error("User authentication failed due to missing user with username {}.", username);
                throw new UsernameNotFoundException("Username not found!");
            }

            List<GrantedAuthority> authorityList = getAuthorityList(user);

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(), user.getAccessToken(), true,
                    true, true, true,
                    authorityList);

        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    private List<GrantedAuthority> getAuthorityList(User user) {
        List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
        for (int i = 0; i < user.getRole() + 1; i++) {
            authorityList.add(new SimpleGrantedAuthority(DemoRole.values()[i]
                    .name()));
        }
        return authorityList;
    }

}
