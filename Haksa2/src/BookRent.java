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
	// 표의 데이터
	JTable table = null;
	String query;
	
	String[] dept={"전체","컴퓨터시스템","멀티미디어","컴퓨터공학","철학","수학"};
	String colName[]={"학번","이름","도서명","대출일"};
	public void list() {
		try {
			System.out.println("연결되었습니다...");
			System.out.println(query);
			//select 문 실행
			rs = stmt.executeQuery(query);
			while(rs.next()){
			      String[] row=new String[4];//컬럼의 갯수가 4
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
		   
		    this.setLayout(null);//레이아웃설정. 레이아웃 사용 안함.
		   
		    JLabel l_dept=new JLabel("학과");
		    
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
					// 목록 초기화
					model.setNumRows(0);
					
					query = "select s.id, s.name, b.title,rdate"+
							 " from bookrent br, student2 s,books b " + 
					 		"where br.id=s.id " + 
					 		"and br.bookno = b.no";
					
					JComboBox cb = (JComboBox)e.getSource();

					int si= cb.getSelectedIndex();
					
					if(si==0) // 전체
					{ query += " order by br.no"; }
					else if(si==1) // 컴퓨터시스템
					{ query += " and s.dept = '컴퓨터시스템' order by br.no"; }
					else if(si==2) // 멀티미디어
					{ query += " and s.dept = '멀티미디어' order by br.no"; }
					else if(si==3) // 컴퓨터공학
					{ query += " and s.dept = '컴퓨터공학' order by br.no"; }
					else if(si==4) // 철학
					{ query += " and s.dept = '철학' order by br.no"; }
					else if(si==5) // 수학
					{ query += " and s.dept = '수학' order by br.no"; }
					//검색목록
					list();
				}
			});
		    
		    setSize(400, 350);
		    //setVisible(true);
		    
		    
		    
		    //전체목록
		    list();
	 }
}
