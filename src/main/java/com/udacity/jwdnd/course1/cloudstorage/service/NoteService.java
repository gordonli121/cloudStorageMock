package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getAllNotes(Integer userId) {return noteMapper.getAllNotes(userId);}

    public int storeNote(Note note) {return noteMapper.insertNote(new Note(null, note.getNoteTitle(), note.getNoteDescription(), note.getUserId()));}

    public int updateNote(Note note) {return noteMapper.updateNote(new Note(note.getNoteId(), note.getNoteTitle(), note.getNoteDescription(), note.getUserId()));}

    public int deleteNote(Integer noteId) {return noteMapper.deleteNote(noteId);}
}
