package vn.inergy.server.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.thingsboard.server.common.data.exception.ThingsboardException;
import org.thingsboard.server.controller.BaseController;
import vn.inergy.server.model.assetFiles.FileDTO;
import org.thingsboard.server.queue.util.TbCoreComponent;
import vn.inergy.server.service.assetFiles.AssetFileService;

import java.util.List;

@Slf4j
@RestController
@TbCoreComponent
@RequestMapping("api/assetFiles/{*file}")
@PreAuthorize("hasAuthority('SYS_ADMIN')")

public class AssetFileController extends BaseController {
    private final AssetFileService assetFileService;

    public AssetFileController(AssetFileService assetFileService) {
        this.assetFileService = assetFileService;
    }

    @GetMapping
    public List<FileDTO> get(@PathVariable() String file) throws ThingsboardException {
        try {
            return assetFileService.get(file);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @PostMapping
    public FileDTO upload(@PathVariable String file, @RequestBody FileDTO fileDto) throws ThingsboardException {
        try {
            return assetFileService.upload(file, fileDto);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String file) throws Exception {
        try {
            assetFileService.delete(file);
        } catch (Exception e) {
            throw handleException(e);
        }
    }
}
