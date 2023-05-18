package org.thingsboard.server.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.thingsboard.server.common.data.exception.ThingsboardException;
import org.thingsboard.server.common.data.id.AssetId;
import org.thingsboard.server.model.assetFiles.FileDTO;
import org.thingsboard.server.queue.util.TbCoreComponent;
import org.thingsboard.server.service.assetFiles.AssetFileService;
import org.thingsboard.server.service.security.permission.Operation;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@TbCoreComponent
@RequestMapping({"api/assetFiles/**", "api/assetFiles"})
public class AssetFileController extends BaseController {
    private final AssetFileService assetFileService;

    public AssetFileController(AssetFileService assetFileService) {
        this.assetFileService = assetFileService;
    }

    @GetMapping
    public List<FileDTO> get(HttpServletRequest request) throws ThingsboardException {
        try {
            String path = getPathFromUri(request);
            return assetFileService.get(path);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @PostMapping
    public FileDTO upload(HttpServletRequest request, @RequestBody FileDTO file) throws ThingsboardException {
        try {
            String path = getPathFromUri(request);
            return assetFileService.upload(path, file);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(HttpServletRequest request) throws Exception {
        try {
            String path = getPathFromUri(request);
            assetFileService.delete(path);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    private String getPathFromUri(HttpServletRequest request) throws Exception {
        String uri = request.getRequestURI();
        String path = uri.replace("/api/assetFiles", "");
        boolean isNotValid = path.matches("(../)+");
        if (isNotValid) {
            throw new Exception("Path is not valid");
        }
        return path;
    }
}
