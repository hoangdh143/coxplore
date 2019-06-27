package com.coxplore.security;

import com.coxplore.repository.GroupModulePermissionRepository;
import com.coxplore.repository.ModuleRepository;
import com.coxplore.repository.UserRepository;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class CustomMethodSecurityExpressionHandler
        extends DefaultMethodSecurityExpressionHandler {
    private UserRepository userRepository;
    private GroupModulePermissionRepository groupModulePermissionRepository;
    private ModuleRepository moduleRepository;

    public CustomMethodSecurityExpressionHandler() {
    }

    @Autowired
    public CustomMethodSecurityExpressionHandler(UserRepository userRepository, GroupModulePermissionRepository groupModulePermissionRepository, ModuleRepository moduleRepository) {
        this.userRepository = userRepository;
        this.groupModulePermissionRepository = groupModulePermissionRepository;
        this.moduleRepository = moduleRepository;
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
            Authentication authentication, MethodInvocation invocation) {
        CustomMethodSecurityExpressionRoot root =
                new CustomMethodSecurityExpressionRoot(authentication, userRepository, groupModulePermissionRepository, moduleRepository);
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setRoleHierarchy(getRoleHierarchy());
        return root;
    }
}

