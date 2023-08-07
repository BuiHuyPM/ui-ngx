package vn.inergy.server.model.fileGenerate;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class FileGenerateDTO {
    @NotNull
    MultipartFile template;

    String vars;
}
