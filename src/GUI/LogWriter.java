package GUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogWriter{
    String line;

    public void setLine(String my_line){
        line = my_line;
    }

    public void startWrite() throws IOException {
        write(line);
    }

    public void write(String line) throws IOException {
        File tempFile = new File("src/GUI/Logs/Game_Log.txt");
        if(tempFile.exists()){
            fileAppender(line);
        }
        else {
            newFileWriter(line);
        }
    }

    public void newFileWriter(String line) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/GUI/Logs/Game_Log.txt"));
        writer.write(line);
        writer.close();
    }

    public void fileAppender(String line) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/GUI/Logs/Game_Log.txt", true));
        writer.newLine();
        writer.append(line);
        writer.close();
    }
}

