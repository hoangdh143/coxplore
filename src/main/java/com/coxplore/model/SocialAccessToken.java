package com.coxplore.model;

import com.coxplore.helper.validation.constraint.XssValidationConstraint;

public class SocialAccessToken {
    @XssValidationConstraint
    private String accessToken;

    private String introduceCode;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getIntroduceCode() {
        return introduceCode;
    }

    public void setIntroduceCode(String introduceCode) {
        this.introduceCode = introduceCode;
    }
}
