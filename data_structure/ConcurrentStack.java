import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This class represents a thread-safe stack and use CAS instruction to ensure thread safe.
 *
 * Created by SylvanasSun on 2017/5/29.
 *
 * @author SylvanasSun
 */
public class ConcurrentStack<E> implements Iterable<E> {

    private AtomicReference<Node<E>> head = new AtomicReference<>(null);
    private AtomicInteger size = new AtomicInteger(0);

    /**
     * This internal static class represents the nodes in the stack.
     */
    private static class Node<E> {
        private final E value;
        private volatile Node<E> next;

        private Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }

    }

    public ConcurrentStack() {
    }

    /**
     * Use elements from other stack to construct this stack.
     */
    public ConcurrentStack(ConcurrentStack<E> that) {
        for (E element : that) {
            this.put(element);
        }
    }

    /**
     * Return the number is elements size of stack.
     */
    public int size() {
        return size.get();
    }

    /**
     * Return the {@code true} represents this stack is empty,{@code false} otherwise.
     */
    public boolean isEmpty() {
        return size.get() == 0;
    }

    /**
     * Insert a new element to the this stack.
     *
     * @return if {@code true} insert success,{@code false} otherwise
     * @throws IllegalArgumentException if {@code value} is null
     */
    public boolean put(E value) {
        if (value == null)
            throw new IllegalArgumentException();
        return putAndReturnResult(value);
    }

    private boolean putAndReturnResult(E value) {
        Node<E> oldNode;
        Node<E> newNode;
        do {
            oldNode = head.get();
            newNode = new Node<E>(value, oldNode);
        } while (!head.compareAndSet(oldNode, newNode));
        sizePlusOne();
        return true;
    }

    /**
     * Return the element of stack top and remove this element.
     *
     * @throws NullPointerException if this stack is empty
     */
    public E pop() {
        if (isEmpty())
            throw new NullPointerException();
        return removeAndReturnElement();
    }

    private E removeAndReturnElement() {
        Node<E> oldNode;
        Node<E> newNode;
        E result;
        do {
            oldNode = head.get();
            newNode = oldNode.next;
            result = oldNode.value;
        } while (!head.compareAndSet(oldNode, newNode));
        sizeMinusOne();
        return result;
    }

    /**
     * Return the element of stack top but no remove this element.
     *
     * @throws NullPointerException if this stack is empty
     */
    public E peek() {
        if (isEmpty())
            throw new NullPointerException();
        return head.get().value;
    }

    private void sizePlusOne() {
        int oldSize;
        int newSize;
        do {
            oldSize = size.get();
            newSize = oldSize + 1;
        } while (!size.compareAndSet(oldSize, newSize));
    }

    private void sizeMinusOne() {
        int oldSize;
        int newSize;
        do {
            oldSize = size.get();
            if (oldSize > 0)
                newSize = oldSize - 1;
            else
                newSize = 0;
        } while (!size.compareAndSet(oldSize, newSize));
    }

    @Override
    public Iterator<E> iterator() {
        return new ConcurrentStackIterator();
    }

    private class ConcurrentStackIterator implements Iterator<E> {
        private ConcurrentStack<E> copy = new ConcurrentStack<>();

        private ConcurrentStackIterator() {
            Node<E> temp = head.get();
            while (temp != null) {
                copy.put(temp.value);
                temp = temp.next;
            }
        }

        @Override
        public boolean hasNext() {
            return !copy.isEmpty();
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return copy.pop();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        ConcurrentStack<Integer> concurrentStack = new ConcurrentStack<>();
        final int THREAD1_START = 0;
        final int THREAD1_END = 50;
        final int THREAD2_START = THREAD1_END + 1;
        final int THREAD2_END = 101;
        Thread thread1 = new Thread(() -> {
            for (int i = THREAD1_START; i < THREAD1_END; i++) {
                concurrentStack.put(i);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = THREAD2_START; i < THREAD2_END; i++) {
                concurrentStack.put(i);
            }
        });
        thread1.start();
        thread2.start();
        Thread.sleep(3000);
        boolean result = concurrentStack.size() == 100;
        if (result) {
            System.out.println("Test success!");
            for (Integer i : concurrentStack)
                System.out.printf("%d ", i);
            System.out.println();
        } else
            System.out.println("Test failed!");
    }

}
