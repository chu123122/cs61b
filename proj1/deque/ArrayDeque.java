package deque;

public class ArrayDeque<Item>{
    public Item[] items;
    public int size;
    public int nextFirst;
    public int nextLast;

    public ArrayDeque(){
        items=(Item[])new Object[8];
        size=0;
        nextFirst=7;
        nextLast=0;
    }
    public Item get(int index){
        if(index>size||index<0)return null;
        return items[index];
    }
    public void printDeque(){
        for (Item item:items) {
            System.out.println(item);
        }
        System.out.println();
    }
    public void addFirst(Item item){
        if(size>= items.length-1) ResizeArgsBigger();
        items[nextFirst]=item;
        nextFirst--;
        size++;
    }
    public void addLast(Item item){
        if(size>= items.length-1) ResizeArgsBigger();
        items[nextLast]=item;
        nextLast++;
        size++;
    }

    public Item removeFirst(){
        if(size==0) return null;
        if (size<=items.length/4&&size>=8) ResizeArgsSmaller();
        int removeNumber=switchNumber(nextFirst+1);
        if(outOfRange(removeNumber))return null;

        Item remove= items[removeNumber];
        items[removeNumber]=null;
        size--;
        return remove;
    }
    public Item removeLast(){
        if(size==0) return null;
        if (size<=items.length/4&&size>=8) ResizeArgsSmaller();
        int removeNumber=switchNumber(nextLast-1);
        if(outOfRange(removeNumber))return null;

        Item remove= items[removeNumber];
        items[removeNumber]=null;
        size--;
        return remove;
    }
    private int switchNumber(int number){
        boolean check=number<0||number>=items.length||items[number]==null;
        if(check){
            if(number==nextLast-1)
                return nextFirst+1;
            else
                return nextLast-1;
        }
        return number;
    }
    private boolean outOfRange(int index){
        if (index>=0&&index<=items.length-1) return false;
        return true;
    }
    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }
    private void ResizeArgsBigger(){
        float multiplier=2f;
        int total=(int)(items.length*multiplier);
        Item[] newItems=(Item[])new Object[total];
        for(int i=0;i<items.length;i++){
            newItems[i]=items[i];
        }
        items=newItems;
    }
    private void ResizeArgsSmaller(){
        float multiplier=0.5f;
        int total=(int)(items.length*multiplier);
        Item[] newItems=(Item[])new Object[total];
        for(int i=0;i<total;i++){
            newItems[i]=items[i];
        }
        items=newItems;
    }




}
