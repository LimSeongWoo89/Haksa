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
		
		this.setTitle("�л����");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar mb = new JMenuBar();
		JMenu menu1 = new JMenu("�л�����");
		JMenuItem student = new JMenuItem("�л����");
		menu1.add(student);
		mb.add(menu1);
		
		this.setJMenuBar(mb);
		
		JMenu menu2 = new JMenu("��������");
		JMenuItem book =new JMenuItem("å���");
		JMenuItem bookDep = new JMenuItem("������");
		JMenuItem rentTotal = new JMenuItem("������Ȳ");
		menu2.add(book);
		menu2.add(bookDep);
		menu2.add(rentTotal);
		
		mb.add(menu2);
		
		student.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("�л����");
				panel.removeAll(); // ��� ������Ʈ ����
				panel.revalidate(); // �ٽ� Ȱ��ȭ
				panel.repaint(); // �ٽñ׸���
				panel.add(new Student()); // ȭ�� ����
				panel.setLayout(null); // ���̾ƿ� ����
				setSize(300,450);
			}
		});
		
		bookDep.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("������");
				BookRent br=new BookRent();
				panel.removeAll(); // ��� ������Ʈ ����
				panel.revalidate(); // �ٽ� Ȱ��ȭ
				panel.repaint(); // �ٽñ׸���
				panel.add(br); // ȭ�� ����
				panel.setLayout(null); // ���̾ƿ� ����
				setSize(400,450);
			}
		});
		rentTotal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("������Ȳ");
				panel.removeAll(); // ��� ������Ʈ ����
				panel.revalidate(); // �ٽ� Ȱ��ȭ
				panel.repaint(); // �ٽñ׸���
				panel.add(new PieChart());
				panel.setLayout(null); // ���̾ƿ� ����
				setSize(500,450);
			}
			
		});
		book.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("å ���");
				panel.removeAll(); // ��� ������Ʈ ����
				panel.revalidate(); // �ٽ� Ȱ��ȭ
				panel.repaint(); // �ٽñ׸���
				panel.add(new Book());
				panel.setLayout(null); // ���̾ƿ� ����
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
