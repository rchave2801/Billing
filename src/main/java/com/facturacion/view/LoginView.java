package com.facturacion.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JTextField;

import com.facturacion.controller.UserDAO;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.SystemColor;
import javax.swing.ImageIcon;

public class LoginView extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frmInicioDeSesin;
	private JTextField tfName;
	private JPasswordField tfPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView window = new LoginView();
					window.frmInicioDeSesin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmInicioDeSesin = new JFrame();
		frmInicioDeSesin.setTitle("Inicio de sesi\u00F3n");
		frmInicioDeSesin.setFont(new Font("Calibri", Font.PLAIN, 12));
		frmInicioDeSesin.setBounds(100, 100, 479, 300);
		frmInicioDeSesin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInicioDeSesin.getContentPane().setLayout(null);
		
		JLabel lblIniciarSesin = new JLabel("Iniciar Sesi\u00F3n");
		lblIniciarSesin.setFont(new Font("Calibri", Font.PLAIN, 35));
		lblIniciarSesin.setBounds(124, 11, 240, 44);
		frmInicioDeSesin.getContentPane().add(lblIniciarSesin);
		
		JLabel lbUser = new JLabel("Usuario");
		lbUser.setFont(new Font("Calibri", Font.PLAIN, 25));
		lbUser.setBounds(10, 100, 138, 31);
		frmInicioDeSesin.getContentPane().add(lbUser);
		
		JLabel lbPass = new JLabel("Contrase\u00F1a");
		lbPass.setFont(new Font("Calibri", Font.PLAIN, 25));
		lbPass.setBounds(10, 159, 138, 31);
		frmInicioDeSesin.getContentPane().add(lbPass);
		
		tfName = new JTextField();
		tfName.setBounds(167, 100, 186, 31);
		frmInicioDeSesin.getContentPane().add(tfName);
		tfName.setColumns(10);
		
		JButton btLogin = new JButton("Iniciar");
		btLogin.setIcon(new ImageIcon(LoginView.class.getResource("/com/facturacion/images/login.png")));
		btLogin.setBackground(SystemColor.menu);
		btLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserDAO userDao = new UserDAO();
				try {
					if(!tfName.getText().isEmpty() && !tfPass.getPassword().toString().isEmpty()) {
						String pass = String.valueOf(tfPass.getPassword());
						if(userDao.loginUser(tfName.getText(), pass)) {
							PrincipalView principal = new PrincipalView();
							frmInicioDeSesin.setVisible(false);
							principal.setVisible(true);
							dispose();
						}
						else {
							JOptionPane.showMessageDialog(null, "El usuario y/o contaseña ingresada son invalidos.","Error",JOptionPane.ERROR_MESSAGE);
						}
					}
					else {
						JOptionPane.showConfirmDialog(null, "Debe ingresar un nombre de usuario y una contraseña");
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		btLogin.setFont(new Font("Calibri", Font.PLAIN, 20));
		btLogin.setBounds(167, 211, 186, 39);
		frmInicioDeSesin.getContentPane().add(btLogin);
		
		tfPass = new JPasswordField();
		tfPass.setBounds(167, 156, 186, 31);
		frmInicioDeSesin.getContentPane().add(tfPass);
	}
}