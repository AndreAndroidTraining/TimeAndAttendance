import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        //System.out.println("Hello world!");
        CombineClockTimes clockTimes = new CombineClockTimes();

        String fileURL2 = "C:\\Users\\Public\\Documents\\clocking.csv";
        String fileURL = "C:\\Users\\ekste\\Downloads\\clocking.csv";
        String filePath = "clocking.csv";
        String outputFilePath = "C:\\Users\\ekste\\Downloads\\Converted.csv";

        List<Record> stanley = getEmployeeByName("Stanley", fileURL);
        List<String[]> inTimeByDate = getInTimeByDate(stanley).stream().distinct().toList();

        for (String[] str : inTimeByDate) {
            System.out.println(Arrays.toString(str));
        }
    }

    public static List<Record> getEmployeeByName (String name, String filePath)
    {

        try {

            System.out.println(filePath);
            DataFrame dataFrame = CSVReader.readCSV(filePath);
            DataFrame employee = dataFrame.filter(4, true, name);
            return employee.values();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<Record> getEmployeeBySurname (String surname, String filePath)
    {

        try {
            System.out.println(filePath);
            DataFrame dataFrame = CSVReader.readCSV(filePath);
            DataFrame employee = dataFrame.filter(5, true, surname);

            return employee.values();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String[]> getInTimeByDate(List<Record> records){

        System.out.println(records);
        String previousDate = String.valueOf(records.get(0).getField(1).asString());
        String previousTime = String.valueOf(records.get(0).getField(2).asString());
        String outTime = "";
        List<String[]> newList = new ArrayList<>();
        //System.out.println(previousDate);

        for (Record r: records) {
            if (!r.getField(1).asString().equals(previousDate)){
                previousDate=r.getField(1).asString();
                previousTime=r.getField(2).asString();
            }else if(r.getField(1).asString().equals(previousDate)&&!r.getField(2).asString().equals(previousTime)){
                outTime = r.getField(2).asString();
                //System.out.println(previousDate+" " + previousTime+ " " + outTime);
                String time = "Time In: "+previousTime+" Time Out: "+ outTime;
                String[] array = {r.getField(4).asString(), r.getField(5).asString(), previousDate, time};
                newList.add(array);
            }

        }
        //System.out.println(previousDate+" " + previousTime+ " " + outTime);
        return newList;
    }
}
