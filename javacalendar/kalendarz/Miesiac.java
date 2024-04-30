package kalendarz;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.AbstractListModel;

public class Miesiac extends AbstractListModel<String> {

    private static final long serialVersionUID = 2425022978363348347L;
	private int rok;
    private int miesiac;

    public Miesiac(int miesiac, int rok) {
        this.rok = rok;
        this.miesiac = miesiac;
        while(this.miesiac >= 13)
        {
        	this.rok+=1;
        	this.miesiac-=12;
        }
        while(this.miesiac < 1)
        {
        	this.rok-=1;
        	this.miesiac+=12;
        }
    }

    public Miesiac() {
    	GregorianCalendar current = new GregorianCalendar();
    	rok = current.get(Calendar.YEAR);
    	miesiac = current.get(Calendar.MONTH)+1;
	}

	public int[] toArray()
    {
    	int[] result = new int[42];

        GregorianCalendar calendar = new GregorianCalendar();

        calendar.set(Calendar.YEAR, rok);
        calendar.set(Calendar.MONTH, miesiac - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
        if (dayOfWeek == 0) dayOfWeek+=7;
        
        int index = dayOfWeek-1;

        while (calendar.get(Calendar.MONTH) == miesiac - 1) {
        	int heck = calendar.get(Calendar.DAY_OF_MONTH);
            result[index++]=heck;
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return result;
	}
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        GregorianCalendar calendar = new GregorianCalendar();

        calendar.set(Calendar.YEAR, rok);
        calendar.set(Calendar.MONTH, miesiac - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
        if (dayOfWeek == 0) dayOfWeek+=7;
        
        for (int i = 1; i < dayOfWeek; i++) {
            result.append("    ");
        }

        while (calendar.get(Calendar.MONTH) == miesiac - 1) {
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                result.append(String.format("%4d\n", calendar.get(Calendar.DAY_OF_MONTH)));
            } else {
                result.append(String.format("%4d", calendar.get(Calendar.DAY_OF_MONTH)));
            }

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return result.toString();
    }

	@Override
	public int getSize() {
		GregorianCalendar calendar = new GregorianCalendar();

        calendar.set(Calendar.YEAR, rok);
        calendar.set(Calendar.MONTH, miesiac - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        
        int result = 0;
        while (calendar.get(Calendar.MONTH) == miesiac - 1) {
        	calendar.add(Calendar.DAY_OF_MONTH, 1);
            result+=1;
        }
        return result;
	}

	@Override
	public String getElementAt(int index) {
		GregorianCalendar calendar = new GregorianCalendar();

        calendar.set(Calendar.YEAR, rok);
        calendar.set(Calendar.MONTH, miesiac - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, index);
        String[] week = {
        		"Poniedziałek",
        		"Wtorek",
        		"Środa",
        		"Czwartek",
        		"Piątek",
        		"Sobota",
        		"Niedziela"
        };
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
        return new StringBuilder(week[(calendar.get(Calendar.DAY_OF_WEEK)+5)%7])
        		.append(" ")
        		.append(calendar.get(Calendar.DAY_OF_MONTH))
        		.append(" ")
        		.append(month[calendar.get(Calendar.MONTH)%12])
        		.toString();
	}

	public void setMiesiac(int miesiac)
	{
		this.miesiac = miesiac;
		while(this.miesiac >= 13)
        {
        	this.rok+=1;
        	this.miesiac-=12;
        }
        while(this.miesiac < 1)
        {
        	this.rok-=1;
        	this.miesiac+=12;
        }
		fireContentsChanged(listenerList, rok, miesiac);
	}
	
	public void setRok(int rok)
	{
		this.rok = rok;
		fireContentsChanged(listenerList, rok, miesiac);
	}
	
	public int getMiesiac() {
		return miesiac;
	}
	
	public int getYear()
	{
		return rok;
	}
}
