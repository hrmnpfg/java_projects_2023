package prezentacja;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.*;

import obliczenia.*;
import rozgrywka.Gra;

public class Okno extends Frame
{
	private static final long serialVersionUID = 1L;
	
	Logger logger;
	
	private Gra gra;
	
	private Label zakresStr = new Label();
	
	private TextField licznik = new TextField(15);
	private TextField mianownik = new TextField(15);
	private Button propozycja = new Button("Propozycja");
	private Scrollbar wykorzystane = new Scrollbar(Scrollbar.HORIZONTAL);
	private Scrollbar zakres = new Scrollbar(Scrollbar.HORIZONTAL);
	private Button przerwanie = new Button("Przerwanie");
	private Button nowa = new Button("Nowa");
	private Button koniec = new Button("Koniec");
    
	private Label opisZakresu = new Label();
	private Label opisWykorzystane = new Label();
	private Label opisStan = new Label();
    
	private Label status = new Label();
    
    boolean ingame = false;
    
    private Okno się = this;
    
    double start;
    double end;
    
    GridBagLayout gridBagLayout = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
	
	private class Kill extends WindowAdapter
    {
        @Override
        public void windowClosing (WindowEvent ev)
        {
            Okno.this.dispose();
            logger.log(Level.INFO, "zamknięto okno przyciskiem X");
        }
    };
    
	public Okno(Logger logger)
    {	
		super("Gra w zgadywanie");
		this.gra = new Gra();
        setSize(800,400);
        setLocation(100,100);
        
		this.logger = logger;
		logger.log(Level.INFO, "wyświetlono okno");
        
        setLayout(gridBagLayout);

        initZakresStr();
        
        initLicznik();

        initMianownik();

        initPropozycja();

        initWykorzystane();

        initZakres();

        initPrzerwanie();

        initNowa();

        initKoniec();
        
        initStatus();
        
        initOpisWykorzystane();
        
        initOpisStan();
        
        initOpisZakresu();
        
        add(licznik);
        add(mianownik);
        add(propozycja);
        add(wykorzystane);
        add(zakres);
        add(zakresStr);
        add(przerwanie);
        add(nowa);
        add(koniec);
        add(status);
        add(opisZakresu);
        add(opisWykorzystane);
        add(opisStan);
        
        aktualizacja();
        addWindowListener(new Kill());
        setResizable(false);
        setVisible(true);
    }
	
	private Wymierna liczba()
	{
		int l, m;
		try
		{
			l = Integer.parseInt(this.licznik.getText());
			m = Integer.parseInt(this.mianownik.getText());
		}
		catch (Exception e)
		{
			return null;
		}
		return new Wymierna(l,m);
	}
	
	public void aktualizacja()
	{
		licznik.setVisible(ingame);
		mianownik.setVisible(ingame);
		przerwanie.setVisible(ingame);
		propozycja.setVisible(ingame);
		zakres.setVisible(ingame);
		wykorzystane.setVisible(ingame);
		status.setVisible(ingame);
		opisZakresu.setVisible(ingame);
	    opisWykorzystane.setVisible(ingame);
	    opisStan.setVisible(ingame);
      
		if (ingame)
		{        
			gbc.gridx = 1;
	        gbc.gridy = 3;
			gridBagLayout.setConstraints(nowa, gbc);
			
			gbc.gridx = 2;
	        gbc.gridy = 3;
			gridBagLayout.setConstraints(koniec, gbc);
		}
		else
		{
			zakresStr.setVisible(false);
			
			gbc.gridx = 0;
	        gbc.gridy = 0;
			gridBagLayout.setConstraints(nowa, gbc);
			
			gbc.gridx = 1;
	        gbc.gridy = 0;
			gridBagLayout.setConstraints(koniec, gbc);
		}
		revalidate();
        repaint();
	}
	
	void initLicznik()
	{
		gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gridBagLayout.setConstraints(licznik, gbc);
	}
	
	void initMianownik()
	{
		gbc.gridx = 1;
        gbc.gridy = 0;
        gridBagLayout.setConstraints(mianownik, gbc);
	}
	
	void initPropozycja()
	{
		propozycja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	Wymierna w;
            	try
            	{
            		w = liczba();
            	}
            	catch(Exception e)
            	{
            		return;
            	}
            	
            	if (w == null)
            	{
            		propozycja.setLabel("błąd danych");
            	}
            	else
            	{
            		propozycja.setLabel(liczba().toString());
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                propozycja.setLabel("Propozycja");
            }
        });
        propozycja.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e)
        	{
        		się.setTitle("Gra w zgadywanie");
        		Wymierna w;
        		int l;
        		try
        		{
        			w = liczba();
        			l = gra.zgadnij(w);
        			logger.log(Level.INFO, "wysłano propozycję: "+w);
        		}
        		catch (Exception e2)
        		{
            		logger.log(Level.WARNING, "błąd podczas wysyłania danych jako propozycji: ("+się.licznik.getText()+"), ("+się.mianownik.getText()+")");
        			się.setTitle("odczytana liczba: "+się.licznik.getText()+"/"+się.mianownik.getText()+", "+gra.sprawdzStan());
        			return;
        		}
        		się.setTitle("odczytana liczba: "+w+", "+gra.sprawdzStan());
        		wykorzystane.setValue(wykorzystane.getValue()+1);
        		
        		double liczba2 = (double)w.getLicznik() / (double)w.getMianownik();
        		if (l < 0)
        		{
        			if (start < liczba2)  start = liczba2;
        			opisStan.setText("Liczba jest większa");
        		}
        		else if (l > 0)
        		{
        			if (end > liczba2)  end = liczba2;
        			opisStan.setText("Liczba jest mniejsza");
        		}
        		else
        		{
        			opisStan.setText("Liczba jest równa");
        			logger.log(Level.INFO, "wygrano grę");
        			opisWykorzystane.setText("Wygrano grę");
        			return;
        		}
        		if (się.gra.przegrana())
        		{
        	        logger.log(Level.INFO, "przegrano grę, liczba była równa "+się.gra.getLiczba());

        		}
        		status.setText(String.format("%.2f", start) + " - " + String.format("%.2f", end));
        		opisWykorzystane.setText("Posostało: "+(wykorzystane.getMaximum()-wykorzystane.getValue()));
        	}
        });
        gbc.gridx = 2;
        gbc.gridy = 0;
        gridBagLayout.setConstraints(propozycja, gbc);
	}
	
	void initWykorzystane()
	{
		gbc.gridx = 0;
	    gbc.gridy = 2;
	    wykorzystane.setMinimum(0);
	    wykorzystane.setValue(0);
	    wykorzystane.setMaximum(gra.getMaksIlośćPrób());
	    gridBagLayout.setConstraints(wykorzystane, gbc);
	}
	
	void initZakresStr()
	{
		gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 5, 5, 5);
        gridBagLayout.setConstraints(zakresStr, gbc);
	}
	
	void initZakres()
	{
		zakres.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				zakresStr.setText("zakres do użycia w następnej grze: " + zakres.getValue());
				zakresStr.setVisible(true);
				się.aktualizacja();
			}
        });

        zakres.setMinimum(5);
        zakres.setValue(5);
	    zakres.setMaximum(30);
		gbc.gridx = 2;
        gbc.gridy = 2;
        gridBagLayout.setConstraints(zakres, gbc);
	}
	
	void initPrzerwanie()
	{
		przerwanie.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e)
        	{
        		try
        		{
        			gra.rezygnuj();
        			logger.log(Level.INFO, "zrezygnowano z gry, liczba była równa "+się.gra.getLiczba());
        		}
        		catch (Exception e2)
        		{
        			logger.log(Level.WARNING, "próba rezygnacji z nieaktywnej gry");
        			się.setTitle(e2.toString());
        		}
        		ingame = false;
        		aktualizacja();
        	}
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gridBagLayout.setConstraints(przerwanie, gbc);
	}
	
	void initNowa()
	{
		nowa.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e)
        	{
        		gra.start(zakres.getValue());
                logger.log(Level.INFO, "rozpoczęto grę z zakresem: "+zakres.getValue());
        		ingame = true;
        		start = 1.0/zakres.getValue();
        		end = 1;
        		status.setText(String.format("%.2f", start) + " - " + String.format("%.2f", 1.0));
        		wykorzystane.setMaximum(gra.getMaksIlośćPrób());
        		wykorzystane.setValue(0);
        		zakresStr.setVisible(false);
        		opisWykorzystane.setText("Posostało: "+(wykorzystane.getMaximum()-wykorzystane.getValue()));
        		aktualizacja();
        	}
        });
        gbc.gridx = 1;
        gbc.gridy = 3;
        gridBagLayout.setConstraints(nowa, gbc);
	}
	
	void initKoniec()
	{
		koniec.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e)
        	{
        		Okno.this.dispose();
                logger.log(Level.INFO, "zamknięto okno przyciskiem Koniec");

        	}
        });
        gbc.gridx = 2;
        gbc.gridy = 3;
        gridBagLayout.setConstraints(koniec, gbc);
	}
	
	void initStatus()
	{
		gbc.gridx = 1;
        gbc.gridy = 2;
        status.setText(String.format("%.2f", start) + " - " + String.format("%.2f", end));
        gridBagLayout.setConstraints(status, gbc);
	}
	
	void initOpisZakresu()
	{
		gbc.gridx = 2;
        gbc.gridy = 1;
        opisZakresu.setText("Ustawianie zakresu dla następnej gry");
        gridBagLayout.setConstraints(opisZakresu, gbc);
	}
    
    void initOpisWykorzystane()
    {
    	gbc.gridx = 0;
        gbc.gridy = 1;
        opisWykorzystane.setText("Posostało: "+(int)(3*Math.log(zakres.getValue())));
        gridBagLayout.setConstraints(opisWykorzystane, gbc);
    }
    
    void initOpisStan()
    {
    	gbc.gridx = 1;
        gbc.gridy = 1;
        opisStan.setText("Liczba jest pomiędzy tymi wartościami");
        gridBagLayout.setConstraints(opisStan, gbc);
    }
}