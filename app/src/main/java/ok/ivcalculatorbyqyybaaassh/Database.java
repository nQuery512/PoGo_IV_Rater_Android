package ok.ivcalculatorbyqyybaaassh;


/**
 * Created by QyyBAASSH on 10/09/2016.
 * Stockage et Gestion de la base de données (CP + Stats)
 */
public class Database extends MainActivity
{
    private int[] _cpmax;
    private int[] _stam;
    private int[] _att;
    private int[] _def;
    private static String[] _CPMultiplier;
    private static int[] _dust;
    private static double CPscalar;

    public Database(int[] cp, int[] stam, int[] att, int[] def, String[] _cpMult, int[] dust)
    {
        this._cpmax = cp;
        this._stam = stam;
        this._att = att;
        this._def = def;
        _dust = dust;
        _CPMultiplier = _cpMult;
    }
    // d indique le decalage
    public static double getCPScalar(int dust_value, int d)
    {
        int indice;
        for(int i = 0; ; i++)
        {
           if(_dust[i] == dust_value)
           {
               indice = i;
               break;
           }
        }
        return Double.valueOf(_CPMultiplier[(indice*4) + d]); //* Math.sqrt(indice*4+d);
    }

    public static void setCPscalar(double CPscalarr)
    {
        CPscalar = CPscalarr;
    }

    public static double retCPscalar()
    {
        return CPscalar;
    }

    public int getDefense(int pokemon_number)
    {
        return _def[pokemon_number];
    }

    public int getAttack(int pokemon_number)
    {
        return _att[pokemon_number];
    }

    public int getStamina(int pokemon_number)
    {
        return _stam[pokemon_number];
    }

    public int getCPMAX(int pokemon_number)
    {
        return _cpmax[pokemon_number];
    }
}
