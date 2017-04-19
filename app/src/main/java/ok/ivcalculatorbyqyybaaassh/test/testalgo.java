

/**
 * Created by QyyBAASSH on 16/09/2016.
 */
//*******************************************************************
// NOTE: please read the 'More Info' tab to the right for shortcuts.
//*******************************************************************

import java.util.Random;

// one class needs to have a main() method

    // arguments are passed using the text field below this editor


public class testalgo {
    public static void main(String[] args) {
        kk okp = new kk("ok");
        //int ok[] = {0, 1, 2, 3};
        //okp.bruteForce(ok, 16 , 1);
        //okp.testrand(30);
        okp.bruteForce(3, 3);
        //okp.printMap();
    }
}
// you can add other public classes to this editor in any order
 class kk {

    private String message;
    private boolean answer = false;

    public kk(String input) {
        message = "Why, " + input + " Isn't this something?";
    }

    public String toString() {
        return message;
    }



    public void bruteForce(int nb_value, int n_cadenas) {
        int nb_possibilite = (int) Math.pow(nb_value, n_cadenas);
        int _all[][] = new int[nb_possibilite][3];
        int a, b, c, i;
        Random rand = new Random();

        _all[0][0] = 0;
        _all[0][1] = 0;
        _all[0][2] = 0;

        for(a=0,b=0,c=0,i=1; i < nb_possibilite;i++)
        {
            while(isPresent(_all, a, b, c)) {
                a = rand.nextInt((nb_value-1) + 1);
                b = rand.nextInt((nb_value-1) + 1);
                c = rand.nextInt((nb_value-1) + 1);
            }
            _all[i][0] = a;
            _all[i][1] = b;
            _all[i][2] = c;
            //System.out.println(a +" " +b +" " +c + " i = "+(i+1) );
        }
        printMap(_all, _all.length);
        System.out.print("successful search, nb_results: " + i);
        System.out.println("");
    }

    boolean isPresent(int _t[][], int a, int b, int c)
    {
        for(int i=0; i < _t.length; i++)
        {
            if(_t[i][0] == a && _t[i][1] == b && _t[i][2] == c)return true;
        }
        return false;
    }


    void printArr(int a[], int n) {
        for (int i = 0; i < n; i++)
            System.out.print("i :" +i+ " "+a[i] + " ");
        System.out.println();
    }

    void printMap(int a[][], int n) {
        int row, columns, i;
        for (i = 0, row=0; row<a.length; row++, i++)
        {
            for(columns=0; columns<3; columns++)
                System.out.print(" " + a[row][columns]);
            System.out.print(" |i: "+ i +" ");
            System.out.println();
        }
    }

    void testrand(int max_test)
    {
        Random rand = new Random();
        for(int i = 0; i < max_test; i++)
        {
            int a = rand.nextInt(15 + 1);
            System.out.println(a);
        }
    }
}


