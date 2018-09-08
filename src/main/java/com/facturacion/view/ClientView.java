package com.facturacion.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.Font;

public class ClientView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientView frame = new ClientView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientView() {
		setResizable(false);
		/*addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				PrincipalView pPal = new PrincipalView();
				pPal.setVisible(true);
			}
		});*/
		setTitle("Administraci\u00F3n de clientes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 418, 228);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNuevoCliente = new JButton("Nuevo Cliente");
		btnNuevoCliente.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnNuevoCliente.setBackground(SystemColor.menu);
		btnNuevoCliente.setIcon(new ImageIcon(ClientView.class.getResource("/com/facturacion/images/Adduser.png")));
		btnNuevoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewClient newCLient = new NewClient();
				newCLient.setVisible(true);
				dispose();
			}
		});
		btnNuevoCliente.setBounds(10, 62, 186, 64);
		contentPane.add(btnNuevoCliente);
		
		JButton btnEditarCliente = new JButton("Editar Cliente");
		btnEditarCliente.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnEditarCliente.setIcon(new ImageIcon(ClientView.class.getResource("/com/facturacion/images/Edituser.png")));
		btnEditarCliente.setBackground(SystemColor.menu);
		btnEditarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EditClient editClient = new EditClient();
				editClient.setVisible(true);
				dispose();
			}
		});
		btnEditarCliente.setBounds(207, 62, 186, 64);
		contentPane.add(btnEditarCliente);
	}

}
