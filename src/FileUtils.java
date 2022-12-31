import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtils {

    /**
     * копирует содержимое файла в ддругой файл
     * @param fileToRead файл, который копируется
     * @param fileToWrite файл, куда копируется
     */
    static void copyFileToFile(File fileToRead, File fileToWrite) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileToWrite, true))) {
            Scanner scanner = new Scanner(fileToRead);
            while (scanner.hasNextLine()) {
                bw.write(scanner.nextLine());
                bw.write(System.lineSeparator());
            }
            scanner.close();
        } catch (IOException ex) {
            System.out.println("В файле " + fileToRead + "возникла какая-то ошибка, прочитать его не получится.");
        }
    }

    /**
     * объединяет список файлов в один
     * @param files список файлов
     * @param resultFile файл, в который надо объединить содержание всех
     */
    static void mergeFiles(ArrayList<File> files, File resultFile) {
        for (File file : files) {
            copyFileToFile(file, resultFile);
        }
    }
}
