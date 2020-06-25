package client;

import java.io.*;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.input.ReversedLinesFileReader;

public class History {

    private static File file;

    public static void createFile(String login) throws IOException {
        file = new File("history_" + login + ".txt");
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    public static void setHistory(String msg) throws IOException {
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(msg.trim());
        bw.newLine();
        bw.flush();
    }

    public static String getHistory() throws IOException {
        int lines = 0;

        FileReader fr = new FileReader(file);
        LineNumberReader lnr = new LineNumberReader(fr);

        while (lnr.readLine() != null) {
            lines++;
        }

        ReversedLinesFileReader rlfr = new ReversedLinesFileReader(file, StandardCharsets.UTF_8);

        StringBuilder sb = new StringBuilder();
        for (int i = 0, j = 100; (i < lines) & (j > 0); i++, j--) {
            sb.append(rlfr.readLine() + "\n");
        }

        return sb.toString();

    }
}
