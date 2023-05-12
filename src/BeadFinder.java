import java.util.ArrayList;
import java.awt.Color;
public class BeadFinder
{
    private ArrayList<Blob> blobs = new ArrayList<Blob>();

    //changing the original picture to black and white
    public static Picture cleanFrame(Picture pic,double tau){
        Picture CFrame=new Picture(pic.width(), pic.height());
        for (int i = 0; i < pic.width(); i++) {
            for (int j = 0; j < pic.height(); j++) {
                Color color;
                color=pic.get(i,j);
                if (color.getGreen()<=tau && color.getRed()<=tau  && color.getBlue()<=tau ){
                    CFrame.set(i,j,Color.BLACK);
                }
                else {CFrame.set(i,j,Color.white);}
            }
        }
        return CFrame;
    }
   
    //filling the blob by pixels
    public static Blob fillblob(Picture Cframe, int col, int row){

        Blob blob = new Blob();
        //تشخیص کوچکترین مستطیلی که بلاب در ان قرار دارد
        //(Fcol, Frow):مختصات گوشه بالا سمت چپ مربع, (Lcol, Lrow):مختصات گوشه پایین سمت راست مربع
        Color color = Cframe.get(col, row);
        int Fcol = col;
        int Lcol = col;
        int Frow = row, Lrow = row;
        int radius;
        boolean flag = true;

        if (col == 0) {
            while (color.equals(color.white)) {
                Lrow++;
                color = Cframe.get(col, Lrow);
            }
            Lrow--;
            row = (Frow + Lrow)/2;
        }

        color = color.white;
        while (color.equals(color.white)) {
            Lcol++;
            if (Lcol == 640) {
                flag = false;
                break;
            } else {
                color = Cframe.get(Lcol, row);
            }
        }
        Lcol--;

        if (flag) {
            float center = (Fcol + Lcol) / 2;
            radius = (int)(center - Fcol + 2);
            Frow = row - radius;
            Lrow = row + radius + 3;
        } else {
            Frow = row - 9;
            Lrow = row + 9 + 3;
        }

        Fcol = Fcol<1   ? 0   : Fcol;
        Lcol = Lcol>637 ? 637 : Lcol;
        Frow = Frow<0   ? 0   : Frow;
        Lrow = Lrow>479 ? 479 : Lrow;

        //پر کردن بلاب
        for (int i = (Fcol==0 ? Fcol : Fcol -1); i < Lcol +2; i++) {
            for (int j = Frow; j < Lrow; j++) {
                color = Cframe.get(i, j);
                if (color.equals(color.white)) {
                    blob.add(i, j);
                }
            }
        }
        blob.getcenter();
        return blob;
    }

    //finds all blobs in the specified picture using luminance threshold tau
    public BeadFinder(Picture picture, double tau){

        //color:رنگ پیکسل خوانده شده  ,up:رنگ پیکسل بالایی آن  ,left:رنگ پیکسل چپ   , ...
        Color color, up, left, down_left, down2_left;
        int a, b, c, d;
        boolean firstwhite;
        Picture Cframe = cleanFrame(picture, tau);
        for (int col = 0; col < 640; col++) {
            for (int row = 0; row < 480; row++) {
                color = Cframe.get(col, row);

                a = row!=0               ? row-1 : row;
                b = col!=0               ? col-1 : col;
                c = row!=479             ? row+1 : row;
                d = row!=478 && row!=479 ? row+2 : row;

                up          = Cframe.get(col, a);
                left        = Cframe.get(b, row);
                down_left   = Cframe.get(b, c);
                down2_left  = Cframe.get(b, d);

                //چک کردن اولین پیکسل سفید هر بلاب
                if (col == 0 && color.equals(color.white)) {
                    firstwhite = up.equals(color.black);
                } else {
                    firstwhite = color.equals(color.white) && up.equals(color.black) && left.equals(color.black) && down_left.equals(color.black) && down2_left.equals(color.black);
                }

                if (firstwhite) {
                    blobs.add(fillblob(Cframe, col, row));
                }
            }
        }
    }

    //return all beads (blobs with >= min pixels)
    public ArrayList<Blob> getBeads(int min){
        ArrayList<Blob> beads = new ArrayList<Blob>();
        for (int i = 0; i < blobs.size(); i++) {
            if (blobs.get(i).mass() >= min) {
                beads.add(blobs.get(i));
            }
        }
        return beads;
    }

    public static void main( String[] args ) {
        String frame1=args[2];
        String path="F:/AvogadroProject-1/"+frame1;
        Picture notCleanFrame=new Picture(path);

//        Picture picture = new Picture(args[2]);
        double tau = Double.parseDouble(args[1]);
        BeadFinder frame = new BeadFinder(notCleanFrame, tau);
        int min = Integer.parseInt(args[0]);
        ArrayList<Blob> beads = frame.getBeads(min);
        for (int i = 0; i < beads.size(); i++) {
            Blob b=beads.get(i);
            System.out.println(b.toString());
        }
    }
}
//java BeadTracker 29 210 25 run_1 | java Avogadro