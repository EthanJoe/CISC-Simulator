package Logic;

import DataStructure.Queue;
import java.util.ArrayList;

/**
 * Created by yichenzhou on 2/21/17.
 */
public class Cache {
    private Queue<CacheLine> queue;
    private int capacity;

    public Cache(int capacity) {
        this.queue = new Queue<CacheLine>();
        this.capacity = capacity;
    }

    public int size() {
        return queue.size();
    }

    public void insert(CacheLine cacheLine) {
        if (queue.size() >= capacity) {
            queue.dequeue();
        }

        queue.enqueue(cacheLine);
    }

    public CacheLine search(String address) {
        for (CacheLine c : queue) {
            if (c.getAddress().equals(address)) {
                return c;
            } else {
                return null;
            }
        }
        return null;
    }

    public void update(CacheLine cacheLine) {
        ArrayList<CacheLine> tmpList = new ArrayList<>();
        for (CacheLine c : queue) {
            if (c.getAddress() == cacheLine.getAddress()) {
                tmpList.add(cacheLine);
            } else {
                tmpList.add(c);
            }
        }
        queue = new Queue<>();
        for (CacheLine c: tmpList) {
            queue.enqueue(c);
        }
    }

    public Queue<CacheLine> getQueue() {
        return queue;
    }
}
