package DataStructure;

/**
 * Created by EthanJoe on 2/12/16.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<Item> implements Iterable<Item> {
    private int count;
    private Node first;
    private Node last;
    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }

    /***
     * constructor for Queue
     */
    public Queue() {
        first = null;
        last = null;
        count = 0;
        assert check();
    }

    /***
     *
     * @return the boolean that whether first node of Queue is 'null'
     */
    public boolean isEmpty() { return first == null; }

    /***
     *
     * @return the number of units in the Queue
     */
    public int size() { return count; }

    /***
     *
     * @param item
     * add the item to the end
     */
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            last.prev = null;
            first = last;
        } else {
            oldLast.next = last;
            last.prev = oldLast;
        }
        count++;
        assert check();
    }

    /***
     *
     * @return the value of First Item before delete it
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow.");
        Item item = first.item;
        if (count == 1) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.prev = null;
        }
        count--;
        return item;
    }

    /***
     *
     * @return the boolean value that determined by whether the queue is ordered correctly
     */
    private boolean check() {
        if (count < 0) {
            return false;
        } else if (count == 0) {
            if (first != null) return false;
            if (last != null) return false;
        } else if (count == 1) {
            if (first == null || last == null) return false;
            if (first != last) return false;
            if (first.next != null || first.prev != null) return false;
        } else {
            if (first == null || last == null) return false;
            if (first == last) return false;
            if (first.prev != null || first.next == null) return false;
            if (last.next != null || last.prev == null) return false;

            // check internal consistency of instance variable count
            int numOfNodes = 0;
            for (Node x = first; x != null && numOfNodes <= count; x = x.next) {
                numOfNodes++;
            }
            if (numOfNodes != count) return false;

            // check internal consistency of instance variable last
            Node testNode = first;
            while (testNode.next != null) {
                testNode = testNode.next;
            }
            if (last != testNode) return false;
        }
        return true;
    }

    /***
     *
     * @return an iterator that iterates over the items in this queue in FIFO order
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() { return current != null; }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
        public void remove() { throw new UnsupportedOperationException(); }
    }

}
