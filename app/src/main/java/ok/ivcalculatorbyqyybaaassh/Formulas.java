package ok.ivcalculatorbyqyybaaassh;

import java.util.Random;

/**
 * Created by QyyBAASSH on 15/09/2016.
 * Formulas that i used (everybody use them)
 * HP = (Base Stam + Stam IV) * Lvl(CPScalar)
 * CP = (Base Atk + Atk IV) * (Base Def + Def IV)^0.5 * (Base Stam + Stam IV)^0.5 * Lvl(CPScalar)^2 / 10
 * Lvl(CPScalar)= TotalCpMultiplier (~0.095*Sqrt(PokemonLevel)
 * 1 Digit after the semi-colon
 */


public class Formulas extends MainActivity{

    private static int nb_results;

    public static double[] AveragePerfectPercent(int _solution[][], int nb_solution) {
        double ok, tmp_tst;
        double total = 0.0f;
        double min_average_max[] = new double[nb_solution];
        for(int i = 0; i < nb_solution; i++) {
            if(_solution[i][0] == 0 && _solution[i][1] == 0 && _solution[i][2] == 0)
                ok = 0.0f;
            else
            {
                tmp_tst = (double) (_solution[i][0] + _solution[i][1] + _solution[i][2])/3;
                ok = 100.0f*(tmp_tst/15);
                System.out.println("ok:" + ok);
                min_average_max[i] = ok;
            }
            total += ok;
        }

        //printMap(_solution, nb_results);
        System.out.println("");
        System.out.print("successful search, nb_results: " + nb_solution);
        System.out.println("");
        return min_average_max;
    }

    /** HP = (Base Stam + Stam IV) * Lvl(CPScalar) **/
    public static double HPFormula(double CPScalar, int baseStam, int stamIV)
    {
        return (int) ((baseStam + stamIV)*CPScalar);
    }

    public static void setNbResults(int n)
    {
        n = nb_results;
    }

    public static int getNbResults()
    {
       return nb_results;
    }

    /** CP = (Base Atk + Atk IV) * (Base Def + Def IV)^0.5 * (Base Stam + Stam IV)^0.5 * Lvl(CPScalar)^2 / 10 **/
    public static double CPFormula(double CPScalar, int baseAttack, int atkIV, int baseDef, int defIV, int baseStam, int stamIV )
    {
        return (int) ((baseAttack+atkIV) * Math.pow(baseDef + defIV, 0.5f) * Math.pow(baseStam+stamIV, 0.5f) *Math.pow(CPScalar, 2) / 10);
    }

    public static double getMinPercent(double[] _perccent)
    {
        double min_val = 100.0f;
        for (int i = 0; i < _perccent.length; i++)
        {
            if(_perccent[i] < min_val)
                min_val = _perccent[i];
        }
        return min_val;
    }

    public static double getAvePercent(double[] _percccent)
    {
        double ave_val = 0.0f;
        for (int i = 0; i < _percccent.length; i++)
        {
            ave_val += _percccent[i];
        }
        return (ave_val/_percccent.length);
    }

    public static double getMaxPercent(double[] _perrcent)
    {
        double max_val = 0.0f;
        for (int i = 0; i < _perrcent.length; i++)
        {
            if(_perrcent[i] > max_val)
                max_val = _perrcent[i];
        }
        System.out.println(_perrcent.length);
        return max_val;
    }

    /** Parcours tout les IV possibles pour ensuite selectionner ceux qui renvoie les bonnes stats **/
    public static int[][] bruteForce(int nb_value, int n_cadenas, Pokemon pok, int[][] _all) {
        int nb_possibilite = (int) Math.pow(nb_value, n_cadenas);
        int _solution[][] = new int [nb_possibilite][3];
        int a, b, c, i, nb_solution_trouve;
        i = 1;
        nb_solution_trouve = 0;
        int tmp_hp;
        int tmp_cp;
        for(int y = 0; y < 4; ) {
            for (i = 0, a = 0, b = 0, c = 0; i < nb_possibilite; i++) {
                a = _all[i][0];
                b = _all[i][1];
                c = _all[i][2];
                Database.setCPscalar(Database.getCPScalar(pok.getDust(), y));
                tmp_hp = (int) Formulas.HPFormula(Database.getCPScalar(pok.getDust(), y), pok.getStam(), a);
                tmp_cp = (int) Formulas.CPFormula(Database.getCPScalar(pok.getDust(), y), pok.getAtt(), b, pok.getDef(), c, pok.getStam(), a);
                System.out.println("hp :" + tmp_hp + "cp:" + tmp_cp + "cp_max:"+ pok.getMaxCp());
                if (tmp_hp == pok.getHP() && tmp_cp == pok.getCP()) {
                    _solution[nb_solution_trouve][0] = a;
                    _solution[nb_solution_trouve][1] = b;
                    _solution[nb_solution_trouve][2] = c;
                    nb_solution_trouve++;
                }
            }
            if(!pok.isPowered())
                y+=2;
            else
                y++;

            System.out.println("_______________________________");
        }
        nb_results = nb_solution_trouve;

        return _solution;
    }

    public static boolean isPresent(int _t[][], int a, int b, int c)
    {
        for(int i=0; i < _t.length; i++)
        {
            if(_t[i][0] == a && _t[i][1] == b && _t[i][2] == c)return true;
        }
        return false;
    }

    public static void printMap(int a[][], int n) {
        int row, columns, i;
        for (i = 0, row=0; row<n; row++, i++)
        {
            for(columns=0; columns<3; columns++)
                System.out.print(" " + a[row][columns]);
            System.out.print(" |i: "+ i +" ");
            System.out.println();
        }
    }

    public static int[][] setAllPossibility(int nb_value, int nb_possibilite) {
        int a, b, c;
        int i;
        int[][] _all = new int[nb_possibilite][3];
        Random rand = new Random();
        _all[0][0] = 0;
        _all[0][1] = 0;
        _all[0][2] = 0;
        for (i = 1, a = 0, b = 0, c = 0; i < nb_possibilite; i++) {
            while (isPresent(_all, a, b, c)) {
                a = rand.nextInt((nb_value - 1) + 1);
                b = rand.nextInt((nb_value - 1) + 1);
                c = rand.nextInt((nb_value - 1) + 1);
            }
            _all[i][0] = a;
            _all[i][1] = b;
            _all[i][2] = c;
        }
        return _all;
    }


}
