import java.util.ArrayList;
public class Blob {
    private ArrayList<Integer> m_x = new ArrayList<Integer>();
    private ArrayList<Integer> m_y = new ArrayList<Integer>();
    private double m_xcenter;
    private double m_ycenter;

    //creates an empty blob
    public Blob(){
        m_xcenter = 0;
        m_ycenter = 0;
    }

    //adds pixel (x, y) to this blob
    public void add(int x, int y){
        m_x.add(x);
        m_y.add(y);
        getcenter();
    }

    //number of pixels added to this blob
    public int mass(){
        return m_x.size();
    }

    //string representation of this blob
    public String toString(){
        int mass = mass();
        return mass + " " + "(" + m_xcenter + "," + m_ycenter + ")" ;
    }

    //Eculidean distance between the center of masses of the two blobs
    public double distanceTo(Blob that){
        double dx = Math.abs(this.m_xcenter - that.m_xcenter);
        double dy = Math.abs(this.m_ycenter - that.m_ycenter);
        double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        return distance;
    }

    //check the blob to be a bead
    public boolean checkBead(int min_pixels){
        int m = mass();
        return m < min_pixels ? false : true;
    }
    
    //find the center of blob
    public double[] getcenter(){
        double[] center = new double[2];
        for (int i = 0; i < this.m_x.size(); i++) {
            this.m_xcenter = this.m_xcenter + this.m_x.get(i);
        }
        for (int i = 0; i < this.m_x.size(); i++) {
            this.m_ycenter = this.m_ycenter + this.m_y.get(i);
        }
        this.m_xcenter = Math.floor(this.m_xcenter / this.m_x.size() * 10000) / 10000;
        this.m_ycenter = Math.floor(this.m_ycenter / this.m_x.size() * 10000) / 10000;
        center[0] = this.m_xcenter;
        center[1] = this.m_ycenter;
        return center;
    }

}



