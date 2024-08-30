package deque;

public class LinkedListDeque<T> {
    private final LLD<T> FrontSentinel;
    private final LLD<T> BackSentinel;
    private int size;

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

    public void addFirst(T item){
        LLD<T> add=new LLD<>(item);
        LLD<T> after=FrontSentinel.next;
        linkTwoList(FrontSentinel,add);
        linkTwoList(add,after);
        size++;
    }
    public void addLast(T item){
        LLD<T> add=new LLD<>(item);
        LLD<T> before=BackSentinel.prev;

        linkTwoList(add,BackSentinel);
        linkTwoList(before,add);
        size++;
    }
    public T removeFirst(){
        if(size==0)return null;
        LLD<T> remove=FrontSentinel.next;
        linkTwoList(FrontSentinel,remove.next);
        size--;
        return remove.value;
    }
    public T removeLast(){
        if(size==0)return null;
        LLD<T> remove=BackSentinel.prev;
        linkTwoList(remove.prev,BackSentinel);
        size--;
        return remove.value;
    }
    public void printDeque(){
        LLD<T> check=FrontSentinel.next;
        while (check!=null){
            if(check==BackSentinel)break;
            System.out.println(check.value);
            check=check.next;
        }
        System.out.println();
    }
    public boolean isEmpty(){
        return size == 0;
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
