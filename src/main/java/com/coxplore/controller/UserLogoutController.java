package com.coxplore.controller;

import com.coxplore.helper.HttpResponse;
import com.coxplore.service.UserService;
import com.coxplore.helper.CustomLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.coxplore.helper.CustomLogger.logRequestResponse;

@RestController
public class UserLogoutController {
    private static final Logger logger = LoggerFactory.getLogger(UserLogoutController.class);

    private UserService userService;

    @Autowired
    public UserLogoutController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/accounts/signout", method = RequestMethod.GET)
    public HttpResponse logout(HttpServletRequest req, HttpServletResponse res) {
        HttpResponse response = userService.logout(req, res);
        logger.info("User id {} sign out", req.getHeader("userId"));
        CustomLogger.logRequestResponse(logger, req, response);
        return response;
    }
}
