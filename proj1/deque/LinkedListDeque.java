package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>,Deque<T>{
    private final LLD<T> FrontSentinel;
    private final LLD<T> BackSentinel;
    private int size;
    @Override
    public T get(int index){
        int currentIndex=0;
        LLD<T> currentCheck=FrontSentinel.next;
        while (currentIndex<index){
            currentCheck=currentCheck.next;
            currentIndex++;
        }
        return currentCheck.value;
    }
    public T getRecursive(int index){
        return recursiveHelper(index,FrontSentinel.next);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            public LLD<T> Front=FrontSentinel.next;
            @Override
            public boolean hasNext() {
                return Front.next != null;
            }

            @Override
            public T next() {
                T item= Front.value;;
                Front=Front.next;
                return item;
            }
        };
    }
    @Override
    public boolean equals(Object o){
        if(o==null)return false;
        if(!(o instanceof LinkedListDeque))return false;
        if(this.size()!=((LinkedListDeque<?>) o).size())return false;
        LLD<T> selfFront=this.FrontSentinel.next;
        LLD<T> checkFront=((LinkedListDeque<T>) o).FrontSentinel.next;
        while(selfFront!=null){
            if(selfFront.value!= checkFront.value)return false;
            selfFront=selfFront.next;
            checkFront=checkFront.next;
        }
        return true;
    }
    @Override
    public void addFirst(T item){
        LLD<T> add=new LLD<>(item);
        LLD<T> after=FrontSentinel.next;
        linkTwoList(FrontSentinel,add);
        linkTwoList(add,after);
        size++;
    }
    @Override
    public void addLast(T item){
        LLD<T> add=new LLD<>(item);
        LLD<T> before=BackSentinel.prev;

        linkTwoList(add,BackSentinel);
        linkTwoList(before,add);
        size++;
    }
    @Override
    public T removeFirst(){
        if(size==0)return null;
        LLD<T> remove=FrontSentinel.next;
        linkTwoList(FrontSentinel,remove.next);
        size--;
        return remove.value;
    }
    @Override
    public T removeLast(){
        if(size==0)return null;
        LLD<T> remove=BackSentinel.prev;
        linkTwoList(remove.prev,BackSentinel);
        size--;
        return remove.value;
    }
    @Override
    public void printDeque(){
        LLD<T> check=FrontSentinel.next;
        while (check!=null){
            if(check==BackSentinel)break;
            System.out.println(check.value);
            check=check.next;
        }
        System.out.println();
    }
    public int size(){
        return size;
    }
    public LinkedListDeque(){
        FrontSentinel=new LLD<>(null);
        BackSentinel=new LLD<>(null);
        linkTwoList(FrontSentinel,BackSentinel);
        size=0;
    }
    private T recursiveHelper(int index,LLD<T> check){
        if(index==0){
            return check.value;
        }else{
            return recursiveHelper(index-1,check.next);
        }
    }
    private void linkTwoList(LLD<T> first, LLD<T> twice){
        first.next=twice;
        twice.prev=first;
    }



    private class LLD<Item>{
        public Item value;
        public LLD<Item> prev;
        public LLD<Item> next;
        public LLD(Item value){
            this.value=value;
        }
    }
}
