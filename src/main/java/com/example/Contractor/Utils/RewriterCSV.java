package com.example.Contractor.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Uses for rewriting .csv files into correct format.
 * <p>
 * Since some data files can be written in invalid format (has more columns than needed),
 * it is necessary to overwrite them.
 */
public final class RewriterCSV {

    private RewriterCSV() {}

    /**
     * Program entry point.
     * <p>
     * Calls {@code rewrite} method for each passed file.
     *
     * @param args files location
     */
    public static void main(String[] args) {
        for (String arg : args) {
            rewrite(arg);
        }
    }

    /**
     * Converts data to valid format.
     * <p>
     * After processing creates file "rewrited_[file_name]",
     * where [file_name] the name of passed file.
     *
     * @param source file path
     */
    public static void rewrite(String source) {
        try {
            File file = new File(source);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            FileWriter writer = new FileWriter(file.getPath().substring(0,
                    1 + file.getPath().lastIndexOf('\\')) + "rewrited_" + file.getName());

            StringBuilder builder;
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");
                builder = new StringBuilder(values[0]).append(";");
                for (int i = 1; i < values.length; i++) {
                    builder.append(values[i]);
                }
                writer.write(builder.append("\n").toString());
            }
            reader.close();
            writer.close();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
