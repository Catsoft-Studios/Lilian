
package lilian;

import java.util.ArrayList;

public class TSTree
{
    /** VARIABLES **/
    
    protected Node root;
    
    /** CONSTRUCTOR **/
    
    public TSTree()
    {
        this.root = null;
    }
    
    public TSTree(Node node)
    {
        this.root = node;
    }
    
    /** PRIVATE METHODS **/
    
    private Node AddQuestion(String text, int textPos, int reference, Node node)
    {
        if (node == null)
        {
            boolean ew = (text.length() -1 == textPos ? true : false);
            node = new Node(text.charAt(textPos), ew);
        }
        
        if (text.charAt(textPos) < node.GetChar())
        {
            node.SetLeft(this.AddQuestion(text, textPos, reference, node.GetLeft()));
        }
        else if (text.charAt(textPos) > node.GetChar())
        {
            node.SetRight(this.AddQuestion(text, textPos, reference, node.GetRight()));
        }
        else
        {
            boolean ew = (text.length()-1 == textPos ? true : false);
            
            if (ew)
            {
                if (!node.ExistsReference(reference))
                {
                    node.SetEndWord(true);
                    node.AddAnswerReference(reference);
                }
            }
            else
            {
                node.SetCenter(this.AddQuestion(text, textPos+1, reference, node.GetCenter()));
            }
        }
        
        return node;
    }
    
    /*private void DisplayTree(Node node)
    {
        if (node == null) System.out.println("X ");
        else
        {
            DisplayTree(node.GetLeft());
            
            System.out.print(node.GetChar()+"(");
            for (int i = 0; i < node.GetAnswerReferenceSize(); ++i)
            {
                System.out.print(+node.GetAnswerReference(i) +",");
            }
            System.out.println(") END_OF_WORD? = "+node.IsWord());
            DisplayTree(node.GetCenter());
            
            DisplayTree(node.GetRight());
        }
    }*/
    
    private ArrayList<Probs> GetAnswerReferencesForWord(String word, int wordPos, Node node)
    {
        if (node == null) return new ArrayList<Probs>(0);
        
        ArrayList<Probs> answers = new ArrayList<Probs>(0);
        
        if (word.charAt(wordPos) < node.GetChar())
        {
            answers = this.GetAnswerReferencesForWord(word, wordPos, node.GetLeft());
        }
        else if (word.charAt(wordPos) > node.GetChar())
        {
            answers = this.GetAnswerReferencesForWord(word, wordPos, node.GetRight());
        }
        else if (node.GetChar() == word.charAt(wordPos))
        {
            //System.out.println("Mathc char for word "+word+" at charater "+node.GetChar()+" wordPos "+wordPos);
            if (wordPos == word.length()-1 && node.IsWord())
            {
                int answersSize = node.GetAnswerReferenceSize();
                
                for (int i = 0; i < answersSize; ++i)
                {
                    int answerPosition = node.GetAnswerReference(i);
                    double answerProb  = (double)1/(double)answersSize;
                    Probs prob = new Probs(answerPosition, answerProb);
                    
                    answers.add(prob);
                }
                
                return answers;
            }
            else
            {
                answers = this.GetAnswerReferencesForWord(word, wordPos+1, node.GetCenter());
            }
        }
        
        return answers;
    }
    
    /** PUBLIC METHODS **/
    
    public void AddQuestion(String text, int reference)
    {
        this.root = this.AddQuestion(text, 0, reference, this.root);
    }
    
    public ArrayList<Probs> GetProbsForWord(String word)
    {
        ArrayList<Probs> probs = new ArrayList<Probs>(0);
        return this.GetAnswerReferencesForWord(word, 0, root);
    }

    /*public void DisplayTree() 
    {
        DisplayTree(this.root);
    }*/
    
    // +-----------------------------------------------------------------------+
    // | INNER CLASS: NODE                                                     |
    // +-----------------------------------------------------------------------+
    
    public class Node
    {
        /** VARIABLES **/
        
        private Node left;
        private Node right;
        private Node center;
        
        private char character;
        private boolean endWord;
        
        private ArrayList<Integer> answerReference;
        
        /** CONSTRUCTOR **/
        
        public Node()
        {
            this.character = '#';
            this.left      = null;
            this.right     = null;
            this.center    = null;
            this.endWord   = false;
            this.answerReference = new ArrayList<Integer>(0);
        }
        
        public Node(char c, boolean ew)
        {
            this.character = c;
            this.left      = null;
            this.right     = null;
            this.center    = null;
            this.endWord   = ew;
            this.answerReference = new ArrayList<Integer>(0);
        }
        
        /** GETTERS **/
        
        public Node GetLeft()   { return this.left;      }
        public Node GetRight()  { return this.right;     }
        public Node GetCenter() { return this.center;    }
        public char GetChar()   { return this.character; }
        public boolean IsWord() { return this.endWord;   }
        
        public int GetAnswerReferenceSize()
        { 
            return this.answerReference.size(); 
        }
        
        public int GetAnswerReference(int i)
        {
            return this.answerReference.get(i).intValue();
        }
        
        public boolean ExistsReference(int reference)
        {
            for (int i = 0; i < this.answerReference.size(); ++i)
            {
                if (this.answerReference.get(i).intValue() == reference)
                {
                    return true;
                }
            }
            
            return false;
        }
        
        /** SETTERS **/
        
        public void SetLeft(Node node)     { this.left   = node; }
        public void SetRight(Node node)    { this.right  = node; }
        public void SetCenter(Node node)   { this.center = node; }
        public void SetChar(char c)        { this.character = c; }
        public void SetEndWord(boolean ew) { this.endWord = ew;  }
        
        public void AddAnswerReference(int answerReference)
        {
            this.answerReference.add(new Integer(answerReference));
        }
    }
    
    
}
