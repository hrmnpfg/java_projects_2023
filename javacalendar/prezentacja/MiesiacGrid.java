package prezentacja;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import kalendarz.Miesiac;

public class MiesiacGrid extends JPanel
{
	private static final long serialVersionUID = 259177560381693639L;
	private String[] month = {
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
	Miesiac miesiac;
	JPanel center;
	MiesiacGrid(Miesiac miesiac)
	{
		this.miesiac = miesiac;
		setLayout(new BorderLayout());
		add(new JLabel(month[miesiac.getMiesiac()-1]),BorderLayout.NORTH);
		setBorder(BorderFactory.createLineBorder(Color.black));
		update();
	}
	
	void setYear(int year)
	{
		miesiac.setRok(year);
		update();
	}
	
	void addListener(MouseListener l)
	{
		addMouseListener(l);
	}
	
	public void update()
	{
		if (center != null) remove(center);
		center = new JPanel();
		int[] list = miesiac.toArray();
		center.setLayout(new GridLayout(6,7));
		int index = 1;
		for(int i : list)
		{
			JLabel n;
			if (i == 0)
			{
				n = new JLabel();
			}
			else n = new JLabel(String.valueOf(i));
			index=function(index);
			if (index%7 == 1)
				n.setForeground(Color.RED);
			center.add(n);
		}
		add(center);
		repaint();
	}
	
	//C++ function
	int function(int c)
	{
		int i = 1;
		while (i != 0)
		{
			if ((c & i) == 0) break;
			i <<= 1;
		}
		if (i != 0)
		{
			c |= i;
		}
		else
		{
			c = ~c;
		}
		i >>= 1;
		while (i != 0)
		{
			c &= ~i;
			i >>= 1;
		}
		return c;
	}
}