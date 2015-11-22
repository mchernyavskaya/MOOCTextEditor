package textgen;

import java.util.AbstractList;


/**
 * A class that implements a doubly linked list
 *
 * @param <E> The type of the elements stored in the list
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MyLinkedList<E> extends AbstractList<E> {
    LLNode<E> head;
    LLNode<E> tail;
    int size;

    /**
     * Create a new empty LinkedList
     */
    public MyLinkedList() {

    }

    /**
     * Appends an element to the end of the list
     *
     * @param element The element to add
     */
    public boolean add(E element) {
        LLNode<E> ellNode = new LLNode<>(element, tail != null ? tail : null);
        if (size == 0) {
            head = ellNode;
        }
        tail = ellNode;
        size++;
        return true;
    }

    /**
     * Get the element at position index
     *
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E get(int index) {
        LLNode<E> current = getNode(index);
        return current.data;
    }

    private LLNode<E> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        int i = 0;
        LLNode<E> current = head;
        while (i < index) {
            current = current.next;
            i++;
        }
        return current;
    }

    /**
     * Add an element to the list at the specified index
     *
     * @param index   The index where the element should be added
     * @param element The element to add
     */
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        if (element == null) {
            throw new NullPointerException("Data cannot be null");
        }
        if (index == size) {
            add(element);
            return;
        }
        LLNode<E> next = getNode(index);
        LLNode<E> node = new LLNode<>(element, next.prev, next);
        if (index == 0) {
            head = node;
        }
        size++;
    }


    /**
     * Return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Remove a node at the specified index and return its data element.
     *
     * @param index The index of the element to remove
     * @return The data element removed
     * @throws IndexOutOfBoundsException If index is outside the bounds of the list
     */
    public E remove(int index) {
        LLNode<E> found = getNode(index);
        if (found.prev != null) {
            found.prev.next = found.next;
        }
        if (found.next != null) {
            found.next.prev = found.prev;
        }
        // head-tail fix
        if (tail == found) {
            tail = found.prev;
        }
        if (head == found) {
            head = found.next;
        }
        // remove links from found
        found.next = null;
        found.prev = null;

        // size fix
        size--;

        return found.data;
    }

    /**
     * Set an index position in the list to a new element
     *
     * @param index   The index of the element to change
     * @param element The new element
     * @return The element that was replaced
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E set(int index, E element) {
        if (element == null) {
            throw new NullPointerException("Data cannot be null");
        }
        LLNode<E> found = getNode(index);
        E oldData = found.data;
        found.data = element;
        return oldData;
    }
}

class LLNode<E> {
    LLNode<E> prev = null;
    LLNode<E> next = null;
    E data = null;

    // Add any other methods you think are useful here
    // E.g. you might want to add another constructor

    public LLNode() {
    }

    public LLNode(E e) {
        this.data = e;
        this.prev = null;
        this.next = null;
    }

    public LLNode(E data, LLNode<E> prev) {
        this(data);
        if (prev != null) {
            this.prev = prev;
            prev.next = this;
        }
    }

    public LLNode(E data, LLNode<E> prev, LLNode<E> next) {
        this(data, prev);
        if (next != null) {
            this.next = next;
            next.prev = this;
        }
    }

    @Override
    public String toString() {
        return "LLNode{" +
                "data=" + data +
                '}';
    }
}
