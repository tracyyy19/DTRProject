import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.PopupFactory;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleConstants.ColorConstants;

import com.itextpdf.text.BaseColor;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.Watermark;
import com.lowagie.text.pdf.PdfAnnotation;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class Preview {
	
	private static JTable classTable;
	private static JTable consultationTable;
	private static JTable relatedTable;
	private static JTable othersTable;
	private static JTable totalHrsTable;
	private static JLabel nameView;
	private static int class_hour;
	
	public static void main(String[] args) {
		showWindow();
		showClass();
		showConsultation();
		showRelated();
		showOthers();
		totalConsultationHrs();
	}
	
	public static void showWindow() {
		JFrame frame = new JFrame("Preview");
		frame.getContentPane().setBackground(new Color(230, 230, 250));
		frame.setBounds(200, 100, 920,690);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(0);
		
		JScrollPane scrollPaneClass = new JScrollPane();
		scrollPaneClass.setBounds(10, 175, 219, 369);
		frame.getContentPane().add(scrollPaneClass);
		
		classTable = new JTable();
		classTable.setBackground(new Color(245, 245, 220));
		scrollPaneClass.setViewportView(classTable);
		
		JScrollPane scrollPaneConsultation = new JScrollPane();
		scrollPaneConsultation.setBounds(228, 175, 227, 369);
		frame.getContentPane().add(scrollPaneConsultation);
		
		consultationTable = new JTable();
		consultationTable.setBackground(new Color(245, 245, 220));
		consultationTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPaneConsultation.setViewportView(consultationTable);
		
		JScrollPane scrollPaneRelated = new JScrollPane();
		scrollPaneRelated.setBounds(456, 175, 227, 369);
		frame.getContentPane().add(scrollPaneRelated);
		
		relatedTable = new JTable();
		relatedTable.setBackground(new Color(245, 245, 220));
		relatedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPaneRelated.setViewportView(relatedTable);
		
		JScrollPane scrollPaneOthers = new JScrollPane();
		scrollPaneOthers.setBounds(679, 175, 213, 369);
		frame.getContentPane().add(scrollPaneOthers);
		
		othersTable = new JTable();
		othersTable.setBackground(new Color(245, 245, 220));
		othersTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPaneOthers.setViewportView(othersTable);
		
//		JScrollPane scrollPaneTotalHrs = new JScrollPane();
//		scrollPaneTotalHrs.setBounds(902, 230, 73, 300);
//		frame.getContentPane().add(scrollPaneTotalHrs);
		
//		totalHrsTable = new JTable();
//		totalHrsTable.setBackground(new Color(245, 245, 220));
//		scrollPaneTotalHrs.setViewportView(totalHrsTable);
		
		JButton btnNewButton = new JButton("Generate PDF");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(new Color(135, 206, 235));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					Connection con = connect();
					String file_name = "";
					JFileChooser j = new JFileChooser();
					j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int x = j.showSaveDialog(frame);
					
					if(x==JFileChooser.APPROVE_OPTION) {
						file_name = j.getSelectedFile().getPath(); 
					}
					Document document = new Document();
					PdfWriter.getInstance(document, new FileOutputStream(file_name + "dtr.pdf"));
					
					document.open();
					
					Paragraph rp = new Paragraph("Republic of the Philippines");
					rp.setAlignment(Element.ALIGN_CENTER);
					document.add(rp);
					
					Paragraph msu = new Paragraph("Mindanao State University");
					msu.setAlignment(Element.ALIGN_CENTER);
					document.add(msu);
					
					Paragraph it = new Paragraph("ILIGAN INSTITUTE OF TECHNOLOGY");
					it.setAlignment(Element.ALIGN_CENTER);
					document.add(it);
					
					Paragraph ic = new Paragraph("Iligan City");
					ic.setAlignment(Element.ALIGN_CENTER);
					document.add(ic);
					
					Font f = new Font(Font.BOLD);
					
					Paragraph fdtr = new Paragraph("FACULTY DAILY TIME RECORD", f);
					fdtr.setAlignment(Element.ALIGN_CENTER);
					document.add(fdtr);
						
					String employeeQuery = "select * from employee";
					Statement employee_st  = con.createStatement();
					ResultSet employee_rs = employee_st.executeQuery(employeeQuery);
					
					while(employee_rs.next()) {
						
						Paragraph monYear = new Paragraph("For the month of " + employee_rs.getString("month")
						+ " " + employee_rs.getString("year"));
						monYear.setAlignment(Element.ALIGN_CENTER);
						document.add(monYear);
						
						document.add(new Paragraph("\n"));

						document.add(new Paragraph("        " + "Name: " + employee_rs.getString("name") +"                               "
								+"          "+ "Department: "  + employee_rs.getString("department")));
					}
					document.add(new Paragraph("\n"));					
					String query_class_sum = "select sum(hrs) from class";
					Statement st_class_sum  = con.createStatement();
					ResultSet rs_class_sum = st_class_sum.executeQuery(query_class_sum);
					while(rs_class_sum.next()) {
						document.add(new Paragraph("\n"));
						Paragraph _class = new Paragraph("Class " + "total hrs: " + rs_class_sum.getString("sum(hrs)"));
						_class.setAlignment(Element.ALIGN_CENTER);
						document.add(_class);	
					}
					rs_class_sum.close();
					st_class_sum.close();
					document.add(new Paragraph("\n"));
					
					String classQ = "select * from class";
					Statement class_st  = con.createStatement();
					ResultSet class_rs = class_st.executeQuery(classQ);

					PdfPTable tbl = new PdfPTable(4);
					
					tbl.addCell("Day");
					tbl.addCell("Time In");
					tbl.addCell("Time Out");
					tbl.addCell("Hrs");
					
					while(class_rs.next()) {
						tbl.addCell(class_rs.getString("id"));
						tbl.addCell(class_rs.getString("time_in"));
						tbl.addCell(class_rs.getString("time_out"));
						tbl.addCell(class_rs.getString("hrs"));
					}
					document.add(tbl);
					
					document.add(new Paragraph("\n"));
					String query_consultation_sum = "select sum(hrs) from consultation";
					Statement st_consultation_sum  = con.createStatement();
					ResultSet rs_consultation_sum = st_consultation_sum.executeQuery(query_consultation_sum);
					while(rs_consultation_sum.next()) {
						document.add(new Paragraph("\n"));
						Paragraph _consultation = new Paragraph("Consultation " + "total hrs: " + rs_consultation_sum.getString("sum(hrs)"));
						_consultation.setAlignment(Element.ALIGN_CENTER);
						document.add(_consultation);	
					}
					rs_consultation_sum.close();
					st_consultation_sum.close();
					document.add(new Paragraph("\n"));
					
					String consultationQ = "select * from consultation";
					Statement consultation_st  = con.createStatement();
					ResultSet consultation_rs = consultation_st.executeQuery(consultationQ);
					
					PdfPTable consult_tbl = new PdfPTable(4);
					
					consult_tbl.addCell("Day");
					consult_tbl.addCell("Time In");
					consult_tbl.addCell("Time Out");
					consult_tbl.addCell("Hrs");
					
					while(consultation_rs.next()) {
						consult_tbl.addCell(consultation_rs.getString("id"));
						consult_tbl.addCell(consultation_rs.getString("time_in"));
						consult_tbl.addCell(consultation_rs.getString("time_out"));
						consult_tbl.addCell(consultation_rs.getString("hrs"));
					}
					document.add(consult_tbl);
					
					String query_related_sum = "select sum(hrs) from related";
					Statement st_related_sum  = con.createStatement();
					ResultSet rs_related_sum = st_related_sum.executeQuery(query_related_sum);
					while(rs_related_sum.next()) {
						document.add(new Paragraph("\n"));
						Paragraph _related = new Paragraph("Related Activities " + "total hrs: " + rs_related_sum.getString("sum(hrs)"));
						_related.setAlignment(Element.ALIGN_CENTER);
						document.add(_related);	
					}
					rs_related_sum.close();
					st_related_sum.close();
								
					document.add(new Paragraph("\n"));
					
					String relatedQ = "select * from related";
					Statement related_st  = con.createStatement();
					ResultSet related_rs = class_st.executeQuery(relatedQ);

					PdfPTable related_tbl = new PdfPTable(4);
					
					related_tbl.addCell("Day");
					related_tbl.addCell("Time In");
					related_tbl.addCell("Time Out");
					related_tbl.addCell("Hrs");
					
					while(related_rs.next()) {
						related_tbl.addCell(related_rs.getString("id"));
						related_tbl.addCell(related_rs.getString("time_in"));
						related_tbl.addCell(related_rs.getString("time_out"));
						related_tbl.addCell(related_rs.getString("hrs"));
					}
					document.add(related_tbl);				
					
					String query_other_sum = "select sum(hrs) from others";
					Statement st_others_sum  = con.createStatement();
					ResultSet rs_other_sum = st_others_sum.executeQuery(query_other_sum);
					while(rs_other_sum.next()) {
						document.add(new Paragraph("\n"));
						Paragraph _others = new Paragraph("Others " + "total hrs: " + rs_other_sum.getString("sum(hrs)"));
						_others.setAlignment(Element.ALIGN_CENTER);
						document.add(_others);	
					}
					rs_other_sum.close();
					rs_other_sum.close();
					document.add(new Paragraph("\n"));
					String othersQ = "select * from others";
					Statement others_st  = con.createStatement();
					ResultSet others_rs = class_st.executeQuery(othersQ);

					PdfPTable others_tbl = new PdfPTable(4);
					
					others_tbl.addCell("Day");
					others_tbl.addCell("Time In");
					others_tbl.addCell("Time Out");
					others_tbl.addCell("Hrs");
					
					while(others_rs.next()) {
						others_tbl.addCell(others_rs.getString("id"));
						others_tbl.addCell(others_rs.getString("time_in"));
						others_tbl.addCell(others_rs.getString("time_out"));
						others_tbl.addCell(others_rs.getString("hrs"));
					}
					document.add(others_tbl);
					
					document.add(new Paragraph("\n"));
					document.add(new Paragraph("\n"));
					document.add(new Paragraph("\n"));
					
					String deptHeadQuery = "select * from employee";
					Statement  deptHead_st  = con.createStatement();
					ResultSet  deptHead_rs = deptHead_st.executeQuery(deptHeadQuery);
					
					while(deptHead_rs.next()) {
						document.add(new Paragraph("This certifies upon my honor that the foregoing is a record for services"
								+ " I rendered to MSU-Iligan Institute of Technology during the month of "
								+ deptHead_rs.getString("month") + " " + deptHead_rs.getString("year")));
					document.add(new Paragraph("\n"));
					document.add(new Paragraph("\n"));
					document.add(new Paragraph("________"+DTR.name.getText()+"________"));
					document.add(new Paragraph("         (Signature over Printed Name)   "));
					document.add(new Paragraph("                                                                                  Certified Correct: "));
					document.add(new Paragraph("\n"));
					document.add(new Paragraph("                                                                                  _______________________________"));
					document.add(new Paragraph("                                                                                              "+ DTR.department_head.getText()));
					document.add(new Paragraph("                                                                                           (Head of Department/Unit)"));
					document.add(new Paragraph("_______________________________"));
					document.add(new Paragraph("          (Designation)"));
					

					}
					document.close();

				}catch(Exception err) {
					System.out.print("Error: " + err);
				}
			}
		});
		btnNewButton.setBounds(488, 570, 195, 57);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				totalRelatedHrs();
				deleteAllData();
				defaultValueClass();
				defaultValueConsultation();
				defaultValueRelated();
				defaultValueOthers();
				totalClassHrs();
				frame.dispose();
			}
		});
		btnExit.setForeground(Color.WHITE);
		btnExit.setBackground(new Color(255, 0, 0));
		btnExit.setBounds(689, 570, 195, 57);
		frame.getContentPane().add(btnExit);
		
		JLabel lblNewLabel_1 = new JLabel("Republic of the Philippines");
		lblNewLabel_1.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 14));
		lblNewLabel_1.setBounds(391, 11, 160, 21);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Mindanao State University");
		lblNewLabel_1_1.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(391, 36, 169, 21);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("ILIGAN INSTITUTE OF TECHNOLOGY");
		lblNewLabel_1_1_1.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 14));
		lblNewLabel_1_1_1.setBounds(368, 57, 230, 21);
		frame.getContentPane().add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Iligan City");
		lblNewLabel_1_1_1_1.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 14));
		lblNewLabel_1_1_1_1.setBounds(440, 78, 73, 21);
		frame.getContentPane().add(lblNewLabel_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("FACULTY DAILY TIME RECORD");
		lblNewLabel_1_1_1_1_1.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 15));
		lblNewLabel_1_1_1_1_1.setBounds(354, 99, 246, 21);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1);
			
		JLabel lblNewLabel_1_1_1_1_1_1 = new JLabel("Class");
		lblNewLabel_1_1_1_1_1_1.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 15));
		lblNewLabel_1_1_1_1_1_1.setBounds(85, 155, 48, 21);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1_1_1 = new JLabel("Consultation");
		lblNewLabel_1_1_1_1_1_1_1.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 15));
		lblNewLabel_1_1_1_1_1_1_1.setBounds(290, 155, 111, 21);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1_1_2 = new JLabel("Related Activities");
		lblNewLabel_1_1_1_1_1_1_2.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 15));
		lblNewLabel_1_1_1_1_1_1_2.setBounds(495, 155, 160, 21);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1_1_2);
		
		JLabel lblNewLabel_1_1_1_1_1_1_3 = new JLabel("Others");
		lblNewLabel_1_1_1_1_1_1_3.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 15));
		lblNewLabel_1_1_1_1_1_1_3.setBounds(749, 155, 73, 21);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1_1_3);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 155, 219, 21);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(227, 155, 230, 21);
		frame.getContentPane().add(panel_1);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_1.setBounds(456, 155, 227, 21);
		frame.getContentPane().add(panel_1_1);
		
		JPanel panel_1_1_1 = new JPanel();
		panel_1_1_1.setLayout(null);
		panel_1_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_1_1.setBounds(679, 155, 213, 21);
		frame.getContentPane().add(panel_1_1_1);
		
		JLabel newLabelName = new JLabel("Name: ");
		newLabelName.setFont(new java.awt.Font("Times New Roman", java.awt.Font.BOLD, 18));
		newLabelName.setBounds(47, 129, 55, 21);
		frame.getContentPane().add(newLabelName);
		
		JLabel lblNamevalue = new JLabel("nameValue");
		lblNamevalue.setFont(new java.awt.Font("Times New Roman", java.awt.Font.BOLD, 18));
		lblNamevalue.setText(DTR.name.getText());
		lblNamevalue.setBounds(112, 129, 159, 21);
		frame.getContentPane().add(lblNamevalue);
		
		JLabel previewLblDepartment = new JLabel("Department:");
		previewLblDepartment.setFont(new java.awt.Font("Times New Roman", java.awt.Font.BOLD, 18));
		previewLblDepartment.setBounds(510, 129, 111, 21);
		frame.getContentPane().add(previewLblDepartment);
		
		JLabel lblDeptvalue = new JLabel("deptValue");
		lblDeptvalue.setFont(new java.awt.Font("Times New Roman", java.awt.Font.BOLD, 18));
		lblDeptvalue.setText(DTR.department.getText());
		lblDeptvalue.setBounds(615, 129, 269, 21);
		frame.getContentPane().add(lblDeptvalue);
		
		JLabel previewLblHead = new JLabel("Head of Department:");
		previewLblHead.setFont(new java.awt.Font("Times New Roman", java.awt.Font.BOLD, 18));
		previewLblHead.setBounds(22, 570, 184, 21);
		frame.getContentPane().add(previewLblHead);
		
		JLabel lblHeadvalue = new JLabel("headValue");
		lblHeadvalue.setFont(new java.awt.Font("Times New Roman", java.awt.Font.BOLD, 18));
		lblHeadvalue.setText(DTR.department_head.getText());
		lblHeadvalue.setBounds(74, 593, 273, 34);
		frame.getContentPane().add(lblHeadvalue);
		frame.setVisible(true);
	}
	
	static Connection connect() {
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost/fdtr";
			Class.forName(driver);
			return DriverManager.getConnection(url, "root", "");
		}catch(Exception e) {
			System.out.print("Couldn't connect: " + e);
		}
		return null;
	}
	
	public static void showClass() {
		
		Connection con = connect();
		DefaultTableModel model = new DefaultTableModel();
		
		model.addColumn("Day");
		model.addColumn("In");
		model.addColumn("Out");
		model.addColumn("Hrs");
		
		try {
			String querry = "select * from class";
			Statement st  = con.createStatement();
			ResultSet rs = st.executeQuery(querry);
			
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getString("id"),
						rs.getString("time_in"),
						rs.getString("time_out"),
						rs.getString("hrs"),
				});
			}
				rs.close();
				st.close();
			
				classTable.setModel(model);
				classTable.setAutoResizeMode(0);
				classTable.getColumnModel().getColumn(0).setPreferredWidth(52);
				classTable.getColumnModel().getColumn(1).setPreferredWidth(52);
				classTable.getColumnModel().getColumn(2).setPreferredWidth(52);
				classTable.getColumnModel().getColumn(3).setPreferredWidth(52);
				
		}catch(Exception err) {
			System.out.print("error: " + err);
		}
	}
	
	public static void showConsultation() {
		
		Connection con = connect();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("In");
		model.addColumn("Out");
		model.addColumn("Hrs");
		
		try {
			String querry = "select * from consultation";
			Statement st  = con.createStatement();
			ResultSet rs = st.executeQuery(querry);
			
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getString("time_in"),
						rs.getString("time_out"),
						rs.getString("hrs"),
				});
			}
				rs.close();
				st.close();
			
				consultationTable.setModel(model);
				consultationTable.setAutoResizeMode(0);
				consultationTable.getColumnModel().getColumn(0).setPreferredWidth(70);
				consultationTable.getColumnModel().getColumn(1).setPreferredWidth(70);
				consultationTable.getColumnModel().getColumn(2).setPreferredWidth(70);

		}catch(Exception err) {
			System.out.print("error: " + err);
		}
	}
	
	public static void showRelated() {
		
		Connection con = connect();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("In");
		model.addColumn("Out");
		model.addColumn("Hrs");
		
		try {
			String querry = "select * from related";
			Statement st  = con.createStatement();
			ResultSet rs = st.executeQuery(querry);
			
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getString("time_in"),
						rs.getString("time_out"),
						rs.getString("hrs"),
				});
			}
				rs.close();
				st.close();
			
				relatedTable.setModel(model);
				relatedTable.setAutoResizeMode(0);
				relatedTable.getColumnModel().getColumn(0).setPreferredWidth(70);
				relatedTable.getColumnModel().getColumn(1).setPreferredWidth(70);
				relatedTable.getColumnModel().getColumn(2).setPreferredWidth(70);

		}catch(Exception err) {
			System.out.print("error: " + err);
		}
	}
	
	public static void showOthers() {
		
		Connection con = connect();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("In");
		model.addColumn("Out");
		model.addColumn("Hrs");
		  Paragraph header = new Paragraph("Copy");
		
		try {
			String query = "select * from others";
			Statement st  = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getString("time_in"),
						rs.getString("time_out"),
						rs.getString("hrs"),
				});
			}
				rs.close();
				st.close();
			
				othersTable.setModel(model);
				othersTable.setAutoResizeMode(0);
				othersTable.getColumnModel().getColumn(0).setPreferredWidth(70);
				othersTable.getColumnModel().getColumn(1).setPreferredWidth(70);
				othersTable.getColumnModel().getColumn(2).setPreferredWidth(70);

		}catch(Exception err) {
			System.out.print("error: " + err);
		}
	}
	
	public static void deleteAllData() {
		Connection con = connect();
		try {
			String deleteQueryClass = "truncate class";
			PreparedStatement ps_class = con.prepareStatement(deleteQueryClass);
			ps_class.execute();
			
			String deleteQueryRelated = "truncate related";
			PreparedStatement ps_relative = con.prepareStatement(deleteQueryRelated);
			ps_relative.execute();
			
			String deleteQueryConsultation = "truncate consultation"; 
			PreparedStatement ps_consulation = con.prepareStatement(deleteQueryConsultation);
			ps_consulation.execute();
			
			String deleteOthers = "truncate others";
			PreparedStatement ps_others = con.prepareStatement(deleteOthers);
			ps_others.execute();
			
			String deleteEmployee = "truncate employee";
			PreparedStatement ps_employee = con.prepareStatement(deleteEmployee);
			ps_employee.execute();
			con.close();

		}catch(Exception err) {
			System.out.print("error: " + err);
		}
	}
	
	public static void defaultValueClass() {
		Connection con = connect();
		try {
			for(int i = 0; i<31; i++) {
				String query = "insert into class (time_in, time_out, hrs) values(?,?,?)";
				PreparedStatement ps;
				ps = con.prepareStatement(query);
				ps.setString(1, "");
				ps.setString(2, "");
				ps.setString(3, "");
				ps.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void defaultValueConsultation() {
		Connection con = connect();
		try {
			for(int i = 0; i<31; i++) {
				String query = "insert into consultation (time_in, time_out, hrs) values(?,?,?)";
				PreparedStatement ps;
				ps = con.prepareStatement(query);
				ps.setString(1, "");
				ps.setString(2, "");
				ps.setString(3, "");
				ps.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void defaultValueRelated() {
		Connection con = connect();
		try {
			for(int i = 0; i<31; i++) {
				String query = "insert into related (time_in, time_out, hrs) values(?,?,?)";
				PreparedStatement ps;
				ps = con.prepareStatement(query);
				ps.setString(1, "");
				ps.setString(2, "");
				ps.setString(3, "");
				ps.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void defaultValueOthers() {
		Connection con = connect();
		try {
			for(int i = 0; i<31; i++) {
				String query = "insert into others (time_in, time_out, hrs) values(?,?,?)";
				PreparedStatement ps;
				ps = con.prepareStatement(query);
				ps.setString(1, "");
				ps.setString(2, "");
				ps.setString(3, "");
				ps.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void empDetails() {
		
		Connection con = connect();
		DefaultTableModel model = new DefaultTableModel();

		try {
			String query = "select * from employee";
			Statement st  = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			if(rs.next()) {
				model.addRow(new Object[] {
						rs.getString("id_number"),
						rs.getString("name"),
						rs.getString("department"),
						rs.getString("department_head"),
				});
			}
				rs.close();
				st.close();
				con.close();
		}catch(Exception err) {
			System.out.print("error: " + err);
		}
	}
	
	public static void totalClassHrs() {
		
		Connection con = connect();
		DefaultTableModel model = new DefaultTableModel();
		
		try {
			String query = "select sum(hrs) from class";
			Statement st  = con.createStatement();
			ResultSet rs = st.executeQuery(query);
		
			if(rs.next()) {
				rs.getString("sum(hrs)");
				System.out.print(rs.getString("sum(hrs)"));
			}
				rs.close();
				st.close();
				con.close();
			}catch(Exception err) {
				System.out.print("error: " + err);
		}
	}
	
public static void totalRelatedHrs() {
		
		Connection con = connect();
		DefaultTableModel model = new DefaultTableModel();
		
		try {
			String query = "select sum(hrs) from related";

			Statement st  = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.next()) {
				rs.getString("sum(hrs)");
				System.out.print(rs.getString("sum(hrs)"));
			}
				rs.close();
				st.close();
				con.close();
			}catch(Exception err) {
				System.out.print("error: " + err);
		}
	}

public static void totalOthersHrs() {
	
	Connection con = connect();
	DefaultTableModel model = new DefaultTableModel();
	
		try {
			String query = "select sum(hrs) from others";
	
			Statement st  = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.next()) {
				rs.getString("sum(hrs)");
				System.out.print(rs.getString("sum(hrs)"));
			}
				rs.close();
				st.close();
				con.close();
			}catch(Exception err) {
				System.out.print("error: " + err);
		}
	}

public static void totalConsultationHrs() {
	
	Connection con = connect();
	DefaultTableModel model = new DefaultTableModel();
		try {
			String query = "select sum(hrs) from consultation";
	
			Statement st  = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.next()) {
				String a = rs.getString("sum(hrs)");
			}
				rs.close();
				st.close();
				con.close();
			}catch(Exception err) {
				System.out.print("error: " + err);
		}
	}
}