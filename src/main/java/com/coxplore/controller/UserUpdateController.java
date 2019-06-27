package com.coxplore.controller;

import com.coxplore.helper.ErrorHttpResponse;
import com.coxplore.helper.HttpResponse;
import com.coxplore.helper.ValidationErrorHttpResponse;
import com.coxplore.helper.ValidationErrorProcess;
import com.coxplore.model.User;
import com.coxplore.model.UserGroupChange;
import com.coxplore.model.UserNonSecuredInfo;
import com.coxplore.model.UserPassword;
import com.coxplore.service.UserService;
import com.coxplore.helper.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

import static com.coxplore.helper.CustomLogger.logRequestResponse;

//import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/accounts")

public class UserUpdateController {
    private static final Logger logger = LoggerFactory.getLogger(UserUpdateController.class);

    public static String uploadDirectory = System.getProperty("spring.servlet.multipart.location");
    private UserService userService;

    @Autowired
    public UserUpdateController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json")
    public HttpResponse update(@Valid @RequestBody UserNonSecuredInfo user, BindingResult bindingResult, HttpServletRequest req,
                               HttpServletResponse res) throws Exception {
        logger.info("Update date User Info of User Id: {}", req.getHeader("userId"));
        // catch if any binding error
        JsonNode bindingError = ValidationErrorProcess.run(bindingResult);
        if (bindingError != null) {
            HttpResponse response = new ValidationErrorHttpResponse(false, 400, "INVALID_USER_INFO", "Invalid field(s)",
                    res, bindingError);
            logger.warn("Invalid User Info");
            CustomLogger.logRequestResponse(logger, req, response);
            return response;
        }

        HttpResponse response = userService.update(user, req, res);
        CustomLogger.logRequestResponse(logger, req, response);

        return response;
    }

    @RequestMapping(value = "/password", method = RequestMethod.PUT, produces = "application/json")
    public HttpResponse changePassword(@Valid @RequestBody UserPassword user, BindingResult bindingResult,
                                       HttpServletRequest req, HttpServletResponse res) {
        logger.info("Change password of User Id: {}", req.getHeader("userId"));
        JsonNode bindingError = ValidationErrorProcess.run(bindingResult);

        if (bindingError != null) {
            HttpResponse response = new ValidationErrorHttpResponse(false, 400, "INVALID_USER_INFO", "Invalid field(s)",
                    res, bindingError);
            logger.warn("Invalid User Info");
            CustomLogger.logRequestResponse(logger, req, response);
            return response;
        }

        HttpResponse response = userService.changePassword(user, req, res);
        CustomLogger.logRequestResponse(logger, req, response);
        return response;
    }

    @RequestMapping(value = "/group", method = RequestMethod.PUT, produces = "application/json")
    public HttpResponse changeGroupRequest(@Valid @RequestBody UserGroupChange user, BindingResult bindingResult,
                                           HttpServletRequest req, HttpServletResponse res) {
        logger.info("Request Change group of User Id: {}", req.getHeader("userId"));
        JsonNode bindingError = ValidationErrorProcess.run(bindingResult);

        if (bindingError != null) {
            HttpResponse response = new ValidationErrorHttpResponse(false, 400, "INVALID_USER_INFO", "Invalid field(s)",
                    res, bindingError);
            logger.warn("Invalid User Info");
            CustomLogger.logRequestResponse(logger, req, response);
            return response;
        }

        HttpResponse response = userService.requestChangeGroup(user, req, res);
        CustomLogger.logRequestResponse(logger, req, response);
        return response;
    }

    @PutMapping("/avatar")
    public HttpResponse changeAvatar(@Valid @RequestBody User user, HttpServletResponse res, HttpServletRequest req)
            throws IOException {
        HttpResponse response = userService.changeAvatar(user.getAvatar(), res, req);
        logger.info("Change Avatar of user Id: {}", req.getHeader("userId"));
        CustomLogger.logRequestResponse(logger, req, response);
        return response;
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ErrorHttpResponse handleException(HttpServletResponse res, InvalidFormatException e) {
        if (e.getTargetType().isAssignableFrom(java.util.Date.class)) {
            logger.warn("Invalid Date format");
            return new ErrorHttpResponse(false, 400, "INVALID_DATE_FORMAT", "Invalid date format, expected yyyy-MM-dd", res);
        }
        logger.warn("Invalid field format");
        return new ErrorHttpResponse(false, 400, "INVALID_FIELD_FORMAT", "Invalid field format", res);
    }
}
