package boardStat;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			HashMap<String,Integer> hm = new HashMap<String,Integer>();
			BufferedReader br = new BufferedReader(new FileReader(new File("C:\\board\\common.txt")));
			String line;
			while((line = br.readLine()) != null){
				hm.put(line, 0);
			}
			br.close();
			br = new BufferedReader(new FileReader(new File("C:\\board\\yesResult.txt")));
			PrintWriter pw = new PrintWriter(new FileWriter(new File("C:\\board\\realYes.txt")));
			while((line = br.readLine()) != null){
				if(hm.containsKey(line)){
					 Integer i = hm.get(line);
					 hm.put(line,i+1);
				}else{
					pw.write(line);
					pw.write('\n');
				}
			}
			Set< Entry<String,Integer> > se = hm.entrySet();
			Iterator< Entry<String,Integer> > itr = se.iterator();  
	        while(itr.hasNext()){  
	        	Entry<String,Integer> entry = itr.next();  
	            if(entry.getValue() > 1){
	            	pw.write(entry.getKey());
	            	pw.write('\n');
	            }
	        }  
			br.close();
			pw.close();
		}catch(Exception e){
			e.printStackTrace();
		} 
	}

}
