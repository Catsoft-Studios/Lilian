
package lilian;

public class Probs 
{
    /** VARIABLES **/
    
    public int answerPosition;
    public double probability;
    
    /** CONSTRUCTOR **/
    
    public Probs()
    {
        this.answerPosition = -1;
        this.probability = 0;
    }
    
    public Probs(int pos, double prob)
    {
        this.answerPosition = pos;
        this.probability = prob;
    }
}
