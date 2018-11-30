import visidia.simulation.process.algorithm.Algorithm;
import visidia.simulation.process.messages.*;
import java.util.concurrent.TimeUnit ;
import java.util.Random;
import java.io.IOException;

public class sync_horologe extends Algorithm
{
	public String getDescription()
	{
		return "Cesi est un exemple";
	}
	public Object clone()
	{
		return new sync_horologe();
	}
	public void init()
	{
		
		putProperty("label", "init...", 0) ;
		Random rand = new Random();
		int randval = 0  ; 
	int yyyy = 2018 ;
	int mm = 1 ;
	int dd = 1 ;
		while(true)
		{	
			randval = rand.nextInt(5) ;
			dd += randval ;
			if( dd > 30 ) {
			dd = 1 ;
			mm++ ;
			if( mm > 12 )
			{
			mm = 1 ;
			yyyy++ ;
			}
			}			
			try {
				TimeUnit.SECONDS.sleep(randval);
			} catch (InterruptedException e) {
  				// do whatever
			} 
			//Envoie proadcast
			sendAll(new StringMessage(Integer.toString(dd)
			+"/"+Integer.toString(mm)
			+"/"+Integer.toString(yyyy)));


		//Envoie d'un message de n'import quelle neud specifier

		//sendTo(0,new StringMessage("Je suis le Noeud "+getId()));
int i = 0 ;
		while(i < 2 )
{
i ++;
			Door d = new Door();
			Message M = receive(d);
			System.out.println("N:" +getId() + "  Recu: "+ M+"   door: "+d.getNum());
			
			String msg = M.toString() ;
int tmpYYYY = Integer.parseInt(msg.split("/")[2]) ;
int tmpMM = Integer.parseInt(msg.split("/")[1]);
int tmpDD = Integer.parseInt(msg.split("/")[0]) ;
		if(tmpYYYY > yyyy ) yyyy = tmpYYYY ;
		if(tmpMM > mm ) mm = tmpMM ;
		if(tmpDD > dd ) dd = tmpDD ;
	}
			putProperty("label", moonfx(dd, mm, yyyy).replace("\n"," || "), 0) ;
		}
		//sendTo(0,new StringMessage("Merci"));
		//Message M = receiveFrom(0);

	}

	   
	public String moonfx(double D, double M, double Y)
    {

        double P2=2*3.14159 ;
        double YY=Y-(int)((12-M)/10) ;
        double MM=M+9;
        if (MM >= 12 ) MM=MM-12 ;

        double K1=(int)(365.25*(YY+4712)) ;
        double K2=(int)(30.6*MM+.5) ;
        double K3=(int)((int)((YY/100)+49)*.75)-38 ;

        double J=K1+K2+D+59;
        if ( J>2299160 ) J=J-K3 ;

        double IP=vFunction((J-2451550.1)/29.530588853);
        double AG=IP*29.53;
        IP=IP*P2;


        double DP=vFunction((J-2451562.2)/27.55454988);
        DP=DP*P2;
        double DI=60.4-3.3*Math.cos(DP)-.6*Math.cos(2*IP-DP)-.5*Math.cos(2*IP);

        double NP=vFunction((J-2451565.2)/27.212220817);
        NP=NP*P2;
        double LA=5.1*Math.sin(NP);

        double RP=vFunction((J-2451555.8)/27.321582241);
        double LO=360*RP+6.3*Math.sin(DP)+1.3*Math.sin(2*IP-DP)+.7*Math.sin(2*IP);

        String text = "" ;
        text +=  "Moon's age from new (days): " +   Double.toString((int)AG)   + "\n";
        text +=  "Distance (Earth radii): " +       Double.toString((int)DI)   + "\n" ;
        text +=  "Ecliptic latitude (degrees): " +  Double.toString(LA)   + "\n";
        text +=  "Ecliptic longitude (degrees): " + Double.toString(LO)   + "\n";
        
    	return text ;
    }
    private double vFunction(double V)
    {
        V=V-(int)(V);
        if(V<0) V=V+1;
        return V ;
    }
}
