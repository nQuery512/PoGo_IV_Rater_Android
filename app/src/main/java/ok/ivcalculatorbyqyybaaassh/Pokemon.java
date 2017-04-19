package ok.ivcalculatorbyqyybaaassh;

//Informations relatives au calcul d'IV
public class Pokemon extends MainActivity {

    private String currentPokemon = null;
    private int currentPokemonNumber = 0;
    private Integer hp, cp, dust = 0; // Informations données par l'utilisateur
    private int stamina, attack, defense, max_cp; // Information données par la BDD
    private boolean powered;

    public Pokemon()
    {

    }

    public void setHP(Integer hp) {this.hp = hp;}
    public void setCP(Integer cp) {this.cp = cp;}
    public void setDust(Integer dust) {this.dust = dust;}
    public Integer getDust() {return this.dust;}
    public Integer getHP() {return this.hp;}
    public Integer getCP() {return this.cp;}
    public void setPowered(boolean powered){this.powered = powered;}

    public boolean isPowered()
    {
        return powered;
    }


    public int getStam()
    {
        return this.stamina;
    }

    public int getAtt()
    {
        return this.attack;
    }

    public int getDef()
    {
        return this.defense;
    }

    public int getMaxCp()
    {
        return this.max_cp;
    }


    // Stockage les informations relatives a l'espèce du Pokemon
    public void setCommonStats(int stam, int att, int def, int maxcp)
    {
        this.stamina = stam;
        this.attack = att;
        this.defense = def;
        this.max_cp = maxcp;
    }

    static public int isPokemon(String name, String[] tab_name) {

        for (int i = 0; i < MainActivity.nbPokemon; ) {
            if (name.equals(tab_name[i]))
                return (i+1);
            else i++;
        }
        return -1;
        }

    public int getCurrentPokemonNumber() {
        return this.currentPokemonNumber;
    }

    public void setCurrentPokemonNumber(int n) {
        if (n <= 151)
            this.currentPokemonNumber = n;
    }

    public String getCurrentPokemon() {
        return currentPokemon;
    }

    public void setCurrentPokemon(String current) {
        this.currentPokemon = current;

        if (!current.equals(currentPokemon)) {
            System.out.print("Erreur interne !");
        }
    }

}
