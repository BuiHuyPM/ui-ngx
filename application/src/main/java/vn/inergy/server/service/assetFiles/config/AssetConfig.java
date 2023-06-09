package vn.inergy.server.service.assetFiles.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AssetConfig {
    @Value("${asset.folderPath:static}")
    String folderPath;

    @Value("${asset.folderName:assetFiles}")
    String folderName;

}
