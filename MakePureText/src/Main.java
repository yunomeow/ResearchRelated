import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println("hi");
		Main main = new Main();
		ArrayList<String> fileList = main.getList("allpost_5000.txt");
		ArrayList<String> yesList = main.getList("yespost.txt");
		DeleteURL dURL = new DeleteURL();
		//System.out.println(dURL.process("test.txt","http://www.google.com.tw https://tw.bid.yahoo.com/tw/booth/Y1533914322 原本想買NIKE的 http://tw.yahoo.com"));
		
		
		for(int i = 0;i < fileList.size();i++){
			System.out.println("Process: " + i);
			String tmp = fileList.get(i);
			String content = main.readFile("C:\\ptt\\"+tmp);
			String name = tmp.substring(0,18);
			content = dURL.process(name,content);
			if(yesList.contains(tmp))
				main.saveFile(name, content, "pureTextYes");
			else
				main.saveFile(name, content, "pureText");
		}
		//main.readFile("C:\\ptttest\\M.1448883618.A.84F.html");
		dURL.printResult();
	}
	private ArrayList<String> getList(String fileName){
		ArrayList<String> fileList  = new ArrayList<String>();
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\text_analysis\\"+fileName)));
			String tmp;
			while((tmp = buf.readLine()) != null){
				fileList.add(tmp);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileList;
	}
	private String[] getFileList(){
		/*try{
			java.io.File folder = new java.io.File(folderPath);
			String[] list = folder.list();    
			return list;
			
		}catch(Exception e){
			System.out.println("'"+folderPath+"'此資料夾不存在");
		} */
		return null;

	}
	private void saveFile(String filename, String content,String folder){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\text_analysis\\"+folder+"\\"+filename+".txt"));
			bw.write(content);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String readFile(String fileName){
		String res = "",tmp;
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			while((tmp = buf.readLine()) != null){
				res = res + tmp + "\n";
			}
			buf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


		String patternStr =  "<div id=\"main-container\">.*</div>";
		Pattern pattern = Pattern.compile(patternStr,Pattern.DOTALL);
		Matcher matcher = pattern.matcher(res);
		//System.out.println("==============");
		if(matcher.find()) {

			res = matcher.group();

		}
		/*Replace <span class="article-meta-value">*/
		res = res.replaceAll("<span class=\"article-meta-value\">.*?</span>", "");
		/*Replace <span class="article-meta-tag">*/
		res = res.replaceAll("<span class=\"article-meta-tag\">.*?</span>", "");
		/*Replace <span class="f2"> */
		//System.out.println(res);
		res = res.replaceAll("<span class=\"f2\">(?s).*?</span>", "");
		/*Replace <span class="f6"> */
		res = res.replaceAll("<span class=\"f6\">(?s).*?</span>", "");
		//System.out.println(res);
		/*Replace <>*/
		res = res.replaceAll("<.*?>", "");
		/*Split by \n*/
		String[] sp = res.split("\n");
		/*End with --*/
		res = "";
		for(int i=0;i<sp.length;i++){

			if(sp[i].compareTo("--") == 0)break;
			if(sp[i].compareTo("")==0)continue;
			res = res + " " + sp[i] + "\n";
			//System.out.println("="+sp[i]+"=");
		}
		//System.out.println(res);
		return res;
	}
}
