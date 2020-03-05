import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

	public class Student extends JPanel implements ActionListener{
		JTextField[] tf= new JTextField[4];
		
		String[] bs = {"등록", "목록","수정","삭제"};
		String tfs = "";

		JButton[] btn = new JButton[4];
		JLabel[] label = new JLabel[4];
		JTextArea taList = new JTextArea(5,23);
		JButton btn2 = new JButton("검색");
		
		String colName[]={"학번","이름","학과","주소"}; // 표에 출력할 컬럼명
		DefaultTableModel model;//=new DefaultTableModel(colName,0);
		// 표의 데이터
		JTable table = null;
		  

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs =null;
		
		String query;
		public void list() {
			
			try {
				
				System.out.println("연결되었습니다...");
				System.out.println(query);
				//select 문 실행
				rs = stmt.executeQuery(query);
			}catch(Exception ex)
			{
				ex.getStackTrace();
			}
		}
		
		public Student() {
			
			DBconn db = new DBconn();
			conn = db.getConn();
			stmt = db.getStmt();
			
			model=new DefaultTableModel(colName,0);
			table = new JTable(model);
			  table.setPreferredScrollableViewportSize(new Dimension(280,150));
			  
			  
			for(int i = 0; i<tf.length;i++) {
				if(i ==0)
					tf[i] =new JTextField(14);
				else
					tf[i] =new JTextField(20);
				label[i] = new JLabel(colName[i]);
				btn[i] = new JButton(bs[i]);
			}
						
			

			this.setLayout(new FlowLayout());
			
			for(int i = 0; i<tf.length;i++) {
				this.add(label[i]);
				if(i==0) {
					this.add(tf[i]);
					this.add(btn2);
				}
				else
					this.add(tf[i]);
			}
			
			

			this.add(new JScrollPane(table));
			
			table.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					table = (JTable)e.getComponent();
					model=(DefaultTableModel)table.getModel();
					String id = (String)model.getValueAt(table.getSelectedRow(),0 ); // id
					String name = (String)model.getValueAt(table.getSelectedRow(),1 ); // name
					String dept = (String)model.getValueAt(table.getSelectedRow(),2 ); // dept
					String address = (String)model.getValueAt(table.getSelectedRow(),3 ); // address
					tf[0].setText(id);
					tf[1].setText(name);
					tf[2].setText(dept);
					tf[3].setText(address);
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
			
			for(int i = 0; i<btn.length;i++) {
				this.add(btn[i]);
				btn[i].addActionListener(this);
			}
			btn2.addActionListener(this);
			
			  
			
			setSize(280,400);
			setVisible(true);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton) e.getSource();
			int result ;

			
			
			// 목록 초기화
			model.setNumRows(0);
			
			
			//Button
			String btnS = btn.getText();
			switch(btnS){
			case "검색" :
				try {
				
					rs = stmt.executeQuery("select * from student2 where id = '"+tf[0].getText()+"'");
					while(rs.next()) {
						String[] row = new String[4];
						row[0]=rs.getString("id");
						row[1]=rs.getString("name");
						row[2]=rs.getString("dept");
						row[3]=rs.getString("address");
						model.addRow(row);

						
						tf[1].setText(rs.getString("name"));
						tf[2].setText(rs.getString("dept"));
						tf[3].setText(rs.getString("address"));
					}

				}catch(Exception ex) {
					ex.printStackTrace();
				}
				break;
			case "등록" : 
				////////공백 체크//////////
				StringTokenizer[] st =new StringTokenizer[4];
				int[] cnt = new int[4]; 
				for(int i =0; i<st.length;i++)
				{
					st[i]= new StringTokenizer(tf[i].getText()," ");
					cnt[i] = st[i].countTokens();
				}
				
				for(int i = 0; i<tf.length;i++)
				{
					if(cnt[i]==0)
					{	JOptionPane.showMessageDialog(null, "공백이 있어 등록이 불가능합니다.","잘못된 입력입니다",JOptionPane.ERROR_MESSAGE);
						break; // 공백이 있으면 스톱
					}
					else {
						if(i==tf.length-1)
						{
							try {
								stmt.executeUpdate("insert into student2(id,name,dept,address) values('"+tf[0].getText()+"','"+tf[1].getText()+"','"+tf[2].getText()+"','"+tf[3].getText()+"')");
								for(int j=0;j <tf.length;j++)
									tf[j].setText("");
								
								}catch(Exception ex) {
									ex.printStackTrace();
								}
						}
					}
					tfs += tf[i].getText()+"";
					
				}
				

				System.out.println(tfs);
				showList();
				break;
			case "목록" :

				showList();

				break;
			case "수정" : 			
				try {
		
				stmt.executeUpdate("update student2 set name = '"+tf[1].getText()+"',dept='"+tf[2].getText()+"',address='"+tf[3].getText()+"' where id = '"+tf[0].getText()+"'");
				JOptionPane.showMessageDialog(null, "수정되었습니다.","수정 완료",JOptionPane.PLAIN_MESSAGE);
				showList();
			}catch(Exception ex) {
				ex.printStackTrace();
			}
				break;
			case "삭제" :
				result = JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?","Confirm",JOptionPane.YES_NO_OPTION);
				if(tf[0].equals(null)&&result == JOptionPane.YES_OPTION) 
				{ 
					showList();
					JOptionPane.showMessageDialog(null, "삭제할 데이터가 없습니다.","삭제 불가",JOptionPane.PLAIN_MESSAGE);
				
				}
				else {		
				
					if(result == JOptionPane.NO_OPTION) {
						showList();
						for(int j=0;j <tf.length;j++)
							tf[j].setText("");
					} 
					else if(result == JOptionPane.YES_OPTION) {
					
						try {
						
							stmt.executeUpdate("delete from student2 where id = '"+tf[0].getText()+"'");
						}catch(Exception ex) {
							ex.printStackTrace();
						}
							showList();
							JOptionPane.showMessageDialog(null, "삭제되었습니다.","삭제 완료",JOptionPane.PLAIN_MESSAGE);
						}
					else {}
				}
			}
			
		}
		public void showList() {
			try {				
				rs = stmt.executeQuery("select * from student2 order by id asc");
				while(rs.next()) {
					String[] row = new String[4];
					row[0]=rs.getString("id");
					row[1]=rs.getString("name");
					row[2]=rs.getString("dept");
					row[3]=rs.getString("address");
					model.addRow(row);
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
}
