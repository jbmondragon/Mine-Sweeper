import java.util.LinkedList;
import java.util.Queue;

public class IntArrayQueue<E> implements Queue<E> {
    private Queue<E> queue;

    // Constructor
    public IntArrayQueue() {
        this.queue = new LinkedList<>();
    }

    // Add an element to the queue
    @Override
    public boolean add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }
        return queue.add(element);
    }

    @Override
    public boolean offer(E element) {
        return queue.offer(element);
    }

    // Retrieve and remove the head of the queue
    @Override
    public E poll() {
        return queue.poll();
    }

    @Override
    public E remove() {
        return queue.remove();
    }

    // Retrieve, but do not remove, the head of the queue
    @Override
    public E peek() {
        return queue.peek();
    }

    @Override
    public E element() {
        return queue.element();
    }

    // Check if the queue is empty
    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // Get the size of the queue
    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public void clear() {
        queue.clear();
    }

    // Other inherited methods
    @Override
    public boolean contains(Object o) {
        return queue.contains(o);
    }

    @Override
    public Object[] toArray() {
        return queue.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return queue.toArray(a);
    }

    @Override
    public boolean remove(Object o) {
        return queue.remove(o);
    }

    @Override
    public boolean containsAll(java.util.Collection<?> c) {
        return queue.containsAll(c);
    }

    @Override
    public boolean addAll(java.util.Collection<? extends E> c) {
        return queue.addAll(c);
    }

    @Override
    public boolean removeAll(java.util.Collection<?> c) {
        return queue.removeAll(c);
    }

    @Override
    public boolean retainAll(java.util.Collection<?> c) {
        return queue.retainAll(c);
    }

    @Override
    public java.util.Iterator<E> iterator() {
        return queue.iterator();
    }
}
