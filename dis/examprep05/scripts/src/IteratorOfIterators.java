import java.util.*;

public class IteratorOfIterators implements Iterator<Integer> {
    private final List<Iterator<Integer>> list;
    private int index = 0;

    public IteratorOfIterators(List<Iterator<Integer>> a) {
        list = a;
    }

    @Override
    public boolean hasNext() {
        for (Iterator<Integer> iterator : list) {
            if (iterator.hasNext()) return true;
        }
        return false;
    }

    @Override
    public Integer next() {
        Iterator<Integer> iterator = list.get(switchIndex(index));
        int number = switchIndex(index);
        while (!iterator.hasNext()) {
            number++;
            iterator = list.get(switchIndex(number));
        }
        index++;
        return iterator.next();
    }

    private int switchIndex(int index) {
        if (index > list.size() - 1) index %= list.size();
        return index;
    }
}

//public class IteratorOfIterators implements Iterator<Integer> {
//    LinkedList<Iterator<Integer>> iterators;
//
//    public IteratorOfIterators(List<Iterator<Integer>> a) {
//        iterators = new LinkedList<>();
//        for (Iterator<Integer> iterator : a) {
//            if (iterator.hasNext()) {
//                iterators.add(iterator);
//            }
//        }
//    }
//
//    @Override
//    public boolean hasNext() {
//        return !iterators.isEmpty();
//    }
//
//    @Override
//    public Integer next() {
//        if (!hasNext()) {
//            throw new NoSuchElementException();
//        }
//        Iterator<Integer> iterator = iterators.removeFirst();
//        int ans = iterator.next();
//        if (iterator.hasNext()) {
//            iterators.addLast(iterator);
//        }
//        return ans;
//    }
//}