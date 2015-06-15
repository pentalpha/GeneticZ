package br.ufrn.imd.pitagoras.geneticz.dataStructures;

import java.lang.Math;
/**
 * No binario com chave Integer e valor Double.
 * 
 * @author Pitágoras Alves
 * @version 24-04-2015
 */
public class BinNodeDouble{

    private BinNodeDouble leftSon, rightSon;
    private BinNodeDouble father;
    private final Integer key;
    private Double data;
    private int height;

    /**
     * Construtor para objetos da classe BinNode
     * 
     * @param father O nó pai deste nó.
     * @param key A chave do nó.
     * @param data Valor para ser armazenado.
     */
    public BinNodeDouble(BinNodeDouble father, int key, Double data)
    {
        // inicializa variáveis de instância
        this.father = father;
        this.key = key;
        this.data = data;
        setHeight();
    }
    
    /**
     * Construtor para objetos da classe BinNode que n�o define um pai inicialmente.
     * 
     * @param key A chave do nó, valor armazenado.
     * @param data Valor para ser armazenado.
     */
    public BinNodeDouble(int key, Double data)
    {
        // inicializa variáveis de instância
        this.key = key;
        this.data = data;
        setHeight();
    }
    

    /**
     * 
     * @return A raiz deste nó.
     */
    public BinNodeDouble getRoot(){
        if (getFather() == null || getFather() == this){
            return this;
        }else{
            return father.getRoot();
        }
    }
    
    /**
     * Calcula o número de nós da arvore a partir deste nó.
     * 
     * @return O número de nós da subarvore.
     */
    public int subTreeSize(){
        int size = 1;
        
        if(leftSon != null){
            size += leftSon.subTreeSize();
        }
        if(rightSon != null){
            size += rightSon.subTreeSize();
        }
        
        return size;
    }
    
    /**
     * Remove um dos nós filhos do nó em questão.
     * 
     * @param   son Nó filho à ser removido
     * @return  Indica se a remoção foi bem sucedida, ou seja,
     *          se o nó "son" era mesmo filho desta instancia.
     */
    public boolean removeChildren(BinNodeDouble son){
        if(rightSon == son){
            rightSon = null;
            return true;
        }else if(leftSon == son){
            leftSon = null;
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Define o filho esquerdo deste nó.
     * Definir como null remove o filho esquerdo.
     * 
     * @param newSon Novo filho esquerdo.
     * @return O antigo filho esquerdo, caso não houvesse filho esquerdo, retorna NULL.
     */
    public BinNodeDouble setLeftSon(BinNodeDouble newSon){
        BinNodeDouble temp = leftSon;
        if(newSon != null){
            newSon.setFather(this);
            leftSon = newSon;
        }else{
            leftSon = null;
        }
        setHeight();
        if(father != null){
            father.setHeight();
        }
        return temp;
    }
    
    /**
     * Define o filho direito deste nó.
     * Definir como null remove o filho direito.
     * 
     * @param newSon Novo filho direito.
     * @return O antigo filho direito, caso não houvesse filho direito, retorna NULL.
     */
    public BinNodeDouble setRightSon(BinNodeDouble newSon){
        BinNodeDouble temp = rightSon;
        if(newSon != null){
            newSon.setFather(this);
            rightSon = newSon;
        }else{
            rightSon = null;
        }
        setHeight();
        if(father != null){
            father.setHeight();
        }
        return temp;
    }
    
    /**
     * Define o nó pai deste nó.
     * 
     * @param   newFather   Novo nó pai.
     * @return  O antigo pai, caso não houvesse pai, retorna NULL.
     */
    public BinNodeDouble setFather(BinNodeDouble newFather){
        if(father != null){
            if(father != newFather){
                father.removeChildren(this);
            }
        }
        BinNodeDouble temp = father;
        father = newFather;
        return temp;
    }

    
    public void setData(Double data){
        this.data = data;
    }
    
    public double getData(){
        return data;
    }
    
    /**
     * Método de acesso ao filho esquerdo
     */
    public BinNodeDouble getLeftSon(){
        return leftSon;
    }
    
    /**
     * Método de acesso ao filho direito
     */
    public BinNodeDouble getRightSon(){
        return rightSon;
    }
    
    /**
     * Método de acesso ao nó pai
     */
    public BinNodeDouble getFather(){
        return father;
    }
    
    /**
     * Método de acesso para a chave deste nó
     */
    public int getKey(){
        return key;
    }
    
    /**
     * 
     * @return Nivel deste nó.
     */
    public int level(){
        if(father == null){
            return 0;
        }else{
            return father.level() + 1;
        }
    }
    
    /**
     * Atualiza a altura deste nó.
     */
    public void setHeight(){
        if(leftSon != null && rightSon != null){
            height = Math.max(getLeftSon().getHeight(), getRightSon().getHeight()) + 1;
        }else if (leftSon != null){
            height = leftSon.getHeight() + 1;
        }else if (rightSon != null){
            height = rightSon.getHeight() + 1;
        }else{
            height = 1;
        }
        
    }
    
    /**
     * 
     * @return A altura deste nó.
     */
    public int getHeight(){
        return height;
    }
    
    /**
     * Faz a contagem das folhas de uma sub-arvore
     * @param node Nó inicial da sub-arvore.
     * @return Numero de nós.
     */
    public static int subLeafs(BinNodeDouble node){
        int amount = 0;
        
        if(node.getRightSon() != null){
            if(node.getRightSon().isLeaf()){
                amount += 1;
            }else{
                amount += subLeafs(node.getRightSon());
            }
        }
        
        if(node.getLeftSon() != null){
            if(node.getLeftSon().isLeaf()){
                amount += 1;
            }else{
                amount += subLeafs(node.getLeftSon());
            }
        }
        
        return amount;
    }
    
    /**
     * 
       * @return Se este nó é uma folha ou não.
     */
    public boolean isLeaf(){
        return (this.rightSon == null && this.leftSon == null);
    }
    
    public int getBalance(){
        int b = 0;
        if(leftSon != null){ b+=leftSon.getHeight();}
        if(rightSon != null){ b-=rightSon.getHeight();}
        return b;
    }
    
    /**
     * @return String de texto que descreve o conteudo deste nó e de seus filhos, recursivamente.
     */
    public String toString(){
        String string = "\n";
        for(int i = 1; i <= level(); i++){
            string += "\t";
        }
        string += key;
        if(leftSon != null || rightSon != null){
            string += "{";
            if(leftSon != null){
                string += leftSon.toString();
            }
        
            if(rightSon != null){
                string += rightSon.toString();
            }
            string += "\n";
            for(int i = 1; i <= level(); i++){
                string += "\t";
            }
            string += "};";
        }else{
            string += ";";
        }
        return string;
    }
}
