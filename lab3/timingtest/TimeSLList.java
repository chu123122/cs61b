package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        AList<Double> times=new AList<>(){};
        int[] ns={1000,2000,4000,8000,16000,32000,64000,128000};
        int[] counts={10000,10000,10000,10000,10000,10000,10000,10000};
        int j=0;
        while(j<=ns.length-1){
            SLList<Integer> aList=new SLList<>(){};
            for(int i =0;i<ns[j];i++){
                aList.addLast(i);
            }
            Stopwatch sw=new Stopwatch();
            for(int k=1;k<=10000;k++){
                aList.getLast();
            }
            double timeInSeconds = sw.elapsedTime();
            times.addLast(timeInSeconds);
            j++;
        }
        AList<Integer> Ns=new AList<>(){};
        AList<Integer> opCounts=new AList<>(){};
        for (int number: ns) {
            Ns.addLast(number);
        }
        for (int number: counts) {
            opCounts.addLast(number);
        }
        printTimingTable(Ns,times,opCounts);
    }

}
