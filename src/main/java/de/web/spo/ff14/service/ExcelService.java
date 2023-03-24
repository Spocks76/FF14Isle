package de.web.spo.ff14.service;

import de.web.spo.ff14.model.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class ExcelService {

    private final XSSFSheet excelSheetCurrentWeek;
    private final XSSFSheet excelSheetPeakMapping;

    public ExcelService(XSSFSheet excelSheetCurrentWeek, XSSFSheet excelSheetPeakMapping) {
        this.excelSheetCurrentWeek = excelSheetCurrentWeek;
        this.excelSheetPeakMapping = excelSheetPeakMapping;
    }

    @Bean
    public WeeklyProducts weeklyProducts() {
        var weeklyProducts = new WeeklyProducts();
        for(var rowIndex = 4; rowIndex < 64; rowIndex++ ) {
            var row = excelSheetCurrentWeek.getRow(rowIndex);
            var peakKey = row.getCell(1).getStringCellValue();
            var productName = row.getCell(2).getStringCellValue();
            var popName = row.getCell(3).getStringCellValue();
            var product = Product.productMap.get(productName);
            var popularity = Popularity.popularityMap.get(popName);
            weeklyProducts.addProduct(product, popularity, peakKey);
        }
        return weeklyProducts;
    }

    @Bean
    public List<CycleValues> cycleValuesList() {
        int maxCycle;
        for(maxCycle = 0; excelSheetCurrentWeek.getRow(4).getCell(4 + maxCycle * 2) != null
                && StringUtils.hasText(excelSheetCurrentWeek.getRow(4).getCell(4 + maxCycle * 2).getStringCellValue()); maxCycle++);
        var cycleValuesList = new ArrayList<CycleValues>(maxCycle);
        for(var cycleIndex = 0; cycleIndex < maxCycle; cycleIndex++) {
            var cycleValues = new CycleValues();
            cycleValuesList.add(cycleValues);
            for(var rowIndex = 4; rowIndex < 64; rowIndex++ ) {
                var row = excelSheetCurrentWeek.getRow(rowIndex);
                var productName = row.getCell(2).getStringCellValue();
                var supplyName = row.getCell(4 + cycleIndex * 2).getStringCellValue();
                var shiftName = row.getCell(5 + cycleIndex * 2).getStringCellValue();
                var product = Product.productMap.get(productName);
                var supply = Supply.supplyMap.get(supplyName);
                var shift = DemandShift.demandShiftMap.get(shiftName);
                cycleValues.addCycleValue(product, new CycleValue(0, supply, shift, 0));
            }
        }
        return cycleValuesList;
    }

    @Bean
    public Map<String, List<String>> peakKeyMapping() {
        var map = new HashMap<String, List<String>>();
        for(var mappingRow = 1;excelSheetPeakMapping.getRow(mappingRow) != null; mappingRow++) {
            map.put(excelSheetPeakMapping.getRow(mappingRow).getCell(1).getStringCellValue(), Arrays.asList(excelSheetPeakMapping.getRow(mappingRow).getCell(2).getStringCellValue().split(",")));
        }
        return map;
    }
}
