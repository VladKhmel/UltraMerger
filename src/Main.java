
import IO.ConsoleInput;
import IO.ConsoleOutput;
import Models.FilesDependencyListBuilder;

import java.io.*;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        FilesDependencyListBuilder fileMerger;
        try {
            System.out.println("Введите путь до папки, в которой хотите отсортировать файлы.");
            fileMerger = new FilesDependencyListBuilder(ConsoleInput.inputDirectoryPath().trim());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        ArrayList<File> sortedFiles = fileMerger.getSortedFilesList();
        ArrayList<File> filesWithLoop = fileMerger.getAllLoopedFiles();

        if (!filesWithLoop.isEmpty()) {
            System.out.println("Из-за наличия циклической зависимости у файлов в данном папке невозможно построить их сортированный список, все файлы, которые получилось объединить были сконкатенированы в файл.");
            System.out.println("Вот список файлов, в которых возникла проблема:");
            ConsoleOutput.printFilesListToConsole(filesWithLoop);
        }

        System.out.println("Все найденные файлы булут объеденены в одном: введите путь до файла, в котором вы хотите получить результат");
        FileUtils.mergeFiles(sortedFiles, new File(ConsoleInput.inputTextFilePath()));
    }
}