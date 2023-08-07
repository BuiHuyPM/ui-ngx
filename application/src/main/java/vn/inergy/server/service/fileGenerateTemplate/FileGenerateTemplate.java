package vn.inergy.server.service.fileGenerateTemplate;

import vn.inergy.server.model.fileGenerate.FileGenerateDTO;

import java.io.IOException;


public interface FileGenerateTemplate {
    byte[] generate(FileGenerateDTO fileGenerateDTO) throws Exception;

    String getFile(FileGenerateDTO fileGenerateDTO);
}
