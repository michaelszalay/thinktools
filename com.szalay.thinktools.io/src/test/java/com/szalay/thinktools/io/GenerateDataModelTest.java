package com.szalay.thinktools.io;

import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class GenerateDataModelTest {
    private static final int NUMBER_OF_FACTORS = 100;
    private static final int NUMBER_OF_DATASETS = 1000;

    @Test
    @Ignore
    public void generateCSV() throws FileNotFoundException {

        final FileOutputStream fout = new FileOutputStream("src/test/resources/generatedModel.csv");
        final PrintWriter out = new PrintWriter(fout);
        for (int i = 0; i < NUMBER_OF_FACTORS; i++) {
            out.print("Factor" + i);
            out.print(",");
        }
        out.print("\n");

        for (int row = 0; row < NUMBER_OF_DATASETS; row++) {
            for (int column = 0; column < NUMBER_OF_FACTORS; column++) {
                out.print(Math.random());
                out.print(",");
            }

            out.print("\n");
        }
    }
}
