import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Main {


    public static void main(String[] args) throws IOException {


        // Чтение CSV файла
        BufferedReader reader = new BufferedReader(new FileReader("lab_1_read.csv"));
        String line;
        String[] array = new String[41];
        int i = 0;
        while ((line = reader.readLine()) != null) {
            array[i] = line;
            i++;
        }
        reader.close();
        System.out.println("Введите искомый ключ: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int key1 = Integer.parseInt(br.readLine());
        // Выбор метода поиска
        System.out.println("Выберите метод поиска:");
        System.out.println("1. Последовательный поиск");
        System.out.println("2. Поиск в неотсортированном массиве");
        System.out.println("3. Бинарный поиск");
        System.out.println("4. Поиск интерполированием");


        int choice = Integer.parseInt(br.readLine());

        // Выполнение выбранного метода поиска
        long startTime = 0;
        int index = -1;
        switch (choice) {
            case 1:
                startTime = System.nanoTime();
                index = sequentialSearch(array, key1);
                break;
            case 2:
                startTime = System.nanoTime();
                index = unsortedSearch(array, key1);
                break;
            case 3:
                startTime = System.nanoTime();
                index = binarySearch(array, key1);
                break;
            case 4:
                startTime = System.nanoTime();
                index = interpolationSearch(array, key1);
                break;
            default:
                System.out.println("Некорректный выбор метода поиска.");
                break;
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        // Вывод результатов поиска
        if (index != -1) {
            System.out.println("Элемент найден в позиции: " + index);
        } else {
            System.out.println("Элемент не найден.");
        }

        // Вывод времени выполнения поиска
        System.out.println("Время выполнения поиска: " + duration + " наносекунд.");
    }

    // методы поиска
    private static int sequentialSearch(String[] array, int key) {
        // алгоритм последовательного поиска
        for (int i = 0; i < array.length; i++) {
            String[] fields = array[i].split(","); // предполагаем, что поля разделены запятой
            int currentKey = Integer.parseInt(fields[0]); // предполагаем, что ключевое поле - это первое поле в строке
            if (currentKey == key) {
                return i; // возвращаем индекс элемента, в котором найден ключ
            }
        }
        return -1; // если ключ не найден, возвращаем -1
    }


    private static int unsortedSearch(String[] array, int key) {
        // алгоритм поиска в неотсортированных массивах древовидно структурированных 
        for (int i = 0; i < array.length; i++) {
            String[] fields = array[i].split(","); // предполагаем, что поля разделены запятой
            int currentKey = Integer.parseInt(fields[0]); // предполагаем, что ключевое поле - это первое поле в строке
            if (currentKey == key) {
                return i; // возвращаем индекс элемента, в котором найден ключ
            }
        }
        return -1; // если ключ не найден, возвращаем -1
    }

    private static int binarySearch(String[] array, int key) {
        // алгоритм бинарного поиска
        Arrays.sort(array, new Comparator<String>() {
            public int compare(String s1, String s2) {
                String[] fields1 = s1.split(",");
                String[] fields2 = s2.split(",");
                int key1 = Integer.parseInt(fields1[0]);
                int key2 = Integer.parseInt(fields2[0]);
                return Integer.compare(key1, key2);
            }
        });


        int left = 0; // левая граница поиска
        int right = array.length - 1; // правая граница поиска
        while (left <= right) {
            int mid = left + (right - left) / 2; // средний индекс
            String[] fields = array[mid].split(","); // предполагаем, что поля разделены запятой
            int currentKey = Integer.parseInt(fields[0]); // предполагаем, что ключевое поле - это первое поле в строке
            if (currentKey == key) {
                return mid; // возвращаем индекс элемента, в котором найден ключ
            } else if (currentKey < key) {
                left = mid + 1; // ищем в правой половине массива
            } else {
                right = mid - 1; // ищем в левой половине массива
            }
        }
        return -1; // если ключ не найден, возвращаем -1
    }

    private static int interpolationSearch(String[] array, int key) {
        // алгоритм поиска интерполированием
        Arrays.sort(array, new Comparator<String>() {
            public int compare(String s1, String s2) {
                String[] fields1 = s1.split(",");
                String[] fields2 = s2.split(",");
                int key1 = Integer.parseInt(fields1[0]);
                int key2 = Integer.parseInt(fields2[0]);
                return Integer.compare(key1, key2);
            }
        });


        int low = 0;
        int high = array.length - 1;
        while (low <= high && key >= Integer.parseInt(array[low].split(",")[0]) && key <= Integer.parseInt(array[high].split(",")[0])) {
            int pos = low + ((key - Integer.parseInt(array[low].split(",")[0])) * (high - low)) / (Integer.parseInt(array[high].split(",")[0]) - Integer.parseInt(array[low].split(",")[0]));
            int cmp = Integer.compare(key, Integer.parseInt(array[pos].split(",")[0]));
            if (cmp < 0) {
                high = pos - 1;
            } else if (cmp > 0) {
                low = pos + 1;
            } else {
                return pos;
            }
        }
        return -1;
    }
}
