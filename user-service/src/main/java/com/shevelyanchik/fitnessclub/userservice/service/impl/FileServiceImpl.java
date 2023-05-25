package com.shevelyanchik.fitnessclub.userservice.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.shevelyanchik.fitnessclub.userservice.exception.ValidationException;
import com.shevelyanchik.fitnessclub.userservice.model.dto.FilePayload;
import com.shevelyanchik.fitnessclub.userservice.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private static final String FILE_SIZE = "fileSize";
    private static final String FILE_ID = "_id";
    private static final String CONTENT_TYPE = "_contentType";

    private final GridFsTemplate gridFsTemplate;

    @Override
    @Transactional
    @SneakyThrows
    public String saveFile(MultipartFile file) {
        DBObject metadata = new BasicDBObject();
        metadata.put(FILE_SIZE, file.getSize());

        Object fileID = gridFsTemplate.store(
                file.getInputStream(), file.getOriginalFilename(), file.getContentType(), metadata);
        return fileID.toString();
    }

    @Override
    @Transactional(readOnly = true)
    public FilePayload findFileById(String id) {
        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where(FILE_ID).is(id)));

        if (Objects.isNull(gridFSFile.getMetadata())) {
            throw new ValidationException("File not found with id: " + id);
        }
        return buildFilePayload(gridFSFile);
    }

    @SneakyThrows
    private FilePayload buildFilePayload(GridFSFile gridFSFile) {
        return FilePayload.builder()
                .fileName(gridFSFile.getFilename())
                .fileType(gridFSFile.getMetadata().get(CONTENT_TYPE).toString())
                .fileSize(gridFSFile.getMetadata().get(FILE_SIZE).toString())
                .file(gridFsTemplate.getResource(gridFSFile).getInputStream().readAllBytes())
                .build();
    }

}
