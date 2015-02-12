
package RechCI;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Thibault
 */
public class DetailsGUI extends javax.swing.JFrame
{
    private String actorsBrut;
    private String title;
    
    public DetailsGUI()
    {
        initComponents();
        
        actorsBrut = null;
        title = null;
        
        DefaultTableModel dtm = (DefaultTableModel)this.tDetails.getModel();
        
        this.tDetails.getColumnModel().getColumn(0).setMinWidth(200);
        this.tDetails.getColumnModel().getColumn(0).setMaxWidth(200);
        
        updateRow();
    }
    
    public DetailsGUI(MovieDetails md)
    {
        initComponents();
        
        actorsBrut = md.getActorsPaths();
        title = md.getTitle();
        
        this.setTitle(md.getTitle());
        
        DefaultTableModel dtm = (DefaultTableModel)this.tDetails.getModel();
        dtm.setValueAt(md.getId(),0, 1);
        dtm.setValueAt(md.getTitle(), 1, 1);
        dtm.setValueAt(md.getReleaseDate(), 2, 1);
        dtm.setValueAt(md.getGenres(), 3, 1);
        dtm.setValueAt(md.getRuntime(), 4, 1);
        dtm.setValueAt(md.getVoteAverage(), 5, 1);
        dtm.setValueAt(md.getVoteCount(), 6, 1);
        dtm.setValueAt(md.getBudget(), 7, 1);
        dtm.setValueAt(md.getRevenue(), 8, 1);
        dtm.setValueAt(md.getCertification(), 9, 1);
        dtm.setValueAt(md.getActors(), 10, 1);
        dtm.setValueAt(md.getDirectors(), 11, 1);
        dtm.setValueAt(md.getSpokenLanguages(), 12, 1);
        dtm.setValueAt(md.getHomepage(), 13, 1);
        dtm.setValueAt(md.getOriginalTitle(), 14, 1);
        dtm.setValueAt(md.getOverview(), 15, 1);
        dtm.setValueAt(md.getProductionCompanies(), 16, 1);
        dtm.setValueAt(md.getProductionCountries(), 17, 1);
        dtm.setValueAt(md.getStatus(), 18, 1);
        dtm.setValueAt(md.getTagline(), 19, 1);
        if(md.getPosterPath() == null)
        {
            dtm.setValueAt("Poster unavailable", 20, 1);
        }
        else
        {
            dtm.setValueAt("<html> <IMG SRC=\"http://cf2.imgobject.com/t/p/w185" + md.getPosterPath() + "\"> </html>", 20, 1);
        }
        this.tDetails.setModel(dtm);
        
        this.tDetails.getColumnModel().getColumn(0).setMinWidth(140);
        this.tDetails.getColumnModel().getColumn(0).setMaxWidth(140);
        
        updateRow();
    }
    
    private void updateRow()
    {
        for (int row = 0; row < this.tDetails.getRowCount(); row++)
        {
            int rowHeight = this.tDetails.getRowHeight();
            
            Component comp = this.tDetails.prepareRenderer(this.tDetails.getCellRenderer(row, 1), row, 1);
            rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
            this.tDetails.setRowHeight(row, rowHeight);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        bActeurs = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tDetails = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        bActeurs.setText("Detail Acteurs");
        bActeurs.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bActeursActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Propriétés", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        tDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {"Id", null},
                {"Title", null},
                {"Release date", null},
                {"Genres", null},
                {"Runtime", null},
                {"Vote average", null},
                {"Vote count", null},
                {"Budget", null},
                {"Revenue", null},
                {"Certification", null},
                {"Actors", null},
                {"Directors", null},
                {"Spoken languages", null},
                {"Homepage", null},
                {"Original title", null},
                {"Overview", null},
                {"Production companies", null},
                {"Production countries", null},
                {"Status", null},
                {"Tagline", null},
                {"Poster path", null}
            },
            new String []
            {
                "", ""
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class, java.lang.Object.class
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
        jScrollPane1.setViewportView(tDetails);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(272, Short.MAX_VALUE)
                .addComponent(bActeurs)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bActeurs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bActeursActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bActeursActionPerformed
    {//GEN-HEADEREND:event_bActeursActionPerformed
        Map actorsPath = new HashMap<>();
        //System.out.println(actorsBrut);
        String[] tokenActors = actorsBrut.split("\"name\" : \"");
        String[] tokenPaths = actorsBrut.split("\"profile_path\" : \"");
        String[] tokenRoles = actorsBrut.split("\"character\" : \"");
        for(int i = 0 ; i < tokenActors.length-1 ; i++)
        {
            String actRole;
            String[] act, path = null, role;
            try
            {
                act = tokenActors[i+1].split("\"");
                path = tokenPaths[i+1].split("\"");
                role = tokenRoles[i+1].split("\"");
                
                actRole = "<html>" + act[0] + "<br>" + role[0] + "</html>";
            } catch(ArrayIndexOutOfBoundsException ex)
            {
                actRole = "NR";
            }
            
            try
            {
                //System.out.println(actRole + "###" + path[0]);
                actorsPath.put(actRole, path[0]);
            } catch(NullPointerException ex)
            {
                actorsPath.put(actRole, "NR");
            }
        }
        
        ActorsDetails maFen = new ActorsDetails(title, actorsPath);
        maFen.setVisible(true);
    }//GEN-LAST:event_bActeursActionPerformed
    
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
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(DetailsGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(DetailsGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(DetailsGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(DetailsGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new DetailsGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bActeurs;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tDetails;
    // End of variables declaration//GEN-END:variables
}
