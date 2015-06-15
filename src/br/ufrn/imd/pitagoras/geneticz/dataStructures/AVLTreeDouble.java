package br.ufrn.imd.pitagoras.geneticz.dataStructures;

 
/**
 * Arvore de pesquisa binaria AVL de chaves Integer que armazena dados de tipo Double.
 * Nao suporta remoçao de dados.
 * 
 * @author Pitagoras Alves
 * @version 24-04-2015
 */
public class AVLTreeDouble
{
    private BinNodeDouble root;
    private int lastKey;

    /**
     * Construtor para objetos da classe AVLTreeDouble.
     * Cria uma arvore vazia.
     */
    public AVLTreeDouble()
    {
        lastKey = 0;
    }
    
    
    /**
     * Este método redefine o no raiz da arvore. Caso o no nao venha de outra arvore AVL,
     * há o risco de quebrar a regra de balanceamento das arvores AVL.
     * @param   node    Novo no raiz;
     */
    public void setRoot(BinNodeDouble node){
        root = node;
    }
    
    public BinNodeDouble getRoot(){
        return root;
    }
    
    /**
     * Insere um novo valor na arvore com uma chave aleatoria.
     * @param data Valor para ser inserido;
     * @return Chave atribuida ao valor inserido;
     */
    public int insert(Double data){
        lastKey++;
        this.insert(lastKey, data);
        return lastKey;
    }
    
    /**
     * Insere um novo valor nesta arvore de pesquisa, com uma chave especifica.
     * 
     * @param key Chave que identifica o valor;
     * @param data Valor a ser inserido;
     */
    public void insert(int key, Double data){
        BinNodeDouble newNode = new BinNodeDouble(key, data);
        if(root == null){
            root = newNode;
        }else{
            insert(root, newNode);
        }
    }
    
    /**
     * Implementa a rotação esquerda utilizada em arvores AVL para manter o balanceamento.
     */
    public void rightRotation(BinNodeDouble node){
        BinNodeDouble b, c, d;
        d = node.getFather();
        c = node;
        b = node.getLeftSon();
        
        if(d != null){
            if(c == d.getRightSon()){
                d.setRightSon(b);
            }else{
                d.setLeftSon(b);
            }
            
        }else{
            b.setFather(null);
        }
        
        if(node == root){
            root = b;
        }
        
        c.setLeftSon(b.getRightSon());
        b.setRightSon(c);
        c.setHeight();
        b.setHeight();
        if(d != null){
            d.setHeight();
        }
        
        //c.setFather(b);*/
    }
    
    /**
     * Implementa a rotação direita utilizada em arvores AVL para manter o balanceamento.
     */
    public void leftRotation(BinNodeDouble node){
        
        BinNodeDouble z, a, b;
        z = node.getFather();
        a = node;
        b = node.getRightSon();
        
        if(z != null){
            if(a == z.getRightSon()){
                z.setRightSon(b);
            }else{
                z.setLeftSon(b);
            }
            
        }else{
            b.setFather(null);
        }
        
        //b.setFather(z);
        if(node == root){
            root = b;
        }
        
        a.setRightSon(b.getLeftSon());
        
        //a.setFather(b);
        b.setLeftSon(a);
        a.setHeight();
        b.setHeight();
        if(z != null){
            z.setHeight();
        }
    }
    
    /**
     * rotação composta pela rotação direita seguida de uma rotação esquerda.
     */
    public void rlRotation(BinNodeDouble node){
        rightRotation(node.getRightSon());
        leftRotation(node);
    }
    
    /**
     * rotação composta pela rotação esquerda seguida de uma rotação direita.
     */
    public void lrRotation(BinNodeDouble node){
        leftRotation(node.getLeftSon());
        rightRotation(node);
    }
    
    /**
     * Insere um novo nó numa sub-arvore.
     * 
     * @param   node    Nó inicial que indica a sub-arvore onde o 
     *                  novo nó será adicionado.
     * @param   newNode Novo nó que será adicionado a esta sub-arvore
     */
    private void insert(BinNodeDouble node, BinNodeDouble newNode){
        if(newNode.getKey() == node.getKey()){
            //N�o faz nada, a chave já existente
            System.out.println("Conflito de chaves");
        }else {
            boolean insertedOnLeft;
            if(newNode.getKey() > node.getKey()){
                if(node.getRightSon() == null){
                    node.setRightSon(newNode);
                }else{
                    insert(node.getRightSon(), newNode);
                }
                insertedOnLeft = false;
            }else{
                if(node.getLeftSon() == null){
                    node.setLeftSon(newNode);
                }else{
                    insert(node.getLeftSon(), newNode);
                }
                insertedOnLeft = true;
            }
            node.setHeight();
            if(node.getBalance() == 2 || node.getBalance() == -2){
                int sonBalance;
                if(insertedOnLeft){
                    sonBalance = node.getLeftSon().getBalance();
                }else{
                    sonBalance = node.getRightSon().getBalance();
                }
                
                if(node.getBalance() < 0){
                    if(sonBalance < 0){
                        //BinNodeDouble.setHeight(node);
                        leftRotation(node);
                        //BinNodeDouble.setHeight(node);
                        //rotation = "right rotation";
                    }else{
                        //BinNodeDouble.setHeight(node);
                        rlRotation(node);
                        //BinNodeDouble.setHeight(node);
                        //rotation = "rl rotation";
                    }
                }else{
                    if(sonBalance > 0){
                        //BinNodeDouble.setHeight(node);
                        rightRotation(node);
                        //BinNodeDouble.setHeight(node);
                        //rotation = "left rotation";
                    }else{
                        //BinNodeDouble.setHeight(node);
                        lrRotation(node);
                        //BinNodeDouble.setHeight(node);
                        //rotation = "lr rotation";
                    }
                }
            }
        }
    }
    
    /**
     * Verifica se uma certa chave está contida nesta arvore.
     * 
     * @param   key Chave à ser pesquisada.
     * @return      Se a chave está presente ou não.
     */
    public boolean contains(int key){
        if(search(key) == null){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * Pesquisa uma chave nesta arvore.
     * 
     * @param   key Chave à ser pesquisada.
     * @return      Nó que contém a chave pesquisada.
     *              Caso a chave não esteja presente, retorna null.
     */
    public BinNodeDouble search(int key){
        BinNodeDouble node = root;
        
        while(node != null){
            if(node.getKey() == key){
                return node;
            }else if(node.getKey() > key){
                node = node.getLeftSon();
            }else{
                //se key n�o � igual nem menor, deve ser maior.
                node = node.getRightSon();
            }
        }
        
        return null;
    }
    
    public String toString(){
        if (root == null){
            return "(Arvore Vazia)";
        }else{
            return root.toString();
        }
    }
    
    /**
     * Imprime o conteudo da arvore na tela.
     */
    public void print(){
        System.out.println(this);
    }
    
    /**
     * @return Iterador para percorrer os elementos desta arvore.
     */
    public BinTreeIteratorDouble getIterator(){
        try{
            return new BinTreeIteratorDouble(root);
        }catch(Exception e){
            return null;
        }
    }
    
    /**
     * Numero de n�s de uma sub-arvore.
     * @param node Nó inicial de uma sub-arvore.
     * @return Numero de nós desta sub-arvore.
     */
    public static int numberOfElements(BinNodeDouble node){
        if(node == null){
            return 0;
        }else{
            return 1 + numberOfElements(node.getRightSon()) + numberOfElements(node.getLeftSon());
        }
    }
}