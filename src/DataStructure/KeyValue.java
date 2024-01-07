package DataStructure;

import java.util.Objects;

public class KeyValue<Key, Value> {

    private Key key;
    private MyLinkedList<Value> values;

    // Конструктор класса
    public KeyValue(Key key, MyLinkedList<Value> values) {
        this.setKey(key);
        this.setValues(values);
    }

    // Геттер для ключа
    public Key getKey() {
        return this.key;
    }

    // Сеттер для ключа
    public void setKey(Key key) {
        this.key = key;
    }

    // Геттер для списка значений
    public MyLinkedList<Value> getValues() {
        return this.values;
    }

    // Сеттер для списка значений
    public void setValues(MyLinkedList<Value> values) {
        this.values = values;
    }

    // Метод для сравнения объектов
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyValue<?, ?> keyValue = (KeyValue<?, ?>) o;
        return Objects.equals(key, keyValue.key) &&
                Objects.equals(values, keyValue.values);
    }

    // Метод для представления объекта в виде строки
    @Override
    public String toString() {
        return String.format("Key: %s -> Value: %s", this.getKey(), this.getValues().toString());
    }
}
