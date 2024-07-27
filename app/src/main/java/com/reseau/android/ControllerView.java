package com.reseau.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ControllerView {

    private static ControllerView instance;
    private  MainActivity mainActivity;
    private MainActivity2 mainActivity2;

    private MainActivity3 mainActivity3;

    private MainActivity4 mainActivity4;

    private static int clickedPosition=-1;

    private Socket cSocket;

    private  static int idArticle = 0;


    private ArrayList<Integer> liste = new ArrayList<>();


    public static ControllerView getInstance() {
        if (instance == null) {
            instance = new ControllerView();
        }
        return instance;
    }

    public void setMainActivity(MainActivity mainActivity) {this.mainActivity = mainActivity;}

    public void setMainActivity2(MainActivity2 mainActivity2) {this.mainActivity2 = mainActivity2;}
    public void setMainActivity3(MainActivity3 mainActivity3) {this.mainActivity3 = mainActivity3;}

    public void setMainActivity4(MainActivity4 mainActivity4) {this.mainActivity4 = mainActivity4;}


    /*-----------------------------------------------------------------------------------------------------*/

    public  void handleLoginButtonClick(String username, String password, int isNewCustomer) {
        if (!username.trim().isEmpty() && !password.trim().isEmpty()) {
            new LoginTask().execute(username, password, String.valueOf(isNewCustomer));
        } else {
            dialogueMessage(mainActivity, "Erreur", "Champs vide");
        }
    }


    private class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            String password = params[1];
            int value = Integer.parseInt(params[2]);

            try {
                cSocket = new Socket("192.168.244.130", 4444);

                String[] raison = new String[1];

                if (!OVESP_Login(name, password, value, raison)) {
                    return raison[0];

                }
            } catch (IOException e) {
                return "Erreur de ClientSocket : " + e.getMessage();
            }

            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                dialogueMessage(mainActivity, "Erreur", result);
            } else {
                // Créez une intention pour passer à l'activité suivante
                Intent otherActivity = new Intent(mainActivity.getApplicationContext(), MainActivity2.class);
                mainActivity.startActivity(otherActivity);
                mainActivity.finish();
                //OVESP_ConsultUpdate();


               /* On va essayer de faire mainactivity2.OVESP_ConsultUpDate();

                if (mainActivity2 != null) {
                    mainActivity2.OVESP_ConsultUpdate();
                }*/

            }
        }
    }

    public void Achat(){
       new AcheterTask().execute();
    }
    private class AcheterTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            Log.println(Log.INFO,"montag","yo");
            String reponse = OVESP_Achat();
            Log.println(Log.INFO,"montag","yo2");
            //String requete = String.format("ACHAT#%d#%d", idArticle, Integer.parseInt(mainActivity2.getEditTextTextQuantite().getText().toString()));
            return reponse;
        }

        @Override
        protected void onPostExecute(String reponse) {
            handleAcheterResult(reponse);
            Log.println(Log.INFO,"montag","yo3");
        }
    }

    private void handleAcheterResult(String reponse) {
        mainActivity2.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.println(Log.INFO,"montag","yo4");

                String[] tokens = reponse.split("#");

                int idArticleResponse = Integer.parseInt(tokens[1]);
                if (idArticleResponse == -1) {
                    dialogueMessage(mainActivity2, "Erreur", "L'article n'a pas été trouvé");
                } else {
                    int quantiteArticle = Integer.parseInt(tokens[2]);
                    if (quantiteArticle == 0) {
                        dialogueMessage(mainActivity, "Erreur", "Quantité insuffisante dans le stock");
                    } else {
                        Log.println(Log.INFO,"montag","AVANT CADDIE");
                        //OVESP_Caddie3();
                        //Caddie();

                        //new OVESPUpdateTask().execute(); // Call OVESP_ConsultUpdate asynchronously
                    }
                }
            }
        });
    }

    public void ViderPanier() {
        new ViderPanierTask().execute();
    }

    public class ViderPanierTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            LibSocket.send(cSocket,"CANCELALL#1");

            String reponse = LibSocket.receive(cSocket);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Exécuter les actions nécessaires après l'annulation du panier
            Caddie();

        }
    }

    public void Logout(){
        new LogoutTask().execute();
    }

    /*private class AchatTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            Log.println(Log.INFO,"ICI","ICI2");
            //int nbr = Integer.parseInt(mainActivity2.getEditTextTextQuantite().getText().toString());
               return OVESP_Achat();

        }


        @Override
        protected void onPostExecute(String reponse) {
            handleAcheterResult(reponse);
        }
    }*/

    public void Caddie() {
        new OVESP_Caddie3Task().execute();
    }

    public class LogoutTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String requete = "LOGOUT#1";
            LibSocket.send(cSocket, requete);
            return LibSocket.receive(cSocket);
        }

        @Override
        protected void onPostExecute(String result) {

            System.out.println("Requete recu" + result);

            //PARSING REPONSE
            String[] mots = result.split("#"); // LOGIN#

            if (cSocket != null && cSocket.isConnected()) {
                try {
                    cSocket.close();
                    cSocket = null;
                } catch (IOException e) {
                    dialogueMessage(mainActivity4, "Erreur de ClientSocket", e.getMessage());
                }
            }
        }



    }

    private class OVESP_Caddie3Task extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String requete = "CADDIE#1";
            LibSocket.send(cSocket,requete);
            String reponse;
            return LibSocket.receive(cSocket);
        }

        @Override
        protected void onPostExecute(String reponse) {
            handleOVESP_CaddieResult(reponse);
        }
    }

    private void handleOVESP_CaddieResult(String reponse) {
        mainActivity2.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                liste.clear();

                TableLayout panier = mainActivity3.getPanier();
                panier.removeAllViews();

                String[] tokens = reponse.split("#");
                float totalprix = 0.0f;

                TableRow headerRow = new TableRow(mainActivity3);
                ajoutColonne(headerRow,  mainActivity3.getString(R.string.article));
                ajoutColonne(headerRow,  mainActivity3.getString(R.string.prix));
                ajoutColonne(headerRow,  mainActivity3.getString(R.string.quantite));
                panier.addView(headerRow);

                for (int i = 1, j =0; i < tokens.length; i += 4, j++){
                    int idArticle=Integer.parseInt(tokens[i]);
                    liste.add(idArticle);
                    String article = tokens[i + 1];
                    int quantite = Integer.parseInt(tokens[i + 2]);
                    String prixStr = tokens[i + 3].replace('.', ',');
                    float prix = Float.parseFloat(tokens[i + 3]);
                    totalprix += prix * quantite;

                    TableRow dataRow = new TableRow(mainActivity2);
                    dataRow.setTag(j);
                    clickedPosition = -1;

                    dataRow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clickedPosition = (int) v.getTag();

                            int selectedColor = ContextCompat.getColor(mainActivity2, R.color.selected_row_color);
                            int defaultColor = ContextCompat.getColor(mainActivity2, R.color.default_row_color);


                            for (int j = 0; j < panier.getChildCount(); j++) {
                                View tableRow = panier.getChildAt(j);
                                if (tableRow instanceof TableRow) {
                                    tableRow.setBackgroundColor(defaultColor);
                                }
                            }


                            dataRow.setBackgroundColor(selectedColor);

                        }
                    });
                    ajoutColonne(dataRow, article);
                    ajoutColonne(dataRow, prixStr);
                    ajoutColonne(dataRow, String.valueOf(quantite));
                    panier.addView(dataRow);

                }
                mainActivity3.getEditTextTextMontantTotal().setText(String.format("%.2f", totalprix).replace('.', ','));


            }

        });


    }

    public void SupprimerArticleSelec() {
        new SupprimerArticleSelecTask().execute();
    }

    private class SupprimerArticleSelecTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String requete;
            int i = clickedPosition;

            if (i != -1) {
                // Construction de la requête
                requete = String.format("CANCEL#%d", liste.get(i));
                liste.remove(i);



                LibSocket.send(cSocket,requete);
                return LibSocket.receive(cSocket);

            }
            return null;
        }

        @Override
        protected void onPostExecute(String reponse) {
            if (clickedPosition != -1) {
                // La suppression a réussi, effectuez des actions supplémentaires si nécessaire
                Caddie();
                clickedPosition = -1;
            } else {
                // Gérer les erreurs de suppression
                dialogueMessage(mainActivity3, "Erreur","Sélectionner  article");
            }
        }
    }

    public void ConfirmerAchat() {
        new ConfirmerAchatTask().execute();
    }

    public class ConfirmerAchatTask extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... voids) {
            String requete = "CONFIRMER";

            LibSocket.send(cSocket,requete);
            return LibSocket.receive(cSocket);
        }

        @Override
        protected void onPostExecute(String reponse) {
            if (reponse != null) {
                handleConfirmerAchat(reponse);
            } else {
                // Gérer le cas où la réponse est null (une erreur s'est produite)
                // Afficher un message d'erreur ou prendre d'autres mesures nécessaires
                dialogueMessage(mainActivity3,"Erreur", "Erreur lors de la confirmation de l'achat");
            }
        }

        private  void handleConfirmerAchat(String reponse){

            String[] mots = reponse.split("#");
            System.out.println("Requete recu" + reponse);

            if(!mots[1].equals("-1"))
            {
                dialogueMessage(mainActivity3,"Confirmation de la commande", "Numero de commande : " +mots[1]);
                Caddie();
            }

            else
                dialogueMessage(mainActivity3,"Erreur", "le caddie est vide");


        }
    }



    private void handleAcheterResult2(String reponse) {
        Log.println(Log.INFO,"ICI","ICI3");
        /*mainActivity2.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String[] mots = reponse.split("#");

                if(mots[0].equals("ACHAT"))
                {
                    if(mots[1].equals("0"))
                    {
                        dialogueMessage(mainActivity2,mots[0],mots[3]);
                    }
                    else if(!mots[1].equals("-1"))
                    {
                        if(!mots[2].equals("0"))
                        {
                            OVESP_Caddie3();
                            Consult(idArticle);

                        }
                        else
                            dialogueMessage(mainActivity2,mots[0],"Pas assez de stock");
                    }
                    else
                        dialogueMessage(mainActivity2,mots[0],"Not Found");
                }
                else
                    dialogueMessage(mainActivity2,mots[0],"Error");


            }
        });*/
    }



    public void OVESP_ConsultUpdate() {
        new OVESPUpdateTask().execute(idArticle);
    }
    private class OVESPUpdateTask extends AsyncTask<Integer, Void, String[]> {
        @Override
        protected String[] doInBackground(Integer... params) {
            int idArticle = params[0];
            Log.println(Log.INFO,"Montag", String.valueOf(idArticle));
            return Consult(idArticle);

        }

        @Override
        protected void onPostExecute(String[] results) {
            Log.println(Log.INFO,"Montag","APRES CONSULT");
            String idc = results[0];
            Log.println(Log.INFO,"Montag",results[1]);
            String nom = results[1];
            String prix = results[2];
            String lot = results[3];
            String image = results[4];

            Log.println(Log.INFO,"Montag","APRES CONSULT2");


            if (!idc.isEmpty()) {
                if (!nom.isEmpty() && !prix.isEmpty() && !lot.isEmpty() && !image.isEmpty()) {
                    prix = prix.replace(".", ",");
                    Log.println(Log.INFO,"Montag",prix);

                    String finalPrix = prix;
                    mainActivity2.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.println(Log.INFO,"Montag","dansleRUN");
                            mainActivity2.getEditTextTextArticle().setText(nom);

                            mainActivity2.getEditTextTextPrix().setText(finalPrix);

                            mainActivity2.getEditTextTextStock().setText(lot);

                            String imageNameWithoutExtension = image.replaceFirst("[.][^.]+$", "").toLowerCase();

                            int imageId = mainActivity2.getResources().getIdentifier(imageNameWithoutExtension, "drawable", mainActivity2.getPackageName());

                            mainActivity2.getImageView().setImageResource(imageId);
                        }
                     });

                }
            }
        }
    }


    public void Suivant() {
        new SuivantTask().execute();
    }

    private class SuivantTask extends AsyncTask<Void, Void, String[]> {
        @Override
        protected String[] doInBackground(Void... voids) {
            idArticle++;
            return Consult(idArticle);
        }

        @Override
        protected void onPostExecute(String[] results) {
            handleOVESPResults(results);
        }
    }

    // Utilisation de AsyncTask pour Precedent
    public void Precedent() {
        new PrecedentTask().execute();
    }
    private class PrecedentTask extends AsyncTask<Void, Void, String[]> {
        @Override
        protected String[] doInBackground(Void... voids) {
            idArticle--;
            return Consult(idArticle);
        }

        @Override
        protected void onPostExecute(String[] results) {
            handleOVESPResults(results);
        }
    }

    private void handleOVESPResults(String[] results) {
        String idc = results[0];
        String nom = results[1];
        String prix = results[2];
        String lot = results[3];
        String image = results[4];

        if (idc != null) {
            if (nom != null && prix != null && lot != null && image != null) {
                prix = prix.replace('.', ',');
                String finalPrix = prix;
                mainActivity2.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainActivity2.getEditTextTextArticle().setText(nom);
                        mainActivity2.getEditTextTextPrix().setText(finalPrix);
                        mainActivity2.getEditTextTextStock().setText(lot);


                        String imageNameWithoutExtension = image.replaceFirst("[.][^.]+$", "").toLowerCase();
                        int imageId = mainActivity2.getResources().getIdentifier(imageNameWithoutExtension, "drawable", mainActivity2.getPackageName());
                        mainActivity2.getImageView().setImageResource(imageId);
                    }
                });
            } else {
                if (idArticle < 1)
                    idArticle++;
                else
                    idArticle--;
            }
        }
    }



    private String[] Consult(int id) {

        id++;
        LibSocket.send(cSocket, "CONSULT#" + id);
        System.out.println("Requete envoye CONSULT#" + id);

        String reponse = LibSocket.receive(cSocket);
        System.out.println("Requete recu " + reponse);
        Log.println(Log.INFO,"Montag",reponse);


        String[] mots = reponse.split("#");
        if (mots[0].equals("CONSULT")) {
            if (!mots[1].equals("-1")) {
                Log.println(Log.INFO,"Montag",mots[2]);
                return new String[] { mots[1], mots[2], mots[3], mots[4], mots[5] };
            } else
                return new String[] { mots[1], null, null, null, null };
        } else
            return new String[] { mots[1], null, null, null, null };
    }


    public Boolean OVESP_Login(String user, String password, int nouveauClient, String[] raison)
    {
        String requete,reponse;
        boolean onContinue = true;


        // ***** Construction de la requete *********************
        requete = "LOGIN#" + user + "#" + password + "#" + nouveauClient;
        System.out.println("Requete : " + requete);

        // ***** Envoi requete + réception réponse **************
        LibSocket.send(cSocket, "LOGIN#" + user + "#" + password + "#" + nouveauClient);
        System.out.println("apres send");

        reponse = LibSocket.receive(cSocket);
        System.out.println("Requete recu" + reponse);

        //PARSING REPONSE
        String[] mots = reponse.split("#"); // LOGIN#

        if (mots[1].equals("ok")) {
            System.out.println("Login Client ok#" + mots[2]);


            //mainWindow.dialogueMessage("Connecter", "Re-bonjour cher client");

        }else{
            //if(mots[1].equals("ko"))
            raison[0] = mots[2];
            System.out.println("Erreur de Login " + raison);
            //mainWindow.dialogueErreur("Erreur",raison);

            onContinue = false;
        }

        return onContinue;

    }

    public String OVESP_Achat(){
        String requete,reponse;
        Log.println(Log.INFO,"montag","OVESPACHAT");
        String textValue = mainActivity2.getEditTextTextNumber().getText().toString();
        int intValue=0;

        // Conversion du texte en valeur int
        try {

             intValue = Integer.parseInt(textValue);

            Log.println(Log.INFO,"montag", String.valueOf(intValue));


        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Gérer le cas où l'utilisateur n'a pas saisi un nombre valide
        }




        LibSocket.send(cSocket,"ACHAT#"+(idArticle+1)+"#"+ intValue);
        Log.println(Log.INFO,"montag","OVESPACHAT2");

         reponse = LibSocket.receive(cSocket);

        Log.println(Log.INFO,"montag",reponse);


        return reponse;

    }

    public void OVESP_Caddie3() {



        String article;

        float prix = 0.0f, totalprix = 0.0f;
        int quantite = 0, i = 0;

        // Envoie de la requête
        //mainWindow.videTablePanier();

        Log.println(Log.INFO,"montag","DANSCADDIE");

        LibSocket.send(cSocket,"CADDIE#1");

        String reponse = LibSocket.receive(cSocket);

        Log.println(Log.INFO,"montag",reponse);


        System.out.println("Requete recu " + reponse);


        // Vider le panier
        //mainWindow.videTablePanier();

        // Parcourir la réponse
        String[] mots = reponse.split("#");
        System.out.println("obhebf" + mots.length );
        liste.clear();

        for (int j = 1; j < mots.length ; j = j+4) {

            // Obtenir l'identifiant de l'article
            try {
                i = Integer.parseInt(mots[j]);
                //liste.add(mots[j]);


                System.out.println(liste);

                System.out.println("Requete recu " + reponse);

            } catch (NumberFormatException e) {
                // Ignorer l'article
                continue;
            }

            // Obtenir l'article
            article = mots[j + 1];//.trim

            // Obtenir la quantité
            try {
                quantite = Integer.parseInt(mots[j + 2]);
                Log.println(Log.INFO,"montag", String.valueOf(quantite));
                System.out.println("QUANTITE " + quantite);
                System.out.println("QUANTITE " + mots[j + 2]);



            } catch (NumberFormatException e) {
                // Ignorer l'article
                continue;
            }

            // Obtenir le prix
            try {
                prix = Float.parseFloat(mots[j + 3]);
                System.out.println("PRIX " + prix);
            }catch (Exception e){
                System.out.println("PRIX execp " + mots[j + 3] + e);
            }

            totalprix += prix * quantite;

            Log.println(Log.INFO,"montag", String.valueOf(totalprix));

            // Ajouter l'article au panier
           // mainWindow.ajouteArticleTablePanier(article, prix, quantite);


        }
       // mainWindow.setTotal(totalprix);

    }



    private void showToast(String message, Context context) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText( context.getApplicationContext(), message, duration);
        toast.show();
    }

    public void dialogueMessage(Context context, String titre, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titre)
                .setMessage(message)
                .setPositiveButton("OK", null)  // Bouton "OK" pour fermer le popup
                .show();
    }

    private void ajoutColonne(TableRow row, String text) {
        TextView textView = new TextView(mainActivity3);
        textView.setText(text);
        textView.setPadding(8, 8, 8, 8);
        textView.setGravity(Gravity.CENTER);
        row.addView(textView);
    }



}
