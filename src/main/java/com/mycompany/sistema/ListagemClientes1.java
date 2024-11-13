/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.sistema;

import entidades.Cliente;
import entidades.ClienteService;
import entidades.Sql;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class ListagemClientes1 extends javax.swing.JFrame {

    Sql sql = new Sql();

    public ListagemClientes1() {
        initComponents();
        abrirTelaCheia();
        sql.setSql("SELECT c FROM Cliente c WHERE c.status LIKE 'ativo' AND c.nome LIKE :criterio ORDER BY c.id");
        carregarClientesTabela();
    }

    private void carregarClientesTabela() {
        ClienteService service = new ClienteService();

        // Define os parâmetros da consulta
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("criterio", "%" + TxtBusca.getText() + "%");

        // Chama o método para listar os clientes
        List<Cliente> clientes = service.listarCliente(sql.getSql(), parametros);
        preencheTabela(clientes);
    }

    private void preencheTabela(List<Cliente> clientes) {

        // Verificar se a lista de clientes não é nula
        if (clientes == null || clientes.isEmpty()) {
            LblQtdBusca.setText("Nenhum encontrado");
            return;
        } else {
            LblQtdBusca.setText("Encontrados: " + clientes.size());
        }
        DefaultTableModel modelo = (DefaultTableModel) TabelaClientes.getModel();
        modelo.setRowCount(0);

        //DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Código", "Nome", "CPF", "Status"}, 0);
        for (Cliente cliente : clientes) {
            modelo.addRow(new Object[]{
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getStatus()
            });
        }
        //TabelaClientes.setModel(modelo);
    }

    // Método para abrir o formulário em tela cheia
    private void abrirTelaCheia() {

        // Configura o título da janela
        setTitle("Listagem de Clientes");

        // Obtém o tamanho da tela
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        // Define o tamanho da janela para o tamanho da tela
        setSize(screenSize.width, screenSize.height);

        // Define a janela como maximizada
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);

        // Mostra a janela
        setVisible(true);

        //Fechar apenar este formulario
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        GrupoBotoes = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        CbxTipoBusca = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        TxtBusca = new javax.swing.JTextPane();
        CbxMostrarTodos = new javax.swing.JCheckBox();
        CbxInativo = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        BtnLimpar = new javax.swing.JButton();
        BtnBuscar = new javax.swing.JButton();
        BtnNovo = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        LblQtdBusca = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaClientes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        CbxTipoBusca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Código", "Nome", "CPF" }));
        CbxTipoBusca.setSelectedIndex(1);
        CbxTipoBusca.setToolTipText("");

        jScrollPane2.setViewportView(TxtBusca);

        GrupoBotoes.add(CbxMostrarTodos);
        CbxMostrarTodos.setText("Mostrar todos");
        CbxMostrarTodos.setToolTipText("");

        GrupoBotoes.add(CbxInativo);
        CbxInativo.setText("Inativos");

        jButton1.setText("jButton1");

        BtnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/cancel.png"))); // NOI18N
        BtnLimpar.setBorder(null);

        BtnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/magnifier.png"))); // NOI18N
        BtnBuscar.setBorder(null);
        BtnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnBuscarMouseClicked(evt);
            }
        });

        BtnNovo.setText("Novo");
        BtnNovo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnNovoMouseClicked(evt);
            }
        });

        jButton5.setText("jButton1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(LblQtdBusca)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(CbxTipoBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2))
                        .addGap(84, 84, 84)
                        .addComponent(BtnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CbxMostrarTodos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CbxInativo, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(383, 383, 383)))
                .addGap(201, 201, 201))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtnLimpar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CbxTipoBusca, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtnNovo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CbxMostrarTodos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CbxInativo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(LblQtdBusca)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        getContentPane().add(jPanel1, gridBagConstraints);

        TabelaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "CPF", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TabelaClientes);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 680;
        gridBagConstraints.ipady = 258;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnBuscarMouseClicked
        // TODO add your handling code here:
        switch (CbxTipoBusca.getItemCount()) {
            case 0: // id
                if (CbxMostrarTodos.isSelected() == true) {
                    sql.setSql("SELECT c FROM Cliente c WHERE c.id LIKE :criterio");
                    break;
                } else if (CbxInativo.isSelected() == true) {
                    sql.setSql("SELECT c FROM Cliente c WHERE c.status LIKE 'ativo' AND c.id LIKE :criterio");
                } else {
                    sql.setSql("SELECT c FROM Cliente c WHERE c.status LIKE 'ativo' AND c.id LIKE :criterio");
                    break;
                }
                break;
            case 1: // nome
                if (CbxMostrarTodos.isSelected() == true) {
                    sql.setSql("SELECT c FROM Cliente c WHERE c.nome LIKE :criterio");
                    break;
                } else if (CbxInativo.isSelected() == true) {
                    sql.setSql("SELECT c FROM Cliente c WHERE c.status LIKE 'ativo' AND c.nome LIKE :criterio");
                } else {
                    sql.setSql("SELECT c FROM Cliente c WHERE c.status LIKE 'ativo' AND c.nome LIKE :criterio");
                    break;
                }
                break;

            case 3: // cpf
                if (CbxMostrarTodos.isSelected() == true) {
                    sql.setSql("SELECT c FROM Cliente c WHERE c.cpf LIKE :criterio");
                    break;
                } else if (CbxInativo.isSelected() == true) {
                    sql.setSql("SELECT c FROM Cliente c WHERE c.status LIKE 'ativo' AND c.cpf LIKE :criterio");
                } else {
                    sql.setSql("SELECT c FROM Cliente c WHERE c.status LIKE 'ativo' AND c.cpf LIKE :criterio");
                    break;
                }
                break;
            default:
                throw new AssertionError();
        }
        carregarClientesTabela();
    }//GEN-LAST:event_BtnBuscarMouseClicked

    private void BtnNovoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnNovoMouseClicked
        // TODO add your handling code here:
        CadastroCliente cadastroCliente = new CadastroCliente();
    }//GEN-LAST:event_BtnNovoMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ListagemClientes1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListagemClientes1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListagemClientes1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListagemClientes1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListagemClientes1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton BtnBuscar;
    javax.swing.JButton BtnLimpar;
    javax.swing.JButton BtnNovo;
    javax.swing.JCheckBox CbxInativo;
    javax.swing.JCheckBox CbxMostrarTodos;
    javax.swing.JComboBox<String> CbxTipoBusca;
    javax.swing.ButtonGroup GrupoBotoes;
    javax.swing.JLabel LblQtdBusca;
    javax.swing.JTable TabelaClientes;
    javax.swing.JTextPane TxtBusca;
    javax.swing.JButton jButton1;
    javax.swing.JButton jButton5;
    javax.swing.JPanel jPanel1;
    javax.swing.JScrollPane jScrollPane1;
    javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
