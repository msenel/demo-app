package me.senel.demo.controller.web;

import me.senel.demo.controller.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController extends AbstractController {

    private static final Logger logger = LoggerFactory
            .getLogger(HomeController.class);


    @RequestMapping("/login")
    public Object login(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return new ModelAndView("login");
    }

    @RequestMapping("/")
    public Object start(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return start();
    }

    @RequestMapping("home")
    public Object home(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        return start();
    }

    private Object start() {
        ModelAndView mV = new ModelAndView("home");

        //landing page

        return mV;
    }


}
