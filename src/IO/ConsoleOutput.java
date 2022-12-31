package IO;

import java.io.File;
import java.util.ArrayList;

public class ConsoleOutput {
    /**
     * выводит список названий файлов в консоль
     * @param filesList список, который надо вывести
     */
    public static void printFilesListToConsole(ArrayList<File> filesList){
        for (File file: filesList) {
            System.out.println("-" +file);
        }
    }
}
