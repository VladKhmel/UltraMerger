package IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Класс, работающий с вводом с консоли
 */
public class ConsoleInput {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * запрашивает и проверяет ввод адреса до директории с консоли
     * @return адресс папки
     * @throws FileNotFoundException если папки с введенным адресом не существует
     */
    public static String inputDirectoryPath() throws FileNotFoundException {
        String userInput = scanner.nextLine();
        File file = new File(userInput);
        if (file.exists() && file.isDirectory()) {
            return userInput;
        }
        throw new FileNotFoundException("Нет файла с таким адресом.");
    }

    /**
     * запрашивает у пользователя ввод адреса до тектового файла
     * @return введенный адресс
     */
    public static String inputTextFilePath() {
        return scanner.nextLine();
    }}
