package prezentacja;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import kalendarz.Miesiac;

public class Okno extends JFrame
{

	private static final long serialVersionUID = 5435337251905100280L;
	int currentYear;
	int currentMonth;
	protected JTabbedPane pane;
	protected MiesiacGrid[] years;
	protected Miesiac[] months;
	protected MiesiacList[] monthLists;
	String[] month = {
    		"Styczeń",
    		"Luty",
    		"Marzec",
    		"Kwiecień",
    		"Maj",
    		"Czerwiec",
    		"Lipiec",
    		"Sierpień",
    		"Wrzesień",
    		"Pażdziernik",
    		"Listopad",
    		"Grudzień"
    };
	JButton a;
	JSpinner b;
	JScrollBar c;
	
	public Okno()
	{
		setLayout(new BorderLayout());
		setTitle("Kalendarz");
		setSize(400,400);
		pane = new JTabbedPane();
		JPanel a1 = new JPanel();
		a1.setLayout(new GridLayout(4,3));
		Miesiac obecny = new Miesiac();
		currentYear = obecny.getYear();
		currentMonth = obecny.getMiesiac();
		years = new MiesiacGrid[12];
		for (int i = 1; i<=12; i++)
		{
			final int a = i;
			years[i-1] = new MiesiacGrid(new Miesiac(i, currentYear));
			years[i-1].addListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					currentMonth = a;
					update();
				}
			});
			a1.add(years[i-1]);
		}
		months = new Miesiac[3];
		
		months[0] = new Miesiac(currentMonth-1,currentYear);
		months[1] = new Miesiac(currentMonth,currentYear);
		months[2] = new Miesiac(currentMonth+1,currentYear);
		
		monthLists = new MiesiacList[3];
		
		monthLists[0] = new MiesiacList(months[0]);
		monthLists[1] = new MiesiacList(months[1]);
		monthLists[2] = new MiesiacList(months[2]);
		
		JPanel a2 = new JPanel();
			
		a2.add(monthLists[0]);
		a2.add(monthLists[1]);
		a2.add(monthLists[2]);
		
		a2.setLayout(new GridLayout());
		
		pane.addTab(String.valueOf(currentYear), a1);
		pane.addTab(month[currentMonth-1], a2);
		JToolBar bar = new JToolBar();
		add(bar, BorderLayout.SOUTH);
		
		a = new JButton("Poprzedni rok");
		bar.add(a);
		a.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentYear-=1;
				update();
			}
		});
		
		a = new JButton("Następny rok");
		bar.add(a);
		a.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentYear+=1;
				update();
			}
		});
		
		b = new JSpinner();
		b.setValue(currentYear);
		bar.add(b);
		b.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				currentYear = (int) b.getValue();
				update();
			}
		});
		
		c = new JScrollBar(JScrollBar.HORIZONTAL, 1, 0, 1, 12);
		bar.add(c);
		c.addAdjustmentListener(new AdjustmentListener() {
			@Override
		      public void adjustmentValueChanged(AdjustmentEvent e) {
		          currentMonth = c.getValue();
		          update();
		        }});
		add(pane, BorderLayout.CENTER);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	void update()
	{
		for (MiesiacGrid m : years)
		{
			m.setYear(currentYear);
			m.repaint();
		}
		
		int currentMonthCopy = currentMonth-1;
		for (Miesiac m : months)
		{
			m.setRok(currentYear);
			m.setMiesiac(currentMonthCopy++);
		}
		
		for (MiesiacList m : monthLists)
		{
			m.repaint();
		}
		b.setValue(currentYear);
		c.setValue(currentMonth);
		pane.setTitleAt(0, String.valueOf(currentYear));
		pane.setTitleAt(1, month[currentMonth-1]);
		repaint();
	}
}