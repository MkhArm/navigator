package DataStructure;

import java.util.*;

public class MyLinkedList<T> implements List<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public Node<T> getHead() {
        return head;
    }

    public Node<T> getTail() {
        return tail;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (T item : this) {
            if (item.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator(0);
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int index = 0;
        for (T item : this) {
            array[index++] = item;
        }
        return Arrays.copyOf(array, size);
    }


    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            // Если массив a меньше, чем размер списка, создаем новый массив
            // с тем же типом, что и массив a, но с размером списка.
            a = Arrays.copyOf(a, size);
        }

        int index = 0;
        for (T item : this) {
            a[index++] = (T1) item; // Приводим к типу T1
        }

        if (index < a.length) {
            a[index] = null; // Помечаем конец массива, если не заполнили его полностью
        }

        return a;
    }

    @Override
    public boolean add(T t) {
        Node<T> newNode = new Node<>(t);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Node<T> current = head;
        while (current != null) {
            if (current.getData().equals(o)) {
                Node<T> prev = current.getPrev();
                Node<T> next = current.getNext();

                if (prev != null) {
                    prev.setNext(next);
                } else {
                    head = next;
                }

                if (next != null) {
                    next.setPrev(prev);
                } else {
                    tail = prev;
                }

                size--;
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c) {
            if (!contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        for (T item : c) {
            add(index++, item);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            if (c.contains(iterator.next())) {
                iterator.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            if (!c.contains(iterator.next())) {
                iterator.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> current = getNodeAtIndex(index);
        return current.getData();
    }

    @Override
    public T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> current = getNodeAtIndex(index);
        T oldValue = current.getData();
        current.setData(element);
        return oldValue;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == size) {
            add(element); // Если добавляем в конец, использовать существующий метод
        } else {
            Node<T> newNode = new Node<>(element);
            Node<T> current = getNodeAtIndex(index);
            Node<T> prev = current.getPrev();

            if (prev != null) {
                prev.setNext(newNode);
                newNode.setPrev(prev);
            } else {
                head = newNode;
            }

            newNode.setNext(current);
            current.setPrev(newNode);

            size++;
        }
    }

    // Вспомогательный метод для получения узла по индексу
    private Node<T> getNodeAtIndex(int index) {
        int currentIndex = 0;
        Node<T> current = head;
        while (currentIndex < index) {
            current = current.getNext();
            currentIndex++;
        }
        return current;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> current = getNodeAtIndex(index);
        T removedValue = current.getData();
        Node<T> prev = current.getPrev();
        Node<T> next = current.getNext();

        if (prev != null) {
            prev.setNext(next);
        } else {
            head = next;
        }

        if (next != null) {
            next.setPrev(prev);
        } else {
            tail = prev;
        }

        size--;
        return removedValue;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        Node<T> current = head;
        while (current != null) {
            if (current.getData().equals(o)) {
                return index;
            }
            current = current.getNext();
            index++;
        }
        return -1; // Элемент не найден
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = size - 1;
        Node<T> current = tail;
        while (current != null) {
            if (current.getData().equals(o)) {
                return index;
            }
            current = current.getPrev();
            index--;
        }
        return -1; // Элемент не найден
    }
    @Override
    public ListIterator<T> listIterator() {
        return new LinkedListIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return new LinkedListIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("fromIndex: " + fromIndex + ", toIndex: " + toIndex + ", Size: " + size);
        }

        List<T> subList = new MyLinkedList<>();
        Node<T> current = getNodeAtIndex(fromIndex);

        for (int i = fromIndex; i < toIndex; i++) {
            subList.add(current.getData());
            current = current.getNext();
        }

        return subList;
    }

    // Внутренний класс для реализации ListIterator
    private class LinkedListIterator implements ListIterator<T> {
        private Node<T> current;
        private Node<T> lastReturned;
        private int index;

        LinkedListIterator(int index) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
            this.current = (index == size) ? null : getNodeAtIndex(index);
            this.lastReturned = null;
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = current;
            current = current.getNext();
            index++;
            return lastReturned.getData();
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            if (current == null) {
                current = tail;
            } else {
                current = current.getPrev();
            }
            lastReturned = current;
            index--;
            return lastReturned.getData();
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            Node<T> nextNode = lastReturned.getNext();
            Node<T> prevNode = lastReturned.getPrev();

            if (prevNode == null) {
                head = nextNode;
            } else {
                prevNode.setNext(nextNode);
                lastReturned.setPrev(null);
            }

            if (nextNode == null) {
                tail = prevNode;
            } else {
                nextNode.setPrev(prevNode);
                lastReturned.setNext(null);
            }

            size--;
            if (current == lastReturned) {
                current = nextNode;
            } else {
                index--;
            }

            lastReturned = null;
        }

        @Override
        public void set(T t) {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            lastReturned.setData(t);
        }

        @Override
        public void add(T t) {
            Node<T> newNode = new Node<>(t);
            if (current == null) {
                if (tail != null) {
                    tail.setNext(newNode);
                    newNode.setPrev(tail);
                    tail = newNode;
                } else {
                    head = tail = newNode;
                }
            } else {
                Node<T> nextNode = current;
                Node<T> prevNode = current.getPrev();

                newNode.setNext(nextNode);
                newNode.setPrev(prevNode);

                if (prevNode == null) {
                    head = newNode;
                } else {
                    prevNode.setNext(newNode);
                }

                nextNode.setPrev(newNode);
            }

            size++;
            index++;
            lastReturned = null;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = head;
        while (current != null) {
            sb.append(current.getData());
            if (current.getNext() != null) {
                sb.append(", ");
            }
            current = current.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

}
