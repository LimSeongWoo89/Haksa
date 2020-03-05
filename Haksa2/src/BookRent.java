import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class BookRent extends JPanel{

	Connection conn = null;
	Statement stmt = null;
	ResultSet rs =null;
	
	DefaultTableModel model;//=new DefaultTableModel(colName,0);
	// ǥ�� ������
	JTable table = null;
	String query;
	
	String[] dept={"��ü","��ǻ�ͽý���","��Ƽ�̵��","��ǻ�Ͱ���","ö��","����"};
	String colName[]={"�й�","�̸�","������","������"};
	public void list() {
		try {
			System.out.println("����Ǿ����ϴ�...");
			System.out.println(query);
			//select �� ����
			rs = stmt.executeQuery(query);
			while(rs.next()){
			      String[] row=new String[4];//�÷��� ������ 4
			      row[0]=rs.getString("id");
			      row[1]=rs.getString("name");
			      row[2]=rs.getString("title");
			      row[3]=rs.getString("rdate");
			      model.addRow(row);
			}			
		}catch(Exception ex)
		{
			ex.getStackTrace();
		}
	}
	
	 public BookRent(){
			DBconn db = new DBconn();
			conn = db.getConn();
			stmt = db.getStmt();
		 
			query = "select s.id, s.name, b.title,rdate"+
					 " from bookrent br, student2 s,books b " + 
			 		"where br.id=s.id " + 
			 		"and br.bookno = b.no";
		   
		    this.setLayout(null);//���̾ƿ�����. ���̾ƿ� ��� ����.
		   
		    JLabel l_dept=new JLabel("�а�");
		    
		    l_dept.setBounds(10, 10, 30, 20);
		    
		    add(l_dept);
		    
		    JComboBox cb_dept=new JComboBox(dept);
		    cb_dept.setBounds(45, 10, 100, 20);
		    add(cb_dept);
		    
		    
		    model=new DefaultTableModel(colName,0);
		    table = new JTable(model);
		    table.setPreferredScrollableViewportSize(new Dimension(300
		    		,200));
		    add(table);
		    JScrollPane sp=new JScrollPane(table);
		    sp.setBounds(10, 40, 365, 250);
		    add(sp); 
		    
		    cb_dept.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// ��� �ʱ�ȭ
					model.setNumRows(0);
					
					query = "select s.id, s.name, b.title,rdate"+
							 " from bookrent br, student2 s,books b " + 
					 		"where br.id=s.id " + 
					 		"and br.bookno = b.no";
					
					JComboBox cb = (JComboBox)e.getSource();

					int si= cb.getSelectedIndex();
					
					if(si==0) // ��ü
					{ query += " order by br.no"; }
					else if(si==1) // ��ǻ�ͽý���
					{ query += " and s.dept = '��ǻ�ͽý���' order by br.no"; }
					else if(si==2) // ��Ƽ�̵��
					{ query += " and s.dept = '��Ƽ�̵��' order by br.no"; }
					else if(si==3) // ��ǻ�Ͱ���
					{ query += " and s.dept = '��ǻ�Ͱ���' order by br.no"; }
					else if(si==4) // ö��
					{ query += " and s.dept = 'ö��' order by br.no"; }
					else if(si==5) // ����
					{ query += " and s.dept = '����' order by br.no"; }
					//�˻����
					list();
				}
			});
		    
		    setSize(400, 350);
		    //setVisible(true);
		    
		    
		    
		    //��ü���
		    list();
	 }
}
