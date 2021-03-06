/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import domain.Controllers.LoginScreenController;
import gui.Coordinator;
import gui.ScreenEnum;

/**
 *
 * @author firattamur
 */
public class LoginPanel extends GamePanel{

    /**
     * Creates new form LoginPanel
     */
    public LoginPanel() {
        initComponents();
    }
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        loginImageField = new javax.swing.JLabel();
        passLine = new javax.swing.JSeparator();
        passInput = new javax.swing.JPasswordField();
        btnConnect = new javax.swing.JButton();
        userNameInput = new javax.swing.JTextField();
        userNameLine = new javax.swing.JSeparator();
        errorLabel = new javax.swing.JLabel();
        lblLogin = new javax.swing.JLabel();

        setBackground(new java.awt.Color(23, 56, 63));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        loginImageField.setIcon(new javax.swing.ImageIcon("Assets/Images/login-image.jpg")); // NOI18N

        passLine.setBackground(new java.awt.Color(146, 229, 192));

        passInput.setBackground(new java.awt.Color(23, 56, 63));
        passInput.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        passInput.setForeground(new java.awt.Color(146, 229, 192));
        passInput.setBorder(null);

        btnConnect.setBackground(new java.awt.Color(146, 229, 192));
        btnConnect.setFont(new java.awt.Font("Phosphate", 0, 18)); // NOI18N
        btnConnect.setForeground(new java.awt.Color(23, 56, 63));
        btnConnect.setText("LOGIN");
       

        userNameInput.setBackground(new java.awt.Color(23, 56, 63));
        userNameInput.setFont(new java.awt.Font("Phosphate", 2, 24)); // NOI18N
        userNameInput.setForeground(new java.awt.Color(146, 229, 192));
        userNameInput.setBorder(null);

        userNameLine.setBackground(new java.awt.Color(146, 229, 192));

        errorLabel.setBackground(new java.awt.Color(23, 56, 63));
        errorLabel.setFont(new java.awt.Font("Phosphate", 0, 14)); // NOI18N
        errorLabel.setForeground(new java.awt.Color(146, 229, 192));
        errorLabel.setText("");
        
        lblLogin.setBackground(new java.awt.Color(23, 56, 63));
        lblLogin.setFont(new java.awt.Font("Phosphate", 0, 48)); // NOI18N
        lblLogin.setForeground(new java.awt.Color(146, 229, 192));
        lblLogin.setText("LOGIN");
        

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(loginImageField, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                	.addGroup(layout.createSequentialGroup())  
                		.addComponent(lblLogin)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(errorLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(btnConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(passLine, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(passInput, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                                    .addComponent(userNameLine)))
                            .addComponent(userNameInput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
            	.addGap(50, 50, 100)
            	.addComponent(lblLogin)
                .addGap(10, 10, 10)
                .addComponent(errorLabel)
                .addGap(18, 18, 18)
                .addComponent(userNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userNameLine, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passInput, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnConnect)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(loginImageField, javax.swing.GroupLayout.PREFERRED_SIZE, 498, Short.MAX_VALUE)
        );
    }// </editor-fold>                        


    // Variables declaration - do not modify                     
    private javax.swing.JButton btnConnect;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JLabel loginImageField;
    private javax.swing.JPasswordField passInput;
    private javax.swing.JSeparator passLine;
    private javax.swing.JTextField userNameInput;
    private javax.swing.JSeparator userNameLine;
    private javax.swing.JLabel lblLogin;
    // End of variables declaration           
    
   
    private PanelListener listener = new PanelListener();
    
    
    private class PanelListener implements ActionListener {

		public PanelListener(){

		}

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			if(actionEvent.getSource().equals(btnConnect)){
				if(userNameInput.getText() == null || userNameInput.getText().length() < 3){
					errorLabel.setText("Invalid Username");
					errorLabel.setVisible(true);
				}else if(passInput.getText() == null || passInput.getText().length() < 3){
					errorLabel.setText("Invalid Password");
					errorLabel.setVisible(true);
				}else{
					boolean success = LoginScreenController.getInstance().connect(userNameInput.getText(),passInput.getText());
					if (success){
						Coordinator.getInstance().openScreen(ScreenEnum.MAIN);
					}else{
						errorLabel.setText("Wrong password or username");
						errorLabel.setVisible(true);
					}
				}

			}

		}

	}


    @Override
	public void bindPanelListener(JFrame frame) {
		btnConnect.addActionListener(listener);
	}

	@Override
	public void removePanelListener(JFrame frame) {
		btnConnect.removeActionListener(listener);
	}

    
}

