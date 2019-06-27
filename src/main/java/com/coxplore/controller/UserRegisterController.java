package com.coxplore.controller;

import com.coxplore.helper.HttpResponse;
import com.coxplore.helper.ValidationErrorHttpResponse;
import com.coxplore.helper.ValidationErrorProcess;
import com.coxplore.model.User;
import com.coxplore.model.UserGroup;
import com.coxplore.service.UserService;
import com.coxplore.helper.CustomLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.ParseException;

import static com.coxplore.helper.CustomLogger.logRequestResponse;

@RestController
public class UserRegisterController {
    private static final Logger logger = LoggerFactory.getLogger(UserRegisterController.class);

    private UserService userService;

    @Autowired
    public UserRegisterController(UserService userService) {
        this.userService = userService;
    }

    // Process input data
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    public HttpResponse register(@Valid @RequestBody User user, BindingResult bindingResult, HttpServletRequest request,
                                 HttpServletResponse res) throws JsonProcessingException, BindException {
        logger.info("Rgister new user");
        JsonNode bindingError = ValidationErrorProcess.run(bindingResult);

        if (bindingError != null) {
            HttpResponse response = new ValidationErrorHttpResponse(false, 400, "INVALID_USER_INFO", "Invalid field(s)",
                    res, bindingError);
            logger.warn("Invalid user info");
            CustomLogger.logRequestResponse(logger, request, response);
            return response;
        }

        HttpResponse response = userService.register(user, res);
        CustomLogger.logRequestResponse(logger, request, response);
        return response;

    }

    @RequestMapping(value = "/activate/request", method = RequestMethod.POST)
    public HttpResponse requestActivate(@RequestBody User user, BindingResult bindingResult, HttpServletRequest req, HttpServletResponse res) {
        logger.info("Request activate User Id: {}", user.getId());
        JsonNode bindingError = ValidationErrorProcess.run(bindingResult);

        if (bindingError != null) {
            HttpResponse response = new ValidationErrorHttpResponse(false, 400, "INVALID_USER_INFO", "Invalid field(s)",
                    res, bindingError);
            logger.warn("Invalid User Info");
            CustomLogger.logRequestResponse(logger, req, response);
            return response;
        }

        HttpResponse response = userService.requestActivate(user, res);
        CustomLogger.logRequestResponse(logger, req, response);
        return response;
    }

    // Process confirmation link
    @RequestMapping(value = "/activate/process", method = RequestMethod.POST, produces = "application/json")
    public HttpResponse processActivate(@RequestBody User user, BindingResult bindingResult, HttpServletRequest req, HttpServletResponse res)
            throws ParseException, JsonProcessingException {
        logger.info("Process activating User Id: {}", user.getId());
        JsonNode bindingError = ValidationErrorProcess.run(bindingResult);

        if (bindingError != null) {
            HttpResponse response = new ValidationErrorHttpResponse(false, 400, "INVALID_USER_INFO", "Invalid field(s)",
                    res, bindingError);
            logger.warn("Invalid User Info");
            CustomLogger.logRequestResponse(logger, req, response);
            return response;
        }

        HttpResponse response = userService.processActivate(user, res);
        CustomLogger.logRequestResponse(logger, req, response);
        return response;

    }

    @RequestMapping(value = "/accounts", method = RequestMethod.POST, produces = "application/json")
    @PreAuthorize("hasAccessRight('module_user', 'create') AND hasAccessRight('module_group', 'read')")
    public HttpResponse adminRegister(@Valid @RequestBody UserGroup user, BindingResult bindingResult,
                                      HttpServletRequest request, HttpServletResponse res) throws JsonProcessingException, BindException {
        logger.info("Register admin");
        JsonNode bindingError = ValidationErrorProcess.run(bindingResult);

        if (bindingError != null) {
            HttpResponse response = new ValidationErrorHttpResponse(false, 400, "INVALID_USER_INFO", "Invalid field(s)",
                    res, bindingError);
            logger.warn("Invalid User Info");
            CustomLogger.logRequestResponse(logger, request, response);
            return response;
        }

        HttpResponse response = userService.adminRegister(user, res);
        CustomLogger.logRequestResponse(logger, request, response);
        return response;
    }
}
