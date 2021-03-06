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
           // バス情報を取得
			BufferedReader br = new BufferedReader(new FileReader("C:\\andProduct\\busFare1.txt" ));

           String line = "" ;
           String[] bussInfo =null;
           int adultCnt = 0;
           while ((line = br.readLine()) != null) {
            	bussInfo = line.split(":");
                // 大人がいるか判定 
            	adultCnt = line.length() - line.replaceAll("A", "").length();
           }
            
           // バス代と乗客
           String[] busFares = bussInfo[0].split(",");
           String[] busPassenger = bussInfo[1].split(",");
           

           // 計算
           // 料金につき　乗客の運賃を計算
           BigDecimal[] busFareSum = new BigDecimal[busPassenger.length];
           BigDecimal busFare = new BigDecimal(0);
           for (int cntFare = 0; cntFare < busFares.length; cntFare++) {
        	   // バス代
        	   busFare =  new BigDecimal(busFares[cntFare]);
        	   
        	   // 乗客
        	   for (int cntPas = 0; cntPas < busPassenger.length; cntPas++) {
        		   // 「p」を含む場合は0円
	        	   if (busPassenger[cntPas].contains("p")) {
	        		   busFareSum[cntPas] = new BigDecimal(0);
	        		   continue;
	        	   }
	        	   
	        	   // 幼児・子供の計算（半額）
	        	   BigDecimal calcFare = busFare;
	        	   if (busPassenger[cntPas].contains("I") || busPassenger[cntPas].contains("C")) {
	        		   calcFare = calcFare.divide(halfFare).divide(new BigDecimal(10)).setScale(0,BigDecimal.ROUND_CEILING).multiply(new BigDecimal(10));
	        	   }
	        	   // 「x」を含む場合は「0.56」を乗算する
	        	   if (busPassenger[cntPas].contains("x")) {
	        		   calcFare = calcFare.multiply(specialFare).divide(new BigDecimal(10)).setScale(0,BigDecimal.ROUND_CEILING).multiply(new BigDecimal(10));
	        	   }
	        	   
	        	   // 料金を合算
	        	   if (busFareSum[cntPas] == null) {
	        		   busFareSum[cntPas] = new BigDecimal(0);
	        	   }
	        	   busFareSum[cntPas] = busFareSum[cntPas].add(calcFare);
        	   }
           }
           
           // 一日乗車券の判定
           HashMap<String, BigDecimal> dayPassMap = new HashMap<String, BigDecimal>();
           dayPassMap.put("An", new BigDecimal(910));
           dayPassMap.put("Ax", new BigDecimal(510));
           dayPassMap.put("Cn", new BigDecimal(460));
           dayPassMap.put("In", new BigDecimal(460));
           dayPassMap.put("Cx", new BigDecimal(260));
           dayPassMap.put("Ix", new BigDecimal(260));
           for (int cntPas = 0; cntPas < busPassenger.length; cntPas++) {
        	   // 一日乗車券の対象かつ料金を超えている場合
        	   if (dayPassMap.get(busPassenger[cntPas]) != null
        			   && busFareSum[cntPas].compareTo(dayPassMap.get(busPassenger[cntPas])) > 0) {
        		   
        		   busFareSum[cntPas] = dayPassMap.get(busPassenger[cntPas]);
        	   }
           }
           
           
           // 「A」かつ「I」が含まれている場合、２つまで0円とする
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
        	   // ２人目以降は計算
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