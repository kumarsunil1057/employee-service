package org.example.vmware.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.vmware.model.Employee;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;



public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = { "Name", "Age"};

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<Employee> csvToEmployees(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Employee> employees = new ArrayList<Employee>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                String name = csvRecord.get("Name");
                String age = csvRecord.get("Age");
                if(verifyName(name) && verifyAge(age)) {
                    Employee emp = new Employee(
                            name,
                            Integer.parseInt(age)
                    );

                    employees.add(emp);
                }else {
                    //TODO: add it to errors list
                    //skip
                }
            }

            return employees;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }
    }

    public static boolean verifyName(String name){
        if(name == null || name.length()==0){
            return false;
        }
        if(StringUtils.isNumeric(name))
            return false;
        return true;
    }

    public static boolean verifyAge(String age){
        if(age == null || age.length()==0){
            return false;
        }
        if(StringUtils.isNumeric(age))
            return true;
        return false;
    }

}