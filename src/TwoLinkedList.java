import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

class TwoLinkedList<T> implements Iterable<T>{

    private Node<T> head;
    private Node<T> tail;
    private int size;

    // Метод для добавления всех элементов из другого списка в конец текущего списка
    public boolean addAll(Collection<? extends T> c) {
        for (T item : c) {
            addLast(item);
        }
        return true;
    }

    public boolean add(T data) {
        addLast(data);
        return true;
    }

    // Метод для добавления элемента в начало списка
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        }
        size++;
    }

    // Метод для добавления элемента в конец списка
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setPrev(tail);
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    // Метод для вставки элемента после указанного элемента
    public void insertAfter(T data, T key) {
        Node<T> newNode = new Node<>(data);
        Node<T> current = head;
        while (current != null) {
            if (current.getData().equals(key)) {
                newNode.setNext(current.getNext());
                if (current.getNext() != null) {
                    current.getNext().setPrev(newNode);
                }
                current.setNext(newNode);
                newNode.setPrev(current);
                size++;
                return;
            }
            current = current.getNext();
        }
    }

    // Метод для удаления элемента из списка
    public void remove(T data) {
        Node<T> current = head;
        while (current != null) {
            if (current.getData().equals(data)) {
                if (current.getPrev() != null) {
                    current.getPrev().setNext(current.getNext());
                } else {
                    head = current.getNext();
                }
                if (current.getNext() != null) {
                    current.getNext().setPrev(current.getPrev());
                } else {
                    tail = current.getPrev();
                }
                size--;
                return;
            }
            current = current.getNext();
        }
    }

    // Метод для удаления первого элемента списка
    public void removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty.");
        }
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head = head.getNext();
            head.setPrev(null);
        }
        size--;
    }

    // Метод для удаления последнего элемента списка
    public void removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty.");
        }
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            tail = tail.getPrev();
            tail.setNext(null);
        }
        size--;
    }

    // Метод для редактирования поля объекта по ключу
    public void editField(T key, String fieldName, T newValue) {
        Node<T> current = head;
        while (current != null) {
            if (current.getData().equals(key)) {
                try {
                    java.lang.reflect.Field field = current.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    field.set(current, newValue);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                return;
            }
            current = current.getNext();
        }
    }

    // Метод для очистки списка
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    // Метод для получения размера списка
    public int size() {
        return size;
    }

    // Метод для проверки, является ли список пустым
    public boolean isEmpty() {
        return size == 0;
    }

    // Метод для получения элемента по индексу
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getData();
    }

    // Метод для поиска элемента по значению поля
    public T findByField(String fieldName, T value) {
        Node<T> current = head;
        while (current != null) {
            try {
                java.lang.reflect.Field field = Node.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object fieldValue = field.get(current);
                if (fieldValue != null && fieldValue.equals(value)) {
                    return current.getData();
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            current = current.getNext();
        }
        return null;
    }

    // Метод для отображения элементов списка
    public void display() {
        Node<T> current = head;
        while (current != null) {
            System.out.println(current.getData() + " ");
            current = current.getNext();
        }
    }

    // Переопределение метода iterator() для реализации интерфейса Iterable
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T data = current.getData();
                current = current.getNext();
                return data;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");
        Node<T> current = head;

        while (current != null) {
            result.append(current.getData());
            if (current.getNext() != null) {
                result.append(" -> ");
            }
            current = current.getNext();
        }

        result.append("}");

        return result.toString();
    }
}
