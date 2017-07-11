package me.senel.demo.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityContextUtils {

    public static String getUserNameInContext()
    {
        Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

        String userName;
        
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername(); 
        } else {
            userName = (String) principal;
        }
        
        return userName;
    }
}
