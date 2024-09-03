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
    public void printDeque() {
        for (T item : this) {
            System.out.println(item);
        }
        System.out.println();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void addFirst(T item) {
        items[nextFirst] = item;
        size++;

        nextFirst = switchNumber(nextFirst - 1);//调整到下一位首位添加
        if (size >= items.length - 1) resizeArgsBigger();
    }

    @Override
    public void addLast(T item) {
        items[nextLast] = item;
        size++;

        nextLast = switchNumber(nextLast + 1);//调整到下一位末位添加
        if (size >= items.length - 1) resizeArgsBigger();
    }


    @Override
    public T removeFirst() {
        if (size == 0) return null;
        int removeNumber = switchNumber(nextFirst + 1);//调整到首位
        while (items[removeNumber] == null) {
            removeNumber += 1;
        }
        T remove = items[removeNumber];
        items[removeNumber] = null;
        size--;

        nextFirst = removeNumber;
        if (size <= items.length / 4 && size >= 8) resizeArgsSmaller();
        return remove;
    }

    @Override
    public T removeLast() {
        if (size == 0) return null;
        int removeNumber = switchNumber(nextLast - 1);//调整到末位
        while (items[removeNumber] == null) {
            removeNumber -= 1;
        }

        T remove = items[removeNumber];
        items[removeNumber] = null;
        size--;

        nextLast = removeNumber;
        if (size <= items.length / 4 && size >= 8) resizeArgsSmaller();
        return remove;
    }

    private int switchNumber(int number) {
        if (number >= items.length) {
            return number - items.length;
        } else if (number < 0) {
            return number + items.length;
        } else {
            return number;
        }
    }
    //TODO:存在问题！
    @Override
    public T get(int index) {
        if (index >= items.length || index < 0) return null;
        int switchNumber = switchNumber((nextFirst + 1) + index);

        return items[switchNumber];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void resizeArgsBigger() {
        float multiplier = 2f;
        int total = (int) (items.length * multiplier);
        T[] newItems = (T[]) new Object[total];
        for (int i = 0; i < nextLast; i++) {
            newItems[i] = items[i];
        }
        int newItemsLast= newItems.length-1;
        for(int i=items.length-1;i>nextFirst;i--){
            newItems[newItemsLast]=items[i];
            newItemsLast--;
        }
        nextFirst=newItemsLast;
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
        Deque<T> check = ((Deque<T>) o);
        for (int i = 0; i < size(); i++) {
            T self = this.get(i);
            T other = check.get(i);
            if (self != other) return false;
        }
        return true;
    }
}
