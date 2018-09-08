package com.facturacion.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.facturacion.controller.ClientDAO;
import com.facturacion.model.Client;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.ImageIcon;

public class NewClient extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfName;
	private JTextField tfId;
	private JTextField tfAddress;
	private JTextField tfPhone;
	private JTextField tfMail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewClient frame = new NewClient();
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
	public NewClient() {
		setResizable(false);
		/*addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ClientView cView = new ClientView();
				cView.setVisible(true);
			}
		});*/
		setTitle("Nuevo cliente");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 384, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Calibri", Font.BOLD, 14));
		lblNombre.setBounds(10, 41, 72, 14);
		contentPane.add(lblNombre);

		JLabel lblCdula = new JLabel("C\u00E9dula");
		lblCdula.setFont(new Font("Calibri", Font.BOLD, 14));
		lblCdula.setBounds(10, 85, 72, 14);
		contentPane.add(lblCdula);

		JLabel lblDireccin = new JLabel("Direcci\u00F3n");
		lblDireccin.setFont(new Font("Calibri", Font.BOLD, 14));
		lblDireccin.setBounds(10, 130, 72, 14);
		contentPane.add(lblDireccin);

		JLabel lblTelefno = new JLabel("Telef\u00F3no");
		lblTelefno.setFont(new Font("Calibri", Font.BOLD, 14));
		lblTelefno.setBounds(10, 177, 72, 14);
		contentPane.add(lblTelefno);

		JLabel lblCorreoElctronico = new JLabel("Correo el\u00E9ctronico");
		lblCorreoElctronico.setFont(new Font("Calibri", Font.BOLD, 14));
		lblCorreoElctronico.setBounds(10, 223, 134, 14);
		contentPane.add(lblCorreoElctronico);

		tfName = new JTextField();
		tfName.setBounds(137, 35, 219, 26);
		contentPane.add(tfName);
		tfName.setColumns(10);

		tfId = new JTextField();
		tfId.setBounds(137, 79, 219, 26);
		contentPane.add(tfId);
		tfId.setColumns(10);

		tfAddress = new JTextField();
		tfAddress.setBounds(137, 124, 219, 26);
		contentPane.add(tfAddress);
		tfAddress.setColumns(10);

		tfPhone = new JTextField();
		tfPhone.setBounds(137, 171, 219, 26);
		contentPane.add(tfPhone);
		tfPhone.setColumns(10);

		tfMail = new JTextField();
		tfMail.setBounds(137, 217, 219, 26);
		contentPane.add(tfMail);
		tfMail.setColumns(10);

		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnAgregar.setBackground(SystemColor.menu);
		btnAgregar.setIcon(new ImageIcon(NewClient.class.getResource("/com/facturacion/images/new.png")));
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientDAO clientDao = new ClientDAO();
				Client c = new Client();
				c.setId(Long.parseLong(tfId.getText()));
				c.setName(tfName.getText());
				c.setPhone(Integer.parseInt(tfPhone.getText()));
				c.setMail(tfMail.getText());
				c.setAddress(tfAddress.getText());
				try {
					clientDao.insertClient(c);
					tfId.setText(null);
					tfName.setText(null);
					tfPhone.setText(null);
					tfMail.setText(null);
					tfAddress.setText(null);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnAgregar.setBounds(95, 266, 120, 36);
		contentPane.add(btnAgregar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnCancelar.setIcon(new ImageIcon(NewClient.class.getResource("/com/facturacion/images/cancel.png")));
		btnCancelar.setBackground(SystemColor.menu);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*ClientView cView = new ClientView();
				cView.setVisible(true);*/
				dispose();
			}
		});
		btnCancelar.setBounds(235, 267, 121, 35);
		contentPane.add(btnCancelar);
	}
}
