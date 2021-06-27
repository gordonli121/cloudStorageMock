package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public File getFileById(Integer fileId) {return fileMapper.getFileById(fileId);}

    public int storeFile(MultipartFile file, Integer userId) throws IOException {
        File newFile = new File(null, file.getOriginalFilename(), file.getContentType(), file.getSize(), userId, file.getBytes());
        return fileMapper.insertFile(newFile);
    }

    public List<File> getAllFiles(Integer userId) {return fileMapper.getFiles(userId);}

    public int deleteFileById(Integer fileId) {return fileMapper.deleteFile(fileId);};

    public boolean isFilenameAvailable(String filename) {
        return fileMapper.getFileByName(filename).isEmpty();
    }
}
