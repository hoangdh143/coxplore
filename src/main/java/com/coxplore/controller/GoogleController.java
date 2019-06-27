package com.coxplore.controller;

import com.coxplore.helper.HttpResponse;
import com.coxplore.helper.ValidationErrorHttpResponse;
import com.coxplore.helper.ValidationErrorProcess;
import com.coxplore.model.SocialAccessToken;
import com.coxplore.service.GoogleService;
import com.coxplore.helper.CustomLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.coxplore.helper.CustomLogger.logRequestResponse;

@RestController
public class GoogleController {
    private static final Logger logger = LoggerFactory.getLogger(GoogleController.class);

    private GoogleService googleService;

    @Autowired
    public GoogleController(GoogleService googleService) {
        this.googleService = googleService;
    }

    @RequestMapping(value = "/login/google", method = RequestMethod.POST, produces = "application/json")
    public HttpResponse processRegistration(@Valid @RequestBody SocialAccessToken socialAccessToken, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse res) throws JsonProcessingException, BindException {
        logger.info("New Google login request");
        JsonNode bindingError = ValidationErrorProcess.run(bindingResult);

        if (bindingError != null) {
            HttpResponse response = new ValidationErrorHttpResponse(false, 400, "INVALID_INFO", "Invalid field(s)", res, bindingError);
            logger.warn("Invalid User Info");
            CustomLogger.logRequestResponse(logger, request, response);
            return response;
        }

        try {
            HttpResponse response = googleService.registerAndLogin(socialAccessToken.getAccessToken(), socialAccessToken.getIntroduceCode(), request, res);
            CustomLogger.logRequestResponse(logger, request, response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            HttpResponse response = new ValidationErrorHttpResponse(false, 500, "SYSTEM_ERROR", "An error occurs", res, bindingError);
            logger.warn(e.getStackTrace().toString());
            CustomLogger.logRequestResponse(logger, request, response);
            return response;
        }
    }

    @GetMapping("/createGoogleAuthorization")
    public String createGoogleAuthorization(HttpServletRequest req) {
        String googleAuthorization = googleService.createGoogleAuthorizationURL();
        logger.info("Create Google authorization");
        CustomLogger.logRequestResponse(logger, req, googleAuthorization);
        return googleAuthorization;
    }

    @GetMapping("/google")
    public HttpResponse createGoogleAccessToken(@RequestParam("code") String code, HttpServletRequest req, HttpServletResponse res) {
        HttpResponse response = googleService.createGoogleAccessToken(code, res);
        logger.info("Create Google access token");
        CustomLogger.logRequestResponse(logger, req, response);
        return response;
    }
}
