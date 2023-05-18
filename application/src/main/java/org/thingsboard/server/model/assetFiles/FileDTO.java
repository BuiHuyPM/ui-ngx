package org.thingsboard.server.model.assetFiles;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileDTO implements Serializable {
    private String name;
    private String path;
    private Boolean isFolder;
    private String data;
    private Long lastModified;

    public FileDTO() {
    }

    public FileDTO(String name, String path, Boolean isFolder, Long lastModified) {
        this.name = name;
        this.path = path;
        this.isFolder = isFolder;
        this.lastModified = lastModified;
    }

    public FileDTO(String name, String path, Boolean isFolder,String data, Long lastModified) {
        this.name = name;
        this.path = path;
        this.isFolder = isFolder;
        this.data = data;
        this.lastModified = lastModified;
    }
}
