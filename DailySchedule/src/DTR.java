import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.crypto.Data;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Image;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.JTextField;
import javax.print.attribute.standard.DocumentName;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import com.toedter.calendar.JDayChooser;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.SystemColor;


public class DTR {
	private JFrame frame;
	private JLabel lblClock;
	private JLabel fullName;
	public static JTextField name;
	private JLabel departmentLbl;
	private JTextField department;
	private JLabel headOfDepartment;
	public static JTextField department_head;
	private JDateChooser dateChooser;
	private JTable table;
	private JComboBox types;
	private String[] type = {"class", "consultation", "related activities", "others"};
	private JComboBox days;
	private String[] day = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	private JLabel lblTimelbl;
	private JLabel lblDate;
	private JLabel lblDate_1;
	private JButton btnGeneratePdf;
	private JLabel employeeIcon;
	private JTextArea time_in;
	private JTextArea time_out;
	private JDateChooser dateDays;
	private JTextField id_number;
	private JLabel lblMonth;
	private JLabel lblYear;
	private JButton preview;
	private Object[]  columns = {"Day", "Time In", "Time Out", "Type"};
	private Object[] row = new Object[4];
	private Object[] time_sched = {"6:00 AM", ""};
	private JTextArea holiday;
	private JLabel dayyys;
	private JMonthChooser month;
	private JYearChooser year;
	private JTextArea id_type;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DTR window = new DTR();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public void clock() {
		
		Thread clock = new Thread() {
			public void run() {
				try {
					while(true) {
						Calendar cal = new GregorianCalendar();
						int day = cal.get(Calendar.DAY_OF_MONTH);
						int month = cal.get(Calendar.MONTH);
						int year = cal.get(Calendar.YEAR);
						
						int second = cal.get(Calendar.SECOND);
						int minutes = cal.get(Calendar.MINUTE);
						int hour = cal.get(Calendar.HOUR_OF_DAY);
						
						
						SimpleDateFormat sdf12  = new SimpleDateFormat("hh:mm:ss aa");
						Date date = cal.getTime();
						String time12 = sdf12.format(date);
						
						lblClock.setText(time12);
						lblDate_1.setText(month + 1 +"/"+day+"/"+year);
						
						sleep(1000);
					}
					
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		clock.start();
	}
	

	/**
	 * Create the application.
	 */
	public DTR() {
		initialize();
		clock();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("FDTR");
		frame.getContentPane().setForeground(new Color(255, 255, 204));
		frame.getContentPane().setBackground(new Color(230, 230, 250));
		frame.setBackground(UIManager.getColor("menu"));
		frame.setBounds(200, 100, 900, 590);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Faculty Daily Time Record");
		lblNewLabel.setBackground(new Color(0, 0, 0));
		lblNewLabel.setForeground(new Color(0, 51, 51));
		lblNewLabel.setFont(new Font("Book Antiqua", Font.BOLD, 24));
		lblNewLabel.setBounds(96, 26, 304, 50);
		frame.getContentPane().add(lblNewLabel);
		
		lblClock = new JLabel("Time");
		lblClock.setForeground(new Color(0, 51, 51));
		lblClock.setFont(new Font("Lucida Fax", Font.BOLD, 18));
		lblClock.setBounds(473, 45, 134, 14);
		frame.getContentPane().add(lblClock);
		
		fullName = new JLabel("Full Name: ");
		fullName.setForeground(new Color(0, 51, 51));
		fullName.setFont(new Font("Book Antiqua", Font.BOLD, 14));
		fullName.setBounds(232, 120, 80, 15);
		frame.getContentPane().add(fullName);
		
		name = new JTextField();
		name.setBackground(new Color(255, 245, 238));
		name.setBounds(232, 145, 206, 27);
		name.setColumns(10);
		frame.getContentPane().add(name);

		
		departmentLbl = new JLabel("Department: ");
		departmentLbl.setForeground(new Color(0, 51, 51));
		departmentLbl.setFont(new Font("Book Antiqua", Font.BOLD, 14));
		departmentLbl.setBounds(10, 184, 90, 15);
		frame.getContentPane().add(departmentLbl);
		
		department = new JTextField();
		department.setBackground(new Color(255, 245, 238));
		department.setColumns(10);
		department.setBounds(10, 209, 206, 27);
		frame.getContentPane().add(department);
		
		headOfDepartment = new JLabel("Head of \r\ndepartment: ");
		headOfDepartment.setForeground(new Color(0, 51, 51));
		headOfDepartment.setFont(new Font("Book Antiqua", Font.BOLD, 14));
		headOfDepartment.setBounds(232, 183, 145, 15);
		frame.getContentPane().add(headOfDepartment);
		
		department_head = new JTextField();
		department_head.setBackground(new Color(255, 245, 238));
		department_head.setColumns(10);
		department_head.setBounds(232, 209, 206, 27);
		frame.getContentPane().add(department_head);
		
		//Format the date
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		JButton btnNewButton = new JButton("");
		btnNewButton.setBackground(new Color(250, 240, 230));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		DefaultTableModel model  = new DefaultTableModel();
		
		// Type
		types = new JComboBox(type);
		types.setBackground(new Color(255, 245, 238));
		types.setBounds(653, 208, 122, 28);
		frame.getContentPane().add(types);
		
		JLabel lblType = new JLabel("Type: ");
		lblType.setForeground(new Color(0, 51, 51));
		lblType.setFont(new Font("Book Antiqua", Font.BOLD, 14));
		lblType.setBounds(653, 184, 56, 15);
		frame.getContentPane().add(lblType);

		JTable table  = new JTable();
				
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		table.setBackground(Color.WHITE);
		table.setForeground(Color.BLACK);
		table.setSelectionBackground(Color.RED);
		table.setGridColor(Color.RED);
		table.setSelectionForeground(Color.WHITE);
		table.setFont(new Font("Tahoma", Font.PLAIN, 17));
		table.setRowHeight(30);
		table.setAutoCreateRowSorter(true);
		
		JScrollPane pane = new JScrollPane(table);
		pane.setForeground(Color.RED);
		pane.setBackground(Color.WHITE);
		pane.setBounds(10,247,508,293);
		frame.getContentPane().add(pane);
		
		lblTimelbl = new JLabel("Time");
		lblTimelbl.setForeground(new Color(0, 51, 51));
		lblTimelbl.setFont(new Font("Lucida Fax", Font.BOLD, 15));
		lblTimelbl.setBounds(473, 20, 62, 14);
		frame.getContentPane().add(lblTimelbl);
		
		lblDate = new JLabel("Date");
		lblDate.setForeground(new Color(0, 51, 51));
		lblDate.setFont(new Font("Lucida Fax", Font.BOLD, 15));
		lblDate.setBounds(662, 20, 40, 14);
		frame.getContentPane().add(lblDate);
		
		lblDate_1 = new JLabel("Date");
		lblDate_1.setForeground(new Color(0, 51, 51));
		lblDate_1.setFont(new Font("Lucida Fax", Font.BOLD, 18));
		lblDate_1.setBounds(662, 45, 182, 14);
		frame.getContentPane().add(lblDate_1);
		
		employeeIcon = new JLabel("");
		employeeIcon.setBounds(10, 11, 96, 96);
		Image imgEmployee = new ImageIcon(this.getClass().getResource("administrator-icon.png")).getImage();
		employeeIcon.setIcon(new ImageIcon(imgEmployee));
		frame.getContentPane().add(employeeIcon);
		
		JButton time_in_btn = new JButton("Add your schedule");
		time_in_btn.setForeground(new Color(255, 255, 255));
		time_in_btn.setFont(new Font("Sitka Text", Font.BOLD, 14));
		time_in_btn.setBackground(new Color(135, 206, 235));
		time_in_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedTypes = types.getSelectedItem().toString();
				String selectedDays = days.getSelectedItem().toString();
				
				Connection con = connect();
				
				try {
					String query_consultation = "select * from consultation";
					Statement st  = con.createStatement();
					ResultSet rs = st.executeQuery(query_consultation);
					
					while(rs.next()) {
						if(rs.getString("id").isEmpty()) {
							System.out.print("hello");
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

				if(selectedTypes == "class") {
					saveClassTbl();
				}else if(selectedTypes == "consultation") {
					saveConsultationTable();
				}else if(selectedTypes == "related activities") {
					saveRelativeActivityTable();
				}else if(selectedTypes == "others") {
					saveOthersTable();
				}	
				
				// insert inputed data to the table
				row[0] = selectedDays;
				row[1] = time_in.getText();
				row[2] = time_out.getText(); 
				row[3] = selectedTypes;
				model.addRow(row);
			}
		});
		time_in_btn.setBounds(605, 329, 170, 57);
		frame.getContentPane().add(time_in_btn);
		
		time_in = new JTextArea();
		time_in.setBackground(SystemColor.inactiveCaption);
		time_in.setBounds(639, 271, 80, 27);
		frame.getContentPane().add(time_in);
		
		time_out = new JTextArea();
		time_out.setBackground(SystemColor.inactiveCaption);
		time_out.setBounds(742, 271, 80, 27);
		frame.getContentPane().add(time_out);
		
		days = new JComboBox(day);
		days.setBackground(new Color(255, 245, 238));
		days.setBounds(521, 208, 113, 27);
		frame.getContentPane().add(days);
		
		id_number = new JTextField();
		id_number.setColumns(10);
		id_number.setBackground(new Color(255, 245, 238));
		id_number.setBounds(10, 146, 206, 27);
		frame.getContentPane().add(id_number);
		
		JLabel idNumberlbl = new JLabel("ID number:");
		idNumberlbl.setForeground(new Color(0, 51, 51));
		idNumberlbl.setFont(new Font("Book Antiqua", Font.BOLD, 14));
		idNumberlbl.setBounds(10, 120, 80, 15);
		frame.getContentPane().add(idNumberlbl);
		
		JLabel lblDay = new JLabel("Day: ");
		lblDay.setForeground(new Color(0, 51, 51));
		lblDay.setFont(new Font("Book Antiqua", Font.BOLD, 14));
		lblDay.setBounds(523, 185, 56, 15);
		frame.getContentPane().add(lblDay);
		
		month = new JMonthChooser();
		month.setBounds(451, 145, 140, 24);
		frame.getContentPane().add(month);
		
		year = new JYearChooser();
		year.setBounds(583, 145, 70, 24);
		frame.getContentPane().add(year);
		
		JLabel lblTimeIn = new JLabel("Time In");
		lblTimeIn.setForeground(new Color(0, 51, 51));
		lblTimeIn.setFont(new Font("Book Antiqua", Font.BOLD, 14));
		lblTimeIn.setBounds(639, 247, 56, 15);
		frame.getContentPane().add(lblTimeIn);
		
		JLabel lblTimeOut = new JLabel("Time Out");
		lblTimeOut.setForeground(new Color(0, 51, 51));
		lblTimeOut.setFont(new Font("Book Antiqua", Font.BOLD, 14));
		lblTimeOut.setBounds(742, 247, 73, 15);
		frame.getContentPane().add(lblTimeOut);
		
		lblMonth = new JLabel("Month:");
		lblMonth.setForeground(new Color(0, 51, 51));
		lblMonth.setFont(new Font("Book Antiqua", Font.BOLD, 14));
		lblMonth.setBounds(473, 120, 80, 15);
		frame.getContentPane().add(lblMonth);
		
		lblYear = new JLabel("Year:");
		lblYear.setForeground(new Color(0, 51, 51));
		lblYear.setFont(new Font("Book Antiqua", Font.BOLD, 14));
		lblYear.setBounds(585, 120, 56, 15);
		frame.getContentPane().add(lblYear);
		
		holiday = new JTextArea();
		holiday.setBackground(SystemColor.inactiveCaption);
		holiday.setBounds(678, 146, 40, 27);
		frame.getContentPane().add(holiday);
		
		JLabel lblHolidays = new JLabel("Holidays: ");
		lblHolidays.setForeground(new Color(0, 51, 51));
		lblHolidays.setFont(new Font("Book Antiqua", Font.BOLD, 14));
		lblHolidays.setBounds(682, 120, 73, 15);
		frame.getContentPane().add(lblHolidays);
		
		JButton add_btn_1 = new JButton("Add");
		add_btn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

					saveClassTbl();
					saveConsultationTable();
					saveRelativeActivityTable();
					saveOthersTable();
//					saveTotalHrs();
					holiday.setText("");
					JOptionPane.showMessageDialog(null, "Holiday Successfully Added");
			}
		});
		add_btn_1.setBounds(726, 145, 56, 27);
		frame.getContentPane().add(add_btn_1);
		
		preview = new JButton("View");
		preview.setFont(new Font("Sitka Text", Font.BOLD, 14));
		preview.setForeground(new Color(255, 255, 255));
		preview.setBackground(new Color(135, 206, 235));
		preview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			if (name.getText().trim().isEmpty() || department.getText().equals("") || department_head.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter all data!");
					// clear all text fields
					name.setText("");
					department.setText("");
					department_head.setText("");
			}
				else {
				Preview preview = new Preview();
				preview.showWindow();
				preview.showClass();
				preview.showConsultation();
				preview.showRelated();
				preview.showOthers();
				saveEmployeeDetails();
			}
			}
		});
		preview.setBounds(605, 397, 170, 50);
		frame.getContentPane().add(preview);
		
		id_type = new JTextArea();
		id_type.setBackground(SystemColor.inactiveCaption);
		id_type.setBounds(538, 271, 62, 27);
		frame.getContentPane().add(id_type);
		
		JLabel dayNum = new JLabel("Day No.");
		dayNum.setFont(new Font("Dialog", Font.BOLD, 14));
		lblTimeIn.setForeground(new Color(0, 51, 51));
		dayNum.setBounds(528, 247, 79, 16);
		frame.getContentPane().add(dayNum);
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
	
	public void saveEmployeeDetails() {
		
		Connection con = connect();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
			String months = sdf.format(new Date());
			SimpleDateFormat sdf_year = new SimpleDateFormat("YYYY");
			String years = sdf_year.format(new Date());
		    
			String query = "insert into employee (id_number, name, department, department_head, month, year) values(?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, id_number.getText());
			ps.setString(2, name.getText());
			ps.setString(3, department.getText());
			ps.setString(4, department_head.getText());
			ps.setString(5, months);
			ps.setString(6, years);

			ps.execute();

		}catch(Exception err) {
			System.out.print("error : " + err);
		}
	}
	
	public void saveClassTbl() {
		Connection con = connect();
		try{
			String hrs = null;
			 String timeIn = time_in.getText();
			 String timeOut = time_out.getText();
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a").withLocale(Locale.ENGLISH);
			 LocalTime in = LocalTime.parse(timeIn, formatter);
		     LocalTime out = LocalTime.parse(timeOut, formatter);
		     int hoursDiff = (out.getHour() - in.getHour()),
		         minsDiff  = (int)Math.abs(out.getMinute() - in.getMinute()),
		         secsDiff  = (int)Math.abs(out.getSecond() - in.getSecond());
		     hrs = hoursDiff+":"+minsDiff+":"+secsDiff;
		     
			if(holiday.getText().equals("")) {
				
			    System.out.print(hrs);
			    
			    
//				String query = "insert into class (time_in, time_out, hrs) values(?,?,?)";
				String query = "update class set time_in = ?, time_out = ?, hrs = ? where id = ?";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, time_in.getText());
				ps.setString(2, time_out.getText());
				ps.setString(3, hrs.toString());
				ps.setString(4, id_type.getText());
				ps.execute();
			}else{
				String query = "update class set time_in = ?, time_out = ?, hrs = ? where id = ?";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, " --- ");
				ps.setString(2, " --- ");
				ps.setString(3, " --- ");
				ps.setString(4, holiday.getText());
				ps.execute();
			}
			
		}catch(Exception err) {
			System.out.print("error : " + err);
		}
	}
	
	public void saveConsultationTable() {
		Connection con = connect();
		try{
			String hrs = null;
			 String timeIn = time_in.getText();
			 String timeOut = time_out.getText();
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a").withLocale(Locale.ENGLISH);
			 LocalTime in = LocalTime.parse(timeIn, formatter);
		     LocalTime out = LocalTime.parse(timeOut, formatter);
		     int hoursDiff = (out.getHour() - in.getHour()),
		         minsDiff  = (int)Math.abs(out.getMinute() - in.getMinute()),
		         secsDiff  = (int)Math.abs(out.getSecond() - in.getSecond());
		     hrs = hoursDiff+":"+minsDiff+":"+secsDiff;
		     
			if(holiday.getText().equals("")) {
				
			    System.out.print(hrs);
			    
//				String query = "insert into consultation (time_in, time_out, hrs) values(?,?,?)";
				String query = "update consultation set time_in = ?, time_out = ?, hrs = ? where id = ?";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, time_in.getText());
				ps.setString(2, time_out.getText());
				ps.setString(3, hrs.toString());
				ps.setString(4, id_type.getText());


				ps.execute();
			}else {
				String query = "update consultation set time_in = ?, time_out = ?, hrs = ? where id = ?";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, " --- ");
				ps.setString(2, " --- ");
				ps.setString(3, " --- ");
				ps.setString(4, holiday.getText());
				ps.execute();
			}
		}catch(Exception err) {
			System.out.print("error : " + err);
		}
	}
	
	public void saveRelativeActivityTable() {
		Connection con = connect();
		try{
			String hrs = null;
			 String timeIn = time_in.getText();
			 String timeOut = time_out.getText();
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a").withLocale(Locale.ENGLISH);
			 LocalTime in = LocalTime.parse(timeIn, formatter);
		     LocalTime out = LocalTime.parse(timeOut, formatter);
		     int hoursDiff = (out.getHour() - in.getHour()),
		         minsDiff  = (int)Math.abs(out.getMinute() - in.getMinute()),
		         secsDiff  = (int)Math.abs(out.getSecond() - in.getSecond());
		     hrs = hoursDiff+":"+minsDiff+":"+secsDiff;

			if(holiday.getText().equals("")) {
				
			    System.out.print(hrs);

//				String query = "insert into related (time_in, time_out, hrs) values(?,?,?)";
				String query = "update related set time_in = ?, time_out = ?, hrs = ? where id = ?";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, time_in.getText());
				ps.setString(2, time_out.getText());
				ps.setString(3, hrs.toString());
				ps.setString(4, id_type.getText());

				ps.execute();
			}else {
				String query = "update related set time_in = ?, time_out = ?, hrs = ? where id = ?";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, " --- ");
				ps.setString(2, " --- ");
				ps.setString(3, " --- ");
				ps.setString(4, holiday.getText());
				ps.execute();
			}
		}catch(Exception err) {
			System.out.print("error : " + err);
		}
	}
	
	public void saveOthersTable() {
		Connection con = connect();
		try{
			String hrs = null;
			 String timeIn = time_in.getText();
			 String timeOut = time_out.getText();
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a").withLocale(Locale.ENGLISH);
			 LocalTime in = LocalTime.parse(timeIn, formatter);
		     LocalTime out = LocalTime.parse(timeOut, formatter);
		     int hoursDiff = (out.getHour() - in.getHour()),
		         minsDiff  = (int)Math.abs(out.getMinute() - in.getMinute()),
		         secsDiff  = (int)Math.abs(out.getSecond() - in.getSecond());
		     hrs = hoursDiff+":"+minsDiff+":"+secsDiff;

			if(holiday.getText().equals("")) {
				
			    System.out.print(hrs);
//			    String join = "select class.hrs, related.hrs, others.hrs, consultation.hrs";
//				String query = "insert into others (time_in, time_out, hrs) values(?,?,?)";
				String query = "update others set time_in = ?, time_out = ?, hrs = ? where id = ?";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, time_in.getText());
				ps.setString(2, time_out.getText());
				ps.setString(3, hrs.toString());
				ps.setString(4, id_type.getText());
				ps.execute();
			}else {
				String query = "update others set time_in = ?, time_out = ?, hrs = ? where id = ?";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, "---");
				ps.setString(2, " --- ");
				ps.setString(3, " --- ");
				ps.setString(4, holiday.getText());
				ps.execute();
			}
		}catch(Exception err) {
			System.out.print("error : " + err);
		}
	}
}
