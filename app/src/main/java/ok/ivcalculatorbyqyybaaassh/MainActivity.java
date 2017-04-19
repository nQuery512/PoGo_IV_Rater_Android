package ok.ivcalculatorbyqyybaaassh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

//TODO: - Comparaison et récupération des résultats avec les stats données (100%)
//TODO: - Intégration des noms anglais des Pokemons
//TODO: - (v.1.0) Sortie de l'application sur multiple versions d'android (4.2 à 5.0)
//TODO: - (v.1.5) Affichage de tout les Pokémons disponible avec leurs barres de stats
//TODO: - (v.2.0) Création de compte + Rangement des Pokemons par box

public class MainActivity extends AppCompatActivity {

    static final int nbPokemon = 151;
    private int trainer_level;

    public int getTrainerLevel() {
        return this.trainer_level;
    }

    public void setTrainerLevel(int n) {
        this.trainer_level = n;
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
    private boolean isEmpty(AutoCompleteTextView actvText) {
        return actvText.getText().toString().trim().length() == 0;
    }

    private void printDoubleArray(double[] array, int size)
    {
        for(int i = 0; i < size; i++)
        {
            System.out.println(" "+ i + ": " + array[i] );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editTextCP = (EditText)findViewById(R.id.editText_CP);
        final EditText editTextHP = (EditText)findViewById(R.id.editText_HP);
        final CheckBox cb_powered = (CheckBox)findViewById(R.id.checkBox);
        final EditText editTextDust = (EditText)findViewById(R.id.editText_dust);


        final Pokemon pok = new Pokemon();
        final int[][] _all = Formulas.setAllPossibility(16, (int) Math.pow(16, 3));

        // Initialisation de la liste des Pokemons
        final String[] tableauString = getResources().getStringArray(R.array.pokemonNameList);

        // Initialisation de la base de données des stats communes
        final Database data = new Database(getResources().getIntArray(R.array.CP),
                getResources().getIntArray(R.array.Stamina),
                getResources().getIntArray(R.array.Attack),
                getResources().getIntArray(R.array.Defense),
                getResources().getStringArray(R.array.array_CPmult),
                getResources().getIntArray(R.array.array_dust_int));

        final AutoCompleteTextView autoComplete = (AutoCompleteTextView) findViewById(R.id.autocompletion);
        final Button boutonRecherche = (Button) findViewById(R.id.ButtonEnvoyer);
        //On crée la liste d'autocomplétion à partir de notre tableau de string appelé tableauString
        //android.R.layout.simple_dropdown_item_1line permet de définir le style d'affichage de la liste
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, tableauString);

        //On affecte cette liste d'autocomplétion à notre objet d'autocomplétion
        autoComplete.setAdapter(adapter);
        autoComplete.setClickable(false);
        autoComplete.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String tmp = autoComplete.getText().toString(); // copie du choix
                int test_number = Pokemon.isPokemon(tmp, tableauString);

                if (test_number != -1) // SI le pokemon existe
                {
                    pok.setCurrentPokemon(tmp); // Récupération du nom
                    pok.setCurrentPokemonNumber(test_number); // Récupération du numéro

                    pok.setCommonStats(data.getStamina(test_number-1), data.getAttack(test_number-1),
                            data.getDefense(test_number-1),
                            data.getCPMAX(test_number-1));
                }
            }
        });

        //final Spinner spinner_dust = (Spinner) findViewById(R.id.dust_spinner);
        //final ArrayAdapter adapter_spinner = ArrayAdapter.createFromResource(this,
               // R.array.array_dust, android.R.layout.simple_spinner_item);
       // adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner_dust.setAdapter(adapter_spinner);



        //Enfin on rajoute un petit écouteur d'évènement sur le bouton pour afficher
        //dans un Toast ce que l'on a rentré dans notre AutoCompleteTextView
        boutonRecherche.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //On vérifie que les informations ont bien été rentrées
                if(!isEmpty(autoComplete) && !isEmpty(editTextCP)
                         && !isEmpty(editTextHP))// && !isEmpty(spinner_dust))
                {
                    //Extraction de la valeur du spinner

                    //int ind = spinner_dust.getSelectedItemPosition();
                   // String[] spinner_content = getResources().getStringArray(R.array.array_dust);
                    //int value = Integer.valueOf(spinner_content[ind]);

                    pok.setDust(Integer.valueOf(editTextDust.getText().toString()));
                    pok.setCP(Integer.valueOf(editTextCP.getText().toString()));
                    pok.setHP(Integer.valueOf(editTextHP.getText().toString()));
                    pok.setPowered(cb_powered.isChecked());

                    //Get all IV's that matches with the current pokemon
                    int _iv[][] = Formulas.bruteForce(16, 3, pok, _all);

                    //Return the percent data from previous fonction
                    double _percent[] = Formulas.AveragePerfectPercent(_iv, Formulas.getNbResults());
                    printDoubleArray(_percent, Formulas.getNbResults());


                    Toast.makeText(MainActivity.this, "nombre de solutions: "
                            + Formulas.getNbResults()
                            + " max: " + Formulas.getMinPercent(_percent)
                            + " moy: " + Formulas.getAvePercent(_percent)
                            + " min: " + Formulas.getMaxPercent(_percent)
                            + " CPscalar: " + Database.retCPscalar()
                            + " Stam: " + pok.getStam()
                            + " Att: " + pok.getAtt()
                            + " Def: " + pok.getDef()
                            ,
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "ERREUR DE SAISIE", Toast.LENGTH_LONG).show();
                    if(isEmpty(editTextCP))
                        editTextCP.setError("Indiquez ces CP");
                    if(isEmpty(editTextHP))
                        editTextHP.setError("Indiquez ces HP");
                    if(isEmpty(autoComplete))
                        autoComplete.setError("Indiquez votre Pokémon");
                }

            }
        });
   }
}

