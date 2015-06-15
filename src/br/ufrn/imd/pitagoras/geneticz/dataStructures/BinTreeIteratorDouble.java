package br.ufrn.imd.pitagoras.geneticz.dataStructures;


/**
 * Iterador para um nó binario de valor Double.
 * É utilizado o traversal In-Ordem.
 * 
 * @author pitagoras 
 * @version 1.5
 */
public class BinTreeIteratorDouble
{

    private BinNodeDouble actualNode, nextNode;

    /**
     * Construtor para objetos da classe BinTreeIterator
     * @param startNode Nó inicial a partir do qual o traversal será iniciado.
     */
    public BinTreeIteratorDouble(BinNodeDouble startNode) throws EmptyTreeException
    {
        // inicializa variÃ¡veis de instÃ¢ncia
        actualNode = new BinNodeDouble(0, 0.0);
        
        if(startNode == null){
            throw new EmptyTreeException("O iterador não pode começar a partir de um nó nulo.");
        }
        nextNode = startNode;
        while (nextNode.getLeftSon() != null){
            nextNode = nextNode.getLeftSon();
        }
        
        try{
            actualNode = iterateNode();
        }catch(Exception e){
            actualNode = nextNode;
            nextNode = null;
        }
    }
    
    /**
     * 
     * @return Se há um elemento atual a ser acessado. Somente é falso quando a iteração tiver acabado.
     */
    public boolean hasActual(){
        return actualNode != null;
    }
    
    /**
     * @return Se, após o elemento atual, há um elemento para ser acessado.
     */
    public boolean hasNext(){
        return nextNode != null;
    }
    
    public BinNodeDouble getNode(){
    	return actualNode;
    }
    
    public double get(){
        return actualNode.getData();
    }
    
    public int getKey(){
        return actualNode.getKey();
    }
    
    /**
     * Avança o nó atual do iterador
     */
    public void iterate(){
        actualNode = iterateNode();
    }
    
    private BinNodeDouble iterateNode(){
        if(nextNode == null){
            return null;
            //throw new EmptyTreeException("NÃ£o hÃ¡ um proximo nÃ³ na arvore.");
        }
        
        BinNodeDouble node = nextNode;
        
        if(nextNode.getRightSon() != null){
            nextNode = nextNode.getRightSon();
            while (nextNode.getLeftSon() != null){
                nextNode = nextNode.getLeftSon();
            }
            return node;
        }else{
            while(true){
                if(nextNode.getFather() == null){
                    nextNode = null;
                    return node;
                }
                
                if(nextNode.getFather().getLeftSon() == nextNode){
                    nextNode = nextNode.getFather();
                    return node;
                }
                
                nextNode = nextNode.getFather();
            }
        }
    }
}
