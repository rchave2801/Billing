package com.facturacion.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.facturacion.controller.ClientDAO;
import com.facturacion.model.Client;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditClient extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfId;
	private JTextField tfName;
	private JTextField tfPhone;
	private JTextField tfAddress;
	private JTextField tfMail;
	private JTextField tsId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditClient frame = new EditClient();
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
	public EditClient() {
		setTitle("Editar datos cliente");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 521, 393);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblBuscar = new JLabel("Buscar");
		lblBuscar.setFont(new Font("Calibri", Font.BOLD, 13));
		lblBuscar.setBounds(10, 46, 46, 14);
		contentPane.add(lblBuscar);

		tsId = new JTextField();
		tsId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Client c = null;
					ClientDAO clientDao = new ClientDAO();
					try {
						c = clientDao.getClient(Long.parseLong(tsId.getText()));
						if (c != null) {
							tfId.setText(String.valueOf(c.getId()));
							tfId.setEnabled(false);
							tfName.setText(c.getName());
							tfPhone.setText(String.valueOf(c.getPhone()));
							tfMail.setText(c.getMail());
							tfAddress.setText(c.getAddress());
						}
					} catch (NullPointerException ex) {
						JOptionPane.showMessageDialog(null, "Error en la obtención de datos del cliente. "+ "(" + ex.getMessage() + ")", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		tsId.setBounds(66, 40, 194, 26);
		contentPane.add(tsId);
		tsId.setColumns(10);

		JButton btnBack = new JButton("Cancelar");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnBack.setIcon(new ImageIcon(EditClient.class.getResource("/com/facturacion/images/cancel.png")));
		btnBack.setBackground(SystemColor.menu);
		btnBack.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnBack.setBounds(363, 307, 113, 36);
		contentPane.add(btnBack);

		JButton btnAccept = new JButton("Aceptar");
		btnAccept.setBackground(SystemColor.menu);
		btnAccept.setIcon(new ImageIcon(EditClient.class.getResource("/com/facturacion/images/success.png")));
		btnAccept.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnAccept.setBounds(223, 307, 113, 36);
		contentPane.add(btnAccept);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(10, 77, 466, 218);
		contentPane.add(panel);
		panel.setLayout(null);
		
				JLabel lblIdentificacin = new JLabel("Identificaci\u00F3n");
				lblIdentificacin.setBounds(10, 24, 88, 14);
				panel.add(lblIdentificacin);
				lblIdentificacin.setFont(new Font("Calibri", Font.BOLD, 13));
				
						JLabel lblNombre = new JLabel("Nombre");
						lblNombre.setBounds(10, 62, 46, 14);
						panel.add(lblNombre);
						lblNombre.setFont(new Font("Calibri", Font.BOLD, 13));
						
								JLabel lblTelfono = new JLabel("Tel\u00E9fono");
								lblTelfono.setBounds(10, 100, 73, 14);
								panel.add(lblTelfono);
								lblTelfono.setFont(new Font("Calibri", Font.BOLD, 13));
								
										JLabel lblDireccin = new JLabel("Direcci\u00F3n");
										lblDireccin.setBounds(10, 138, 73, 14);
										panel.add(lblDireccin);
										lblDireccin.setFont(new Font("Calibri", Font.BOLD, 13));
										
												JLabel lblCorreoElctronico = new JLabel("Correo el\u00E9ctronico");
												lblCorreoElctronico.setBounds(10, 176, 123, 14);
												panel.add(lblCorreoElctronico);
												lblCorreoElctronico.setFont(new Font("Calibri", Font.BOLD, 13));
												
														tfId = new JTextField();
														tfId.setBounds(123, 14, 240, 26);
														panel.add(tfId);
														tfId.setColumns(10);
														
																tfName = new JTextField();
																tfName.setBounds(123, 54, 240, 26);
																panel.add(tfName);
																tfName.setColumns(10);
																
																		tfPhone = new JTextField();
																		tfPhone.setBounds(123, 94, 240, 26);
																		panel.add(tfPhone);
																		tfPhone.setColumns(10);
																		
																				tfAddress = new JTextField();
																				tfAddress.setBounds(123, 134, 240, 26);
																				panel.add(tfAddress);
																				tfAddress.setColumns(10);
																				
																						tfMail = new JTextField();
																						tfMail.setBounds(123, 174, 240, 26);
																						panel.add(tfMail);
																						tfMail.setColumns(10);
	}
}
