import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("file.encoding", "UTF-8");
		Main main = new Main();
		System.out.println(main.getClass());
        System.out.println(main.getClass().getClassLoader());
        //System.out.println(main.getClass().getClassLoader().getResource("/").getPath());
        /*URL url;

        url = Main.class.getResource("ptttest");
        System.out.println("URL = " + url.getPath() + url.getFile());*/
		main.getFileList();
	}
	private List<String> makeNeedList(){
		List<String> res = new ArrayList<String>();
		URL url = Main.class.getResource("allpost.txt");
        File file = new File(url.getPath());
        try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
			    res.add(line);
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	private void getFileList(){
		
		String folderPath = "ptt/";
		URL url = Main.class.getResource("ptt");
        System.out.println("URL = " + url.getPath() );
		try{
			java.io.File folder = new File(url.getPath());
			String[] list = folder.list();
			String content;
			List<String> need = makeNeedList();
			for(int i = 0; i < list.length; i++){
				if(need.indexOf(list[i]) == -1){
					continue;
				}
				//System.out.println("now = " + list[i]);
				content = readFile(folderPath + list[i]);
				//System.out.println(content);
				writeInDB(list[i],content);
			}
		}catch(Exception e){
			System.out.println("'"+folderPath+" not exist");
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
			
			tmp = matcher.group();
			res = "<pre>" + tmp + "</pre>";
			//System.out.println(res);

		}
		 
		return res;
	}
	private void writeInDB(String fileName,String content){
		Connection conn = null;

	    try {
	    	Class.forName("com.mysql.jdbc.Driver").newInstance();
	    }
	    catch (ClassNotFoundException ce){
	      // 若載入過程中發生錯誤
	      System.out.println("SQLException:"+ce.getMessage());
	    } catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Properties info = new Properties();
			info.setProperty("user", "root");
			info.setProperty("password", "socialcomputing");
			info.setProperty("characterEncoding", "utf-8");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/ptt_post", info);
			Statement stmt = conn.createStatement();
			String qry1 = "INSERT INTO Posts VALUES (\'"+fileName+"\',\'"+content+"\')";
			stmt.executeUpdate(qry1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(fileName);
			e.printStackTrace();
		}

		
	}
}
