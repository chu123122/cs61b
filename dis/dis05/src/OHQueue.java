import java.util.Iterator;
public class OHQueue implements Iterable<OHRequest> {

    private static OHRequest first;
    public OHQueue (OHRequest queue) {
        first =queue;
    }

    public static void main(String [] args) {
        OHRequest s5 = new OHRequest("I deleted all of my files", "Allyson", null);
        OHRequest s4 = new OHRequest("conceptual: what is Java", "Omar", s5);
        OHRequest s3 = new OHRequest("git: I never did lab 1", "Connor", s4);
        OHRequest s2 = new OHRequest("help", "Hug", s3);
        OHRequest s1 = new OHRequest("no I haven't tried stepping through", "Itai", s2);

        for (OHRequest next : new OHQueue(s1)) {
            System.out.println(next.name);
        }
    }

    @Override
    public Iterator<OHRequest> iterator() {
        return new OHIterator(first);
    }
}