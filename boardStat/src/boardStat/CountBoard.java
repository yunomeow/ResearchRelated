package boardStat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountBoard {
	public static void main(String[] args){

		CountBoard cb = new CountBoard();
		cb.start();
	}
	public void start(){
		BufferedReader br;
		HashMap<String,Counting> hashmap = new HashMap<String, Counting>();
		try {
			br = new BufferedReader(new FileReader(new File("C:\\board\\allpost.txt")));
			String line,boardName;
			while((line = br.readLine()) != null){
				boardName = process(line);
				if(hashmap.containsKey(boardName)){
					Counting c = hashmap.get(boardName);
					c.a = c.a + 1;
					hashmap.put(boardName,c);
				}else{
					Counting c = new Counting(1,0);
					hashmap.put(boardName, c);
				}
			}
			
			
			br = new BufferedReader(new FileReader(new File("C:\\board\\realYes.txt")));
			while((line = br.readLine()) != null){
				boardName = process(line);
				if(hashmap.containsKey(boardName)){
					Counting c = hashmap.get(boardName);
					c.b = c.b + 1;
					hashmap.put(boardName,c);
				}else{
					Counting c = new Counting(0,1);
					hashmap.put(boardName, c);
				}
			}
			printResult(hashmap);
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String process(String fileName) throws Exception{
		//System.out.println(fileName);
		BufferedReader br = new BufferedReader(new FileReader(new File("C:\\ptt\\"+fileName)));
		String line,page="";
		while((line = br.readLine()) != null){
			page = page + line;
		}
		br.close();
		return getArticleBoardName(page);
	}
	private String getArticleBoardName(String input){
		//System.out.println(input);
		String patternStr =  "<span class=\"board-label\">看板 </span>.*?</a>";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(input);
		String res = null;
		if (matcher.find()) {
			res = matcher.group();
		}
		if(res == null)return null;
		res = res.split("<span class=\"board-label\">看板 </span>|</a>")[1];
		//System.out.println(res);
		return res;
	}
	private void printResult(HashMap<String,Counting> hashmap){
		Set< Entry<String,Counting> > set = hashmap.entrySet();
		Iterator< Entry<String,Counting> > itr = set.iterator();  
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(new File("C:\\board\\boardStatnew.txt")));
			while(itr.hasNext()){  
				Entry<String,Counting> entry = itr.next();  
				pw.println(entry.getKey() + " " + entry.getValue().a + " " + entry.getValue().b);
				//System.out.println(entry.getKey() + " " + entry.getValue());
			}  
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/*private void makeAll(){
		StringBuffer fileList = new StringBuffer();
		String folderPath = "C:\\ptt\\";
		java.io.File folder = new java.io.File(folderPath );
		List<String> list = (List) Arrays.asList(folder.list());

		HashMap<String,Integer> hashmap = new HashMap<String, Integer>();
		try {		
			for(String str : list){
				String boardName;

				boardName = process(str);
				if(boardName==null)continue;
				if(hashmap.containsKey(boardName)){
					Integer i = hashmap.get(boardName);
					hashmap.put(boardName,i+1);
				}else{
					hashmap.put(boardName, 1);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printResult(hashmap);

	}*/
}
