
package lilian;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

class DomainController 
{
    /** VARIALBLES **/
    
    private DataController data;
    
    private TSTree tstree;
    
    private Answers answers;
    
    private Map<Object, Probs> maxProbs;
    
    public String process;
    
    /** CONSTRUCTOR **/
    
    public DomainController()
    {
        this.tstree   = new TSTree();
        this.answers  = new Answers();
        this.maxProbs = new HashMap<Object, Probs>(0);
        this.process  = "";
        
        this.data = new DataController();
        this.data.ReadInput(this.tstree, this.answers);
    }
    
    /** PRIVATE METHODS **/
    
    private String[] SplitString(String text)
    {
        text = text.replaceAll("[^A-Za-z0-9' ]", " ");
        return text.split("\\s+");
    }
    
    private ArrayList<String> GetFinalAnswer()
    {
        if (this.maxProbs.isEmpty())
        {
            ArrayList<String> error = new ArrayList<String>(0);
            error.add("ERROR: No answer");
            return error;
        }
        
        this.process += "\n> Merged probabilities:\n";
        
        
        Collection c = this.maxProbs.values();
        Iterator it = c.iterator();
        
        int bestAnswerPosition = -1;
        double bestAnswerProb  = 0.0;
        
        ArrayList<String> finalAnswers = new ArrayList<String>(0);
        
        while(it.hasNext())
        {
            Probs p = (Probs) it.next();
            
            this.process += "Probability: "+p.probability+" ";
            this.process += "question: "+p.answerPosition+"\n";
            
            if (bestAnswerProb == p.probability)
            {
                finalAnswers.add(this.answers.GetAnswer(p.answerPosition));
            }
            if (bestAnswerProb < p.probability) 
            {
                bestAnswerPosition = p.answerPosition;
                bestAnswerProb = p.probability;
                
                finalAnswers.clear();
                finalAnswers.add(this.answers.GetAnswer(p.answerPosition));
            }
        }
        
        return finalAnswers;
    }
    
    private void MergeProbabilities(ArrayList<Probs> wordProbs) 
    {
        for (Probs singleProb : wordProbs)
        {
            Probs sumProb = this.maxProbs.get(new Integer(singleProb.answerPosition));
            
            if (sumProb != null)
            {
                sumProb.probability += singleProb.probability;
                this.maxProbs.put(new Integer(singleProb.answerPosition), sumProb);
            }
            else
            {
                this.maxProbs.put(new Integer(singleProb.answerPosition), singleProb);
            }
        }
    }
    
    /** PUBLIC METHODS **/
    
    public String Ask(String text) 
    {
        if (text.trim().length() == 0) return "ERROR: Empty question";
        
        this.process = "";
        this.maxProbs.clear();
        
        String[] wordSet = this.SplitString(text);
        
        for (String word : wordSet)
        {
            this.process += "\n> Processing word: "+word+"\n";
            
            ArrayList<Probs> wordProbs = this.tstree.GetProbsForWord(word);
            
            for (Probs prob : wordProbs)
            {
                this.process += "Probability: "+prob.probability+" ";
                this.process += "question: "+prob.answerPosition+"\n";
            }
            
            this.MergeProbabilities(wordProbs);
        }
        
        ArrayList<String> setAnswers = this.GetFinalAnswer();
        String answer = setAnswers.get(0);
        
        if (setAnswers.size() > 1)
        {
            this.process += "\n> WARNING! Draw probabilities for answers:\n";
            for (int i = 0; i < setAnswers.size(); ++i)
            {
                this.process += setAnswers.get(i)+"\n";
            }
            
            Random random = new Random(System.currentTimeMillis());
            answer = setAnswers.get(random.nextInt(setAnswers.size()));
        }
        
        return answer;
    }
}
