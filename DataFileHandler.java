import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Клас DataFileHandler управляє роботою з файлами даних byte.
 */
public class DataFileHandler {
    /**
     * Завантажує масив об'єктів byte з файлу.
     * 
     * @param filePath Шлях до файлу з даними.
     * @return Масив об'єктів byte.
     */
    public static Byte[] loadArrayFromFile(String filePath) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            return fileReader.lines()
                    .map(currentLine -> currentLine.trim().replaceAll("^\\uFEFF", ""))
                    .filter(currentLine -> !currentLine.isEmpty())
                    .map(Byte::parseByte)
                    .toArray(Byte[]::new);
        } catch (IOException ioException) {
            throw new RuntimeException("Помилка читання даних з файлу: " + filePath, ioException);
        }
    }

    /**
     * Зберігає масив об'єктів byte у файл.
     * 
     * @param byteArray Масив об'єктів byte.
     * @param filePath Шлях до файлу для збереження.
     */
    public static void writeArrayToFile(Byte[] byteArray, String filePath) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath))) {
            String content = Arrays.stream(byteArray)
                    .map(String::valueOf)
                    .collect(Collectors.joining(System.lineSeparator()));
           
            fileWriter.write(content);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
