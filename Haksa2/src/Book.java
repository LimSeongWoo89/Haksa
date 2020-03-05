import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Book extends JPanel{
	
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs =null;
	
	DefaultTableModel model;//=new DefaultTableModel(colName,0);
	// ǥ�� ������
	JTable table = null;
	String query;
	String sql;
	
	int index=0;
	String no;
	String bname;
	int ea=0;
	String sname;
	int cnt=0;
	String colName[]={"å no","������","����","å ����"};
	
	JButton btn1 = new JButton("�뿩");
	JButton btn2 = new JButton("�ݳ�");
	int[] fullea;
	
	// �뿩 ��¥ �� �뿩��ȣ
	Date d = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	String now = sdf.format(d);
	int nowcnt =0;
	
	public Book() {
		fullea = new int[7];
		DBconn db = new DBconn();
		conn = db.getConn();
		stmt = db.getStmt();
		setLayout(null);//���̾ƿ�����. ���̾ƿ� ��� ����.
		query ="select * from books";
	 
	    
		model=new DefaultTableModel(colName,0);
	    table = new JTable(model);
	    table.setPreferredScrollableViewportSize(new Dimension(300
	    		,200));
	    add(table);
	    JScrollPane sp=new JScrollPane(table);
	    sp.setBounds(10, 40, 365, 250);
	    add(sp); 
	    
	    add(btn1);
	    btn1.setBounds(70, 300, 80, 30);
	    add(btn2);
	    btn2.setBounds(220, 300, 80, 30);
	    
	    btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String h =JOptionPane.showInputDialog("�й��� �Է��ϼ���.");
				String sql2="select id from student2 where id='"+h+"'";
				try {
					
					rs=stmt.executeQuery(sql2);
					
					
					
					if(!rs.next()){
						System.out.println("�л�����");
						JOptionPane.showMessageDialog(null, "�й��� ��ġ���� �ʽ��ϴ�.","�뿩 �Ұ�",JOptionPane.PLAIN_MESSAGE);
					}
					else
					{	
						String aq=rs.getString("id");
					System.out.println(aq+no);
						if(ea != 0)
						{
							String bkrNo =null;
							do{
								nowcnt++; // �뿩��ȣ ����
								bkrNo =now+nowcnt;
								sql2 = "select no from bookRent where no='"+bkrNo+"'";
								rs=stmt.executeQuery(sql2);
								
								System.out.println(bkrNo);
							}while(rs.next());
							sql2 = "select id from bookRent where id='"+h+"' and bookno='"+no+"'";
							System.out.println(stmt.executeQuery(sql2));
							rs=stmt.executeQuery(sql2);
							if(!rs.next()) {
								System.out.println("�뿩 �ߺ� ����");
								ea--; // å�� �����
								sql2 = "insert into bookRent values('"+bkrNo+"','"+aq+"','"+no+"','"+now+"')";
								sql = "update books set ea="+ea+" where no='"+no+"'";
								
								stmt.executeUpdate(sql);
								stmt.execute(sql2);
							}else {
								System.out.println("�뿩 �ߺ�");
								JOptionPane.showMessageDialog(null, "�̹� �� å �ѱ��� �뿩�߽��ϴ�.","�뿩 �Ұ�",JOptionPane.PLAIN_MESSAGE);
								
								
							}
							
						}
						else
						{
							JOptionPane.showMessageDialog(null, "�뿩�� å�� �����ϴ�.","�뿩 �Ұ�",JOptionPane.PLAIN_MESSAGE);
						}
					}
				}catch(Exception s)
				{
					s.printStackTrace();
				}
					
				model.setNumRows(0); // ��� �ʱ�ȭ
				list();
			}
		});
		btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String h =JOptionPane.showInputDialog("�й��� �Է��ϼ���.");
				System.out.println("number"+no);
				String sql2="select id from bookRent where id='"+h+"' and bookno='"+no+"'";
				System.out.println(sql2);
				try {
					
						if(ea < fullea[index])
						{
							rs=stmt.executeQuery(sql2);
							if(!rs.next()){
								System.out.println("�й� �Ǵ� �뿩��ȣ����");
								JOptionPane.showMessageDialog(null, "�й� �Ǵ� �뿩��ȣ�� ��ġ���� �ʽ��ϴ�.","�뿩 �Ұ�",JOptionPane.PLAIN_MESSAGE);
							}
							else
							{	
							ea++;
							sql = "update books set ea="+ea+" where no='"+no+"'";
					
							stmt.executeUpdate(sql);
							
							sql2 ="delete from bookrent where id='"+h+"' and bookno='"+no+"'";
							stmt.execute(sql2);
							}
						}else
						{
							JOptionPane.showMessageDialog(null, "�ݳ��� å�� �����ϴ�.","�ݳ� �Ұ�",JOptionPane.PLAIN_MESSAGE);
						}
					
				}catch(Exception s)
				{
					s.printStackTrace();
				}
				
				model.setNumRows(0); // ��� �ʱ�ȭ
				list();

				
			}
		});
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				table = (JTable)e.getComponent();
				model=(DefaultTableModel)table.getModel();
				no = (String)model.getValueAt(table.getSelectedRow(),0 ); // åno
				bname = (String)model.getValueAt(table.getSelectedRow(),1 ); // ������
				sname = (String)model.getValueAt(table.getSelectedRow(),2 ); // ����
				ea =  Integer.parseInt((String) model.getValueAt(table.getSelectedRow(),3 )); // å ����
				index=table.getSelectedRow();
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}
			
		});
		list();
		
		
	    setSize(400, 350);
	    //setVisible(true);

	}
	
	public void list() {
		cnt=0;
		try {
			System.out.println("Book ���..");
			System.out.println(query);
			//select �� ����
			rs = stmt.executeQuery(query);
			
			while(rs.next()){
				
			      String[] row=new String[4];//�÷��� ������ 4
			      row[0]=rs.getString("no");
			      row[1]=rs.getString("title");
			      row[2]=rs.getString("author");
			      row[3]=rs.getString("ea");
			      fullea[cnt]=rs.getInt("fullea");
			      model.addRow(row);
			      
			      cnt++;
			}			
		}catch(Exception ex)
		{
			ex.getStackTrace();
		}
	}
}
