package ps.out.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MainTest{
	static BigDecimal halfFare = new BigDecimal("2.00");
	static BigDecimal specialFare = new BigDecimal("0.56");
	public static void main(String[] argst){
		try {
           // ƒoƒXî•ñ‚ğæ“¾
			BufferedReader br = new BufferedReader(new FileReader("C:\\andProduct\\busFare1.txt" ));

           String line = "" ;
           String[] bussInfo =null;
           int adultCnt = 0;
           while ((line = br.readLine()) != null) {
            	bussInfo = line.split(":");
                // ‘ål‚ª‚¢‚é‚©”»’è 
            	adultCnt = line.length() - line.replaceAll("A", "").length();
           }
            
           // ƒoƒX‘ã‚Ææ‹q
           String[] busFares = bussInfo[0].split(",");
           String[] busPassenger = bussInfo[1].split(",");
           

           // ŒvZ
           // —¿‹à‚É‚Â‚«@æ‹q‚Ì‰^’À‚ğŒvZ
           BigDecimal[] busFareSum = new BigDecimal[busPassenger.length];
           BigDecimal busFare = new BigDecimal(0);
           for (int cntFare = 0; cntFare < busFares.length; cntFare++) {
        	   // ƒoƒX‘ã
        	   busFare =  new BigDecimal(busFares[cntFare]);
        	   
        	   // æ‹q
        	   for (int cntPas = 0; cntPas < busPassenger.length; cntPas++) {
        		   // upv‚ğŠÜ‚Şê‡‚Í0‰~
	        	   if (busPassenger[cntPas].contains("p")) {
	        		   busFareSum[cntPas] = new BigDecimal(0);
	        		   continue;
	        	   }
	        	   
	        	   // —c™Eq‹Ÿ‚ÌŒvZi”¼Šzj
	        	   BigDecimal calcFare = busFare;
	        	   if (busPassenger[cntPas].contains("I") || busPassenger[cntPas].contains("C")) {
	        		   calcFare = calcFare.divide(halfFare).divide(new BigDecimal(10)).setScale(0,BigDecimal.ROUND_CEILING).multiply(new BigDecimal(10));
	        	   }
	        	   // uxv‚ğŠÜ‚Şê‡‚Íu0.56v‚ğæZ‚·‚é
	        	   if (busPassenger[cntPas].contains("x")) {
	        		   calcFare = calcFare.multiply(specialFare).divide(new BigDecimal(10)).setScale(0,BigDecimal.ROUND_CEILING).multiply(new BigDecimal(10));
	        	   }
	        	   
	        	   // —¿‹à‚ğ‡Z
	        	   if (busFareSum[cntPas] == null) {
	        		   busFareSum[cntPas] = new BigDecimal(0);
	        	   }
	        	   busFareSum[cntPas] = busFareSum[cntPas].add(calcFare);
        	   }
           }
           
           // ˆê“úæÔŒ”‚Ì”»’è
           HashMap<String, BigDecimal> dayPassMap = new HashMap<String, BigDecimal>();
           dayPassMap.put("An", new BigDecimal(910));
           dayPassMap.put("Ax", new BigDecimal(510));
           dayPassMap.put("Cn", new BigDecimal(460));
           dayPassMap.put("In", new BigDecimal(460));
           dayPassMap.put("Cx", new BigDecimal(260));
           dayPassMap.put("Ix", new BigDecimal(260));
           for (int cntPas = 0; cntPas < busPassenger.length; cntPas++) {
        	   // ˆê“úæÔŒ”‚Ì‘ÎÛ‚©‚Â—¿‹à‚ğ’´‚¦‚Ä‚¢‚éê‡
        	   if (dayPassMap.get(busPassenger[cntPas]) != null
        			   && busFareSum[cntPas].compareTo(dayPassMap.get(busPassenger[cntPas])) > 0) {
        		   
        		   busFareSum[cntPas] = dayPassMap.get(busPassenger[cntPas]);
        	   }
           }
           
           
           // uAv‚©‚ÂuIv‚ªŠÜ‚Ü‚ê‚Ä‚¢‚éê‡A‚Q‚Â‚Ü‚Å0‰~‚Æ‚·‚é
           int fareSum = 0;
           ArrayList<Integer> infants = new ArrayList<Integer>();
           for (int cntPas = 0; cntPas < busPassenger.length; cntPas++) {
        	   if (adultCnt != 0 && busPassenger[cntPas].contains("I")) {
        		   infants.add(busFareSum[cntPas].intValue());
        	   } else {
        		   fareSum = fareSum + busFareSum[cntPas].intValue();
        	   }
           }
           Collections.sort(infants);
           int cntInfants = 0;
           int freeInfants = adultCnt * 2;
           for (int cntInf = infants.size() -1; 0 <= cntInf; cntInf--) {
        	   // ‚Ql–ÚˆÈ~‚ÍŒvZ
        	   if (cntInfants >= freeInfants) {
        		   fareSum = fareSum + infants.get(cntInf);
        	   }
        	   cntInfants++;
           }
           System.out.print(fareSum);
		}catch(Exception e){
			System.err.println(e);
		}
	}
}