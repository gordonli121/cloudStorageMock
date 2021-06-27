package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;
    private String noteError = null;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public String createNote(@ModelAttribute Note note, Authentication authentication, Model model) {
        this.noteError = null;

        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        note.setUserId(userId);
        int rowsAdded = noteService.storeNote(note);

        if (rowsAdded < 0) {
            this.noteError = "Error during adding new note. Please try again! ";
        }

        if (this.noteError == null) {
            model.addAttribute("noteSuccess", "You have successfully added a new note! ");
        } else {
            model.addAttribute("noteError", this.noteError);
        }

        return "result";
    }

    @PutMapping
    public String updateNote(@ModelAttribute Note note, Authentication authentication, Model model) {
        this.noteError = null;

        int rowsUpdated = noteService.updateNote(note);

        if (rowsUpdated < 0){
            this.noteError = "Error during updating a note. Please try again! ";
        }

        if (this.noteError == null) {
            model.addAttribute("noteSuccess", "You have successfully updated a note! ");
        } else {
            model.addAttribute("noteError", this.noteError);
        }

        return "result";
    }

    @DeleteMapping
    public String deleteNote(@ModelAttribute Note note, Authentication authentication, Model model) {
        this.noteError = null;

        int rowsDeleted = noteService.deleteNote(note.getNoteId());

        if (rowsDeleted < 0){
            this.noteError = "Error during deleting a note. Please try again! ";
        }

        if (this.noteError == null) {
            model.addAttribute("noteSuccess", "You have successfully deleted a note! ");
        } else {
            model.addAttribute("noteError", this.noteError);
        }

        return "result";
    }
}
