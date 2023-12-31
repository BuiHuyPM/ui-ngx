package vn.inergy.server.service.assetFiles;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.thingsboard.server.common.data.exception.ThingsboardErrorCode;
import org.thingsboard.server.common.data.exception.ThingsboardException;
import vn.inergy.server.model.assetFiles.FileDTO;
import vn.inergy.server.service.assetFiles.config.AssetConfig;

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
    private final String root = AssetConfig.folderPath + AssetConfig.assetFiles;
    private final ResourceLoader resourceLoader;

    @Value("${asset-file.allow:^.*(.png|.jpg|.svg|.webp|.gif|.doc|.docx|.json|.pdf|.css|.js|.html|.md|.xlsx|.xls|.ttf|.woff|.ftl|.otf|.woff2)$}")
    private String allowRegex;

    public AssetFileServiceImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<FileDTO> get(String folderR) throws Exception {
        String folder = beautify(folderR);
        Resource resource = resourceLoader.getResource(ResourceUtils.FILE_URL_PREFIX + root + folder);
        if (!resource.exists()) {
            throw new Exception("Folder is not exists:" + root + folder);
        }
        File[] listOfFiles = resource.getFile().listFiles();
        if (listOfFiles != null) {
            return Arrays.stream(listOfFiles).map(file -> {
                try {
                    Resource fileResource = resourceLoader.getResource(ResourceUtils.FILE_URL_PREFIX + file.getPath());
                    String name = file.getName();
                    String path = folder + "/" + file.getName();
                    path = path.replace("//", "/");
                    Boolean isFolder = file.isDirectory();
                    Long lastModified = fileResource.lastModified();
                    String data = "";
                    if (!isFolder) {
                        byte[] fileContent = Files.readAllBytes(Paths.get(file.getPath()));
                        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
                        data += "data:" + mimeType + ";base64,";
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
    public void upload(String folderR, List<FileDTO> fileDTOs) throws Exception {
        String folder = beautify(folderR);
        Resource resource = resourceLoader.getResource(ResourceUtils.FILE_URL_PREFIX + root + folder);
        if (!resource.exists()) {
            throw new Exception("Folder is not exists:" + root + folder);
        }
        List<String> errors = new ArrayList<>();
        for (FileDTO fileDTO : fileDTOs) {
            File file = new File(resource.getFile(), fileDTO.getName());

            if (file.exists()) {
                errors.add("File " + fileDTO.getName() + " is really exists");
                continue;
            }
            if (!fileDTO.getIsFolder() && !allowEx(fileDTO.getName())) {
                errors.add("File " + fileDTO.getName() + " is not allow. Only files are allowed to upload: .png, jpg, svg, webp, gif, doc, docx, json, pdf, css, js, html, md, xlsx, xls, ttf, woff, otf, woff2");
                continue;
            }

            boolean isCreated = fileDTO.getIsFolder() ? file.mkdirs() : file.createNewFile();

            if (!isCreated) {
                errors.add((fileDTO.getIsFolder() ? "Cannot create " : "Cannot upload ") + fileDTO.getName());
                continue;
            }
            if (!fileDTO.getIsFolder()) {
                String base64String = fileDTO.getData();
                byte[] decodedBytes = Base64.getDecoder().decode(base64String.getBytes(StandardCharsets.UTF_8));
                Files.write(Paths.get(file.getPath()), decodedBytes);
            }
        }
        if (!errors.isEmpty()) {
            throw new ThingsboardException("ERRORS: - " + String.join(", - ", errors), ThingsboardErrorCode.BAD_REQUEST_PARAMS);
        }
    }

    @Override
    public void delete(String folderR) throws Exception {
        String folder = beautify(folderR);
        Resource rootResource = resourceLoader.getResource(ResourceUtils.FILE_URL_PREFIX + root);
        Resource resource = resourceLoader.getResource(ResourceUtils.FILE_URL_PREFIX + root + folder);
        if (rootResource.equals(resource)) {
            throw new Exception("Folder is root can not delete");
        }

        if (!resource.exists()) {
            throw new Exception("Folder is not exists:" + root + folder);
        }
        File file = resource.getFile();
        List<String> errors = new ArrayList<>();
        Files.walk(Paths.get(file.getPath())).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(file1 -> {
            boolean isDelete = file1.delete();
            if (!isDelete){
                errors.add(file1.getName());
            }
        });
        if (!errors.isEmpty()) {
            throw new ThingsboardException("Cannot delete file: - " + String.join(", - ", errors), ThingsboardErrorCode.BAD_REQUEST_PARAMS);
        }
    }

    public String beautify(String folder) throws Exception {
        String folderBeautify = folder;
        if (!folderBeautify.startsWith("/")) {
            folderBeautify = "/" + folderBeautify;
        }
        if (validDotDot((folderBeautify))){
            throw new Exception("Folder is not valid: "+folder);
        }
        return folderBeautify;
    }

    public boolean validDotDot(String folder){
        return folder.matches(".*\\.\\./.*");
    }

    private boolean allowEx(String name) {
        return name.toLowerCase().matches(allowRegex);
    }
}
