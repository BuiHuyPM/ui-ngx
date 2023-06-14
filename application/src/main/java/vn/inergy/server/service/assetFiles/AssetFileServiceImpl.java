package vn.inergy.server.service.assetFiles;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import vn.inergy.server.model.assetFiles.FileDTO;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AssetFileServiceImpl implements AssetFileService {
    private final String root = "static/assetFiles";
    private final ResourceLoader resourceLoader;

    public AssetFileServiceImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<FileDTO> get(String folderR) throws Exception {
        String folder = beautify(folderR);
        Resource resource = resourceLoader.getResource("classpath:" + root + folder);
        if (!resource.exists()) {
            throw new Exception("Folder is not exists");
        }
        File[] listOfFiles = resource.getFile().listFiles();
        if (listOfFiles != null) {
            return Arrays.stream(listOfFiles).map(file -> {
                try {
                    Resource fileResource = resourceLoader.getResource("file:" + file.getPath());
                    String name = file.getName();
                    String path = folder + "/" + file.getName();
                    path = path.replace("//", "/");
                    Boolean isFolder = file.isDirectory();
                    Long lastModified = fileResource.lastModified();
                    String data = "";
                    if (!isFolder) {
                        byte[] fileContent = Files.readAllBytes(Paths.get(file.getPath()));
                        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
                        data += "data:"+mimeType+";base64,";
                        data += Base64.getEncoder().encodeToString(fileContent);
                    }
                    return new FileDTO(name, path, isFolder, data, lastModified);
                } catch (Exception ignored) {
                    return null;
                }
            }).filter(Objects::nonNull).sorted(FileDTO.fileDTOComparator()).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public FileDTO upload(String folderR, FileDTO fileDTO) throws Exception {
        String folder = beautify(folderR);
        Resource resource = resourceLoader.getResource("classpath:" + root + folder);
        if (!resource.exists()) {
            throw new Exception("File is not exists");
        }

        File file = new File(resource.getFile(), fileDTO.getName());

        if (file.exists()) {
            throw new Exception("File " + fileDTO.getName() + " is really exists");
        }
        if (!fileDTO.getIsFolder() && !allowEx(fileDTO.getName())) {
            throw new Exception("File " + fileDTO.getName() + " is not allow. Only files are allowed to upload: jpg, gif, doc, pdf, css, js, html, xlsx, xls");
        }

        boolean isCreated = fileDTO.getIsFolder() ? file.mkdirs() : file.createNewFile();

        if (isCreated) {
            if (!fileDTO.getIsFolder()) {
                String base64String = fileDTO.getData();
                byte[] decodedBytes = Base64.getDecoder().decode(base64String.getBytes(StandardCharsets.UTF_8));
                Files.write(Paths.get(file.getPath()), decodedBytes);
            }
            Resource newResource = resourceLoader.getResource("file:" + file.getPath());
            fileDTO.setLastModified(newResource.lastModified());
            fileDTO.setPath(folder + "/" + fileDTO.getName());
            return fileDTO;
        }
        throw new Exception("Can't create folder");
    }

    @Override
    public void delete(String folderR) throws Exception {
        String folder = beautify(folderR);
        Resource rootResource = resourceLoader.getResource("classpath:" + root);
        Resource resource = resourceLoader.getResource("classpath:" + root + folder);
        if (rootResource.equals(resource)) {
            throw new Exception("Folder is root can not delete");
        }

        if (!resource.exists()) {
            throw new Exception("Folder is not exists");
        }
        File file = resource.getFile();
        Files.walk(Paths.get(file.getPath())).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
    }

    public String beautify(String folder){
        if (!folder.startsWith("/")){
            return "/"+folder;
        }
        return folder;
    }
    private boolean allowEx(String name) {
        return name.matches("^.*(.jpg|.JPG|.gif|.GIF|.doc|.DOC|.pdf|.PDF|.css|.js|.html|.xlsx.|xls)$");
    }
}
