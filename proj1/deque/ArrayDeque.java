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

        nextFirst = switchNumberSet(nextFirst - 1);//调整到下一位首位添加
        if (size >= items.length - 1) resizeArgsBigger();
    }

    @Override
    public void addLast(T item) {
        items[nextLast] = item;
        size++;

        nextLast = switchNumberSet(nextLast + 1);//调整到下一位末位添加
        if (size >= items.length - 1) resizeArgsBigger();
    }


    @Override
    public T removeFirst() {
        if (size == 0) return null;
        int removeNumber = switchNumberSet(nextFirst + 1);//调整到首位
        while (items[removeNumber] == null) {
            removeNumber += 1;
        }
        T remove = items[removeNumber];
        items[removeNumber] = null;
        size--;

        nextFirst = removeNumber;
        if (size == 0) {
            nextFirst = 7;
            nextLast = 0;
        }
        if (size <= items.length / 4 && size >= 8) resizeArgsSmaller();
        return remove;
    }

    @Override
    public T removeLast() {
        if (size == 0) return null;
        int removeNumber = switchNumberSet(nextLast - 1);//调整到末位
        while (items[removeNumber] == null) {
            removeNumber -= 1;
        }

        T remove = items[removeNumber];
        items[removeNumber] = null;
        size--;

        nextLast = removeNumber;
        if (size == 0) {
            nextFirst = 7;
            nextLast = 0;
        }
        if (size <= items.length / 4 && items.length > 8) resizeArgsSmaller();
        return remove;
    }

    private int switchNumberSet(int number) {
        if (number >= items.length) {
            return number - items.length;
        } else if (number < 0) {
            return number + items.length;
        } else {
            return number;
        }
    }

    private int switchNumberSet(int number, T[] items) {
        if (number >= items.length) {
            return number - items.length;
        } else if (number < 0) {
            return number + items.length;
        } else {
            return number;
        }
    }

    private int switchNumberGet(int number) {
        if (number >= items.length) {
            int returnNumber = number - items.length;
            while (items[returnNumber] == null) {
                returnNumber++;
            }
            return returnNumber;
        } else if (number < 0) {
            int returnNumber = number + items.length;
            while (items[returnNumber] == null) {
                returnNumber--;
            }
            return returnNumber;
        } else {
            return number;
        }
    }

    @Override
    public T get(int index) {
        if (index >= items.length || index < 0) return null;
        int switchNumber = switchNumberGet((nextFirst + 1) + index);

        return items[switchNumber];
    }

    private void resizeArgsBigger() {
        float multiplier = 2f;
        int total = (int) (items.length * multiplier);
        T[] newItems = (T[]) new Object[total];
        for (int i = nextLast - 1; i >= 0; i--) {
            if (items[i] != null)
                newItems[i] = items[i];
        }
        int newItemsLast = newItems.length - 1;
        for (int i = items.length - 1; i > nextFirst; i--) {
            if (items[i] != null)
                newItems[newItemsLast] = items[i];
            newItemsLast--;
        }
        nextFirst = newItemsLast;
        items = newItems;
    }

    private boolean checkOutRange() {
        if (nextFirst < nextLast) {
            for (int j = nextLast; j < nextFirst; j++) {
                if (items[j] != null) return false;
            }
            return true;
        }
        return false;
    }

    //TODO:当nextLast一直推进到nextLast后会导致Resize异常！（同理nextFirst会导致一样的问题）
    //TODO:1.考虑添加bool变量（繁琐）2.划分区域，指针依旧跟随（尝试ing）
    private void resizeArgsSmaller() {
        float multiplier = 0.5f;
        int total = (int) (items.length * multiplier);
        T[] newItems = (T[]) new Object[total];

        boolean outOfRange = (items[0] == null && checkOutRange());
        boolean toLeftRange = false;
        boolean toRightRange = false;
        if (outOfRange) {
            if (nextFirst + nextLast < items.length) {
                toLeftRange = true;
            } else {
                toRightRange = true;
            }
        }
        if (!toRightRange) {
            int oldItemsFirst = switchNumberGet(0);
            for (int i = 0; i < nextLast; i++) {
                int switchNumber = switchNumberSet(i, newItems);
                if (i != switchNumber)
                    newItems[switchNumber] = items[oldItemsFirst];
                else
                    newItems[i] = items[oldItemsFirst];
                oldItemsFirst++;
            }
            nextLast = oldItemsFirst;
        }

        if (!toLeftRange) {
            int oldItemsLast = switchNumberGet(items.length - 1);
            int currentNextLast;
            if (toRightRange) currentNextLast = newItems.length - (items.length - nextFirst);
            else currentNextLast = nextLast;
            for (int i = newItems.length - 1; i > currentNextLast; i--) {
                int switchNumber = switchNumberSet(i, newItems);
                if (i != switchNumber)
                    newItems[switchNumber] = items[oldItemsLast];
                else
                    newItems[i] = items[oldItemsLast];
                oldItemsLast--;
            }
            if (!toRightRange)
                nextFirst = newItems.length - (items.length - nextFirst);
            else
                nextLast = oldItemsLast;
        }
        items = newItems;
    }


    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int firstNumber = switchNumberSet(nextFirst + 1);

            @Override
            public boolean hasNext() {
                return items[firstNumber] != null;
            }

            @Override
            public T next() {
                T next = items[firstNumber];
                firstNumber = switchNumberSet(firstNumber + 1);
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
