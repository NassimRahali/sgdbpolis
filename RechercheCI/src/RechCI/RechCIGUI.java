
package RechCI;

import com.mongodb.*;
import java.awt.Color;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Thibault
 */
public class RechCIGUI extends javax.swing.JFrame
{
    private DefaultListModel modActeurs;
    private DefaultListModel modRealisateurs;
    private DefaultListModel modGenres;
    DB moviesDB;
    MongoClient mongoC;
    DBCollection collection;
    
    public RechCIGUI()
    {
        
        initComponents();
        this.PRech.setVisible(false);
        this.PResult.setVisible(false);
        
        modActeurs = new DefaultListModel();
        this.lActeurs.setModel(modActeurs);
        
        modRealisateurs = new DefaultListModel();
        this.lRealisateurs.setModel(modRealisateurs);
        
        modGenres = new DefaultListModel();
        this.lGenres.setModel(modGenres);
        
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        this.tResults.getColumnModel().getColumn(0).setMinWidth(50);
        this.tResults.getColumnModel().getColumn(0).setMaxWidth(70);
        this.tResults.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        PRech = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        bAddActeur = new javax.swing.JButton();
        bRemoveActeur = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lActeurs = new javax.swing.JList();
        tfActeur = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tfTitre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lRealisateurs = new javax.swing.JList();
        bRemoveRealisateur = new javax.swing.JButton();
        bAddRealisateur = new javax.swing.JButton();
        tfRealisateur = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lGenres = new javax.swing.JList();
        jLabel9 = new javax.swing.JLabel();
        bRemoveGenre = new javax.swing.JButton();
        bAddGenre = new javax.swing.JButton();
        tfGenre = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        tfMPAA = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tfVoteDe = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        tfVoteA = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tfNVotesDe = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        tfNVotesA = new javax.swing.JTextField();
        dcApres = new com.toedter.calendar.JDateChooser();
        dcAvant = new com.toedter.calendar.JDateChooser();
        bSearchOnId = new javax.swing.JButton();
        tfID = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lCrit = new javax.swing.JLabel();
        bSearchOnCriteria = new javax.swing.JButton();
        PResult = new javax.swing.JPanel();
        lbMongo = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tResults = new javax.swing.JTable();
        Menu = new javax.swing.JMenuBar();
        miMenuReset = new javax.swing.JMenu();
        miConnexionMongo = new javax.swing.JMenuItem();
        miResetTable = new javax.swing.JMenuItem();
        miDecoMongo = new javax.swing.JMenuItem();
        miAlimCB = new javax.swing.JMenu();
        miCommander = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rech CI");

        PRech.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setText("Acteur");

        bAddActeur.setText("ADD");
        bAddActeur.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bAddActeurActionPerformed(evt);
            }
        });

        bRemoveActeur.setText("REMOVE");
        bRemoveActeur.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bRemoveActeurActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(lActeurs);

        jLabel4.setText("Titre");

        jLabel5.setText("Réalisateur");

        jScrollPane3.setViewportView(lRealisateurs);

        bRemoveRealisateur.setText("REMOVE");
        bRemoveRealisateur.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bRemoveRealisateurActionPerformed(evt);
            }
        });

        bAddRealisateur.setText("ADD");
        bAddRealisateur.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bAddRealisateurActionPerformed(evt);
            }
        });

        jLabel6.setText("Dates (JJ/MM/AAAA)");

        jLabel7.setText("Après");

        jLabel8.setText("Avant");

        jScrollPane2.setViewportView(lGenres);

        jLabel9.setText("Genre");

        bRemoveGenre.setText("REMOVE");
        bRemoveGenre.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bRemoveGenreActionPerformed(evt);
            }
        });

        bAddGenre.setText("ADD");
        bAddGenre.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bAddGenreActionPerformed(evt);
            }
        });

        jLabel10.setText("Certification MPAA");

        jLabel11.setText("Vote average de");

        tfVoteDe.setText("0");

        jLabel12.setText("à");

        tfVoteA.setText("10");

        jLabel13.setText("Nombre de votes de");

        tfNVotesDe.setText("0");

        jLabel14.setText("à");

        tfNVotesA.setText("5814");

        dcApres.setDateFormatString("dd/MM/YYYY");

        dcAvant.setDateFormatString("dd/MM/YYYY");

        bSearchOnId.setText("Recherche (ID)");
        bSearchOnId.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bSearchOnIdActionPerformed(evt);
            }
        });

        jLabel1.setText("Recherche sur un ID uniquement");

        lCrit.setText("Recherche sur des critères");

        bSearchOnCriteria.setText("Recherche (critères)");
        bSearchOnCriteria.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bSearchOnCriteriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PRechLayout = new javax.swing.GroupLayout(PRech);
        PRech.setLayout(PRechLayout);
        PRechLayout.setHorizontalGroup(
            PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PRechLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(PRechLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(37, 37, 37)
                        .addComponent(tfTitre))
                    .addGroup(PRechLayout.createSequentialGroup()
                        .addGroup(PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PRechLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bAddActeur)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bRemoveActeur))
                            .addComponent(tfActeur))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PRechLayout.createSequentialGroup()
                        .addGroup(PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PRechLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                                .addComponent(bAddRealisateur)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bRemoveRealisateur))
                            .addComponent(tfRealisateur))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PRechLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dcApres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dcAvant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PRechLayout.createSequentialGroup()
                        .addGroup(PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PRechLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(tfMPAA))
                            .addComponent(tfGenre, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PRechLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bAddGenre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bRemoveGenre)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PRechLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(tfVoteDe, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(tfVoteA, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(PRechLayout.createSequentialGroup()
                        .addGroup(PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PRechLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(27, 27, 27)
                                .addComponent(tfID, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PRechLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(tfNVotesDe, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(tfNVotesA, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bSearchOnId))
                    .addGroup(PRechLayout.createSequentialGroup()
                        .addComponent(lCrit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bSearchOnCriteria)))
                .addContainerGap())
        );
        PRechLayout.setVerticalGroup(
            PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PRechLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bSearchOnId, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bSearchOnCriteria, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lCrit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PRechLayout.createSequentialGroup()
                        .addGroup(PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(bAddActeur)
                            .addComponent(bRemoveActeur))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfActeur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(9, 9, 9)
                .addGroup(PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfTitre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PRechLayout.createSequentialGroup()
                        .addGroup(PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(bRemoveRealisateur)
                                .addComponent(bAddRealisateur)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfRealisateur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6))
                        .addComponent(dcApres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dcAvant, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8))
                .addGroup(PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PRechLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PRechLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(bRemoveGenre)
                            .addComponent(bAddGenre))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfGenre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(tfMPAA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(tfVoteDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(tfVoteA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PRechLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(tfNVotesDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(tfNVotesA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PResult.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lbMongo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbMongo.setOpaque(true);

        tResults.setAutoCreateRowSorter(true);
        tResults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "Id", "Titre"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        tResults.setToolTipText("");
        tResults.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                tResultsMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tResults);

        javax.swing.GroupLayout PResultLayout = new javax.swing.GroupLayout(PResult);
        PResult.setLayout(PResultLayout);
        PResultLayout.setHorizontalGroup(
            PResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PResultLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
                    .addComponent(lbMongo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PResultLayout.setVerticalGroup(
            PResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PResultLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbMongo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        miMenuReset.setText("Menu");

        miConnexionMongo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        miConnexionMongo.setText("Connexion Mongo");
        miConnexionMongo.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miConnexionMongoActionPerformed(evt);
            }
        });
        miMenuReset.add(miConnexionMongo);

        miResetTable.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        miResetTable.setText("Reset Table");
        miResetTable.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miResetTableActionPerformed(evt);
            }
        });
        miMenuReset.add(miResetTable);

        miDecoMongo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        miDecoMongo.setText("Déconnexion Mongo");
        miDecoMongo.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miDecoMongoActionPerformed(evt);
            }
        });
        miMenuReset.add(miDecoMongo);

        Menu.add(miMenuReset);

        miAlimCB.setText("AlimCB");

        miCommander.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        miCommander.setText("Commander les films");
        miCommander.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miCommanderActionPerformed(evt);
            }
        });
        miAlimCB.add(miCommander);

        Menu.add(miAlimCB);

        setJMenuBar(Menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PRech, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(PResult, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(PResult, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PRech, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bAddActeurActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bAddActeurActionPerformed
    {//GEN-HEADEREND:event_bAddActeurActionPerformed
        if(!(this.tfActeur.getText()).isEmpty())
        {
            String acteur = this.tfActeur.getText();
            this.modActeurs.addElement(acteur);
            
            this.tfActeur.setText("");
        }
    }//GEN-LAST:event_bAddActeurActionPerformed

    private void bRemoveActeurActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bRemoveActeurActionPerformed
    {//GEN-HEADEREND:event_bRemoveActeurActionPerformed
        if(this.lActeurs.getSelectedIndex() != -1)
        {
            this.modActeurs.remove(this.lActeurs.getSelectedIndex());
        }
    }//GEN-LAST:event_bRemoveActeurActionPerformed

    private void bAddRealisateurActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bAddRealisateurActionPerformed
    {//GEN-HEADEREND:event_bAddRealisateurActionPerformed
        if(!(this.tfRealisateur.getText()).isEmpty())
        {
            String realisateur = this.tfRealisateur.getText();
            this.modRealisateurs.addElement(realisateur);
            
            this.tfRealisateur.setText("");
        }
    }//GEN-LAST:event_bAddRealisateurActionPerformed

    private void bRemoveRealisateurActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bRemoveRealisateurActionPerformed
    {//GEN-HEADEREND:event_bRemoveRealisateurActionPerformed
        if(this.lRealisateurs.getSelectedIndex() != -1)
        {
            this.modRealisateurs.remove(this.lRealisateurs.getSelectedIndex());
        }
    }//GEN-LAST:event_bRemoveRealisateurActionPerformed

    private void bAddGenreActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bAddGenreActionPerformed
    {//GEN-HEADEREND:event_bAddGenreActionPerformed
        if(!(this.tfGenre.getText()).isEmpty())
        {
            String genre = this.tfGenre.getText();
            this.modGenres.addElement(genre);
            
            this.tfGenre.setText("");
        }
    }//GEN-LAST:event_bAddGenreActionPerformed

    private void bRemoveGenreActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bRemoveGenreActionPerformed
    {//GEN-HEADEREND:event_bRemoveGenreActionPerformed
        if(this.lGenres.getSelectedIndex() != -1)
        {
            this.modGenres.remove(this.lGenres.getSelectedIndex());
        }
    }//GEN-LAST:event_bRemoveGenreActionPerformed

    private void bSearchOnIdActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bSearchOnIdActionPerformed
    {//GEN-HEADEREND:event_bSearchOnIdActionPerformed
        String _id = tfID.getText();
        DefaultTableModel model = (DefaultTableModel) this.tResults.getModel();
        if(!_id.isEmpty())
        {
            // On effectue la requête - récup: id (par défaut), title uniquement
            DBObject queryDoc = new BasicDBObject("_id", Integer.parseInt(_id));
            DBObject projectionDoc = new BasicDBObject("title", 1);
            DBObject document = collection.findOne(queryDoc, projectionDoc);
            
            if(document != null)
            {
                this.lbMongo.setBackground(Color.GREEN);
                this.lbMongo.setText("Recherche sur l'ID "
                        + Integer.parseInt(_id)
                        + " effectuée. ID trouvé.");
                
                displayDocument(document);
            }
            else
            {
                this.lbMongo.setBackground(Color.RED);
                this.lbMongo.setText("Recherche sur "
                        + Integer.parseInt(_id)
                        + " effectuée. ID introuvable.");
            }
            
            this.tfID.setText("");
        }
    }//GEN-LAST:event_bSearchOnIdActionPerformed

    private void miConnexionMongoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miConnexionMongoActionPerformed
    {//GEN-HEADEREND:event_miConnexionMongoActionPerformed
        try
        {
            mongoC = new MongoClient("localhost");
            moviesDB = mongoC.getDB("movies");
            collection = moviesDB.getCollection("movies");
            
            this.lbMongo.setBackground(Color.GREEN);
            this.lbMongo.setText("Connexion à la BDD movies effectuée.");
            
            this.PRech.setVisible(true);
            this.PResult.setVisible(true);
        } catch (UnknownHostException ex)
        {
            Logger.getLogger(RechCIGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_miConnexionMongoActionPerformed

    private void miDecoMongoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miDecoMongoActionPerformed
    {//GEN-HEADEREND:event_miDecoMongoActionPerformed
        this.PRech.setVisible(false);
        this.lbMongo.setText("");
        this.PResult.setVisible(false);
    }//GEN-LAST:event_miDecoMongoActionPerformed

    private void bSearchOnCriteriaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bSearchOnCriteriaActionPerformed
    {//GEN-HEADEREND:event_bSearchOnCriteriaActionPerformed
        DefaultTableModel dtm = (DefaultTableModel)this.tResults.getModel();
        DBObject queryDoc = new BasicDBObject();
        DBObject projectionDoc = new BasicDBObject("title", 1);
        
        List<BasicDBObject> listeRequetes = new ArrayList<>();
        
        String title = this.tfTitre.getText();
        if(!title.isEmpty())
        {
            String recherche = "^.*" + title + ".*";
            listeRequetes.add(new BasicDBObject("title", Pattern.compile(recherche)));
        }
        
        String certification = this.tfMPAA.getText();
        if(!certification.isEmpty())
        {
            String recherche = "^.*" + certification + ".*";
            listeRequetes.add(new BasicDBObject("certification", Pattern.compile(recherche)));
        }
        
        int nActeurs = this.lActeurs.getModel().getSize();
        if(nActeurs != 0)
        {
            for(int i = 0 ; i < nActeurs ; i++)
            {
                String acteur = this.lActeurs.getModel().getElementAt(i).toString();
                listeRequetes.add(new BasicDBObject("actors.name", acteur));
                //System.out.println(acteur);
            }
        }
        
        int nRealisateurs = this.lRealisateurs.getModel().getSize();
        if(nRealisateurs != 0)
        {
            for(int i = 0 ; i < nRealisateurs ; i++)
            {
                String realisateur = this.lRealisateurs.getModel().getElementAt(i).toString();
                listeRequetes.add(new BasicDBObject("directors.name", realisateur));
                //System.out.println(realisateur);
            }
        }
        
        int nGenres = this.lGenres.getModel().getSize();
        if(nGenres != 0)
        {
            for(int i = 0 ; i < nGenres ; i++)
            {
                String genre = this.lGenres.getModel().getElementAt(i).toString();
                listeRequetes.add(new BasicDBObject("genres.name", genre));
                //System.out.println(genre);
            }
        }
        
        double voteAvLow = Double.parseDouble(this.tfVoteDe.getText());
        double voteAvHigh = Double.parseDouble(this.tfVoteA.getText());
        if(voteAvLow >= 0 && voteAvLow <= 10 && voteAvHigh >= 0 && voteAvHigh <= 10 && voteAvLow < voteAvHigh)
        {
            //System.out.println("De " + voteAvLow + " à " + voteAvHigh + " /10");
            listeRequetes.add(new BasicDBObject("vote_average", new BasicDBObject("$gte", voteAvLow)));
            listeRequetes.add(new BasicDBObject("vote_average", new BasicDBObject("$lte", voteAvHigh)));
        }
        else
        {
            javax.swing.JOptionPane.showMessageDialog(null,"Vote Average non conforme ! \n La requète n'en tiendra pas compte");
            this.tfNVotesDe.setText("0");
            this.tfVoteA.setText("10");
        }
        
        double voteCoLow = Double.parseDouble(this.tfNVotesDe.getText());
        double voteCoHigh = Double.parseDouble(this.tfNVotesA.getText());
        if(voteCoLow >= 0 && voteCoLow <= 5814 && voteCoHigh >= 0 && voteCoHigh <= 5814 && voteCoLow < voteAvHigh)
        {
            //System.out.println("De " + voteCoLow + " à " + voteCoHigh + " nbre de votes");
            listeRequetes.add(new BasicDBObject("vote_count", new BasicDBObject("$gte", voteCoLow)));
            listeRequetes.add(new BasicDBObject("vote_count", new BasicDBObject("$lte", voteCoHigh)));
        }
        else
        {
            javax.swing.JOptionPane.showMessageDialog(null,"Vote Count non conforme ! \n La requète n'en tiendra pas compte");
            this.tfNVotesDe.setText("0");
            this.tfNVotesA.setText("5814");
        }
        
        Calendar cApres = this.dcApres.getCalendar();
        if(cApres != null)
        {
            SimpleDateFormat sdfApres = new SimpleDateFormat("yyyy-MM-dd");
            String dateApres = sdfApres.format(cApres.getTime());
            System.out.println(dateApres);
            listeRequetes.add(new BasicDBObject("release_date", new BasicDBObject("$gte", dateApres)));
        }
        
        Calendar cAvant = this.dcAvant.getCalendar();
        if(cAvant != null)
        {
            SimpleDateFormat sdfAvant = new SimpleDateFormat("yyyy-MM-dd");
            String dateAvant = sdfAvant.format(cAvant.getTime());
            System.out.println(dateAvant);
            listeRequetes.add(new BasicDBObject("release_date", new BasicDBObject("$lte", dateAvant)));
        }
        
        queryDoc.put("$and", listeRequetes);
        DBCursor cursor = collection.find(queryDoc, projectionDoc);
        if(cursor != null)
        {
            this.lbMongo.setBackground(Color.GREEN);
            this.lbMongo.setText("Recherche effectuée : " + cursor.count() + " résultats");
        }
        iterateOverCursor(cursor);
        cursor.close();
    }//GEN-LAST:event_bSearchOnCriteriaActionPerformed

    private void miResetTableActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miResetTableActionPerformed
    {//GEN-HEADEREND:event_miResetTableActionPerformed
        ((DefaultTableModel) this.tResults.getModel()).setRowCount(0);
        this.lbMongo.setBackground(Color.GREEN);
        this.lbMongo.setText("Reset de la JTable effectué.");
    }//GEN-LAST:event_miResetTableActionPerformed

    private void tResultsMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_tResultsMouseClicked
    {//GEN-HEADEREND:event_tResultsMouseClicked
        if(evt.getClickCount() == 2)
        {
            TableModel tm = this.tResults.getModel();
            int ligne = this.tResults.getSelectedRow();
            int _id = Integer.parseInt(tm.getValueAt(ligne, 0).toString());
            this.lbMongo.setText("_id = " + _id);
            
            // On effectue la requète sur le film dont l'id est sélectionné, pour avoir tous les détails le concernant
            DBObject queryDoc = new BasicDBObject("_id", _id);
            DBObject document = collection.findOne(queryDoc);
            
            if(document != null)
            {
                this.lbMongo.setBackground(Color.GREEN);
                this.lbMongo.setText("Recherche sur l'ID "
                        + _id
                        + " effectuée. ID trouvé.");
                
                //displayDocumentConsole(document);
                displayDetailDocument(document);
            }
            else
            {
                this.lbMongo.setBackground(Color.RED);
                this.lbMongo.setText("Recherche sur "
                        + _id
                        + " effectuée. ID introuvable.");
            }
        }
    }//GEN-LAST:event_tResultsMouseClicked

    private void miCommanderActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miCommanderActionPerformed
    {//GEN-HEADEREND:event_miCommanderActionPerformed
        int[] indices = this.tResults.getSelectedRows();
        List<Integer> ids = new ArrayList<Integer>();
        
        for(int i = 0 ; i < indices.length ; i++)
        {
            String idS = this.tResults.getValueAt(indices[i], 0).toString();
            int id = Integer.parseInt(idS);
            if(!ids.contains(id))
                ids.add(id);
        }
        DBObject queryDoc = new BasicDBObject();
        queryDoc.put("_id", new BasicDBObject("$in", ids));
        
        DBCursor cursor = collection.find(queryDoc);
        
        List<MovieToOracle> ListMovies = new ArrayList<>();
        cursor.forEach(dbObject ->
        {
            //displayDocumentConsole(dbObject);            
            Map<String,String> map = new HashMap<>();
            dbObject.keySet().stream().forEach(k ->
            {
                Object value = dbObject.get(k);
                if(value != null)
                    map.put(k, value.toString());
                System.out.println(k + "::" + value.toString());
            });
            
            MovieToOracle mto = new MovieToOracle();
            mto.setArtists(map.get("actors"));
            mto.setCertification(map.get("certification"));
            mto.setCompanies(map.get("production_companies"));
            mto.setGenres(map.get("genres"));
            mto.setId(map.get("_id"));
            mto.setImage(map.get("poster_path"));
            mto.setLanguages(map.get("spoken_languages"));
            mto.setOverview(map.get("overview"));
            mto.setProduction_country(map.get("production_countries"));
            mto.setReleased_date(map.get("release_date"));
            mto.setRuntime(map.get("runtime"));
            mto.setTitle(map.get("title"));
            mto.setVote_average(map.get("vote_average"));
            mto.setVote_count(map.get("vote_count"));
            
            ListMovies.add(mto);
        });

    }//GEN-LAST:event_miCommanderActionPerformed
    
    public void displayDocument(DBObject dbObject)
    {
        DefaultTableModel model = (DefaultTableModel) this.tResults.getModel();
        List<String> lResult = new ArrayList<>();
        
        dbObject.keySet().stream().forEach(k ->
        {
            lResult.add(dbObject.get(k).toString());
        });
        model.addRow(lResult.toArray());
    }
    
    public void displayDocumentConsole(DBObject dbObject)
    {
        dbObject.keySet().stream().forEach(key ->
        {
            Object value = dbObject.get(key);
            System.out.printf("%s   %s\n",
                    key,
                    value != null ? value.toString() : "null");
        });
    }
    
    public void displayDetailDocument(DBObject dbObject)
    {
        Map<String,String> map = new HashMap<>();
        
        dbObject.keySet().stream().forEach(k ->
        {
            Object value = dbObject.get(k);
            if(value != null)
                map.put(k, value.toString());
            //System.out.println(k + "::" + value.toString());
        });
        MovieDetails movie = new MovieDetails();
        
        String actorsBrut = map.get("actors");
        String actors = "<html>";
        String[] tokenActors = actorsBrut.split("\"name\" : \"");
        for(int i = 0 ; i < tokenActors.length-1 ; i++)
        {
            String[] str = tokenActors[i+1].split("\"");
            actors = actors + str[0] + "<br>";
        }
        actors = actors + "</html>";
        movie.setActors(actors);
        
        movie.setBudget(map.get("budget"));
        movie.setCertification(map.get("certification"));
        
        String directorsBrut = map.get("directors");
        String directors = "<html>";
        String[] tokenDirectors = directorsBrut.split("\"name\" : \"");
        for(int i = 0 ; i < tokenDirectors.length-1 ; i++)
        {
            String[] str = tokenDirectors[i+1].split("\"");
            directors = directors + str[0] + "<br>";
        }
        directors = directors + "</html>";
        movie.setDirectors(directors);
        
        String genresBrut = map.get("genres");
        String genres = "<html>";
        String[] tokenGenres = genresBrut.split("\"name\" : \"");
        for(int i = 0 ; i < tokenGenres.length-1 ; i++)
        {
            String[] str = tokenGenres[i+1].split("\"");
            genres = genres + str[0] + "<br>";
        }
        genres = genres + "</html>";
        movie.setGenres(genres);
        
        movie.setHomepage(map.get("homepage"));
        movie.setId(map.get("_id"));
        movie.setOriginalTitle(map.get("original_title"));
        movie.setOverview(map.get("overview"));
        movie.setPosterPath(map.get("poster_path"));
        
        String prodCompBrut = map.get("production_companies");
        String prodComp = "<html>";
        String[] tokenProdComp = prodCompBrut.split("\"name\" : \"");
        for(int i = 0 ; i < tokenProdComp.length-1 ; i++)
        {
            String[] str = tokenProdComp[i+1].split("\"");
            prodComp = prodComp + str[0] + "<br>";
        }
        prodComp = prodComp + "</html>";
        movie.setProductionCompanies(prodComp);
        
        String prodCountBrut = map.get("production_countries");
        String prodCount = "<html>";
        String[] tokenProdCount = prodCountBrut.split("\"name\" : \"");
        for(int i = 0 ; i < tokenProdCount.length-1 ; i++)
        {
            String[] str = tokenProdCount[i+1].split("\"");
            prodCount = prodCount + str[0] + "<br>";
        }
        prodCount = prodCount + "</html>";
        movie.setProductionCountries(prodCount);
        
        movie.setReleaseDate(map.get("release_date"));
        movie.setRevenue(map.get("revenue"));
        movie.setRuntime(map.get("runtime"));
        
        String spokLangBrut = map.get("spoken_languages");
        String spokLang = "<html>";
        String[] tokenSpokLang = spokLangBrut.split("\"name\" : \"");
        for(int i = 0 ; i < tokenSpokLang.length-1 ; i++)
        {
            String[] str = tokenSpokLang[i+1].split("\"");
            spokLang = spokLang + str[0] + "<br>";
        }
        spokLang = spokLang + "</html>";
        movie.setSpokenLanguages(spokLang);
        
        movie.setStatus(map.get("status"));
        movie.setTagline(map.get("tagline"));
        movie.setTitle(map.get("title"));
        movie.setVoteAverage(map.get("vote_average"));
        movie.setVoteCount(map.get("vote_count"));
        
        movie.setActorsPaths(map.get("actors"));
        
        DetailsGUI dgui = new DetailsGUI(movie);
        dgui.setVisible(true);
    }
    
    public void iterateOverCursor(DBCursor dbCursor)
    {
        dbCursor.forEach(dbObject ->
        {
            displayDocument(dbObject);
        });
    }
    
    public void iterateOverCursorConsole(DBCursor dbCursor)
    {
        dbCursor.forEach(dbObject ->
        {
            displayDocumentConsole(dbObject);
        });
    }
    
    public static void main(String args[])
    {
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(RechCIGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new RechCIGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar Menu;
    private javax.swing.JPanel PRech;
    private javax.swing.JPanel PResult;
    private javax.swing.JButton bAddActeur;
    private javax.swing.JButton bAddGenre;
    private javax.swing.JButton bAddRealisateur;
    private javax.swing.JButton bRemoveActeur;
    private javax.swing.JButton bRemoveGenre;
    private javax.swing.JButton bRemoveRealisateur;
    private javax.swing.JButton bSearchOnCriteria;
    private javax.swing.JButton bSearchOnId;
    private com.toedter.calendar.JDateChooser dcApres;
    private com.toedter.calendar.JDateChooser dcAvant;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JList lActeurs;
    private javax.swing.JLabel lCrit;
    private javax.swing.JList lGenres;
    private javax.swing.JList lRealisateurs;
    private javax.swing.JLabel lbMongo;
    private javax.swing.JMenu miAlimCB;
    private javax.swing.JMenuItem miCommander;
    private javax.swing.JMenuItem miConnexionMongo;
    private javax.swing.JMenuItem miDecoMongo;
    private javax.swing.JMenu miMenuReset;
    private javax.swing.JMenuItem miResetTable;
    private javax.swing.JTable tResults;
    private javax.swing.JTextField tfActeur;
    private javax.swing.JTextField tfGenre;
    private javax.swing.JTextField tfID;
    private javax.swing.JTextField tfMPAA;
    private javax.swing.JTextField tfNVotesA;
    private javax.swing.JTextField tfNVotesDe;
    private javax.swing.JTextField tfRealisateur;
    private javax.swing.JTextField tfTitre;
    private javax.swing.JTextField tfVoteA;
    private javax.swing.JTextField tfVoteDe;
    // End of variables declaration//GEN-END:variables
    
}