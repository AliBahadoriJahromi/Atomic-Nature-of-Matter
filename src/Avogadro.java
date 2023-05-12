import java.util.*;
public class Avogadro {

    public static void main( String[] args ){
        Scanner scn = new Scanner(System.in);
        double a=0;
        double sum=0;
        int n = 0;
        while (scn.hasNextLine()) {
            String b = scn.nextLine();
            if (!b.equals("")) {
                if (b.charAt(0) == ' ')
                    b = b.substring(1);
                a = Double.parseDouble(b);
                a*=0.175*1E-6;
                sum += a*a;
                n++;
            }
        }
        //Data analysis:
        double D = sum /(2*n);
        double viscose = 9.135 * 1E-4;
        double T = 297;
        double ro = 0.5 * 1E-6;
        double k = ((6 * Math.PI * viscose * ro * D)/T);
        double R = 8.31446;
        double Na = R/k;
        String str= String.valueOf(k);
        str=str.substring(0,str.indexOf(".")+5).concat(str.substring(str.indexOf("E")));
        double result=Double.parseDouble(str);
        //print boltzmann & Avogadro
        System.out.println("Boltzmann : "+result);
        String str1= String.valueOf(Na);
        str1=str1.substring(0,str1.indexOf(".")+5).concat(str1.substring(str1.indexOf("E")));
        double result1=Double.parseDouble(str1);
        System.out.println("Avogadro : "+result1);
    }
}
