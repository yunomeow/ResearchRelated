import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			List<String> user01 = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new FileReader(new File("C:\\user01.txt")));
			String line;
			while((line = br.readLine()) != null){
				user01.add(line);
			}
			br.close();
			br = new BufferedReader(new FileReader(new File("C:\\user02.txt")));
			PrintWriter pw = new PrintWriter(new FileWriter(new File("C:\\res\\common.txt")));
			while((line = br.readLine()) != null){
				if(user01.contains(line)){
					pw.write(line);
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
