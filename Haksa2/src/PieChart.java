import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.*;

public class PieChart extends JPanel {
	private int [] data = new int[4];
	private int [] arcAngle = new int [4];
	private Color [] color = {Color.RED, Color.BLUE, Color.MAGENTA, Color.ORANGE};
	private String [] itemName = new String[4];
	//private JTextField [] tf = new JTextField [4];
	private ChartPanel chartPanel = new ChartPanel();
	
	static Connection conn = null;
	static Statement stmt = null;
	String sql = "select s.dept, count(br.no) from bookrent br, student2 s where br.id=s.id group by s.dept";
	ResultSet rs =null;
	
	
	public PieChart() {

		DBconn db  =new DBconn();
		
		conn = db.getConn();
		stmt = db.getStmt();
		
		this.setLayout(new BorderLayout());
		drawChart();
		this.add(new InputPanel(), BorderLayout.NORTH);
		this.add(chartPanel, BorderLayout.CENTER);
		
		this.setSize(550,550);
		setVisible(true);
		
		
	}
	
	private void drawChart() {
		System.out.println(3);
		int sum=0;
		int j=0;
		try{
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				System.out.print(rs.getString("count(br.no)"));
				data[j] = Integer.parseInt(rs.getString("count(br.no)"));
				itemName[j] = rs.getString("dept");
				sum+=data[j];
				j++;
			}
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
			if(sum == 0) return;

			for(int i=0; i<data.length; i++) 
				arcAngle[i]=(int)Math.round((double)(data[i])/(double)sum*360);
		
		

		
		chartPanel.repaint();
	}

	private class InputPanel extends JPanel {
		public InputPanel() {
			System.out.println(2);
			this.setBackground(Color.LIGHT_GRAY);
			
			
			for(int i=0; i<itemName.length; i++) {
				
				
				add(new JLabel(itemName[i]+" "+data[i]+" °Ç    "));
				
			}
		}
		

	}
	
	private class ChartPanel extends JPanel {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			System.out.println(1);
			int startAngle = 90;
			for(int i=0; i<data.length; i++) {
				g.setColor(color[i]);
				g.drawString(itemName[i]+" "+Math.round(arcAngle[i]*100./360.)+"%", 50+i*100, 20);
			}
			for(int i=0; i<data.length; i++) {
				g.setColor(color[i]);
				g.fillArc(150,50,200,200,startAngle, arcAngle[i]);
				startAngle = startAngle + arcAngle[i];
			}
		}	
	}
} 