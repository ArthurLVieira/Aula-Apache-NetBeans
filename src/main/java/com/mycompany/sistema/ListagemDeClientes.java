/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.sistema;

import entidades.Cliente;
import entidades.ClienteService;
import entidades.Sql;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.hibernate.hql.internal.antlr.HqlSqlTokenTypes;

/**
 *
 * @author user
 */
public class ListagemDeClientes extends javax.swing.JPanel {

    Object id = null;
    Object nome = null;
    Sql sql = new Sql();

    /**
     * Creates new form teste
     */
    public ListagemDeClientes() {
        initComponents();
        AberturaDeTela();
        sql.setSql("SELECT c FROM Cliente c WHERE c.status LIKE 'ativo' ORDER BY c.id");
        //sql.setSql("SELECT c FROM Cliente c WHERE c.status LIKE 'ativo' AND c.nome LIKE :criterio ORDER BY c.id");
        carregarClientesTabela();
    }

    private void preencheTabela(List<Cliente> clientes) {
        if (clientes == null || clientes.isEmpty()) {
            LblQtdBusca.setText("Nenhum encontrado");
            JOptionPane.showMessageDialog(this, "Nenhum encontrado");
        } else {
            LblQtdBusca.setText("Encontrados: " + clientes.size());
            DefaultTableModel modelo = (DefaultTableModel) TabelaClientes.getModel();
            modelo.setRowCount(0);
            for (Cliente cliente : clientes) {
                modelo.addRow(new Object[]{
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getCpf(),
                    cliente.getStatus()
                });
            }
        }
    }

    private void carregarClientesTabela() {
        ClienteService service = new ClienteService();
        /*
        // Define os parâmetros da consulta;
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("criterio", "%" + TxtBusca.getText() + "%");
        */ 

        // Define os parâmetros da consulta
        Map<String, Object> parametros = new HashMap<>();
        String criterioBusca = TxtBusca.getText().trim();
        
        // Verifica se o critério está vazio
        if (!criterioBusca.isEmpty()) {
            parametros.put("criterio", "%" + criterioBusca + "%");
        }

        // Chama o método para listar os clientes
        List<Cliente> clientes = service.listarCliente(sql.getSql(), parametros);
        preencheTabela(clientes);
    }

    private void AberturaDeTela() {
        TxtBusca.requestFocusInWindow();
    }

    private void mostrarPopup(MouseEvent evt) {
        if (evt.isPopupTrigger() && TabelaClientes.getSelectedRow() != -1) {
            int linhaSelecionada = TabelaClientes.rowAtPoint(evt.getPoint());
            TabelaClientes.setRowSelectionInterval(linhaSelecionada, linhaSelecionada);
            PopMnCliente.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }

    private void ClickTabela(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            int linhaSelecionada = TabelaClientes.getSelectedRow();
            if (linhaSelecionada != -1) {
                id = TabelaClientes.getValueAt(linhaSelecionada, 0);
                nome = TabelaClientes.getValueAt(linhaSelecionada, 1);
                if (this.id != null) {
                    Long id = (long) this.id;
                    ClienteService service = new ClienteService();
                    Cliente cliente = service.buscarClientePorId(id);
                    CadastroCliente cadastroCliente = new CadastroCliente(cliente);
                    cadastroCliente.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent e) {
                            Busca(evt);
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione o cliente para Edição!");
                }
            }
        }
        if (evt.getClickCount() == 1) {
            int linhaSelecionada = TabelaClientes.getSelectedRow();
            if (linhaSelecionada != -1) {
                id = TabelaClientes.getValueAt(linhaSelecionada, 0);
                nome = TabelaClientes.getValueAt(linhaSelecionada, 1);
            }
        }
    }

    private void Excluir(MouseEvent evt) {
        if (id != null) {
            switch (JOptionPane.showConfirmDialog(this, "Deseja Excluir: " + this.id
                    + ", " + nome, "Confirmação!", JOptionPane.YES_NO_OPTION)) {
                case JOptionPane.YES_OPTION:
                    Long id = (long) this.id;
                    ClienteService service = new ClienteService();
                    service.DeletarCliente(id);
                    JOptionPane.showMessageDialog(null, id + ", " + nome + " Excluido!");
                    this.id = null;
                    Busca(evt);
                    break;
                case JOptionPane.NO_OPTION:
                    this.id = null;
                    break;
                default:
                    throw new AssertionError();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Selecione o cliente Para Exclusão");
        }
    }

    private void Editar(MouseEvent evt) {
        if (this.id != null) {
            Long id = (long) this.id;
            ClienteService service = new ClienteService();
            Cliente cliente = service.buscarClientePorId(id);
            CadastroCliente cadastroCliente = new CadastroCliente(cliente);
            cadastroCliente.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    Busca(evt);
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "Selecione o cliente para Edição!");
        }
    }

    private void Salvar(MouseEvent evt) {
        CadastroCliente cadastroCliente = new CadastroCliente();

        cadastroCliente.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                id = null;
                Busca(evt);
            }
        });
    }

    private void Busca(MouseEvent evt) {
        switch (CbxTipoBusca.getSelectedIndex()) {
            case 0: // id
                if (CbxMostrarTodos.isSelected() == true) {
                    sql.setSql("SELECT c FROM Cliente c WHERE c.id = :criterio ORDER BY c.id");
                    //sql.setSql("SELECT c FROM Cliente c WHERE c.id LIKE :criterio ORDER BY c.id");
                    break;
                }
                if (CbxInativo.isSelected() == true) {
                    sql.setSql("SELECT c FROM Cliente c WHERE c.status LIKE 'inativo' AND c.id = :criterio ORDER BY c.id");
                    //sql.setSql("SELECT c FROM Cliente c WHERE c.status LIKE 'inativo' AND c.id LIKE :criterio ORDER BY c.id");
                    break;
                }
                if (CbxMostrarTodos.isSelected() && CbxInativo.isSelected() == false) {
                    sql.setSql("SELECT c FROM Cliente c WHERE c.status LIKE 'ativo' AND c.id = :criterio ORDER BY c.id");
                    //sql.setSql("SELECT c FROM Cliente c WHERE c.status LIKE 'ativo' AND c.id LIKE :criterio ORDER BY c.id");
                    break;
                }
                break;
            case 1: // nome
                if (CbxMostrarTodos.isSelected() == true) {
                    sql.setSql("SELECT c FROM Cliente c WHERE c.nome LIKE :criterio ORDER BY c.id");
                    break;
                }
                if (CbxInativo.isSelected() == true) {
                    sql.setSql("SELECT c FROM Cliente c WHERE c.status LIKE 'inativo' AND c.nome LIKE :criterio ORDER BY c.id");
                    break;
                }
                if (CbxMostrarTodos.isSelected() && CbxInativo.isSelected() == false) {
                    sql.setSql("SELECT c FROM Cliente c WHERE c.status LIKE 'ativo' AND c.nome LIKE :criterio ORDER BY c.id");
                    break;
                }
                break;

            case 3: // cpf
                if (CbxMostrarTodos.isSelected() == true) {
                    sql.setSql("SELECT c FROM Cliente c WHERE c.cpf LIKE :criterio ORDER BY c.id");
                    break;
                }
                if (CbxInativo.isSelected() == true) {
                    sql.setSql("SELECT c FROM Cliente c WHERE c.status LIKE 'inativo' AND c.cpf LIKE :criterio ORDER BY c.id");
                    break;
                }
                if (CbxMostrarTodos.isSelected() && CbxInativo.isSelected() == false) {
                    sql.setSql("SELECT c FROM Cliente c WHERE c.status LIKE 'ativo' AND c.cpf LIKE :criterio ORDER BY c.id");
                    break;
                }
                break;
            default:
                throw new AssertionError();
        }
        carregarClientesTabela();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GroupBotoesBusca = new javax.swing.ButtonGroup();
        PopMnCliente = new java.awt.PopupMenu();
        Editar = new java.awt.MenuItem();
        Excluir = new java.awt.MenuItem();
        PnlPrincipal = new javax.swing.JPanel();
        CbxTipoBusca = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        TxtBusca = new javax.swing.JTextPane();
        CbxMostrarTodos = new javax.swing.JCheckBox();
        CbxInativo = new javax.swing.JCheckBox();
        BtnEditarCliente = new javax.swing.JButton();
        BtnLimpar = new javax.swing.JButton();
        BtnBuscar = new javax.swing.JButton();
        BtnNovoCliente = new javax.swing.JButton();
        BtnExcluirCliente = new javax.swing.JButton();
        LblQtdBusca = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaClientes = new javax.swing.JTable();

        PopMnCliente.setLabel("popupMenu1");

        Editar.setLabel("menuItem1");
        Editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditarActionPerformed(evt);
            }
        });
        PopMnCliente.add(Editar);
        PopMnCliente.addSeparator();
        Excluir.setName("");
        Excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExcluirActionPerformed(evt);
            }
        });
        PopMnCliente.add(Excluir);
        Excluir.getAccessibleContext().setAccessibleDescription("");

        CbxTipoBusca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Código", "Nome", "CPF" }));
        CbxTipoBusca.setSelectedIndex(1);
        CbxTipoBusca.setToolTipText("");

        jScrollPane2.setViewportView(TxtBusca);

        GroupBotoesBusca.add(CbxMostrarTodos);
        CbxMostrarTodos.setText("Mostrar todos");
        CbxMostrarTodos.setToolTipText("");

        GroupBotoesBusca.add(CbxInativo);
        CbxInativo.setText("Inativos");

        BtnEditarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/png/icon_editar.png"))); // NOI18N
        BtnEditarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnEditarClienteMouseClicked(evt);
            }
        });

        BtnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/cancel.png"))); // NOI18N
        BtnLimpar.setBorder(null);
        BtnLimpar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnLimparMouseClicked(evt);
            }
        });

        BtnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/magnifier.png"))); // NOI18N
        BtnBuscar.setBorder(null);
        BtnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnBuscarMouseClicked(evt);
            }
        });

        BtnNovoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/png/icon_novo.png"))); // NOI18N
        BtnNovoCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnNovoClienteMouseClicked(evt);
            }
        });

        BtnExcluirCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/png/icon_deletar.png"))); // NOI18N
        BtnExcluirCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnExcluirClienteMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Listagem De Clientes");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout PnlPrincipalLayout = new javax.swing.GroupLayout(PnlPrincipal);
        PnlPrincipal.setLayout(PnlPrincipalLayout);
        PnlPrincipalLayout.setHorizontalGroup(
            PnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CbxInativo, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PnlPrincipalLayout.createSequentialGroup()
                        .addGroup(PnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(LblQtdBusca, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PnlPrincipalLayout.createSequentialGroup()
                                .addComponent(CbxTipoBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(BtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(BtnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CbxMostrarTodos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(80, 80, 80)
                        .addComponent(BtnNovoCliente)
                        .addGap(18, 18, 18)
                        .addComponent(BtnExcluirCliente)
                        .addGap(18, 18, 18)
                        .addComponent(BtnEditarCliente)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 157, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        PnlPrincipalLayout.setVerticalGroup(
            PnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlPrincipalLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(PnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(BtnEditarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                        .addComponent(BtnNovoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtnExcluirCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PnlPrincipalLayout.createSequentialGroup()
                        .addGroup(PnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CbxTipoBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CbxMostrarTodos))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CbxInativo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LblQtdBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
        TabelaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaClientesMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                TabelaClientesMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(TabelaClientes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PnlPrincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PnlPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BtnNovoClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnNovoClienteMouseClicked
        // TODO add your handling code here:
        Salvar(evt);
    }//GEN-LAST:event_BtnNovoClienteMouseClicked

    private void BtnLimparMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnLimparMouseClicked
        // TODO add your handling code here:
        GroupBotoesBusca.clearSelection();
        CbxInativo.setSelected(false);
        CbxMostrarTodos.setSelected(false);
        CbxTipoBusca.setSelectedIndex(1);
        TxtBusca.setText("");
        sql.setSql("SELECT c FROM Cliente c WHERE c.status LIKE 'ativo' ORDER BY c.id");
        carregarClientesTabela();
        this.id = null;
    }//GEN-LAST:event_BtnLimparMouseClicked

    private void TabelaClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaClientesMouseClicked
        // TODO add your handling code here:
        mostrarPopup(evt);
        ClickTabela(evt);

    }//GEN-LAST:event_TabelaClientesMouseClicked

    private void BtnExcluirClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnExcluirClienteMouseClicked
        // TODO add your handling code here: 
        Excluir(evt);

    }//GEN-LAST:event_BtnExcluirClienteMouseClicked

    private void BtnEditarClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnEditarClienteMouseClicked
        // TODO add your handling code here:
        Editar(evt);
    }//GEN-LAST:event_BtnEditarClienteMouseClicked

    private void BtnBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnBuscarMouseClicked
        // TODO add your handling code here:
        Busca(evt);
    }//GEN-LAST:event_BtnBuscarMouseClicked

    private void ExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExcluirActionPerformed
        // TODO add your handling code here:
        if (id != null) {
            switch (JOptionPane.showConfirmDialog(this, "Deseja Excluir: " + this.id
                    + ", " + nome, "Confirmação!", JOptionPane.YES_NO_OPTION)) {
                case JOptionPane.YES_OPTION:
                    Long id = (long) this.id;
                    ClienteService service = new ClienteService();
                    service.DeletarCliente(id);
                    JOptionPane.showMessageDialog(null, id + ", " + nome + " Excluido!");
                    this.id = null;
                    sql.setSql("SELECT c FROM Cliente c WHERE c.status LIKE 'ativo' ORDER BY c.id");
                    carregarClientesTabela();
                    break;
                case JOptionPane.NO_OPTION:
                    this.id = null;
                    break;
                default:
                    throw new AssertionError();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Selecione o cliente Para Exclusão");
        }
    }//GEN-LAST:event_ExcluirActionPerformed

    private void EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarActionPerformed
        // TODO add your handling code here:
        if (this.id != null) {
            Long id = (long) this.id;
            ClienteService service = new ClienteService();
            Cliente cliente = service.buscarClientePorId(id);
            CadastroCliente cadastroCliente = new CadastroCliente(cliente);
            cadastroCliente.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    sql.setSql("SELECT c FROM Cliente c WHERE c.status LIKE 'ativo' ORDER BY c.id");
                    carregarClientesTabela();
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "Selecione o cliente para Edição!");
        }
    }//GEN-LAST:event_EditarActionPerformed

    private void TabelaClientesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaClientesMouseReleased
        // TODO add your handling code here:
        mostrarPopup(evt);
    }//GEN-LAST:event_TabelaClientesMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnEditarCliente;
    private javax.swing.JButton BtnExcluirCliente;
    private javax.swing.JButton BtnLimpar;
    private javax.swing.JButton BtnNovoCliente;
    private javax.swing.JCheckBox CbxInativo;
    private javax.swing.JCheckBox CbxMostrarTodos;
    private javax.swing.JComboBox<String> CbxTipoBusca;
    private java.awt.MenuItem Editar;
    private java.awt.MenuItem Excluir;
    private javax.swing.ButtonGroup GroupBotoesBusca;
    private javax.swing.JLabel LblQtdBusca;
    private javax.swing.JPanel PnlPrincipal;
    private java.awt.PopupMenu PopMnCliente;
    private javax.swing.JTable TabelaClientes;
    private javax.swing.JTextPane TxtBusca;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

    private void setTitle(String listagem_de_Clientes) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void setExtendedState(int MAXIMIZED_BOTH) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void setDefaultCloseOperation(int DISPOSE_ON_CLOSE) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
