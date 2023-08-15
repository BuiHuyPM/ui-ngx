package vn.inergy.server.service.assetFiles.install;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import vn.inergy.server.service.assetFiles.AssetFileServiceImpl;
import vn.inergy.server.service.assetFiles.config.AssetConfig;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class AssetFile {
    private final ResourceLoader resourceLoader;


    public AssetFile(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public boolean createRoot() throws Exception {
        Resource resource = resourceLoader.getResource(ResourceUtils.FILE_URL_PREFIX + AssetConfig.folderPath);
        if (!resource.exists()) {
            Files.createDirectories(Path.of(AssetConfig.folderPath));
        }
        Resource assetResource = resourceLoader.getResource(ResourceUtils.FILE_URL_PREFIX + AssetConfig.folderPath + AssetConfig.assetFiles);
        if (!assetResource.exists()) {
            Files.createDirectories(Path.of(AssetConfig.folderPath + AssetConfig.assetFiles));
        }
        return true;
    }
}
