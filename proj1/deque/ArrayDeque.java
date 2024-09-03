package deque;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class ArrayDeque<Item> implements Iterable<Item>, Deque<Item> {
    private Item[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (Item[]) new Object[8];
        size = 0;
        nextFirst = 7;
        nextLast = 0;
    }

    @Override
    public Item get(int index) {
        if (index >= items.length || index < 0) return null;
        int switchNumber = switchNumber((nextFirst + 1) + index);
        return items[switchNumber];
    }

    @Override
    public void printDeque() {
        for (Item item : this) {
            System.out.println(item);
        }
        System.out.println();
    }

    @Override
    public void addFirst(Item item) {
        int addNumber = switchNumber(nextFirst);
        items[addNumber] = item;
        nextFirst--;
        size++;

        if (size >= items.length - 1) ResizeArgsBigger();
    }

    @Override
    public void addLast(Item item) {
        int addNumber = switchNumber(nextLast);
        items[addNumber] = item;
        nextLast++;
        size++;

        if (size >= items.length - 1) ResizeArgsBigger();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Item removeFirst() {
        if (size == 0) return null;
        int removeNumber = switchNumber(nextFirst + 1);

        Item remove = items[removeNumber];
        items[removeNumber] = null;
        size--;
        nextFirst++;

        if (size <= items.length / 4 && size >= 8) ResizeArgsSmaller();
        return remove;
    }

    @Override
    public Item removeLast() {
        if (size == 0) return null;
        int removeNumber = switchNumber(nextLast - 1);

        Item remove = items[removeNumber];
        items[removeNumber] = null;
        size--;
        nextLast--;

        if (size <= items.length / 4 && size >= 8) ResizeArgsSmaller();
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

    private void ResizeArgsBigger() {
        float multiplier = 2f;
        int total = (int) (items.length * multiplier);
        Item[] newItems = (Item[]) new Object[total];
        for (int i = 0; i < items.length; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
    }

    private void ResizeArgsSmaller() {
        float multiplier = 0.5f;
        int total = (int) (items.length * multiplier);
        Item[] newItems = (Item[]) new Object[total];
        for (int i = 0; i < total; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
    }


    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            int firstNumber = switchNumber(nextFirst + 1);

            @Override
            public boolean hasNext() {
                return items[firstNumber] != null;
            }

            @Override
            public Item next() {
                Item next = items[firstNumber];
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
