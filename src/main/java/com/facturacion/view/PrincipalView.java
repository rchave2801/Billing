package com.facturacion.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.SystemColor;

public class PrincipalView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrincipalView frame = new PrincipalView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PrincipalView() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				int result = JOptionPane.showConfirmDialog(null, "Desea salir de la aplicación por completo?","Warning",JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				else if (result == JOptionPane.NO_OPTION) {
					remove(result);
				}
			}
		});
		setTitle("Sistema de Facturaci\u00F3n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 512, 344);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnClientes = new JButton("Clientes");
		btnClientes.setBackground(SystemColor.menu);
		btnClientes.setBorder(null);
		btnClientes.setIcon(new ImageIcon(PrincipalView.class.getResource("/com/facturacion/images/user.png")));
		btnClientes.setFont(new Font("Calibri", Font.PLAIN, 19));
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientView client = new ClientView();
				client.setVisible(true);
			}
		});
		btnClientes.setBounds(10, 33, 220, 64);
		contentPane.add(btnClientes);
		
		JButton btnCartera = new JButton("Cartera");
		btnCartera.setBackground(SystemColor.menu);
		btnCartera.setBorder(null);
		btnCartera.setIcon(new ImageIcon(PrincipalView.class.getResource("/com/facturacion/images/money.png")));
		btnCartera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnCartera.setFont(new Font("Calibri", Font.PLAIN, 19));
		btnCartera.setBounds(278, 33, 220, 64);
		contentPane.add(btnCartera);
		
		JButton btnVenta = new JButton("Nueva Venta");
		btnVenta.setBackground(SystemColor.menu);
		btnVenta.setBorder(null);
		btnVenta.setIcon(new ImageIcon(PrincipalView.class.getResource("/com/facturacion/images/nuevo.png")));
		btnVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GenerateBill generateBill = new GenerateBill();
				generateBill.setVisible(true);
			}
		});
		btnVenta.setFont(new Font("Calibri", Font.PLAIN, 19));
		btnVenta.setBounds(10, 177, 220, 64);
		contentPane.add(btnVenta);
		
		JButton btnFactura = new JButton("Imprimir");
		btnFactura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PrintView printView = new PrintView();
				printView.setVisible(true);
			}
		});
		btnFactura.setIcon(new ImageIcon(PrincipalView.class.getResource("/com/facturacion/images/printer.png")));
		btnFactura.setFont(new Font("Calibri", Font.PLAIN, 19));
		btnFactura.setBorder(null);
		btnFactura.setBackground(SystemColor.menu);
		btnFactura.setBounds(280, 177, 218, 64);
		contentPane.add(btnFactura);
	}

}
