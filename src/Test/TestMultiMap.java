package Test;

import DataStructure.KeyValue;
import DataStructure.MultiMap;

public class TestMultiMap {

    public static void main(String[] args) {
        MultiMap<String, String> multiMap = new MultiMap<>();

        // 1. Тест конструктора по умолчанию
        System.out.println("1. Конструктор по умолчанию:");

        // 2. Тест конструктора с заданной начальной ёмкостью
        MultiMap<String, String> multiMapWithCapacity = new MultiMap<>(20);
        System.out.println("2. Конструктор с начальной ёмкостью (Ёмкость: " + multiMapWithCapacity.capacity() + ")");

        // 3. Тест метода добавления элемента в хэш-таблицу
        multiMap.add("Ключ1", "Значение1");
        multiMap.add("Ключ2", "Значение2");
        multiMap.add("Ключ3", "Значение3");
        System.out.println("3. Добавление элементов: " + multiMap);

        // 4. Тест метода получения количества коллизий
        System.out.println("4. Коллизии: " + multiMap.getCollisions());

        // 5. Тест приватного метода проверки необходимости увеличения ёмкости хэш-таблицы
        System.out.println("5. Проверка growIfNeeded (Текущий размер: " + multiMap.size() + ", Ёмкость: " + multiMap.capacity() + ")");
        for (int i = 0; i < 16; i++) {
            multiMap.add("Ключ" + i, "Значение" + i);
        }
        System.out.println("Новый размер после добавления 16 элементов: " + multiMap.size());
        System.out.println("Новая ёмкость после добавления 16 элементов: " + multiMap.capacity());

        // 6. Тест метода получения размера хэш-таблицы
        System.out.println("6. Размер: " + multiMap.size());

        // 7. Тест метода получения текущей ёмкости хэш-таблицы
        System.out.println("7. Ёмкость: " + multiMap.capacity());

        // 8. Тест метода получения списка значений по ключу
        System.out.println("8. Получение значений для 'Ключ1': " + multiMap.getValues("Ключ1"));

        // 9. Тест метода toString для представления хэш-таблицы в виде строки
        System.out.println("9. Вывод toString: " + multiMap);

        // 10. Тест итератора для перебора элементов хэш-таблицы
        System.out.println("10. Итератор:");
        for (KeyValue<String, String> keyValue : multiMap) {
            System.out.println("    Ключ: " + keyValue.getKey() + ", Значения: " + keyValue.getValues());
        }

        // 11. Тест метода удаления элемента по ключу
        multiMap.remove("Ключ1");
        System.out.println("11. Удаление 'Ключ1': " + multiMap);
    }
}
