package com.facturacion.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.facturacion.controller.BillDAO;

public class PrintView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFactura;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrintView frame = new PrintView();
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
	public PrintView() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			dispose();
		}
	});
		setTitle("Imprimir factura");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 380, 141);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNmeroDeFactura = new JLabel("Número de factura");
		lblNmeroDeFactura.setFont(new Font("Calibri", Font.BOLD, 14));
		lblNmeroDeFactura.setBounds(10, 21, 121, 14);
		contentPane.add(lblNmeroDeFactura);
		
		txtFactura = new JTextField();
		txtFactura.setColumns(10);
		txtFactura.setBounds(10, 43, 177, 30);
		contentPane.add(txtFactura);
		
		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!txtFactura.getText().isEmpty() && txtFactura.getText() != null) {
					try {
						printBill(Integer.parseInt(txtFactura.getText()));
						txtFactura.setText(null);
					}catch(Exception ex) {
						ex.printStackTrace();
					}										
				}
				else{
					JOptionPane.showMessageDialog(null, "Debe ingresar un número de factura", "Warning",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnImprimir.setBackground(SystemColor.menu);
		btnImprimir.setIcon(new ImageIcon(PrintView.class.getResource("/com/facturacion/images/printer_small.png")));
		btnImprimir.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnImprimir.setBounds(225, 37, 121, 45);
		contentPane.add(btnImprimir);
	}
	
	private void printBill(int billNumber) {
		BillDAO billDao = new BillDAO();
		String price = "";
		String bill = "";
		if(validateBill(billNumber)) {
			price = billDao.getBillPrice(billNumber);
			bill = String.valueOf(billNumber);
			if(!price.isEmpty() && price != null){
				billDao.printByBillNumber(price, bill);
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "El número de factura ingresado no existe en el sistema.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	private boolean validateBill(int billNumber) {
		BillDAO billDao = new BillDAO();
		if(billDao.validateBill(billNumber))
			return true;
		else 
			return false;
	}
}
