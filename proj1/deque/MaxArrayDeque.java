package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{

    private final Comparator<T> comparator;
    public MaxArrayDeque(Comparator<T> c){
        super();
        comparator=c;
    }
    public T max(){
        T max=null;
        for (T item:this) {
            if(max==null|| comparator.compare(item, max) > 0){
                max=item;
            }
        }
        return max;
    }
    public T max(Comparator<T> c){
        T max=null;
        for (T item:this) {
            if(max==null|| c.compare(item, max) > 0){
                max=item;
            }
        }
        return max;
    }
}
