import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Haksa2 extends JFrame{
	JPanel panel = null; // container
	static Connection conn = null;
	static Statement stmt = null;

	
	public Haksa2() {
		DBconn db  =new DBconn();
		
		conn = db.getConn();
		stmt = db.getStmt();
		
		this.setTitle("학사관리");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar mb = new JMenuBar();
		JMenu menu1 = new JMenu("학생관리");
		JMenuItem student = new JMenuItem("학생목록");
		menu1.add(student);
		mb.add(menu1);
		
		this.setJMenuBar(mb);
		
		JMenu menu2 = new JMenu("도서관리");
		JMenuItem book =new JMenuItem("책목록");
		JMenuItem bookDep = new JMenuItem("대출목록");
		JMenuItem rentTotal = new JMenuItem("대출현황");
		menu2.add(book);
		menu2.add(bookDep);
		menu2.add(rentTotal);
		
		mb.add(menu2);
		
		student.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("학생목록");
				panel.removeAll(); // 모든 컴포넌트 삭제
				panel.revalidate(); // 다시 활성화
				panel.repaint(); // 다시그리기
				panel.add(new Student()); // 화면 생성
				panel.setLayout(null); // 레이아웃 없음
				setSize(300,450);
			}
		});
		
		bookDep.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("대출목록");
				BookRent br=new BookRent();
				panel.removeAll(); // 모든 컴포넌트 삭제
				panel.revalidate(); // 다시 활성화
				panel.repaint(); // 다시그리기
				panel.add(br); // 화면 생성
				panel.setLayout(null); // 레이아웃 없음
				setSize(400,450);
			}
		});
		rentTotal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("대출현황");
				panel.removeAll(); // 모든 컴포넌트 삭제
				panel.revalidate(); // 다시 활성화
				panel.repaint(); // 다시그리기
				panel.add(new PieChart());
				panel.setLayout(null); // 레이아웃 없음
				setSize(500,450);
			}
			
		});
		book.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("책 목록");
				panel.removeAll(); // 모든 컴포넌트 삭제
				panel.revalidate(); // 다시 활성화
				panel.repaint(); // 다시그리기
				panel.add(new Book());
				panel.setLayout(null); // 레이아웃 없음
				setSize(400,450);
			}
		});
		
		panel = new JPanel();
		//panel.setSize(200,400);
		add(panel);
		
		
		addWindowListener(new WindowListener() {

			
			@Override
			public void windowOpened(WindowEvent e) {}
			
			@Override
			public void windowIconified(WindowEvent e) {}
			
			@Override
			public void windowDeiconified(WindowEvent e) {}
			
			@Override
			public void windowDeactivated(WindowEvent e) {}
			
			@Override
			public void windowClosing(WindowEvent e) {
			
					db.closeSet();
					
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {}
			
			@Override
			public void windowActivated(WindowEvent e) {}
		});
		
		this.setBounds(700, 300, 400, 450);
		this.setVisible(true);
		
	}
	

	
	public static void main(String[] args) {
		new Haksa2();

	}

}
