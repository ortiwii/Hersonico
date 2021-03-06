package a.projectPackage;

import salbuespenak.*;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import kartak.Karta;
import kartak.ListaKartak;

public class Teklatua 
{
	//atributoak
	private static Teklatua nireTeklatua = null;
	private Scanner sc;
	//eraikitzailea
	private Teklatua ()
	{	
		sc = new Scanner(System.in);
	}
	public static Teklatua getNireTeklatua()
	{
		if (nireTeklatua == null)
		{
			nireTeklatua = new Teklatua ();
		}
		return Teklatua.nireTeklatua;
	}
	
	//gainontzeko metodoak
	public String Irakurri (String aurrekoMezua)
	{
		System.out.println(aurrekoMezua);
		System.out.print("	");
		String mezua = sc.next();
		return mezua;
	}
	public int OsoaIrakurri (String pAurrekoMezua)
	{
		System.out.println(pAurrekoMezua);
		int emaitza = sc.nextInt();
		return emaitza ;
    }
	public void itxaronEnterArte () 
	{
		
        System.out.println("\n	-- Sakatu // ENTER // jarraitzeko --");
        try 
        {
            System.in.read(new byte[2]);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
	}
	private void konprobatuEaZenbakiEgokiaDen (int irakurritakoZenb, int kont) throws ZenbakiaEzDaEgokiaSalbuespena
	{
		if ((irakurritakoZenb > kont)||(irakurritakoZenb < 0)) // Faltana si metes un numero negativo que salte exception.
		{
			throw new ZenbakiaEzDaEgokiaSalbuespena();
		}
	}
	public Karta irakurriAukera (String pAurrekoMezua, ListaKartak pAukerak) 
	{
		boolean salataria = false;
		Karta egungoKarta = null;
		while (!salataria)
		{
			try
			{
				System.out.println(pAurrekoMezua);
				if (pAukerak != null )
				{
					Iterator <Karta> itr = pAukerak.getIteradorea();
					int kont = 0;
					System.out.println("	"+kont+") Pasatu");
					while (itr.hasNext())
					{
						kont ++;
						egungoKarta = itr.next();
						System.out.print("	"+kont+") "); egungoKarta.imprimatu();
					}
					System.out.print("	");int aukeratutakoZenb = sc.nextInt();
					this.konprobatuEaZenbakiEgokiaDen(aukeratutakoZenb, kont);

					//dagoeneko aukera zein den badakigunez, prozesua errepikatuko dugu
						//baldin pasa duen
					if (aukeratutakoZenb == 0)
					{
						egungoKarta = null;
					}
						//baldin karta bat aukeratu duen
					else
					{
						egungoKarta = pAukerak.getPosizioHonetakoKarta(aukeratutakoZenb);
					}
						//loop-tik ateratzeko
					salataria = true;
				}
				else
				{
					System.out.println("	Ez dago aukeratu dezakezun kartarik");
					egungoKarta = null;
					salataria = true;
				}
				salataria = true;
			}
			catch (InputMismatchException e) 
			{
				System.out.println("	Ez duzu zenbaki oso bat sartu, berriro saiatu");
				sc.next();
			}
			catch (ZenbakiaEzDaEgokiaSalbuespena e)
			{
				System.out.println("	Ez duzu zenbaki egoki bat sartu, berriro saiatu");
			}
		}
		return egungoKarta;
	}
	public Karta irakurriEtsaiarenKarta (String mezua)
	{
		boolean salataria = false;
		Karta aukeratutakoKarta = null;
		while (!salataria)
		{
			try
			{
				int kont = 0;
//				Jokalaria jokEtsaia = Partida.getNirePartida().getJokalariEtsaia();
				if (Partida.getNirePartida().getJokalariEtsaiarenZelaikoKartak() != null || Partida.getNirePartida().getJokalariEtsaiarenZelaikoKartak().getLuzeera() != 0)
				{
					System.out.println(mezua);
					//Aqui vamos a comprobar si alguna de sus cartas tiene la habilidad diana
					if (Partida.getNirePartida().getJokalariEtsaiarenZelaikoKartak().getDianaDutenKartenLista() != null )//|| jokEtsaia.getNireZelaikoKartak().getDianaDutenKartenLisa().getLuzeera() != 0)
					{	//Hemen sartzen bada badakigu kartaren batek diana duela
						Iterator <Karta> itr = Partida.getNirePartida().getJokalariEtsaiarenZelaikoKartak().getDianaDutenKartenLista().getIteradorea();
						System.out.println("	0) Pasatu");
						while (itr.hasNext())
						{
							aukeratutakoKarta = itr.next();
							kont ++;
							System.out.print("	"+kont+") ");aukeratutakoKarta.imprimatu();
						}
						System.out.print("	");int zenb = sc.nextInt();
						this.konprobatuEaZenbakiEgokiaDen(zenb, kont);
						//Orain badakigu zein den gure aukera
						aukeratutakoKarta = Partida.getNirePartida().getJokalariEtsaiarenZelaikoKartak().getDianaDutenKartenLista().getPosizioHonetakoKarta(zenb);
						if (zenb == 0)
						{
							aukeratutakoKarta = null;
						}
					}
					else  
					{	//Hemen sartzen bada badakigu ez dagoela diana duten kartarik, hau da, edonori eraso diezaike
						System.out.print("	0) ");Partida.getNirePartida().imprimatuHeroiEtsaia();
						Iterator <Karta> itr = Partida.getNirePartida().getJokalariEtsaiarenZelaikoKartak().getIteradorea();
						while (itr.hasNext())
						{
							kont ++;
							aukeratutakoKarta = itr.next();
							System.out.print("	"+kont+") ");aukeratutakoKarta.imprimatu();
						}
						System.out.print("	");int zenb = sc.nextInt();
						this.konprobatuEaZenbakiEgokiaDen(zenb, kont);
						//Orain dagoeneko badakigu zein aukeratu duen
						if (zenb == 0)
						{
							aukeratutakoKarta = null;
						}
						else
						{
							aukeratutakoKarta = Partida.getNirePartida().getJokalariEtsaiarenZelaikoKartak().getPosizioHonetakoKarta(zenb);
						}
					}
				}
				else
				{
					System.out.println("Ez dago Kartarik zelaian, soilik heroiari eraso diezaiokezu");
					aukeratutakoKarta = null;
				}
				salataria = true;
			}
			catch (InputMismatchException e) 
			{
				System.out.println("	Ez duzu zenbaki oso bat sartu, berriro saiatu");
				sc.next();
			}
			catch (ZenbakiaEzDaEgokiaSalbuespena e)
			{
				System.out.println("	Ez duzu zenbaki egoki bat sartu, berriro saiatu");
				sc.next();
			}
		}	
		return aukeratutakoKarta;
	}
	public void imprimatuX_ (int luzeera)
	{
		for (int i = 1; i <= luzeera; i++)
		{
			System.out.print("_");
		}
	}
	public int baiAlaEzAukera (String pMezua)
	{
		boolean salataria = false;
		int emaitza = 0;
		while (!salataria)
		{
			try
			{
				System.out.println(pMezua);
				System.out.print("	");System.out.println("1) Bai");
				System.out.print("	");System.out.println("0) Ez");
				System.out.print("	");
				emaitza = sc.nextInt();
				this.konprobatuEaZenbakiEgokiaDen(emaitza, 1);
				salataria = true;
			}
			catch (InputMismatchException e)
			{
				System.out.println("	Ez duzu zenbaki oso bat sartu, berriro saiatu:");
				sc.next();
			}
			catch (ZenbakiaEzDaEgokiaSalbuespena e)
			{
				System.out.println("	"+e.getMessage());
			}
		}
		return emaitza;
	}
}
