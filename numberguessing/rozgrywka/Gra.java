package rozgrywka;

import obliczenia.*;

public class Gra 
{
    private int zakres;
    private Wymierna liczba;
    private int maksIlośćPrób;
	private int licznikPrób;
    
	private enum StanGry { AKTYWNA, WYGRANA, PODDANIE, PORAZKA, NIEAKTYWNA }
	private StanGry stan;

    public Gra() {
    	this.stan = StanGry.NIEAKTYWNA;
    }
    
    public int getMaksIlośćPrób() {
		return maksIlośćPrób;
	}

	public void setMaksIlośćPrób(int maksIlośćPrób) {
		if (stan == StanGry.AKTYWNA) {
            throw new IllegalStateException("Gra jest aktywna");
        }
		this.maksIlośćPrób = maksIlośćPrób;
	}

    public void start(int z) {
        if (z < 5 || z > 20) {
            throw new IllegalArgumentException("Zakres musi być między 5 a 20");
        }

        zakres = z;

        int licz, mian;
        //do {
            licz = (int) (Math.random() * zakres) + 1;
            mian = (int) (Math.random() * zakres) + 1;
        //} while (licz >= mian);

        this.liczba = new Wymierna(licz, mian);
        
        System.out.println(liczba);

        assert this.liczba.getLicznik() < this.liczba.getMianownik() : "Błąd: Licznik musi być mniejszy od mianownika.";

        this.maksIlośćPrób = (int) Math.ceil(3*Math.log(z))-1;
        
        this.licznikPrób = 0;
        this.stan = StanGry.AKTYWNA;
    }

    public int zgadnij(Wymierna propozycja) {
        if (stan != StanGry.AKTYWNA) 
        {
            throw new IllegalStateException("Gra nie jest aktywna");
        }
        if (propozycja == null)
        {
        	throw new IllegalArgumentException("Niepoprawna propozycja");
        }
        licznikPrób++;

        if (propozycja.equals(liczba)) 
        {
            stan = StanGry.WYGRANA;
        } 
        else if (licznikPrób >= maksIlośćPrób) 
        {
            stan = StanGry.PORAZKA;
        }
        return propozycja.compareTo(liczba);
    }

    public void rezygnuj() {
        if (stan != StanGry.AKTYWNA) {
            throw new IllegalStateException("Nie można zrezygnować z gry w tym stanie");
        }

        stan = StanGry.PODDANIE;
    }

    public boolean aktywna()
    {
    	return stan == StanGry.AKTYWNA;
    }
    
    public String sprawdzStan() {
        switch (stan) {
            case WYGRANA:
                return "Zwycięstwo!";
            case PODDANIE:
                return "Rezygnacja. Poprawna liczba to: " + liczba;
            case PORAZKA:
                return "Porażka. Poprawna liczba to: " + liczba;
            case NIEAKTYWNA:
            	return "Gra jeszcze się nie rozpoczęła.";
            default:
                return "Gra trwa. Liczba prób: " + licznikPrób;
        }
    }

	public String getLiczba() {
		return this.liczba.toString();
	}

	public boolean przegrana() {
		return stan == StanGry.PORAZKA;
	}
}