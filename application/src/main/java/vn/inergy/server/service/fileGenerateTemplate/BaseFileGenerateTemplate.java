package vn.inergy.server.service.fileGenerateTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.DocumentException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import io.github.erdos.stencil.API;
import io.github.erdos.stencil.EvaluatedDocument;
import io.github.erdos.stencil.PreparedTemplate;
import io.github.erdos.stencil.TemplateData;
import org.apache.commons.io.IOUtils;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.xhtmlrenderer.pdf.ITextRenderer;
import vn.inergy.server.model.fileGenerate.FileGenerateDTO;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@Service
public class BaseFileGenerateTemplate implements FileGenerateTemplate {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Configuration freemarkerConfig;

    @Override
    public byte[] generate(FileGenerateDTO fileGenerateDTO) throws Exception {
        MultipartFile template = fileGenerateDTO.getTemplate();
        byte[] fileContent = new byte[0];
        if (template == null) {
            return fileContent;
        }
        Map<String, Object> vars = objectMapper.readValue(fileGenerateDTO.getVars(), HashMap.class);
        String fileContentType = template.getContentType();
        String originalFilename = template.getOriginalFilename();
        if (FileGenerateConst.EXCEL2003.getValue().equals(fileContentType) || FileGenerateConst.EXCEL_WORKBOOK.getValue().equals(fileContentType)) {
            fileContent = excel(template, vars);
        }
        if (FileGenerateConst.WORD_2003.getValue().equals(fileContentType) || FileGenerateConst.WORD_DOCUMENT.getValue().equals(fileContentType)) {
            fileContent = word(template, vars);
        }
        if (originalFilename != null && originalFilename.endsWith(".ftl")) {
            fileContent = pdf(template, vars);
        }
        return fileContent;
    }

    @Override
    public String getFile(FileGenerateDTO fileGenerateDTO) {
        MultipartFile template = fileGenerateDTO.getTemplate();
        String originalFilename = template.getOriginalFilename();
        if (originalFilename != null && originalFilename.endsWith(".ftl")) {
            return originalFilename.replace(".ftl",".pdf");
        }
        return originalFilename;
    }

    public byte[] excel(MultipartFile template, Map<String, Object> vars) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Context context = new Context();
        for (String key : vars.keySet()) {
            context.putVar(key, vars.get(key));
        }
        JxlsHelper.getInstance().processTemplate(template.getInputStream(), os, context);
        return os.toByteArray();
    }

    public byte[] word(MultipartFile template, Map<String, Object> vars) throws IOException {
        File theDir = new File("/temporary");
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        Path templatePath = Path.of("/temporary/" + template.getOriginalFilename());
        template.transferTo(templatePath);
        File file = templatePath.toFile();
        PreparedTemplate preparedTemplate = API.prepare(file);
        TemplateData data = TemplateData.fromMap(vars);
        EvaluatedDocument result = API.render(preparedTemplate, data);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        result.writeToStream(os);
        file.deleteOnExit();
        return os.toByteArray();
    }

    public byte[] pdf(MultipartFile multipartFile, Map<String, Object> vars) throws Exception {
        ByteArrayInputStream stream = new ByteArrayInputStream(multipartFile.getBytes());
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
