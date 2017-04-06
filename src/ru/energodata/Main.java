package ru.energodata;

import java.io.*;
import java.nio.file.InvalidPathException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: IndexExtractor <path to CHDPA .txt file> <path to result file");
            System.out.println("Example: compareTXT C:\\_data\\KRYEMRYESPUBLIKA_POLNAYA.txt C:\\_data\\crimea_index.txt");
        } else {
            try {
                File inputFile = new File(args[0]);
                File outputFile = new File(args[1]);
                Set<String> indexList = new HashSet();
                        try(BufferedReader rdr1 = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "Windows-1251")))
                        {
                            Pattern pattern = Pattern.compile("[0-9]{6}");
                            for (String line; (line = rdr1.readLine()) != null;) {
                                Matcher matcher = pattern.matcher(line);
                                if (matcher.find())
                                {
                                    int size1 = indexList.size();
                                    indexList.add(matcher.group(0));
                                    int size2 = indexList.size();
                                    if (size2 > size1) System.out.println(matcher.group(0));
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Обработка завершена");
                        collectionToTXT(indexList, outputFile);
            }
            catch (InvalidPathException |  NullPointerException ex) {
                ex.printStackTrace();
                System.out.println(args[0] + " is not correct file");
            }
        }
    }
    private static void collectionToTXT(Set<String> list, File outputFile) {
        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "Windows-1251"));
            for (String string : list)
            {
                bw.write(string);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e){e.printStackTrace();}
    }
}