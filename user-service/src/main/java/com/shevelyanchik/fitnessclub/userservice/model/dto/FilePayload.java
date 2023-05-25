package com.shevelyanchik.fitnessclub.userservice.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilePayload {

    private String fileName;

    private String fileType;

    private String fileSize;

    private byte[] file;

}
