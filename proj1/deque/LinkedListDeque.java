package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {
    private final LLD<T> frontSentinel;
    private final LLD<T> backSentinel;
    private int size;

    public LinkedListDeque() {
        frontSentinel = new LLD<>(null);
        backSentinel = new LLD<>(null);
        linkTwoList(frontSentinel, backSentinel);
        size = 0;
    }

    @Override
    public T get(int index) {
        int currentIndex = 0;
        LLD<T> currentCheck = frontSentinel.next;
        while (currentIndex < index) {
            currentCheck = currentCheck.next;
            currentIndex++;
        }
        return currentCheck.value;
    }

    public T getRecursive(int index) {
        return recursiveHelper(index, frontSentinel.next);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private LLD<T> front = frontSentinel.next;

            @Override
            public boolean hasNext() {
                return front.next != null;
            }

            @Override
            public T next() {
                T item = front.value;
                ;
                front = front.next;
                return item;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Deque)) {
            return false;
        }
        if (this.size() != ((Deque<?>) o).size()) {
            return false;
        }
        Deque<T> check = ((Deque<T>) o);
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i) != check.get(i)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void addFirst(T item) {
        LLD<T> add = new LLD<>(item);
        LLD<T> after = frontSentinel.next;
        linkTwoList(frontSentinel, add);
        linkTwoList(add, after);
        size++;
    }

    @Override
    public void addLast(T item) {
        LLD<T> add = new LLD<>(item);
        LLD<T> before = backSentinel.prev;

        linkTwoList(add, backSentinel);
        linkTwoList(before, add);
        size++;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        LLD<T> remove = frontSentinel.next;
        linkTwoList(frontSentinel, remove.next);
        size--;
        return remove.value;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        LLD<T> remove = backSentinel.prev;
        linkTwoList(remove.prev, backSentinel);
        size--;
        return remove.value;
    }

    @Override
    public void printDeque() {
        LLD<T> check = frontSentinel.next;
        while (check != null) {
            if (check == backSentinel) {
                break;
            }
            System.out.println(check.value);
            check = check.next;
        }
        System.out.println();
    }

    public int size() {
        return size;
    }

    private T recursiveHelper(int index, LLD<T> check) {
        if (index == 0) {
            return check.value;
        } else {
            return recursiveHelper(index - 1, check.next);
        }
    }

    private void linkTwoList(LLD<T> first, LLD<T> twice) {
        first.next = twice;
        twice.prev = first;
    }


    private class LLD<Item> {
        private final Item value;
        private LLD<Item> prev;
        private LLD<Item> next;

        public LLD(Item value) {
            this.value = value;
        }
    }
}
