package com.apps.essentials.essentials.service.pdf;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author RiJAS
 * @Date 26-10-2024
 */
@Configuration
public class FontConfig {

    @Bean("malayalamFont")
    public Font getMalayalamFont() throws IOException {
        String fontPath = "NotoSansMalayalam-Regular.ttf";
        BaseFont baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font malayalamFont = new Font(baseFont, 12, Font.NORMAL);
        return malayalamFont;
    }

}
