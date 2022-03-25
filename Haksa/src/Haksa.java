import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Haksa extends JFrame {
	JTextField tfId=null; //btnLIst(��ư),taList(���̺�),cbFruits(�޺�).ckb(üũ�ڽ�)
	JTextField tfName=null;
	JTextField tfDepartment=null;
	JTextField tfAddress=null;
	JTextArea taList=null;
	JButton btnSave=null; //insert -> Create
	JButton btnList=null; //select -> Read
	JButton btnModify=null;//update ���� 
	JButton btnRemove=null;//delete ����
		//CRUD ��ư ���� ���
	JButton btnSearch=null; //�˻���ư
	
	
JMenuItem menuItem1=null;


DefaultTableModel model=null;//���̺� ���� ������
JTable table=null;//���̺�

public Haksa() {
	this.setTitle("�л����");
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	this.setLayout(new FlowLayout());
	
	
	
	JMenuBar mb=new JMenuBar();
	JMenu menu1=new JMenu("�л�����");
	this.menuItem1=new JMenuItem("�л�����");
	menu1.add(this.menuItem1);
	mb.add(menu1); 
	this.setJMenuBar(mb);
	
	
	
	
	this.add(new JLabel("�й�"));
	this.tfId=new JTextField(13);
	this.add(this.tfId);
		
	this.btnSearch=new JButton("Search");
	this.add(this.btnSearch);
	this.btnSearch.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				//oracle jdbc����̹� �ε�
				Class.forName("oracle.jdbc.driver.OracleDriver");// jdbc driver load
				//Connection
				Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ora_user","hong");// ����
				System.out.println("����Ϸ�");
				
				Statement stmt=conn.createStatement();
								
				ResultSet rs= stmt.executeQuery("select*from student where id='"+tfId.getText()+"'");
				
				model.setNumRows(0);					
				while(rs.next()) {
				
					String[] row=new String[4];//�÷��� ������ 4
					row[0]=rs.getString("id");
					row[1]=rs.getString("name");
					row[2]=rs.getString("dept");
					row[3]=rs.getString("address");

					model.addRow(row);
					
					tfId.setText(rs.getString("id"));
					tfName.setText(rs.getString("name"));
					tfDepartment.setText(rs.getString("dept"));
					tfAddress.setText(rs.getString("address"));
										
					
				}
				
				rs.close();
				stmt.close();
				conn.close();
				
			}
			catch(Exception e1) {
				e1.printStackTrace();
			}
			
		}});
	
	this.add(new JLabel("�̸�"));
	this.tfName =new JTextField(20);
	this.add(this.tfName);
	
	this.add(new JLabel("�а�"));
	this.tfDepartment=new JTextField(20);
	this.add(this.tfDepartment);
	
	this.add(new JLabel("�ּ�"));
	this.tfAddress=new JTextField(20);
	this.add(this.tfAddress);
	
	
	//this.taList= new JTextArea(17,23);
	//this.add(new JScrollPane(this.taList));
	
    // �÷��� �߰�
    String colName[]={"�й�","�̸�","�а�","�ּ�"};
	this.model=new DefaultTableModel(colName,0);
	this.table = new JTable(this.model);
	this.table.setPreferredScrollableViewportSize(new Dimension(250,300));//���̺� ũ�� 250px * 300px
	this.table.addMouseListener(new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			table=(JTable)e.getComponent();//Ŭ���� ���̺� ���ϱ�
		    model=(DefaultTableModel)table.getModel();//���̺��� �� ���ϱ�
		    
		  // tfId.setText((String)table.getValueAt(table.getSelectedRow(), 0)); //�й�
		   String no=(String)model.getValueAt(table.getSelectedRow(), 0);//�й�
		   tfId.setText(no);
		   
		    String name=(String)model.getValueAt(table.getSelectedRow(), 1); //�̸�
		    tfName.setText(name);

		    String dept=(String)model.getValueAt(table.getSelectedRow(), 2); //�а�
		    tfDepartment.setText(dept);

		}

		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		});
	
	JScrollPane sp=new JScrollPane(this.table);
	
	this.add(sp);
	// �Ǵ� 	JScrollPane sp=this.add(new JScrollPane(table));
	
	this.btnSave=new JButton("���");
	this.add(this.btnSave);
	this.btnSave.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				//oracle jdbc����̹� �ε�
				Class.forName("oracle.jdbc.driver.OracleDriver");// jdbc driver load
				//Connection
				Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ora_user","hong");// ����
				System.out.println("����Ϸ�");
				
				Statement stmt=conn.createStatement();
				
				stmt.executeUpdate("insert into student values('"+tfId.getText()+"','"+tfName.getText()+"','"+tfDepartment.getText()+"','"+tfAddress.getText()+"')");//������ ���� ���� ���� ���ϵ�
				//stmt.executeUpdate("update student set dept='���ڰ���' where id='1234567'");
				//stmt.executeUpdate("delete from student where id='1234567'");
						
				ResultSet rs= stmt.executeQuery("select*from student");
				
				model.setNumRows(0);	
				
				while(rs.next()) {
					String[] row=new String[4];//�÷��� ������ 4
					row[0]=rs.getString("id");
					row[1]=rs.getString("name");
					row[2]=rs.getString("dept");
					row[3]=rs.getString("address");
					
					model.addRow(row);
					
				}
				
				rs.close();
				stmt.close();
				conn.close();
				
			}
			catch(Exception e1) {
				e1.printStackTrace();
			}
			
		}});
	
	this.btnList=new JButton("���");
	this.btnList.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				//oracle jdbc����̹� �ε�
				Class.forName("oracle.jdbc.driver.OracleDriver");// jdbc driver load
				//Connection
				Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ora_user","hong");// ����
				System.out.println("����Ϸ�");
				
				Statement stmt=conn.createStatement();
				
				//stmt.executeUpdate("insert into student values('11111','ȫ�浿','ö�а�')");//������ ���� ���� ���� ���ϵ�
				//stmt.executeUpdate("update student set dept='���ڰ���' where id='1234567'");
				
				
				ResultSet rs= stmt.executeQuery("select*from student");
				
				model.setNumRows(0);
				
				while(rs.next()) {
					String[] row=new String[4];//�÷��� ������ 4
					row[0]=rs.getString("id");
					row[1]=rs.getString("name");
					row[2]=rs.getString("dept");
					row[3]=rs.getString("address");
					
					model.addRow(row);
					
				}
				
				rs.close();
				stmt.close();
				conn.close();
				
			}
			catch(Exception e1) {
				e1.printStackTrace();
			}
			
		}});
	
	this.add(this.btnList);
	this.btnModify=new JButton("����");
	this.btnModify.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				//oracle jdbc����̹� �ε�
				Class.forName("oracle.jdbc.driver.OracleDriver");// jdbc driver load
				//Connection
				Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ora_user","hong");// ����
				System.out.println("����Ϸ�");
				
				Statement stmt=conn.createStatement();
				
				stmt.executeUpdate("update student set name='"+tfName.getText()+"', dept='"+tfDepartment.getText()+"' where id='"+tfId.getText()+"'");
				
					
				ResultSet rs= stmt.executeQuery("select*from student");
			
				model.setRowCount(0); //����ʱ�ȭ
				while(rs.next()) {
					String[] row=new String[4];//�÷��� ������ 4
					row[0]=rs.getString("id");
					row[1]=rs.getString("name");
					row[2]=rs.getString("dept");
					row[3]=rs.getString("address");
					
					model.addRow(row);
					
					
				}
				
				rs.close();
				stmt.close();
				conn.close();
				
			}
			catch(Exception e1) {
				e1.printStackTrace();
			}
			
		}});
	
	
	
	
	this.add(this.btnModify);
	this.btnRemove=new JButton("����");
	this.btnRemove.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			int result=JOptionPane.showConfirmDialog(null, "�����Ͻðڽ��ϱ�?","Confirm",JOptionPane.YES_NO_OPTION);
			if(result==JOptionPane.YES_OPTION) {
				//����ó��
				
				try {
					//oracle jdbc����̹� �ε�
					Class.forName("oracle.jdbc.driver.OracleDriver");// jdbc driver load
					//Connection
					Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ora_user","hong");// ����
					System.out.println("����Ϸ�");
					
					Statement stmt=conn.createStatement();
					
					stmt.executeUpdate("delete from student where id='"+tfId.getText()+"'");
							
					ResultSet rs= stmt.executeQuery("select*from student");
					
				
					model.setNumRows(0);	
					while(rs.next()) {
						String[] row=new String[4];//�÷��� ������ 4
						row[0]=rs.getString("id");
						row[1]=rs.getString("name");
						row[2]=rs.getString("dept");
						row[3]=rs.getString("address");
						
						model.addRow(row);
						
					}
					
					rs.close();
					stmt.close();
					conn.close();
					
				}
				catch(Exception e1) {
					e1.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(null, "�����Ǿ����ϴ�.","Massage",JOptionPane.INFORMATION_MESSAGE);
			}
			
		}});
	this.add(this.btnRemove);
	
	this.setSize(300,550);
	this.setVisible(true);
	
	}
	
	public static void main(String[] args) {
		new Haksa();

	}

}
