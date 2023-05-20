package com.shevelyanchik.fitnessclub.userservice.service;

import com.shevelyanchik.fitnessclub.userservice.model.dto.FilePayload;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String saveFile(MultipartFile file);

    FilePayload findFileById(String id);

}
