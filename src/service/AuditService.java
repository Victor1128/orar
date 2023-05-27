package service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    FileWriter fileWriter;

    public void write(String action) throws IOException {
        String now = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        fileWriter.write(action + " " + now + "\n");
        fileWriter.flush();
    }
    public AuditService() {
        try {
            File file = new File("data/audit.csv");
            file.createNewFile();
            this.fileWriter = new FileWriter(file, true);
        } catch (IOException e) {
            System.out.println(e);
        }
    }


}
