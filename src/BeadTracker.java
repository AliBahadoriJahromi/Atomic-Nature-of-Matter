import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class BeadTracker {
    public BeadTracker(int minPixel, double delta,BeadFinder A1, BeadFinder A2) {

        ArrayList<Blob> beads1 = A1.getBeads(minPixel);
        ArrayList<Blob> beads2 = A2.getBeads(minPixel);
        for (int i = 0; i < beads1.size(); i++) {
            for (int j = 0; j < beads2.size(); j++) {
                double distance = beads1.get(i).distanceTo(beads2.get(j));
                if (distance <= delta) {
                    System.out.format("%.4f\n",distance);
                    break;
                }
            }
        }
    }
    public static void main(String[] args) throws IOException {
        int minPixel= Integer.parseInt(args[0]);
        double tau= Double.parseDouble(args[1]);
        double delta= Double.parseDouble(args[2]);
        String folder=args[3];
        //read folder
        String[] frames = new String[201];
        //list frames
        File filename = new File("F:/AvogadroProject-1/"+folder);
        String[] list = filename.list();
        //distance 2 bead
        for (int i = 0; i < 199; i++) {
            Picture a1 = new Picture("F:/AvogadroProject-1/"+folder+"/"+list[i]);
            BeadFinder b1 = new BeadFinder(a1, tau);
            Picture a2 = new Picture("F:/AvogadroProject-1/"+folder+"/"+list[i+1]);
            BeadFinder b2 = new BeadFinder(a2, tau);
            BeadTracker A = new BeadTracker(minPixel, delta, b1, b2);
//                System.out.println();
        }
    }//end main

}//end class

// java BeadFinder 0 180 run_1/frame00000.jpg

// java BeadTracker 25 180.0 25.0 run_1 | java Avogadro

// java BeadTracker 25 180 25 run_1 > displacements-run_1.txt
// Get-Content displacements-run_1.txt | java Avogadro


