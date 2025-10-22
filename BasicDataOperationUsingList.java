import java.util.Vector;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Клас BasicDataOperationUsingList реалізує операції з колекціями типу Vector для даних byte.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataOperations()} - Виконує комплекс операцій з даними.</li>
 *   <li>{@link #performArraySorting()} - Упорядковує масив елементів byte.</li>
 *   <li>{@link #findInArray()} - Здійснює пошук елемента в масиві byte.</li>
 *   <li>{@link #locateMinMaxInArray()} - Визначає найменше і найбільше значення в масиві.</li>
 *   <li>{@link #sortList()} - Сортує колекцію List з byte.</li>
 *   <li>{@link #findInList()} - Пошук конкретного значення в списку.</li>
 *   <li>{@link #locateMinMaxInList()} - Пошук мінімального і максимального значення в списку.</li>
 * </ul>
 */
public class BasicDataOperationUsingList {
    private byte byteValueToSearch;
    private Byte[] byteArray;
    private List<Byte> dateTimeList;

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
     * @param byteValueToSearch Значення для пошуку
     * @param byteArray Масив byte
     */
    BasicDataOperationUsingList(byte byteValueToSearch, Byte[] byteArray) {
        this.byteValueToSearch = byteValueToSearch;
        this.byteArray = byteArray;
        this.dateTimeList = new Vector<>(Arrays.asList(byteArray));
    }
    
    /**
     * Виконує комплексні операції з структурами даних.
     * 
     * Метод завантажує масив і список об'єктів byte, 
     * здійснює сортування та пошукові операції.
     */
    public void executeDataOperations() {
        // спочатку працюємо з колекцією List
        findInList();
        locateMinMaxInList();
        
        sortList();
        
        findInList();
        locateMinMaxInList();

        // потім обробляємо масив чисел
        findInArray();
        locateMinMaxInArray();

        performArraySorting();
        
        findInArray();
        locateMinMaxInArray();

        // зберігаємо відсортований масив до окремого файлу
        DataFileHandler.writeArrayToFile(byteArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Упорядковує масив об'єктів byte за зростанням.
     * Фіксує та виводить тривалість операції сортування в наносекундах.
     */
    void performArraySorting() {
        long timeStart = System.nanoTime();

        Arrays.sort(byteArray);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву чисел");
    }

    /**
     * Здійснює пошук заданого типу даних.
     */
    void findInArray() {
        long timeStart = System.nanoTime();

        int position = Arrays.binarySearch(this.byteArray, byteValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в масивi чисел");

        if (position >= 0) {
            System.out.println("Елемент '" + byteValueToSearch + "' знайдено в масивi за позицією: " + position);
        } else {
            System.out.println("Елемент '" + byteValueToSearch + "' відсутній в масиві.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в масиві чисел.
     */
    void locateMinMaxInArray() {
        if (byteArray == null || byteArray.length == 0) {
            System.out.println("Масив є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();

        byte minValue = byteArray[0];
        byte maxValue = byteArray[0];

        for (byte currentDateTime : byteArray) {
            if (byteValueToSearch < minValue) {
                minValue = currentDateTime;
            }
            if (byteValueToSearch > maxValue) {
                maxValue = currentDateTime;
            }
        }

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмального i максимального числа в масивi");

        System.out.println("Найменше значення в масивi: " + minValue);
        System.out.println("Найбільше значення в масивi: " + maxValue);
    }

    /**
     * Шукає конкретне значення чисел в колекції Vector.
     */
    void findInList() {
        long timeStart = System.nanoTime();

        int position = Collections.binarySearch(this.dateTimeList, byteValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в List чисел");        

        if (position >= 0) {
            System.out.println("Елемент '" + byteValueToSearch + "' знайдено в Vector за позицією: " + position);
        } else {
            System.out.println("Елемент '" + byteValueToSearch + "' відсутній в Vector.");
        }
    }

    /**
     * Визначає найменше і найбільше значення в колекції Vector з числами.
     */
    void locateMinMaxInList() {
        if (dateTimeList == null || dateTimeList.isEmpty()) {
            System.out.println("Колекція Vector є пустою або не ініціалізованою.");
            return;
        }

        long timeStart = System.nanoTime();

        byte minValue = Collections.min(dateTimeList);
        byte maxValue = Collections.max(dateTimeList);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмального i максимального числа в List");

        System.out.println("Найменше значення в List: " + minValue);
        System.out.println("Найбільше значення в List: " + maxValue);
    }

    /**
     * Упорядковує колекцію List з об'єктами byte за зростанням.
     * Відстежує та виводить час виконання операції сортування.
     */
    void sortList() {
        long timeStart = System.nanoTime();

        Collections.sort(dateTimeList);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування Vector числа");
    }
}