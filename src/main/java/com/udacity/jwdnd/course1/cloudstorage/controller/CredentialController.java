package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;
    private String credentialError = null;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping
    public String createCredential(@ModelAttribute Credential credential, Authentication authentication, Model model) {
        this.credentialError = null;

        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        credential.setUserId(userId);
        int rowsAdded = credentialService.storeCredential(credential);

        if (rowsAdded < 0) {
            this.credentialError = "Error during adding new credential. Please try again! ";
        }

        if (this.credentialError == null) {
            model.addAttribute("credentialSuccess", "You have successfully added a new credential! ");
        } else {
            model.addAttribute("credentialError", this.credentialError);
        }

        return "result";
    }

    @PutMapping
    public String updateCredential(@ModelAttribute Credential credential, Authentication authentication, Model model) {
        this.credentialError = null;

        System.out.println("controller: ");
        System.out.println(credential.getPassword());
        System.out.println(credential.getKey());
        int rowsUpdated = credentialService.updateCredential(credential);

        if (rowsUpdated < 0){
            this.credentialError = "Error during updating a credential. Please try again! ";
        }

        if (this.credentialError == null) {
            model.addAttribute("credentialSuccess", "You have successfully updated a credential! ");
        } else {
            model.addAttribute("credentialError", this.credentialError);
        }

        return "result";
    }

    @DeleteMapping
    public String deleteCredential(@ModelAttribute Credential credential, Authentication authentication, Model model) {
        this.credentialError = null;

        int rowsDeleted = credentialService.deleteCredential(credential.getCredentialId());

        if (rowsDeleted < 0){
            this.credentialError = "Error during deleting a credential. Please try again! ";
        }

        if (this.credentialError == null) {
            model.addAttribute("credentialSuccess", "You have successfully deleted a credential! ");
        } else {
            model.addAttribute("credentialError", this.credentialError);
        }

        return "result";
    }
}
