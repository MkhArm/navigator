package DataStructure;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TwoLinkedList<T> implements Iterable<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

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

    @SafeVarargs
    public final void addAll(T... elements) {
        for (T element : elements) {
            addLast(element);
        }
    }

    public void addAll(Iterable<T> elements) {
        for (T element : elements) {
            addLast(element);
        }
    }

    public boolean contains(T data) {
        Node<T> current = head;

        while (current != null) {
            if (current.getData().equals(data)) {
                return true;
            }
            current = current.getNext();
        }

        return false;
    }

    public TwoLinkedList<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("Invalid sublist indices");
        }

        TwoLinkedList<T> subList = new TwoLinkedList<>();
        Node<T> current = head;

        for (int i = 0; i < fromIndex; i++) {
            current = current.getNext();
        }

        for (int i = fromIndex; i < toIndex; i++) {
            subList.addLast(current.getData());
            current = current.getNext();
        }

        return subList;
    }


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

    public void insertAfter(T data, T key) {
        Node<T> newNode = new Node<>(data);
        Node<T> current = head;
        while (current != null) {
            if (current.getData() != null && current.getData().equals(key)) {
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

    public void remove(T data) {
        Node<T> current = head;
        while (current != null) {
            if (current.getData() != null && current.getData().equals(data)) {
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

    public void editField(T key, String fieldName, Object newValue) {
        Node<T> current = head;
        while (current != null) {
            if (current.getData() != null && current.getData().equals(key)) {
                try {
                    Field field = current.getData().getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    field.set(current.getData(), newValue);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                return;
            }
            current = current.getNext();
        }
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

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

    public T findByField(String fieldName, Object value) {
        Node<T> current = head;
        while (current != null) {
            try {
                Field field = current.getData().getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object fieldValue = field.get(current.getData());
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

    public void display() {
        Node<T> current = head;
        while (current != null) {
            System.out.print(current.getData() + " ");
            current = current.getNext();
        }
        System.out.println();
    }

    public int indexOf(T data) {
        Node<T> current = head;
        int index = 0;

        while (current != null) {
            if (current.getData().equals(data)) {
                return index;
            }
            current = current.getNext();
            index++;
        }

        return -1;
    }

    public void sort(Comparator<T> comparator) {
        if (size > 1) {
            boolean swapped;
            do {
                swapped = false;
                Node<T> current = head;
                Node<T> next = head.getNext();

                while (next != null) {
                    if (comparator.compare(current.getData(), next.getData()) > 0) {
                        T temp = current.getData();
                        current.setData(next.getData());
                        next.setData(temp);
                        swapped = true;
                    }

                    current = next;
                    next = next.getNext();
                }
            } while (swapped);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T> {
        private Node<T> current = head;
        private Node<T> lastReturned = null;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the list");
            }
            T data = current.getData();
            lastReturned = current;
            current = current.getNext();
            return data;
        }

        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException("remove() can only be called after a call to next()");
            }
            if (lastReturned.getPrev() != null) {
                lastReturned.getPrev().setNext(lastReturned.getNext());
            } else {
                head = lastReturned.getNext();
            }
            if (lastReturned.getNext() != null) {
                lastReturned.getNext().setPrev(lastReturned.getPrev());
            } else {
                tail = lastReturned.getPrev();
            }
            size--;
            lastReturned = null;
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("TwoLinkedList{");
        Iterator<T> iterator = iterator();

        while (iterator.hasNext()) {
            result.append(iterator.next());
            if (iterator.hasNext()) {
                result.append(" -> ");
            }
        }

        result.append(", size=").append(size).append('}');
        return result.toString();
    }
}
