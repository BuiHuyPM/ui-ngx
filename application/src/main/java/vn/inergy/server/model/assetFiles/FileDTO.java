package vn.inergy.server.model.assetFiles;

import lombok.Data;

import java.io.Serializable;
import java.util.Comparator;

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

    public FileDTO(String name, String path, Boolean isFolder, String data, Long lastModified) {
        this.name = name;
        this.path = path;
        this.isFolder = isFolder;
        this.data = data;
        this.lastModified = lastModified;
    }

    public static Comparator<FileDTO> fileDTOComparator() {
        return (o1, o2) -> {
            if (o2.isFolder == o1.isFolder) {
                return o1.getName().compareTo(o2.getName());
            } else {
                return Boolean.compare(o2.isFolder, o1.isFolder);
            }
        };
    }
}
