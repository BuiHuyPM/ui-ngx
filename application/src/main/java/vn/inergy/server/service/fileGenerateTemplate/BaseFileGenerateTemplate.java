package vn.inergy.server.service.fileGenerateTemplate;

import freemarker.template.Configuration;
import freemarker.template.Template;
import io.github.erdos.stencil.API;
import io.github.erdos.stencil.EvaluatedDocument;
import io.github.erdos.stencil.PreparedTemplate;
import io.github.erdos.stencil.TemplateData;
import org.apache.commons.io.IOUtils;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.ResourceUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;
import vn.inergy.server.model.fileGenerate.FileGenerateDTO;
import vn.inergy.server.service.assetFiles.config.AssetConfig;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;


@Service
public class BaseFileGenerateTemplate implements FileGenerateTemplate {
    private final Configuration freemarkerConfig;
    private final ResourceLoader resourceLoader;

    public BaseFileGenerateTemplate(Configuration freemarkerConfig, ResourceLoader resourceLoader) {
        this.freemarkerConfig = freemarkerConfig;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public byte[] generate(FileGenerateDTO fileGenerateDTO) throws Exception {
        File file = getFile(fileGenerateDTO.getPath());
        Path path = file.toPath();
        String mimeType = Files.probeContentType(path);
        String originalFilename = file.getName();
        Map<String, Object> vars = fileGenerateDTO.getVars();
        byte[] fileContent = new byte[0];
        if (FileGenerateConst.EXCEL2003.getValue().equals(mimeType) || FileGenerateConst.EXCEL_WORKBOOK.getValue().equals(mimeType)) {
            fileContent = excel(file, vars);
        }
        if (FileGenerateConst.WORD_2003.getValue().equals(mimeType) || FileGenerateConst.WORD_DOCUMENT.getValue().equals(mimeType)) {
            fileContent = word(file, vars);
        }
        if (originalFilename.endsWith(".ftl")) {
            fileContent = pdf(file, vars);
        }
        return fileContent;
    }

    @Override
    public String getFileName(FileGenerateDTO fileGenerateDTO) throws Exception {
        File file = getFile(fileGenerateDTO.getPath());
        String originalFilename = file.getName();
        if (originalFilename.endsWith(".ftl")) {
            return originalFilename.replace(".ftl", ".pdf");
        }
        return originalFilename;
    }

    public File getFile(String path) throws Exception {
        String uploadPath = AssetConfig.folderPath;
        String local = ResourceUtils.FILE_URL_PREFIX + uploadPath;
        if (!path.startsWith("/")) {
            local += "/";
        }
        Resource resource = resourceLoader.getResource(local + path);
        if (!resource.exists()) {
            throw new Exception("File not found");
        }
        return resource.getFile();
    }

    public byte[] excel(File file, Map<String, Object> vars) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Context context = new Context();
        for (String key : vars.keySet()) {
            context.putVar(key, vars.get(key));
        }
        InputStream inputStream = new FileInputStream(file);
        JxlsHelper.getInstance().processTemplate(inputStream, os, context);
        return os.toByteArray();
    }

    public byte[] word(File file, Map<String, Object> vars) throws IOException {
        PreparedTemplate preparedTemplate = API.prepare(file);
        TemplateData data = TemplateData.fromMap(vars);
        EvaluatedDocument result = API.render(preparedTemplate, data);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        result.writeToStream(os);
        return os.toByteArray();
    }

    public byte[] pdf(File file, Map<String, Object> vars) throws Exception {
        FileInputStream stream = new FileInputStream(file);
        String fileString = IOUtils.toString(stream, StandardCharsets.UTF_8);
        Template template = new Template("templateName", new StringReader(fileString), freemarkerConfig);
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, vars);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(os);
        return os.toByteArray();
    }
}
