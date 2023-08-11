package vn.inergy.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thingsboard.server.queue.util.TbCoreComponent;
import vn.inergy.server.model.fileGenerate.FileGenerateDTO;
import org.springframework.http.HttpStatus;
import vn.inergy.server.service.fileGenerateTemplate.FileGenerateConst;
import vn.inergy.server.service.fileGenerateTemplate.FileGenerateTemplate;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RestController
@TbCoreComponent
@RequestMapping("api/fileGenerate")
@PreAuthorize("hasAnyAuthority('SYS_ADMIN','TENANT_ADMIN','CUSTOMER_USER')")
public class FileGenerateController {
    private final FileGenerateTemplate fileGenerateTemplate;

    public FileGenerateController(FileGenerateTemplate fileGenerateTemplate) {
        this.fileGenerateTemplate = fileGenerateTemplate;
    }

    @PostMapping
    public ResponseEntity<byte[]> generate(@RequestBody FileGenerateDTO fileGenerateDTO) throws Exception {
        byte[] bytes = fileGenerateTemplate.generate(fileGenerateDTO);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileGenerateTemplate.getFileName(fileGenerateDTO));
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }
}
