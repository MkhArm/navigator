package DataStructure;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class MultiMap<K, V> implements Iterable<KeyValue<K, V>> {

    private int collisions; // Счетчик коллизий
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.80d;
    public List<KeyValue<K, V>>[] slots; // Массив списков, представляющих слоты хэш-таблицы
    private int count; // Количество элементов в хэш-таблице
    private int capacity; // Общая ёмкость хэш-таблицы

    // Конструктор по умолчанию
    public MultiMap() {
        this.slots = new List[INITIAL_CAPACITY];
        this.capacity = INITIAL_CAPACITY;
    }

    // Конструктор с заданной начальной ёмкостью
    public MultiMap(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be a positive integer");
        }
        this.slots = new MyLinkedList[capacity];
        this.capacity = capacity;
    }

    // Добавление элемента в хэш-таблицу
    // Добавление значения по хэшу ключа
    public void add(K key, V value) {
        int slotNumber = findSlotNumber(key);

        if (slots[slotNumber] == null) {
            slots[slotNumber] = new MyLinkedList<>();
        } else {
            collisions++;
        }

        boolean keyExists = false;
        for (KeyValue<K, V> keyValue : slots[slotNumber]) {
            if (keyValue.getKey().equals(key)) {
                keyValue.getValues().add(value);
                keyExists = true;
                break;
            }
        }

        if (!keyExists) {
            KeyValue<K, V> newKeyValue = new KeyValue<>(key, new MyLinkedList<>());
            newKeyValue.getValues().add(value);
            slots[slotNumber].add(newKeyValue);
            count++;
            growIfNeeded();
        }
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
        int newCapacity = this.capacity * 2;
        List<KeyValue<K, V>>[] newSlots = new MyLinkedList[newCapacity];

        for (List<KeyValue<K, V>> slot : slots) {
            if (slot != null) {
                for (KeyValue<K, V> keyValue : slot) {
                    int newSlotNumber = Math.abs(keyValue.getKey().hashCode()) % newCapacity;
                    if (newSlots[newSlotNumber] == null) {
                        newSlots[newSlotNumber] = new MyLinkedList<>();
                    }
                    newSlots[newSlotNumber].add(keyValue);
                }
            }
        }

        this.slots = newSlots;
        this.capacity = newCapacity;
    }

    // Получение размера хэш-таблицы
    public int size() {
        return this.count;
    }

    // Получение текущей ёмкости хэш-таблицы
    public int capacity() {
        return this.capacity;
    }

    // Получение списка значений по ключу
    public MyLinkedList<V> get(K key) {
        int slotNumber = findSlotNumber(key);

        if (slots[slotNumber] != null) {
            for (KeyValue<K, V> keyValue : slots[slotNumber]) {
                if (keyValue.getKey().equals(key)) {
                    return keyValue.getValues();
                }
            }
        }

        return null;
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
        int slotNumber = findSlotNumber(key);
        if (slots[slotNumber] != null) {
            for (KeyValue<K, V> keyValue : slots[slotNumber]) {
                if (keyValue.getKey().equals(key)) {
                    slots[slotNumber].remove(keyValue);
                    count--;
                    return;
                }
            }
        }
    }

    public List<V> getValues() {
        List<V> values = new MyLinkedList<>();
        for (KeyValue<K, V> keyValue : this) {
            values.addAll(keyValue.getValues());
        }
        return values;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{\n");

        for (int i = 0; i < capacity; i++) {
            if (slots[i] != null) {
                result.append("  Slot ").append(i).append(": ").append(slots[i]).append("\n");
            }
        }

        result.append("}");

        return result.toString();
    }

}