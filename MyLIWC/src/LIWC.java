import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class LIWC {
	ArrayList<String> posList;
	HashMap< Integer,Double > standard;
	HashMap< Integer,String > category;
	HashMap< String , ArrayList<Integer> > dict;
	HashMap< Integer , ArrayList<String> > cat_word;
	public LIWC(){
		//open dictionary
		//make dictionary
		loadStandard();
		loadDict();
		load();
	}
	private void loadStandard(){
		standard = new HashMap< Integer, Double >();
		category = new HashMap< Integer,String > ();
		BufferedReader buf;
		try {
			buf = new BufferedReader(new InputStreamReader(new FileInputStream("standard.txt")));
			String tmp;
			while((tmp = buf.readLine()) != null){
				String[] str = tmp.split(" ");
				standard.put((Integer)Integer.parseInt(str[0]) , (Double)Double.parseDouble(str[1]));
			}
			buf.close();
			buf = new BufferedReader(new InputStreamReader(new FileInputStream("category.txt")));
			while((tmp = buf.readLine()) != null){
				String[] str = tmp.split(" ");
				category.put((Integer)Integer.parseInt(str[0]) , str[1]);
			}
			buf.close();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void loadDict(){
		dict = new HashMap< String, ArrayList<Integer> >();
		cat_word = new HashMap< Integer , ArrayList<String> >();
		BufferedReader buf;
		try {
			buf = new BufferedReader(new InputStreamReader(new FileInputStream("cliwc.txt")));
			String tmp;
			while((tmp = buf.readLine()) != null){
				String[] str = tmp.split("\t");
				dict.put(str[0], new ArrayList<Integer>());
				ArrayList<Integer> list = dict.get(str[0]);
				for(int i=1;i<str.length;i++){
					if(standard.containsKey((Integer)Integer.parseInt(str[i]))){
						Integer k = (Integer)Integer.parseInt(str[i]);
						list.add(k);
						
						if(!cat_word.containsKey(k)){
							cat_word.put(k, new ArrayList<String>() );
						}
						ArrayList<String> cat_list = cat_word.get(k);
						cat_list.add(str[0]);
					}
				}
			}
			buf.close();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void load(){
		try {
			posList = new ArrayList<String>();
			BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream("part_of_speech.txt")));
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
	public static void main(String[] args){
		//System.out.println("Hello  棒棒");
		LIWC liwc = new LIWC();
		liwc.cal(liwc.readFile("test/out/text.txt"));
		return ;
	}
	private String modify(String input){
		String marks = "。。.，,、'；;：:？?！!『』「」【】﹝﹞《》〈〉“”’‘〞〝'\"-_+=[]{}<>/\\|()@#$%^&*~`‧\n\t";
		int len = marks.length();
		for(int i=0;i<len;i++){
			input = input.replace(marks.charAt(i),' ');
		}
		input = input.replaceAll(" +", " ");
		return input.trim();
	}
	private void cal(String input){
		input = input.replaceAll("　", " ");
		input = modify(input);
		String[] words = input.split(" |\n|\t");
		//System.out.println("==" + input  +"==");
		HashMap<Integer , Integer> stat =  new HashMap<Integer , Integer>();
		Integer wc = words.length;
		//System.out.println("WC = " + wc);
		for(String word: words){
			if(dict.containsKey(word)){
				for(Integer c: dict.get(word)){
					if(!stat.containsKey(c)){
						stat.put(c, 0);
					}
					stat.put(c,stat.get(c)+1);
				}
			}
		}
	//	System.out.println("<br>===========<br>");
		
		double v2;
		v2 =  wc.doubleValue();
		double v1 = standard.get(0);
		double res;
		boolean flag = false;
		res = (v1 - v2) / (v1 + v2 + 0.00001);
		if(Math.abs(res) > 0.8){
			if(res > 0)System.out.print("可增加文章長度<br>");
			else System.out.print("可減少文章長度<br>");
			flag = true;
		}
		
		for(Integer k : category.keySet()){
			if(stat.get(k) != null)
				v2 = 100.0*stat.get(k).doubleValue() / wc.doubleValue();
			else
				v2 = 0;
			//System.out.println(k + " : " + val);
			v1 = standard.get(k);
			res = (v1 - v2) / (v1 + v2 + 0.00001);
			//System.out.println("v1: " + v1 + " v2: " + v2 + "<br>");
			
			if(Math.abs(res) > 0.8){
				flag = true;
				if(res > 0)System.out.print("可增加");
				else System.out.print("可減少");
				System.out.print(category.get(k));
				System.out.print("，例如：");
				Random random = new Random();
				ArrayList<String> a = cat_word.get(k);
				int N = a.size();
				int pos = random.nextInt(N) % N;
				System.out.print(a.get(pos%N) + ", "+ a.get((pos+1)%N)  + ", " + a.get((pos+2)%N));
				System.out.println("<br>");
			}
			
		}
		if(!flag)System.out.println("無建議<br>");
	}
	private String readFile(String fileName){
		String res = "",tmp;
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			while((tmp = buf.readLine()) != null){
				res = res + tmp + "\n";
			}
			buf.close();
			for(int i=0;i<posList.size();i++){
				
				res = res.replaceAll("\\("+posList.get(i)+"\\)", "");
				//System.out.println(res);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return res;
	}
}
