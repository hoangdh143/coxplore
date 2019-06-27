package com.coxplore.controller;

import com.coxplore.helper.CustomLogger;
import com.coxplore.helper.HttpResponse;
import com.coxplore.helper.ValidationErrorHttpResponse;
import com.coxplore.helper.ValidationErrorProcess;
import com.coxplore.model.User;
import com.coxplore.service.EmailService;
import com.coxplore.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class ForgetPasswordController {
    private static final Logger logger = LoggerFactory.getLogger(ForgetPasswordController.class);

    private UserService userService;

    @Autowired
    public ForgetPasswordController(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService,
                                    EmailService emailService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/accounts/forget_password", method = RequestMethod.POST, produces = "application/json")
    public HttpResponse forgetPasswordRequest(@Valid @RequestBody User user, BindingResult bindingResult,
                                              HttpServletRequest request, HttpServletResponse res) {
        logger.info("New forget password request");
        JsonNode bindingError = ValidationErrorProcess.run(bindingResult);

        if (bindingError != null) {
            HttpResponse response = new ValidationErrorHttpResponse(false, 400, "INVALID_USER_INFO", "Invalid field(s)",
                    res, bindingError);
            logger.warn("Invalid User Info");
            CustomLogger.logRequestResponse(logger, request, response);
            return response;
        }

        HttpResponse response = userService.forgetPasswordRequest(user, res);
        CustomLogger.logRequestResponse(logger, request, response);
        return response;
    }

    @RequestMapping(value = "/accounts/forget_password/verify", method = RequestMethod.PUT, produces = "application/json")
    public HttpResponse forgetPasswordVerify(@Valid @RequestBody User user, BindingResult bindingResult,
                                             HttpServletRequest req, HttpServletResponse res) {
        logger.info("Forget password verification");
        JsonNode bindingError = ValidationErrorProcess.run(bindingResult);

        if (bindingError != null) {
            HttpResponse response = new ValidationErrorHttpResponse(false, 400, "INVALID_USER_INFO", "Invalid field(s)",
                    res, bindingError);
            logger.warn("Invalid User Info");
            CustomLogger.logRequestResponse(logger, req, response);
            return response;
        }

        HttpResponse response = userService.forgetPasswordVerify(user, res);
        CustomLogger.logRequestResponse(logger, req, response);
        return response;
    }

    @RequestMapping(value = "/accounts/forget_password", method = RequestMethod.PUT, produces = "application/json")
    public HttpResponse changePasswordWhenForget(@Valid @RequestBody User user, BindingResult bindingResult,
                                                 HttpServletRequest req, HttpServletResponse res) {
        logger.info("New change password request");
        JsonNode bindingError = ValidationErrorProcess.run(bindingResult);

        if (bindingError != null) {
            HttpResponse response = new ValidationErrorHttpResponse(false, 400, "INVALID_USER_INFO", "Invalid field(s)",
                    res, bindingError);
            CustomLogger.logRequestResponse(logger, req, response);
            return response;
        }

        HttpResponse response = userService.changePasswordWhenForget(user, req, res);
        CustomLogger.logRequestResponse(logger, req, response);

        return response;
    }
}
