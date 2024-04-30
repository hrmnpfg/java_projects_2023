package obliczenia;

public class Wymierna implements Comparable<Wymierna> {
    private int licznik, mianownik = 1;

    public Wymierna() 
    {
        this.licznik = 0;
        this.mianownik = 1;
    }

    public Wymierna(int n) 
    {
        this(n, 1);
    }

    public Wymierna(int k, int m) 
    {
        if (m == 0) 
        {
            throw new IllegalArgumentException("Mianownik nie może być równy 0");
        }

        if (m < 0) 
        {
            k = -k;
            m = -m;
        }

        int nwd = nwd(Math.abs(k), Math.abs(m));
        this.licznik = k / nwd;
        this.mianownik = m / nwd;
    }
    
    public int getLicznik() 
    {
        return licznik;
    }

    public int getMianownik() 
    {
        return mianownik;
    }

    private int nwd(int a, int b) 
    {
        if (b == 0) 
        {
            return a;
        }
        else 
        {
            return nwd(b, a % b);
        }
    }

    @Override
    public String toString() 
    {
        if (mianownik == 1)
        {
            return Integer.toString(licznik);
        }
        else
        {
            return licznik + "/" + mianownik;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Wymierna wymierna = (Wymierna) obj;

        return licznik == wymierna.licznik && mianownik == wymierna.mianownik;
    }

    @Override
    public int compareTo(Wymierna o) {
        int roznica = this.licznik * o.mianownik - o.licznik * this.mianownik;
        return (int) Math.signum(roznica);
    }

    public static Wymierna dodaj(Wymierna a, Wymierna b) 
    {
        int licznik = a.licznik * b.mianownik + b.licznik * a.mianownik;
        int mianownik = a.mianownik * b.mianownik;
        return new Wymierna(licznik, mianownik);
    }

    public static Wymierna odejmij(Wymierna a, Wymierna b) 
    {
        int licznik = a.licznik * b.mianownik - b.licznik * a.mianownik;
        int mianownik = a.mianownik * b.mianownik;
        return new Wymierna(licznik, mianownik);
    }

    public static Wymierna pomnoz(Wymierna a, Wymierna b) 
    {
        int licznik = a.licznik * b.licznik;
        int mianownik = a.mianownik * b.mianownik;
        return new Wymierna(licznik, mianownik);
    }

    public static Wymierna podziel(Wymierna a, Wymierna b) 
    {
        if (b.licznik == 0) 
        {
            throw new ArithmeticException("Dzielenie przez 0");
        }
        int licznik = a.licznik * b.mianownik;
        int mianownik = a.mianownik * b.licznik;
        return new Wymierna(licznik, mianownik);
    }
}