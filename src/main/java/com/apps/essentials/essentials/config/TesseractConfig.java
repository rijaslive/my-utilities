package com.apps.essentials.essentials.config;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

@Configuration
@Slf4j
public class TesseractConfig {

    public static final String PRESERVE_INTERWORD_SPACES = "preserve_interword_spaces";
    @Value("${tesseract.data.path}")
    private String tesseractDataPath;

    private final String  OMP_THREAD_LIMIT = "OMP_THREAD_LIMIT";

    @Bean
    @Primary
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    Tesseract tesseract() {
        log.info("Loading tesseract default bean ");
        System.setProperty(OMP_THREAD_LIMIT, String.valueOf(Runtime.getRuntime().availableProcessors()));
        Tesseract tesseract = new Tesseract();
        //extraction works well with  PSM_SINGLE_BLOCK
        tesseract.setPageSegMode(ITessAPI.TessPageSegMode.PSM_SINGLE_COLUMN);
        tesseract.setOcrEngineMode(ITessAPI.TessOcrEngineMode.OEM_LSTM_ONLY);
        tesseract.setDatapath(tesseractDataPath);
        //files of the example : https://github.com/tesseract-ocr/tessdata
        log.info("Tesseract data => {}", tesseractDataPath);
        return tesseract;
    }

}