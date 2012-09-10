
package lilian;

import java.util.ArrayList;


public class Answers 
{
    /** VARIABLES **/
    
    private ArrayList<String> answers;
    
    /** CONSTRUCTOR **/
    
    public Answers()
    {
        this.answers = new ArrayList<String>(0);
    }
    
    /** GETTERS **/
    
    public String GetAnswer(int i)
    {
        return this.answers.get(i);
    }
    
    public int GetSize()
    {
        return this.answers.size();
    }
    
    /** SETTERS **/
    
    public void AddAnswer(String answer)
    {
        this.answers.add(answer);
    }
}
