package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 7;
        nextLast = 0;
    }

    @Override
    public T get(int index) {
        if (index >= items.length || index < 0) return null;
        int switchNumber = switchNumber((nextFirst + 1) + index);
        return items[switchNumber];
    }

    @Override
    public void printDeque() {
        for (T item : this) {
            System.out.println(item);
        }
        System.out.println();
    }

    @Override
    public void addFirst(T item) {
        int addNumber = switchNumber(nextFirst);
        items[addNumber] = item;
        nextFirst--;
        size++;

        if (size >= items.length - 1) resizeArgsBigger();
    }

    @Override
    public void addLast(T item) {
        int addNumber = switchNumber(nextLast);
        items[addNumber] = item;
        nextLast++;
        size++;

        if (size >= items.length - 1) resizeArgsBigger();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) return null;
        int removeNumber = switchNumber(nextFirst + 1);

        T remove = items[removeNumber];
        items[removeNumber] = null;
        size--;
        nextFirst++;

        if (size <= items.length / 4 && size >= 8) resizeArgsSmaller();
        return remove;
    }

    @Override
    public T removeLast() {
        if (size == 0) return null;
        int removeNumber = switchNumber(nextLast - 1);

        T remove = items[removeNumber];
        items[removeNumber] = null;
        size--;
        nextLast--;

        if (size <= items.length / 4 && size >= 8) resizeArgsSmaller();
        return remove;
    }

    private int switchNumber(int number) {
        if (number < 0) {
            number = items.length + number;
            while (items[number] == null) {
                number--;
            }
        } else if (number >= items.length) {
            number = number - items.length;
            while (items[number] == null) {
                number++;
            }
        }
        return number;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void resizeArgsBigger() {
        float multiplier = 2f;
        int total = (int) (items.length * multiplier);
        T[] newItems = (T[]) new Object[total];
        for (int i = 0; i < items.length; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
    }

    private void resizeArgsSmaller() {
        float multiplier = 0.5f;
        int total = (int) (items.length * multiplier);
        T[] newItems = (T[]) new Object[total];
        for (int i = 0; i < total; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
    }


    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int firstNumber = switchNumber(nextFirst + 1);

            @Override
            public boolean hasNext() {
                return items[firstNumber] != null;
            }

            @Override
            public T next() {
                T next = items[firstNumber];
                firstNumber = switchNumber(firstNumber + 1);
                return next;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Deque)) return false;
        if (this.size() != ((Deque<?>) o).size()) return false;
        Deque<?> check = ((Deque<?>) o);
        for (int i = nextFirst + 1; i < nextLast; i = switchNumber(i + 1)) {
            if (items[i] != check.get(i)) return false;
        }
        return true;
    }
}
