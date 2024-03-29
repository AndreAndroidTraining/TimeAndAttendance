import java.io.*;
import java.util.Objects;

public class CSVReader {
    public static DataFrame readCSV(String filePath) throws IOException {
        System.out.println("******************FILE NAME ******************** " + filePath);
        return new DataFrame(readString(filePath), ',');
    }

    public static DataFrame readTSV(String filePath) throws IOException {
        return new DataFrame(readString(filePath), '\t');
    }

    private static String readString(String filename) throws IOException {
        System.out.println("******************FILE NAME ******************** " + filename);
        File file = new File(filename);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);


        BufferedReader reader = new BufferedReader(fr);
        String line = null;
        StringBuilder builder = new StringBuilder();
        while((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        reader.close();
        return builder.toString();
    }
}
