import java.util.*;

public class FilteredList<T> implements Iterable<T>{

    private final List<T> list;
    private final Predicate<T> filter;
    public FilteredList(List<T> L, Predicate<T> filter) {
        list=L;
        this.filter=filter;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index=0;
            @Override
            public boolean hasNext() {
                while (index<list.size()){
                    if(filter.test(list.get(index)))return true;
                    index++;
                }
                return false;
            }

            @Override
            public T next() {
                if(!hasNext())throw new NoSuchElementException();
                T restult=list.get(index);
                index++;
                return restult;
            }
        };
    }
}