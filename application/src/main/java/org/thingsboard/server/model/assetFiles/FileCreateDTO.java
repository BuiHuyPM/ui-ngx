package org.thingsboard.server.model.assetFiles;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileCreateDTO implements Serializable {
    private String fileName;
    private String data;
}
