package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;
    private final UserService userService;
    private String fileError = null;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity viewFile(@PathVariable("fileId") Integer fileId) {
        File newFile = fileService.getFileById(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(newFile.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" +
                        newFile.getFileName() + "\"")
                .body(newFile.getFileData());
    }

    @PostMapping
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, Model model) throws IOException {
        this.fileError = null;
        if (fileUpload.isEmpty()) {
            this.fileError = "No file uploaded! ";
        } else if (!fileService.isFilenameAvailable(fileUpload.getOriginalFilename())) {
            this.fileError = "File name already exists! ";
        } else {
            User user = userService.getUser(authentication.getName());
            Integer userId = user.getUserId();

            int rowsAdded = fileService.storeFile(fileUpload, userId);

            if (rowsAdded < 0) {
                this.fileError = "Error during storing new file. Please try again! ";
            }
        }

        if (this.fileError == null) {
            model.addAttribute("fileSuccess", "You have successfully uploaded a new file! ");
        } else {
            model.addAttribute("fileError", this.fileError);
        }

        return "result";
    }

    @DeleteMapping
    public String deleteFile(@ModelAttribute File file, Authentication authentication, Model model) {
        this.fileError = null;

        int rowsDeleted = fileService.deleteFileById(file.getFileId());

        if (rowsDeleted < 0) {
            this.fileError = "Error during deleting file. Please try again! ";
        }

        if (this.fileError == null) {
            model.addAttribute("fileSuccess", "You have successfully deleted a file! ");
        } else {
            model.addAttribute("fileError", this.fileError);
        }

        return "result";
    }
}
