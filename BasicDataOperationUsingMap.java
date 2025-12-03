import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

/**
 * Клас BasicDataOperationUsingMap реалізує операції з колекціями типу Map для зберігання пар ключ-значення.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataOperations()} - Виконує комплекс операцій з даними Map.</li>
 *   <li>{@link #findByKey()} - Здійснює пошук елемента за ключем в Map.</li>
 *   <li>{@link #findByValue()} - Здійснює пошук елемента за значенням в Map.</li>
 *   <li>{@link #addEntry()} - Додає новий запис до Map.</li>
 *   <li>{@link #removeByKey()} - Видаляє запис з Map за ключем.</li>
 *   <li>{@link #removeByValue()} - Видаляє записи з Map за значенням.</li>
 *   <li>{@link #sortByKey()} - Сортує Map за ключами.</li>
 *   <li>{@link #sortByValue()} - Сортує Map за значеннями.</li>
 * </ul>
 */
public class BasicDataOperationUsingMap {
    // Задані ключ/значення для операцій згідно з завданням
    private final Chinchilla KEY_TO_SEARCH_AND_DELETE = new Chinchilla("Гномик", 6.7);
    private final Chinchilla KEY_TO_ADD = new Chinchilla("Барсик", 5.67);

    private final String VALUE_TO_SEARCH_AND_DELETE = "Зінаїда";
    private final String VALUE_TO_ADD = "Гнат";

    private HashMap<Chinchilla, String> hashtable;
    private LinkedHashMap<Chinchilla, String> treeMap;

    // Значення сортуємо за звичайним порівнянням String (null-значення передані перші)

    /**
     * Внутрішній клас Chinchilla для зберігання інформації про домашню тварину.
     * Тепер характеристиками є кличка (nickname) та вага (weight).
     * Сортування (природний порядок, Comparable):
     *  - nickname — за зменшенням (descending)
     *  - weight   — за зменшенням (descending)
     */
    public static class Chinchilla implements Comparable<Chinchilla> {
        private final String nickname;
        private final Double weight;

        public Chinchilla(String nickname) {
            this.nickname = nickname;
            this.weight = null;
        }

        public Chinchilla(String nickname, Double weight) {
            this.nickname = nickname;
            this.weight = weight;
        }

        public String getNickname() {
            return nickname;
        }

        public Double getWeight() {
            return weight;
        }

        /**
         * Порівнює за nickname (спочатку) та weight (якщо nickname однакові).
         * Обидва поля сортуються за зменшенням.
         */
        @Override
        public int compareTo(Chinchilla other) {
            if (other == null) return 1;

            // Порівняння nickname за спаданням
            if (this.nickname == null && other.nickname != null) return 1;
            if (this.nickname != null && other.nickname == null) return -1;
            if (this.nickname != null && other.nickname != null) {
                int nickComp = other.nickname.compareTo(this.nickname); // інвертоване для спадання
                if (nickComp != 0) return nickComp;
            }

            // Якщо клички однакові (або обидві null) — порівнюємо weight за спаданням
            if (this.weight == null && other.weight == null) return 0;
            if (this.weight == null) return 1; // null йде в кінець при спаданні
            if (other.weight == null) return -1;

            return other.weight.compareTo(this.weight); // інвертоване для спадання
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Chinchilla other = (Chinchilla) obj;

            boolean nickEq = nickname != null ? nickname.equals(other.nickname) : other.nickname == null;
            boolean weightEq = weight != null ? weight.equals(other.weight) : other.weight == null;
            return nickEq && weightEq;
        }

        @Override
        public int hashCode() {
            int result = nickname != null ? nickname.hashCode() : 0;
            long bits = weight != null ? Double.doubleToLongBits(weight) : 0L;
            int weightHash = (int)(bits ^ (bits >>> 32));
            result = 31 * result + weightHash;
            return result;
        }

        @Override
        public String toString() {
            if (weight != null) {
                return "Chinchilla{nickname='" + nickname + "', weight='" + weight + "'}";
            }
            return "Chinchilla{nickname='" + nickname + "'}";
        }
    }

    /**
     * Конструктор, який ініціалізує об'єкт з готовими даними.
     * 
     * @param hashtable HashMap з початковими даними (ключ: Chinchilla, значення: ім'я власника)
     * @param treeMap LinkedHashMap з початковими даними (ключ: Chinchilla, значення: ім'я власника)
     */
    BasicDataOperationUsingMap(HashMap<Chinchilla, String> hashtable, LinkedHashMap<Chinchilla, String> treeMap) {
        this.hashtable = hashtable;
        this.treeMap = treeMap;
    }
    
    /**
     * Виконує комплексні операції з Map.
     * 
     * Метод виконує різноманітні операції з Map: пошук, додавання, видалення та сортування.
     */
    public void executeDataOperations() {
        // Спочатку працюємо з HashMap
        System.out.println("========= Операції з HashMap =========");
        System.out.println("Початковий розмір HashMap: " + hashtable.size());
        
        // Пошук до сортування
        findByKeyInHashMap();
        findByValueInHashMap();

        printHashMap();
        sortHashMap();
        // Вивід незмінної (оригінальної) HashMap
        printHashMap();

        // Пошук після сортування (на тій же оригінальній hashtable)
        findByKeyInHashMap();
        findByValueInHashMap();

        addEntryToHashMap();
        
        removeByKeyFromHashMap();
        removeByValueFromHashMap();
               
        System.out.println("Кінцевий розмір HashMap: " + hashtable.size());

        // Потім обробляємо LinkedHashMap
        System.out.println("\n\n========= Операції з LinkedHashMap =========");
        System.out.println("Початковий розмір LinkedHashMap: " + treeMap.size());
        
        findByKeyInLinkedHashMap();
        findByValueInLinkedHashMap();

        printLinkedHashMap();

    // Сортуємо LinkedHashMap за ключами та виводимо один раз (щоб не множити блоки виводу)
    sortLinkedHashMap();
    printLinkedHashMap();

        addEntryToLinkedHashMap();
        
        removeByKeyFromLinkedHashMap();
        removeByValueFromLinkedHashMap();
        
        System.out.println("Кінцевий розмір LinkedHashMap: " + treeMap.size());
    }


    // ===== Методи для HashMap =====

    /**
     * Виводить вміст HashMap без сортування.
     * HashMap не гарантує жодного порядку елементів.
     */
    private void printHashMap() {
        System.out.println("\n=== Пари ключ-значення в HashMap ===");
        long timeStart = System.nanoTime();

        hashtable.entrySet().forEach(entry ->
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue())
        );

        PerformanceTracker.displayOperationTime(timeStart, "виведення пари ключ-значення в HashMap");
    }

    /**
     * Сортує HashMap за ключами.
     * Використовує Collections.sort() з природним порядком Chinchilla (Chinchilla.compareTo()).
     * Перезаписує hashmap відсортованими даними.
     */
    private void sortHashMap() {
        long timeStart = System.nanoTime();

       hashtable = hashtable.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        HashMap::new
                ));

        PerformanceTracker.displayOperationTime(timeStart, "сортування HashMap за ключами");
    }

    /**
     * Здійснює пошук елемента за ключем в HashMap.
     * Використовує Chinchilla.hashCode() та Chinchilla.equals() для пошуку.
     */
    void findByKeyInHashMap() {
        long timeStart = System.nanoTime();

        boolean found = hashtable.containsKey(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в HashMap");

        if (found) {
            String value = hashtable.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Власник: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в HashMap.");
        }
    }

    /**
     * Здійснює пошук елемента за значенням в HashMap.
     * Сортує список Map.Entry за значеннями та використовує бінарний пошук.
     */
    void findByValueInHashMap() {
        long timeStart = System.nanoTime();

        List<Chinchilla> keysToRemove = hashtable.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());


        keysToRemove.forEach(hashtable::remove);

        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук за значенням в HashMap");

        if (!keysToRemove.isEmpty()) {
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено в HashMap");
        } else {
            System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутній в HashMap.");
        }
    }

    /**
     * Додає новий запис до HashMap.
     */
    void addEntryToHashMap() {
        long timeStart = System.nanoTime();

        hashtable.put(KEY_TO_ADD, VALUE_TO_ADD);

        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до HashMap");

        System.out.println("Додано новий запис: Chinchilla='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
    }

    /**
     * Видаляє запис з HashMap за ключем.
     */
    void removeByKeyFromHashMap() {
        long timeStart = System.nanoTime();

        String removedValue = hashtable.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з HashMap");

        if (removedValue != null) {
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    /**
     * Видаляє записи з HashMap за значенням.
     */
    void removeByValueFromHashMap() {
        long timeStart = System.nanoTime();

        List<Chinchilla> keysToRemove = hashtable.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
       
        keysToRemove.forEach(hashtable::remove);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з HashMap");

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    // ===== Методи для LinkedHashMap =====

    /**
     * Виводить вміст LinkedHashMap.
     * LinkedHashMap автоматично відсортована за ключами (Chinchilla nickname за зростанням, species за спаданням).
     */
    private void printLinkedHashMap() {
        System.out.println("\n=== Пари ключ-значення в LinkedHashMap ===");

        long timeStart = System.nanoTime();
        for (Map.Entry<Chinchilla, String> entry : treeMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        PerformanceTracker.displayOperationTime(timeStart, "виведення пар ключ-значення в LinkedHashMap");
    }

    /**
     * Сортує LinkedHashMap за ключами з використанням природного порядку Chinchilla.
     * Перезаписує treeMap відсортованими даними (LinkedHashMap зберігає порядок вставки).
     */
    private void sortLinkedHashMap() {
        long timeStart = System.nanoTime();

        List<Chinchilla> sortedKeys = new ArrayList<>(treeMap.keySet());
        Collections.sort(sortedKeys);

        LinkedHashMap<Chinchilla, String> sorted = new LinkedHashMap<>();
        for (Chinchilla key : sortedKeys) {
            sorted.put(key, treeMap.get(key));
        }

        treeMap = sorted;

        PerformanceTracker.displayOperationTime(timeStart, "сортування LinkedHashMap за ключами");
    }

    /**
     * Здійснює пошук елемента за ключем в LinkedHashMap.
     * Використовує Chinchilla.compareTo() для навігації по дереву.
     */
    void findByKeyInLinkedHashMap() {
        long timeStart = System.nanoTime();

        boolean found = treeMap.containsKey(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в LinkedHashMap");

        if (found) {
            String value = treeMap.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Власник: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в LinkedHashMap.");
        }
    }

    /**
     * Здійснює пошук елемента за значенням в LinkedHashMap.
     * Сортує список Map.Entry за значеннями та використовує бінарний пошук.
     */
    void findByValueInLinkedHashMap() {
        long timeStart = System.nanoTime();

        // Створюємо список Entry та сортуємо за значеннями
        List<Map.Entry<Chinchilla, String>> entries = new ArrayList<>(treeMap.entrySet());
        Comparator<Map.Entry<Chinchilla, String>> comparator = Comparator.comparing(
            Map.Entry::getValue, Comparator.nullsFirst(Comparator.naturalOrder())
        );
        Collections.sort(entries, comparator);

        // Створюємо тимчасовий Entry для пошуку
        Map.Entry<Chinchilla, String> searchEntry = new Map.Entry<Chinchilla, String>() {
            public Chinchilla getKey() { return null; }
            public String getValue() { return VALUE_TO_SEARCH_AND_DELETE; }
            public String setValue(String value) { return null; }
        };

        int position = Collections.binarySearch(entries, searchEntry, comparator);

        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук за значенням в LinkedHashMap");

        if (position >= 0) {
            Map.Entry<Chinchilla, String> foundEntry = entries.get(position);
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Chinchilla: " + foundEntry.getKey());
        } else {
            System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутній в LinkedHashMap.");
        }
    }

    /**
     * Додає новий запис до LinkedHashMap.
     */
    void addEntryToLinkedHashMap() {
        long timeStart = System.nanoTime();

        treeMap.put(KEY_TO_ADD, VALUE_TO_ADD);

        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до LinkedHashMap");

        System.out.println("Додано новий запис: Chinchilla='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
    }

    /**
     * Видаляє запис з LinkedHashMap за ключем.
     */
    void removeByKeyFromLinkedHashMap() {
        long timeStart = System.nanoTime();

        String removedValue = treeMap.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з LinkedHashMap");

        if (removedValue != null) {
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    /**
     * Видаляє записи з LinkedHashMap за значенням.
     */
    void removeByValueFromLinkedHashMap() {
        long timeStart = System.nanoTime();

        List<Chinchilla> keysToRemove = new ArrayList<>();
        for (Map.Entry<Chinchilla, String> entry : treeMap.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE)) {
                keysToRemove.add(entry.getKey());
            }
        }
        
        for (Chinchilla key : keysToRemove) {
            treeMap.remove(key);
        }

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з LinkedHashMap");

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    /**
     * Головний метод для запуску програми.
     */
    public static void main(String[] args) {
        // Створюємо початкові дані (ключ: Chinchilla{nickname, weight}, значення: ім'я власника)
        HashMap<Chinchilla, String> hashtable = new HashMap<>();
        hashtable.put(new Chinchilla("Пухнастик", 12.5), "Ярослав");
        hashtable.put(new Chinchilla("Комета", 2.0), "Зінаїда");
        hashtable.put(new Chinchilla("Сніжинка", 7.45), "Поліна");
        hashtable.put(new Chinchilla("Гномик", 6.7), "Арсеній");
        hashtable.put(new Chinchilla("Комета", 2.6), "Арсеній");
        hashtable.put(new Chinchilla("Білосніжка", 6.1), "Андрій");
        hashtable.put(new Chinchilla("Пухнастик", 10.04), "Ярослав");
        hashtable.put(new Chinchilla("Цукерка", 15.1), "Зінаїда");
        hashtable.put(new Chinchilla("Місяць", 10.10), "Стефанія");
        hashtable.put(new Chinchilla("Стріла", 3.78), "Тимофій");

        LinkedHashMap<Chinchilla, String> treeMap = new LinkedHashMap<Chinchilla, String>() {{
            put(new Chinchilla("Пухнастик", 12.5), "Ярослав");
            put(new Chinchilla("Комета", 2.0), "Зінаїда");
            put(new Chinchilla("Сніжинка", 7.45), "Поліна");
            put(new Chinchilla("Гномик", 6.7), "Арсеній");
            put(new Chinchilla("Комета", 2.6), "Арсеній");
            put(new Chinchilla("Білосніжка", 6.1), "Андрій");
            put(new Chinchilla("Пухнастик", 10.04), "Ярослав");
            put(new Chinchilla("Цукерка", 15.1), "Зінаїда");
            put(new Chinchilla("Місяць", 10.10), "Стефанія");
            put(new Chinchilla("Стріла", 3.78), "Тимофій");
        }};

        // Створюємо об'єкт і виконуємо операції
        BasicDataOperationUsingMap operations = new BasicDataOperationUsingMap(hashtable, treeMap);
        operations.executeDataOperations();
    }
}