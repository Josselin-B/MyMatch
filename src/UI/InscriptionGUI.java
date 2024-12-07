package UI;

import composant.Participant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;


public class InscriptionGUI extends JFrame {

    private JTextField nameField;
    private JLabel idLabel;
    private JTextArea poulesTextArea; // Zone de texte pour afficher les poules

    Random random = new Random();


    /**
     * INITIALISATION DES VARS GLOBAL
     */
    private int id_n =0;      //Id qui sert à l'incrementation des participants
    int taillePoule =3;

    ArrayList<Participant> participants= new ArrayList<>();
    ArrayList<Participant[]> poules = new ArrayList<>();



    private void afficherPoules() {
        poulesTextArea.setText(""); // On vide le texte avant d'afficher
        int numeroPoule = 1;
        for (Participant[] poule : poules) {
            poulesTextArea.append("Poule " + numeroPoule + ":\n");
            for (Participant p : poule) {
                if (p != null) {
                    poulesTextArea.append(" - " + p.getName() + " (ID: " + p.getId() + ")\n");
                }
            }
            poulesTextArea.append("\n");
            numeroPoule++;
        }
    }



    public InscriptionGUI() {
        super("Inscription Tournoi");

        // Configuration de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        // Création du panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        /**
         * Section BOUTON Inscription
         *
         */
        // Label pour indiquer d'entrer le nom
        JLabel nameLabel = new JLabel("Entrez votre nom : ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(nameLabel, gbc);

        // Champ de texte pour le nom
        nameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(nameField, gbc);

        // Bouton valider les names
        JButton validateButton = new JButton("S'inscrire");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(validateButton, gbc);


        // Action du bouton des names
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nameField.getText().trim();
                if (!nom.isEmpty()) {
                    Participant participant = new Participant(nom,id_n);
                    id_n++;
                    participants.add(participant);
                    System.out.println(participant);
                } else {
                    idLabel.setText("Veuillez entrer un nom valide.");
                }
            }
        });

        /**
         * AFFICHAGE DES POULES
         */
        // Zone de texte pour afficher les poules
        poulesTextArea = new JTextArea(10, 30);
        poulesTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(poulesTextArea);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(scrollPane, gbc);


        /**
         * BOUTON POUR PHASE DE POULE
         */
        JButton poulesButton = new JButton("Générer les poules");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(poulesButton, gbc);

        // Action du bouton pour générer les poules
        poulesButton.addActionListener(e -> {
                    if (participants.isEmpty()) {
                        idLabel.setText("Aucun participant inscrit !");
                        return;
                    } else {
                        int nbPoules = (participants.size() / taillePoule)+(participants.size()%taillePoule);
                        System.out.println(nbPoules);
                        ArrayList<Participant> copyParticipant = new ArrayList<>(participants); //copie de la liste initiale de participant pour effectuer des opérations de suppression
                        int nbId = participants.size();
                        for(int i = 0; i < nbPoules; i++) {     //remplie les poules en décrémentantant à chaque fois
                           if(copyParticipant.isEmpty()){
                               System.out.println("Arret du tableau");
                               break;
                           }
                           Participant[] poule = new Participant[taillePoule];
                           for(int j = 0; j < taillePoule; j++) {   //creation de poule une à une
                               if(copyParticipant.isEmpty()){break;}        //cas ou une poule est inferieur à 3
                               int place = random.nextInt(0,nbId);              //remplie des poules de nb joueurs de façon aléatoire
                               System.out.println("place : "+place);
                               poule[j]=copyParticipant.get(place);
                               copyParticipant.remove(place);
                               nbId--;
                           }
                            poules.add(poule);
                        }

                    }

                    for(Participant[] poule : poules) {
                        for(Participant p : poule) {
                            System.out.println(p);
                        }
                    }
                    afficherPoules(); //afficher les poules

        });



        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Lancement de l'interface dans le thread d'Event Dispatch (bonnes pratiques Swing)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InscriptionGUI();
            }
        });
    }
}