package br.ufrn.imd.pitagoras.geneticz.dataStructures;


/**
 * Iterador para um n� binario de valor Double.
 * � utilizado o traversal In-Ordem.
 * 
 * @author pitagoras 
 * @version 1.5
 */
public class BinTreeIteratorDouble
{

    private BinNodeDouble actualNode, nextNode;

    /**
     * Construtor para objetos da classe BinTreeIterator
     * @param startNode N� inicial a partir do qual o traversal ser� iniciado.
     */
    public BinTreeIteratorDouble(BinNodeDouble startNode) throws EmptyTreeException
    {
        // inicializa variáveis de instância
        actualNode = new BinNodeDouble(0, 0.0);
        
        if(startNode == null){
            throw new EmptyTreeException("O iterador n�o pode come�ar a partir de um n� nulo.");
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
     * @return Se h� um elemento atual a ser acessado. Somente � falso quando a itera��o tiver acabado.
     */
    public boolean hasActual(){
        return actualNode != null;
    }
    
    /**
     * @return Se, ap�s o elemento atual, h� um elemento para ser acessado.
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
     * Avan�a o n� atual do iterador
     */
    public void iterate(){
        actualNode = iterateNode();
    }
    
    private BinNodeDouble iterateNode(){
        if(nextNode == null){
            return null;
            //throw new EmptyTreeException("Não há um proximo nó na arvore.");
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
