package org.thingsboard.server.service.assetFiles;


import org.thingsboard.server.model.assetFiles.FileDTO;

import java.util.List;

public interface AssetFileService {
    boolean createRoot() throws Exception;
    List<FileDTO> get(String path) throws Exception;

    FileDTO upload(String path, FileDTO fileCreateDTO) throws Exception;

    void delete(String path) throws Exception;
}
