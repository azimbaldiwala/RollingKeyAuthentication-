import java.io.*;  
public class ReadFileData   
{  

    public int getKeyIndex(String filepath){
        int index = 0;
        try{
            File file=new File(filepath);    //creates a new file instance  
            FileReader fr=new FileReader(file);   //reads the file  
            BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
            StringBuffer sb=new StringBuffer();    //constructs a string buffer with no characters  
            String line;
            while((line=br.readLine())!=null)  
            {  
                 index = Integer.parseInt(line);
            } 
            fr.close();   
        } catch(IOException e){
            e.printStackTrace();
            
        }
        return index;
    }


    public void updateKeyIndex(int current_index, String filename){
        try{

            String new_index = "" + (current_index + 1);
            PrintWriter writer = new PrintWriter(filename);
            writer.print(new_index);
            // other operations
            writer.close(); 
        } catch(IOException e){
            e.printStackTrace();
            
        }
    }

    public int[] getData()
    {  

        int data[] = new int[4];  // seed, mod, multiplier, increment
        try  
        {  
            File file=new File("data.txt");    //creates a new file instance  
            FileReader fr=new FileReader(file);   //reads the file  
            BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
            StringBuffer sb=new StringBuffer();    //constructs a string buffer with no characters  
            String line;
            int i = 0;  
            while((line=br.readLine())!=null)  
            {  
                data[i] = Integer.parseInt(line);
                i++;
            }  
            fr.close();    //closes the stream and release the resources 
        }  
        catch(IOException e)  
        {  
            e.printStackTrace();  
        }
        return data;
    }  
}  