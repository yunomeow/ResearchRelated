import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main main = new Main();
		main.getFileList();
	}

	private void getFileList(){
		StringBuffer fileList = new StringBuffer();
		String folderPath = "C:\\ptt\\";
		try{
			java.io.File folder = new java.io.File(folderPath );
			List<String> list = (List) Arrays.asList(folder.list());
			Collections.shuffle(list, new Random(System.currentTimeMillis()));
			List<String> judge01,user01,user02,user03,allpost;
			judge01 = new ArrayList<String>();
			user01 = new ArrayList<String>();
			user02 = new ArrayList<String>();
			user03 = new ArrayList<String>();
			allpost = new ArrayList<String>();
			for(int i = 0; i < 5000; i++){
				allpost.add(list.get(i));
			}
			//0~99 for all
			for(int i = 0; i < 100; i++){
				judge01.add(list.get(i));
				user01.add(list.get(i));
				user02.add(list.get(i));
				user03.add(list.get(i));
			}
			//100~199 for users
			for(int i = 100; i < 200; i++){
				user01.add(list.get(i));
				user02.add(list.get(i));
				user03.add(list.get(i));
			}
			for(int i = 200; i < 1800; i++){
				user01.add(list.get(i));
			}
			for(int i = 1800; i < 3400; i++){
				user02.add(list.get(i));
			}			
			for(int i = 3400; i < 5000; i++){
				user03.add(list.get(i));
			}
			//write in file
			Collections.shuffle(judge01, new Random(System.currentTimeMillis()));
			writeToFile("judge01",judge01);
			Collections.shuffle(user01, new Random(System.currentTimeMillis()));
			writeToFile("user01",user01);
			Collections.shuffle(user02, new Random(System.currentTimeMillis()));
			writeToFile("user02",user02);
			Collections.shuffle(user03, new Random(System.currentTimeMillis()));
			writeToFile("user03",user03);
			writeToFile("allpost",allpost);
		}catch(Exception e){
			System.out.println("'"+folderPath+" not exist");
		} 
	}
	private void writeToFile(String name,List<String> list){
		try {
			PrintWriter pw = new PrintWriter(new File(name+".txt"));
			for(int i=0;i<list.size();i++){
				pw.println(list.get(i));
			}
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
