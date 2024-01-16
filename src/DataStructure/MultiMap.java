package DataStructure;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MultiMap<K, V> implements Iterable<KeyValue<K, V>> {

    private int collisions; // Счетчик коллизий
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.80d;
    public TwoLinkedList<KeyValue<K, V>>[] slots; // Массив списков, представляющих слоты хэш-таблицы
    private int count; // Количество элементов в хэш-таблице
    private int capacity; // Общая ёмкость хэш-таблицы

    // Конструктор по умолчанию
    public MultiMap() {
        this.slots = new TwoLinkedList[INITIAL_CAPACITY];
        this.capacity = INITIAL_CAPACITY;
    }

    // Конструктор с заданной начальной ёмкостью
    public MultiMap(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be a positive integer");
        }
        this.slots = new TwoLinkedList[capacity];
        this.capacity = capacity;
    }

    // Добавление элемента в хэш-таблицу
    // Добавление значения по хэшу ключа
    public void add(K key, V value) {
        int initialSlot = findSlotNumber(key);
        int slotNumber = initialSlot;

        // Ищем первый свободный слот или слот с существующим ключом
        while (slots[slotNumber] != null) {
            Iterator<KeyValue<K, V>> iterator = slots[slotNumber].iterator();
            while (iterator.hasNext()) {
                KeyValue<K, V> keyValue = iterator.next();
                if (keyValue.getKey().equals(key)) {
                    // Ключ уже существует, добавляем значение в существующий элемент
                    keyValue.getValues().addLast(value);
                    return;
                }
            }

            // Слот занят, ищем следующий
            slotNumber = (slotNumber + 1) % capacity;

            // Если вернулись на исходный слот, значит, все слоты заняты, и нужно увеличить хэш-таблицу
            if (slotNumber == initialSlot) {
                grow();
                // После увеличения, нужно пересчитать номер слота
                slotNumber = findSlotNumber(key);
            }
        }

        // Если дошли сюда, значит, нашли свободный слот
        if (slots[slotNumber] == null) {
            slots[slotNumber] = new TwoLinkedList<>();
        }

        // Добавляем новый элемент в конец списка в найденном слоте
        KeyValue<K, V> newKeyValue = new KeyValue<>(key, new TwoLinkedList<>());
        newKeyValue.getValues().addLast(value);
        slots[slotNumber].addLast(newKeyValue);
        count++;
        growIfNeeded();
    }

    // Получение количества коллизий
    public int getCollisions() {
        return collisions;
    }

    // Нахождение номера слота для ключа
    private int findSlotNumber(K key) {
        return Math.abs(key.hashCode()) % this.capacity;
    }

    // Проверка необходимости увеличения ёмкости хэш-таблицы
    private void growIfNeeded() {
        if ((double) (this.size() + 1) / this.capacity > LOAD_FACTOR) {
            grow();
        }
    }

    // Увеличение ёмкости хэш-таблицы
    private void grow() {
        int newCapacity = capacity * 2; // Увеличиваем ёмкость вдвое
        MultiMap<K, V> newMap = new MultiMap<>(newCapacity);
        for (TwoLinkedList<KeyValue<K, V>> slotList : slots) {
            if (slotList != null) {
                Iterator<KeyValue<K, V>> iterator = slotList.iterator();
                while (iterator.hasNext()) {
                    KeyValue<K, V> keyValue = iterator.next();
                    TwoLinkedList<V> values = keyValue.getValues();
                    for (V value : values) {
                        newMap.add(keyValue.getKey(), value); // Добавляем каждое значение
                    }
                }
            }
        }
        slots = newMap.slots; // Обновляем ссылку на массив слотов
        capacity = newMap.capacity; // Обновляем ёмкость
    }


    public int size() {
        return this.count;
    }

    // Получение текущей ёмкости
    public int capacity() {
        return this.capacity;
    }

    // Получение списка значений по ключу
    public TwoLinkedList<K> getKeys() {
        TwoLinkedList<K> keysList = new TwoLinkedList<>();
        for (TwoLinkedList<KeyValue<K, V>> slotList : slots) {
            if (slotList != null) {
                Iterator<KeyValue<K, V>> iterator = slotList.iterator();
                while (iterator.hasNext()) {
                    KeyValue<K, V> keyValue = iterator.next();
                    keysList.addLast(keyValue.getKey());
                }
            }
        }
        return keysList;
    }

    public TwoLinkedList<V> getAllValues() {
        TwoLinkedList<V> allValues = new TwoLinkedList<>();

        for (TwoLinkedList<KeyValue<K, V>> slotList : slots) {
            if (slotList != null) {
                Iterator<KeyValue<K, V>> iterator = slotList.iterator();
                while (iterator.hasNext()) {
                    KeyValue<K, V> keyValue = iterator.next();
                    allValues.addAll(keyValue.getValues());
                }
            }
        }
        return allValues;
    }

    // Итератор для перебора элементов хэш-таблицы
    @Override
    public Iterator<KeyValue<K, V>> iterator() {
        return new HashTableIterator();
    }

    // Внутренний класс для итерации по хэш-таблице
    private class HashTableIterator implements Iterator<KeyValue<K, V>> {
        private int currentSlot = 0;
        private int currentSlotIndex = 0;
        private Iterator<KeyValue<K, V>> currentIterator = getNextIterator();
        private KeyValue<K, V> lastReturned;

        @Override
        public boolean hasNext() {
            while ((currentIterator == null || !currentIterator.hasNext()) && currentSlot < capacity - 1) {
                currentSlot++;
                currentIterator = getNextIterator();
                currentSlotIndex = 0;
            }
            return currentIterator != null && currentIterator.hasNext();
        }

        @Override
        public KeyValue<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the hash table");
            }
            lastReturned = currentIterator.next();
            currentSlotIndex++;
            return lastReturned;
        }

        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException("remove() can only be called after a call to next()");
            }
            MultiMap.this.remove(lastReturned.getKey());
            lastReturned = null;
            currentSlotIndex--;
            count--;
        }

        private Iterator<KeyValue<K, V>> getNextIterator() {
            if (currentSlot < capacity && slots[currentSlot] != null) {
                return slots[currentSlot].iterator();
            }
            return null;
        }
    }

    public void remove(K key) {
        int slotNumber = findSlotNumber(key); // Находим номер слота для ключа
        if (slots[slotNumber] != null) {
            Iterator<KeyValue<K, V>> iterator = slots[slotNumber].iterator();
            while (iterator.hasNext()) {
                KeyValue<K, V> keyValue = iterator.next();
                if (keyValue.getKey().equals(key)) {
                    iterator.remove(); // Удаляем элемент из списка слота
                    count--; // Уменьшаем количество элементов в хэш-таблице
                    return;
                }
            }
        }
    }

    public TwoLinkedList<V> getValues(K key) {
        int slotNumber = findSlotNumber(key);
        if (slots[slotNumber] != null) {
            Iterator<KeyValue<K, V>> iterator = slots[slotNumber].iterator();
            while (iterator.hasNext()) {
                KeyValue<K, V> keyValue = iterator.next();
                if (keyValue.getKey().equals(key)) {
                    return keyValue.getValues(); // Возвращаем список значений по ключу
                }
            }
            return null;
        }
        // Если ключ не найден, возвращаем null
        return null;
    }



    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{\n");

        for (int i = 0; i < capacity; i++) {
            if (slots[i] != null) {
                result.append("  Slot ").append(i).append(": ").append(slots[i].toString()).append("\n");
            }
        }

        result.append("}");

        return result.toString();
    }

}
