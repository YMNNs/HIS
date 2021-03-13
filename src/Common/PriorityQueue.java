package Common;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PriorityQueue<E extends Comparable<E>> implements Serializable {

    private MaxHeap<E> maxHeap;

    public PriorityQueue() {
        maxHeap = new MaxHeap<>();
    }


    public boolean isEmpty() {
        return maxHeap.isEmpty();
    }

    public int size() {
        return maxHeap.getSize();
    }

    public E getFront() {
        return maxHeap.getFront();
    }


    public boolean contains(Object o) {
        return maxHeap.contains(o);
    }


    public boolean add(E e) {
        return maxHeap.add(e);
    }

    public boolean remove(Object o) {
        return maxHeap.remove(o);
    }


    public boolean containsAll(@NotNull Collection<?> c) {
        return maxHeap.containsAll(c);
    }


    public boolean addAll(@NotNull Collection<? extends E> c) {
        for (E e : c) {
            maxHeap.add(e);
        }
        return true;
    }


    public boolean removeAll(@NotNull Collection<?> c) {
        return false;
    }


    public boolean retainAll(@NotNull Collection<?> c) {
        return false;
    }


    public E poll() {
        return maxHeap.extractMax();
    }


    public E peek() {
        return maxHeap.getFront();
    }
}

class MaxHeap<E extends Comparable<E>> implements Serializable {

    private ArrayList<E> list;


    public MaxHeap() {
        list = new ArrayList<E>();
    }


    private int parent(int i) {
        return (i - 1) / 2;
    }


    private int leftChild(int i) {
        return i * 2 + 1;
    }


    private int rightChild(int i) {
        return i * 2 + 2;
    }

    boolean remove(Object o) {
        return list.remove(o);
    }

    /**
     * 冒泡排序
     *
     * @param k 第k个元素
     */
    private void siftUp(int k) {
        while (k > 0 && list.get(k).compareTo(list.get(parent(k))) > 0) {
            Collections.swap(list, k, parent(k));
            k = parent(k);
        }
    }


    public boolean add(E e) {
        list.add(list.size(), e);
        siftUp(list.size() - 1);
        return true;
    }


    public E getFront() {
        return list.get(0);
    }


    private void siftDown(int k) {
        while (leftChild(k) < list.size()) {
            int j;
            j = leftChild(k);
            if (j + 1 < list.size() && list.get(j).compareTo(list.get(j + 1)) < 0) {
                j = rightChild(k);
            }
            if (list.get(k).compareTo(list.get(j)) < 0) {
                Collections.swap(list, k, j);
            } else {
                break;
            }
            k = j;

        }
    }


    public E extractMax() {
        if (list.size() == 0) {
            throw new IllegalArgumentException("Exception");
        }
        E retMax = list.get(0);
        Collections.swap(list, 0, list.size() - 1);
        list.remove(list.size() - 1);
        siftDown(0);
        return retMax;
    }


    public boolean isEmpty() {
        return list.size() == 0;
    }


    public int getSize() {
        return list.size();
    }

    public boolean contains(Object o) {
        return list.contains(o);
    }

    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }


}