import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
	ArrayList<String> posList;
	public static void main(String[] args){
		Main main = new Main();
		main.load();
		String[] fileList = main.getFileList("C:\\text_analysis\\facebook_compare\\raw_text\\Result_Cond1");
		for(int i=0;i<fileList.length;i++){
			main.process(fileList[i]);
		}
	}
	public String[] getFileList(String folderPath){
		try{
			java.io.File folder = new java.io.File(folderPath);
			String[] list = folder.list();    
			return list;
			
		}catch(Exception e){
			System.out.println("'"+folderPath+" not found");
		} 
		return null;

	}
	public void load(){
		try {
			posList = new ArrayList<String>();
			BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\text_analysis\\part_of_speech.txt")));
			String tmp;
			while((tmp = buf.readLine()) != null){
				posList.add(tmp);
				//System.out.println(tmp);
			}
			buf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void process(String fileName){
		try {
		
			BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\text_analysis\\facebook_compare\\raw_text\\Result_Cond1\\"+fileName)));
			BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\text_analysis\\facebook_compare\\raw_text\\Result_Cond1_noPOS\\"+fileName));
			String tmp,res="";
			while((tmp = buf.readLine()) != null){
				res = res + tmp + "\n";
			}
			
			for(int i=0;i<posList.size();i++){
				
				res = res.replaceAll("\\("+posList.get(i)+"\\)", "");
				//System.out.println(res);
			}
			bw.write(res);
			bw.close();
			buf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
