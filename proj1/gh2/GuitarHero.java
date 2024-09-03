package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class GuitarHero {

    public static void main(String[] args){
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        GuitarString[] guitarStrings=new GuitarString[keyboard.length()];
        for(int i=0;i<keyboard.length();i++){
            double frequency=440*Math.pow(2,(i-24)/12.0);
            guitarStrings[i]=new GuitarString(frequency);
        }

        while (true){
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index=keyboard.indexOf(key);
               if(index!=-1){
                   guitarStrings[index].pluck();
               }
            }

            double sample=0;
            for (GuitarString string: guitarStrings) {
                sample+=string.sample();
            }

            StdAudio.play(sample);

            for (GuitarString string: guitarStrings) {
                string.tic();
            }
        }
    }
}
