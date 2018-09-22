package com.facturacion.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import com.facturacion.controller.BillDAO;
import com.facturacion.controller.ClientDAO;
import com.facturacion.controller.ServiceDAO;
import com.facturacion.model.Service;

public class GenerateBill extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfIdCliente;
	private JTextField txt1;
	private JTextField txt2;
	private JTextField txt3;
	private JTextField txt4;
	private JTextField txt5;
	private JTextField txt6;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GenerateBill frame = new GenerateBill();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GenerateBill() {
		setResizable(false);
		setTitle("Generar Factura");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 639, 378);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JList<String> listService = populateJlist();
		listService.setBounds(10, 54, 320, 168);
		contentPane.add(listService);

		JLabel lblListaDeServicios = new JLabel("Lista de servicios");
		lblListaDeServicios.setFont(new Font("Calibri", Font.BOLD, 19));
		lblListaDeServicios.setBounds(10, 29, 177, 23);
		contentPane.add(lblListaDeServicios);

		JLabel lblCliente = new JLabel("Identificación cliente");
		lblCliente.setFont(new Font("Calibri", Font.BOLD, 19));
		lblCliente.setBounds(403, 29, 230, 23);
		contentPane.add(lblCliente);

		tfIdCliente = new JTextField();
		tfIdCliente.setBounds(403, 53, 177, 30);
		contentPane.add(tfIdCliente);
		tfIdCliente.setColumns(10);

		JButton btnGenerar = new JButton("Generar");
		btnGenerar.setIcon(new ImageIcon(GenerateBill.class.getResource("/com/facturacion/images/invoice.png")));
		btnGenerar.setBackground(SystemColor.menu);
		btnGenerar.setFont(new Font("Calibri", Font.PLAIN, 16));
		btnGenerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!tfIdCliente.getText().isEmpty() && !listService.isSelectionEmpty()) {
					try {
						long id = Long.parseLong(tfIdCliente.getText());
						if(validateUser(id)) {
							createBill(listService, id);
							tfIdCliente.setText(null);
							listService.clearSelection();
						}
						else {
							JOptionPane.showMessageDialog(null, "El número de identificación: "+id+"."+"\n"+"No se encuentra registrado en el sistema.", "Warning",
									JOptionPane.WARNING_MESSAGE);
							NewClient newClient = new NewClient();
							newClient.setVisible(true);
						}
					}catch(NumberFormatException n) {
						
					}
					catch(SQLException n) {
						
					}					
				} else if (!tfIdCliente.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar al menos un servicio.", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Debe ingresar la identificación del cliente.", "Warning",
							JOptionPane.WARNING_MESSAGE);
				}
			}

			private void createBill(JList<String> listService, long id) {
				BillDAO billDao = new BillDAO();
				int[] serviceCodes = null;
				List<String> serviceNames = null;
				boolean response = false;
				serviceCodes = listService.getSelectedIndices();
				serviceNames = listService.getSelectedValuesList();
				int[] quantitiesValues = getValues(serviceNames);
				for (int i = 0; i < serviceCodes.length; i++) {		
					serviceCodes[i] = serviceCodes[i] + 1;
				}
				try {
					response = billDao.generateBill(serviceCodes, quantitiesValues, id);
					if (!response) {
						JOptionPane.showMessageDialog(null,
								"No fue posible generar la factura. Intente nuevamente.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null,
							"Solo se permite ingresar números en la identificación del cliente.", "Error",
							JOptionPane.ERROR_MESSAGE);
					tfIdCliente.requestFocusInWindow();
					e.printStackTrace();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null,
							"Ocurrió un error en la base de datos." + " - " + e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
		btnGenerar.setBounds(281, 278, 131, 43);
		contentPane.add(btnGenerar);

		JButton btnNewButton = new JButton("Cancelar");
		btnNewButton.setIcon(new ImageIcon(GenerateBill.class.getResource("/com/facturacion/images/cancel.png")));
		btnNewButton.setBackground(SystemColor.menu);
		btnNewButton.setFont(new Font("Calibri", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnNewButton.setBounds(449, 279, 131, 43);
		contentPane.add(btnNewButton);
		
		txt1 = new JTextField();
		txt1.setText("1");
		txt1.setColumns(10);
		txt1.setBounds(340, 60, 24, 14);
		txt1.setHorizontalAlignment(JTextField.CENTER);
		contentPane.add(txt1);
		
		
		txt2 = new JTextField();
		txt2.setText("1");
		txt2.setColumns(10);
		txt2.setBounds(340, 85, 24, 14);
		txt2.setHorizontalAlignment(JTextField.CENTER);
		contentPane.add(txt2);
		
		txt3 = new JTextField();
		txt3.setText("1");
		txt3.setColumns(10);
		txt3.setBounds(340, 110, 24, 14);
		txt3.setHorizontalAlignment(JTextField.CENTER);
		contentPane.add(txt3);
		
		txt4 = new JTextField();
		txt4.setText("1");
		txt4.setColumns(10);
		txt4.setBounds(340, 135, 24, 14);
		txt4.setHorizontalAlignment(JTextField.CENTER);
		contentPane.add(txt4);
		
		txt5 = new JTextField();
		txt5.setText("1");
		txt5.setColumns(10);
		txt5.setBounds(340, 160, 24, 14);
		txt5.setHorizontalAlignment(JTextField.CENTER);
		contentPane.add(txt5);
		
		txt6 = new JTextField();
		txt6.setText("1");
		txt6.setColumns(10);
		txt6.setBounds(340, 185, 24, 14);
		txt6.setHorizontalAlignment(JTextField.CENTER);
		contentPane.add(txt6);
	}

	private JList<String> populateJlist() {
		ServiceDAO serviceDao = new ServiceDAO();
		List<Service> listaServicios = new ArrayList<>();
		DefaultListModel<String> dlm = new DefaultListModel<String>();
		try {
			listaServicios = serviceDao.getServices();
		} catch (Exception e) {
			e.printStackTrace();
		}
		dlm.ensureCapacity(listaServicios.size());
		int i = 0;
		for (Service service : listaServicios) {
			dlm.add(i, service.getName());
			i++;
		}
		JList<String> jl = new JList<String>(dlm);
		jl.setFont(new Font("Calibri", Font.PLAIN, 18));
		jl.setBackground(SystemColor.menu);
		jl.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		return jl;
	}
	
	private int[] getValues(List<String> services) {
		int[] values = new int[services.size()];
		for(int i = 0; i < services.size(); i++) {
			if(services.get(i).equalsIgnoreCase("Imagen corporativa")) {
				values[i] = Integer.parseInt(txt1.getText());		
			}
			else if(services.get(i).equalsIgnoreCase("Tarjetas")) {
				values[i] = Integer.parseInt(txt2.getText());
			}
			else if(services.get(i).equalsIgnoreCase("Volantes")) {
				values[i] = Integer.parseInt(txt3.getText());
			}
			
			else if(services.get(i).equalsIgnoreCase("Pendón")) {
				values[i] = Integer.parseInt(txt4.getText());
			}
			
			else if(services.get(i).equalsIgnoreCase("Camisas")) {
				values[i] = Integer.parseInt(txt5.getText());
			}
			
			else if(services.get(i).equalsIgnoreCase("Gorras")) {
				values[i] = Integer.parseInt(txt6.getText());
			}
		}
		
		return values;
	}
	
	private boolean validateUser(long id) throws SQLException {
		ClientDAO clientDao = new ClientDAO();
		long c;
		c=clientDao.searchClientById(id);
		if(c == 0)
			return false;
		else
			return true;
	}
}
