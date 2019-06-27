package com.coxplore.controller;

import com.coxplore.helper.HttpResponse;
import com.coxplore.service.UserService;
import com.coxplore.helper.CustomLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.coxplore.helper.CustomLogger.logRequestResponse;

@RestController
public class UserAuthenticateController {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticateController.class);
    private UserService userService;

    @Autowired
    public UserAuthenticateController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.GET, produces = "application/json")
    public HttpResponse register(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, BindException {
        HttpResponse response = userService.authenticate(req, res);
        logger.info("Authenticate user");
        CustomLogger.logRequestResponse(logger, req, response);
        return response;
    }
}
