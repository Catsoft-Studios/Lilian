
package lilian;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataController 
{
    
    /** VARIABLES **/
    
    private String filename = "Corpus.dat";
    private File file;
    
    /** CONSTRUCTOR **/
    
    public DataController()
    {
        this.file = new File("src/lilian/Data/"+this.filename);
    }
    
    /** PRIVATE METHODS **/
    
    private String[] SplitString(String text)
    {
        text = text.toLowerCase();
        text = text.replaceAll("/[^A-Za-z0-9 ]/", text);
        return text.split("\\s+");
    }
    
    /** PUBLIC METHODS **/
    
    public void ReadInput(TSTree tstree, Answers answers)
    {
        try 
        {
            FileReader reader = null;
            reader = new FileReader(this.file.getPath());
            
            BufferedReader inputStream = new BufferedReader(reader);
            String line = null;
            
            while ((line = inputStream.readLine()) != null)
            {
                if (line.length() == 0 || line.charAt(0) == '#')
                {
                    continue;
                }
                else if (line.charAt(0) == '+')
                {
                    // Question:
                    line = line.substring(2);
                    int answerReference = answers.GetSize() - 1;
                    
                    String[] words = this.SplitString(line);
                    for (String word : words)
                    {
                        tstree.AddQuestion(word, answerReference);
                    }
                }
                else if (line.charAt(0) == '-')
                {
                    // Answer:
                    line = line.substring(2);
                    answers.AddAnswer(line);
                }
            }
            
            inputStream.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(DataController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //tstree.DisplayTree();
    }
}
