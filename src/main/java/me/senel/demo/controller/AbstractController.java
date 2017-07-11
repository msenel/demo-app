package me.senel.demo.controller;


import me.senel.demo.util.SecurityContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public abstract class AbstractController {

    private static final Logger logger = LoggerFactory
            .getLogger(AbstractController.class);

    protected String getPrincipal() {
        return SecurityContextUtils.getUserNameInContext();
    }

}
