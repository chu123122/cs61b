import java.util.NoSuchElementException;

public class TYIterator extends OHIterator {
    public TYIterator(OHRequest queue) {
        super(queue);
    }

    @Override
    public OHRequest next() {
        if(!hasNext())
            throw new NoSuchElementException();
        OHRequest result=curr;
        if(result.description.contains("thank u")) curr=next();
        else curr=curr.next;
        return result;

//        OHRequest result=super.next();
//        if(result!=null&&result.description.contains("thank u"))
//            result=super.next();
//
//        return result;
    }
}
