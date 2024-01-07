package DataStructure;

public class Node<T> {
    private T data;
    private Node<T> prev;
    private Node<T> next;

    Node(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public void setData(T data) {
        this.data = data;
    }
}