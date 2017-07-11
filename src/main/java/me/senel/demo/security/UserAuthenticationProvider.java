package me.senel.demo.security;

import me.senel.demo.model.User;
import me.senel.demo.service.UserService;
import me.senel.demo.service.exception.DataAccessException;
import me.senel.demo.service.exception.UserNotFoundExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {

		User user = null;
		try {
			user =  userService.getByEmail(authentication.getPrincipal().toString());
		} catch (DataAccessException ex) {
			throw new UsernameNotFoundException(ex.getMessage());
		} catch (UserNotFoundExeption ex) {
			throw new UsernameNotFoundException("Invalid user name!");
		}

		if (!user.getAccessToken().equals(authentication.getCredentials().toString())) {
			throw new BadCredentialsException("Invalid password!");
		}

		return new UserAuthenticationToken(authentication.getPrincipal(),
				authentication.getCredentials(), getAuthorityList(user));

	}

	private List<GrantedAuthority> getAuthorityList(User user) {
		List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
		for (int i = 0; i < user.getRole() + 1; i++) {
			authorityList.add(new SimpleGrantedAuthority(DemoRole.values()[i]
					.name()));
		}
		return authorityList;
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
