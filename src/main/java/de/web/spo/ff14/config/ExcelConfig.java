package de.web.spo.ff14.config;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
public class ExcelConfig {

    @Bean
    public XSSFWorkbook excelWorkbook() throws IOException, InvalidFormatException {
        File file = new File("C:\\Users\\Spocks\\Documents\\isle_sim.xlsx");
        return new XSSFWorkbook(file);
    }

    @Bean
    public XSSFSheet excelSheetCurrentWeek(XSSFWorkbook excelWorkbook) {
        return excelWorkbook.getSheet("Week 18");
    }

    @Bean
    public XSSFSheet excelSheetPeakMapping(XSSFWorkbook excelWorkbook) {
        return excelWorkbook.getSheet("Peak Mapping");
    }

}
