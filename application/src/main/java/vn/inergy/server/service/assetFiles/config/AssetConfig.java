package vn.inergy.server.service.assetFiles.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


public class AssetConfig {
   public static final  String folderPath = "uploads";

    public static final  String assetFiles = "/assetFiles";
}
