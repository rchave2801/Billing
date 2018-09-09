package com.facturacion.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BillView extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frmFactura;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BillView window = new BillView();
					window.frmFactura.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BillView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFactura = new JFrame();
		frmFactura.setResizable(false);
		frmFactura.setTitle("Factura");
		frmFactura.setBounds(100, 100, 346, 120);
		frmFactura.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFactura.getContentPane().setLayout(null);
		
		JButton btnGenerar = new JButton("Generar");
		btnGenerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GenerateBill generateBill = new GenerateBill();
				generateBill.setVisible(true);
				setVisible(false);
			}
		});
		btnGenerar.setBounds(193, 41, 89, 23);
		frmFactura.getContentPane().add(btnGenerar);
		
		JButton btnCotizar = new JButton("Cotizar");
		btnCotizar.setBounds(42, 41, 89, 23);
		frmFactura.getContentPane().add(btnCotizar);
		
	}
}
