package src.com.pack.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class FileSearch {
	String line;
	String found;
	String elementdetail = null;
	String ElementName = null;
	public String[] parseFile(String searchStr) throws FileNotFoundException	{
		searchStr=ErrorType(searchStr);
		String basePath = new File("").getAbsolutePath();
		basePath=basePath+"\\ObjectRepository.txt";
//		System.out.println("Search Strin-"+searchStr);
		Scanner scan = new Scanner(new File(basePath));
		while(scan.hasNext())
		{
			String line = scan.nextLine().toLowerCase().toString(); 
			 
			if(line.contains(searchStr.toLowerCase())) {
//				System.out.println("Error Line-"+line);
				 StringTokenizer st = new StringTokenizer(line);
				 int tot = st.countTokens();
				 int i=0;
				 while (st.hasMoreTokens()) {
				 String data = st.nextToken();
					 if(i==0) {
						 elementdetail=data; 
					 }
					 if(i==tot-1) {
						 ElementName=data;
					 }
					 i++;
					//	 System.out.println(st.nextToken());
				 }
				System.out.println("elementdetail-"+elementdetail);
				System.out.println("ElementName-"+ElementName);
				 found=line;	
				break;		
			} 
		}
		scan.close();
		return new String[]{ elementdetail, ElementName };
	}
	
	public String ErrorType(String ErrorDetail) {
		String errorty;
		String errobject=null;
		String erroelemen=null;
		if (ErrorDetail.contains("NoSuchElementException")){
			final String REGeX ="(Using.*, )";
			final String RegeX ="(value.*)";
			Pattern p = Pattern.compile(REGeX);
			Pattern p1 = Pattern.compile(RegeX);
            java.util.regex.Matcher m = p.matcher(ErrorDetail);
            java.util.regex.Matcher m1 = p1.matcher(ErrorDetail);
            boolean foun= m1.find();
//            System.out.println("going to regex loop-"+m1.group(1));
//            System.out.println(m1.group(1));
            if(foun){
//              System.out.println("now in regex loop");
          //    String theGroup1 = m.group(1);
         //     System.out.println(theGroup1);
              String theGroup2=m1.group(1);
        //      System.out.println(theGroup1);
              System.out.println(theGroup2);
       //       errobject=theGroup1;
              erroelemen=theGroup2;
              erroelemen=erroelemen.replace("}", "");
              erroelemen=erroelemen.replace("value=", "");
              System.out.println("Error Element-"+erroelemen);
            }
				errorty ="Element Not found";
		}
		return erroelemen;
	}

	public  void clearTheFile() throws Exception {
		String basePath = new File("").getAbsolutePath();
//		System.out.println("Basep"+basePath);
		String filepath = new File("data/file.txt").getAbsolutePath();
//		System.out.println("Absp"+filepath);
		FileWriter fwOb = new FileWriter(filepath, false); 
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
    }
	
	public  void clearTheFile(String Datafile) throws Exception {
		FileWriter fwOb = new FileWriter(Datafile, false); 
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
    }
	
	public  void writeInFile(String writename) throws Exception {
		String basePath = new File("").getAbsolutePath();
//		System.out.println("Basep"+basePath);
		String filepath = new File("data/file.txt").getAbsolutePath();
//		System.out.println("Absp"+filepath);
		FileWriter fwOb = new FileWriter(filepath, false); 
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.println(writename);
        pwOb.close();
        fwOb.close();
    }
	public void createFile(String filename)throws Exception{
		String filepath = new File(filename).getAbsolutePath();
		FileWriter fwOb = new FileWriter(filepath, false); 
		fwOb.close();
	}
	public  void writeInFile(String filename,String value1, String value2) throws Exception {
		String basePath = new File("").getAbsolutePath();
//		System.out.println("Basep"+basePath);
		String filepath = new File(filename).getAbsolutePath();
//		System.out.println("Absp"+filepath);
		FileWriter fwOb = new FileWriter(filepath, true); 
        PrintWriter pwOb = new PrintWriter(fwOb, true);
       // pwOb.write(value1+ "," +value2);
       pwOb.println(value1+ "," +value2);
        pwOb.close();
        fwOb.close();
    }
	
	public  String[] searchinFile(String filename, String searchterm) throws Exception{
		File file = new File(filename);
		int lineNum = 0;
	//	 String newarr[] = new String[5]; 
		try {
		    Scanner scanner = new Scanner(file);
		    //now read the file line by line...
		    
		    while (scanner.hasNextLine()) {
		        String line = scanner.nextLine();
		        if(line.contains(searchterm)) { 
		        	lineNum++;
		        	//newarr[lineNum]=line;
		           //   System.out.println("ho hum, i found it on line " +lineNum);
		        }
		        
		    }
		} catch(FileNotFoundException e) { 
		    //handle this
		}
		 String newarr[] = new String[lineNum]; 
		 
		 try {
			    Scanner scanner = new Scanner(file);
			    //now read the file line by line...
			    lineNum=0;
			    while (scanner.hasNextLine()) {
			        String line = scanner.nextLine();
			        if(line.contains(searchterm)) { 
			        	
			        	newarr[lineNum]=line;
			        	lineNum++;
			             // System.out.println("ho hum, i found it on line " +line);
			        }
			        
			    }
			} catch(FileNotFoundException e) { 
			    //handle this
			}

		
		return newarr;
	}
	
	public String  readFromFile() throws Exception{
		String basePath = new File("").getAbsolutePath();
//		System.out.println("Basep"+basePath);
		String filepath = new File("data/file.txt").getAbsolutePath();
//		System.out.println("Absp"+filepath);
		Path path = Paths.get(filepath);
		Scanner scanner = new Scanner(path);
		//read line by line
	//	while(scanner.hasNextLine()){
		    //process each line
		    String line = scanner.nextLine();
		   
//		}
		scanner.close();
		 System.out.println("Data file-"+line);
		return line;
		
	}


}
