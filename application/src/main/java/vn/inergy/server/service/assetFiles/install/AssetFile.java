package vn.inergy.server.service.assetFiles.install;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import vn.inergy.server.service.assetFiles.config.AssetConfig;

import java.io.File;

@Component
public class AssetFile {
    private final ResourceLoader resourceLoader;
    final
    AssetConfig assetConfig;

    public AssetFile(ResourceLoader resourceLoader, AssetConfig assetConfig) {
        this.resourceLoader = resourceLoader;
        this.assetConfig = assetConfig;
    }

    public boolean createRoot() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:" + assetConfig.getFolderPath());
        File file = new File(resource.getFile(), assetConfig.getFolderName());
        if (!file.exists()) {
            return file.mkdirs();
        }
        return true;
    }
}
