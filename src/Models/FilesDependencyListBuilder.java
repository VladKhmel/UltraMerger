package Models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class FilesDependencyListBuilder {
    String root;
    ArrayList<FileDependencyList> files;

    public FilesDependencyListBuilder(String root) {
        this.root = root + "\\";
        files = findAllTextFilesInDirectoryAndChildren(new File(root));
    }

    /**
     * Класс, представляющий собой структуру, хранящую необходимые файлы для данного файла
     */
    private class FileDependencyList {
        File file;
        List<File> requiredFiles;

        FileDependencyList(File file) {
            this.file = file;
            requiredFiles = findRequiredFiles();
        }

        /**
         * Читает файл и составляет список из файлов, которые необходимы для него по ключевому слову "require"
         * @return список зависимости
         */
        private ArrayList<File> findRequiredFiles() {
            ArrayList<File> required = new ArrayList<File>();
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String[] arr = scanner.nextLine().split(" ", 2);
                    if (Objects.equals(arr[0], "require")) {
                        required.add(new File(root + arr[1].substring(1, arr[1].length() - 1)));
                    }
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return required;
        }

        public String toString() {
            StringBuilder s = new StringBuilder(file.toString());
            s.append(" требует: ");
            for (File child : requiredFiles) {
                s.append(child.toString()).append(" ");
            }
            return s.toString();
        }
    }

    /**
     *  Находит все файлы в данной директории и возвращает FileDependencyList-ы для них
     * @param directory директория, в которой надо найти все файлы
     * @return список файлов из директории
     */
    private ArrayList<FileDependencyList> findAllTextFilesInDirectory(File directory) {
        ArrayList<FileDependencyList> textFiles = new ArrayList<>();
        try {
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    textFiles.add(new FileDependencyList(file));
                }
            }
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }
        return textFiles;
    }

    /**
     * Находит все файлы в данной директории и ее поддиректориях и возвращает FileDependencyList-ы для них
     * @param directory директория, в которой надо найти все файлы
     * @return список файлов из директории
     */
    private ArrayList<FileDependencyList> findAllTextFilesInDirectoryAndChildren(File directory) {
        ArrayList<FileDependencyList> textFiles = findAllTextFilesInDirectory(directory);
        File[] files = directory.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                textFiles.addAll(findAllTextFilesInDirectoryAndChildren(file));
            }
        }
        return textFiles;
    }

    /**
     * Сортирует все файлы в папке root в зависимости от того, какие файлы для них требуются
     * @return сортированный список файлов
     */
    public ArrayList<File> getSortedFilesList() {
        ArrayList<File> sortedFiles = new ArrayList<File>();
        FileDependencyList tree = FileFirstEmpty(files);

        while (tree != null) {
            sortedFiles.add(tree.file);

            for (FileDependencyList i : files) {
                i.requiredFiles.remove(tree.file);
            }
            files.remove(tree);

            tree = FileFirstEmpty(files);
        }
        return sortedFiles;
    }

    /**
     * получает все файлы, которые имеют цикл зависимости
     * @return список зацикленных файлов
     */
    public ArrayList<File> getAllLoopedFiles() {
        ArrayList<File> relatedFiles = new ArrayList<File>();
        for (FileDependencyList file : files) {
            if (!file.requiredFiles.isEmpty()) {
                relatedFiles.add(file.file);
            }
        }
        return relatedFiles;
    }

    /**
     * возвращает первый найденный файл в списке, для которого все зависимости ужен выполнены. Вспомогательный метод для сортировки
     * @param filesList список файлов, откуда мы ищем файл
     * @return  найденный файл, если такой есть, если таких нет - null
     */
    private FileDependencyList FileFirstEmpty(ArrayList<FileDependencyList> filesList) {
        for (FileDependencyList file : filesList) {
            if (file.requiredFiles.isEmpty()) {
                return file;
            }
        }
        return null;
    }
}
