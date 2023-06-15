package vn.inergy.server.service.assetFiles;


import vn.inergy.server.model.assetFiles.FileDTO;

import java.util.List;

public interface AssetFileService {
    List<FileDTO> get(String path) throws Exception;

    void upload(String path, List<FileDTO> fileDTOs) throws Exception;

    void delete(String path) throws Exception;
}
