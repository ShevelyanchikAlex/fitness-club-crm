package com.shevelyanchik.fitnessclub.userservice.service;

import com.shevelyanchik.fitnessclub.userservice.model.dto.FilePayload;
import org.springframework.web.multipart.MultipartFile;

/**
 * A FileService interface provides methods for managing files.
 *
 * @version 1.0.1
 */
public interface FileService {

    /**
     * Saves the provided file.
     *
     * @param file The file to be saved.
     * @return The ID of the saved file.
     */
    String saveFile(MultipartFile file);

    /**
     * Finds a file by the specified ID.
     *
     * @param id The ID of the file to find.
     * @return The file payload corresponding to the ID.
     */
    FilePayload findFileById(String id);

}
