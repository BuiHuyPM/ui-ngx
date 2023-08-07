package vn.inergy.server.service.fileGenerateTemplate;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FileGenerateConst {
    EXCEL2003("application/vnd.ms-excel", "Excel 2003"),
    EXCEL_WORKBOOK("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "Excel WorkBook"),
    WORD_2003("application/msword", "Word 2003"),
    WORD_DOCUMENT("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "Word Document"),
    PDF("application/pdf", "PDF");
    private final String value;
    private final String name;
    FileGenerateConst(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
