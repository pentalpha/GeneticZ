package br.ufrn.imd.pitagoras.geneticz.dataStructures;


/**
 * HeapMax generico em que cada nÛ possui chave Double.
 * 
 * @author Pitagoras Alves 
 * @version 1.0
 */
public class HeapMinDouble<D>
{
    private int actualSize, maxSize;
    
    private Object[] nodes;
    /**
     * Construtor para objetos da classe HeapMax
     * @param size Tamanho maximo do heap.
     */
    public HeapMinDouble(int size)
    {
        // inicializa vari√°veis de inst√¢ncia
        nodes = new Object[size+1];
        System.out.println("Tamanho instanciado do heap: " + size);
        maxSize = size;
        actualSize = 0;
    }

    public int getSize(){
        return actualSize;
    }
    
    /**
     * 
     * @return Maior elemento deste HeapMax
     */
    @SuppressWarnings("unchecked")
	public Node<Double, D> getMaxNode(){
        if(actualSize == 0){
            return null;
        }else{
            Node<Double, D> node = (Node<Double, D>) nodes[1];
            return node;
        }
    }
    
    /**
     * @return Valor do maior elemento deste HeapMax
     */
    @SuppressWarnings("unchecked")
	public D getMin(){
        if(actualSize == 0){
            return null;
        }else{
            Node<Double, D> node = (Node<Double, D>) nodes[1];
            
            return node.getData();
        }
    }
    
    private int parent(int i){
        if(i == 1){
            return 0;
        }else{
            return i/2;
        }
    }
    
    private int leftChild(int i){
        int n = 2*i;
        if(n <= maxSize){
            return n;
        }else{
            return 0;
        }
    }
    
    private int rightChild(int i){
        int n = (2*i) + 1;
        if(n <= maxSize){
            return n;
        }else{
            return 0;
        }
    }
    
    /**
     * Sobe um elemento do heap.
     * @param i indice do elemento
     */
    private void subir(int i){
        if(i == 1){
            return;
        }else{
            if((value(parent(i)).getKey() > value(i).getKey()) 
            || (nodes[parent(i)] == null)){
                Object aux = nodes[parent(i)];
                nodes[parent(i)] = nodes[i];
                nodes[i] = aux;
                
                subir(parent(i));
            }
        }
    }
    
    /**
     * Acessa o valor de um indice do HeapMax
     * @param i indice do elemento deste HeapMax.
     * @return Valor do elemento.
     */
    @SuppressWarnings("unchecked")
	private Node<Double, D> value(int i){
        if(i < 1 || i > actualSize){
            return null;
        }else{
            return (Node<Double, D>) nodes[i];
        }
    }
    
    /**
     * Desce um elemento do heap.
     * @param i indice do elemento
     */
    private void descer(int i){
        
        if(i >= actualSize || i == 0){
            return;
        }else{
            //System.out.println("Descer" + i);
            int child = 0;
            
            
            if(value(rightChild(i)) != null){
            	
            	/*Node<Double, D> rightChild = value(rightChild(i));
                //double rightChildKey = rightChild.getKey();
                
                Node<Double, D> leftChild = value(leftChild(i));
                //double leftChildKey = leftChild.getKey();
            	if(rightChild != null && leftChild != null){
            		if(rightChild.getKey() > leftChild.getKey()){
                        child = rightChild(i);
                    }else{
                    	child = leftChild(i);
                    }
            	}else if(rightChild != null){
            		child = rightChild(i);
            	}else{
            		child = leftChild(i);
            	}*/
            	if(value(leftChild(i)) != null){
            		if(value(rightChild(i)).getKey() < value(leftChild(i)).getKey()){
                        child = rightChild(i);
                    }else{
                    	child = leftChild(i);
                    }
            	}else{
            		child = rightChild(i);
            	}
            	
            }else if (value(leftChild(i)) != null){
                child = leftChild(i);
            }    
            
            if(child > 0){
                if(value(child).getKey() < value(i).getKey()){
                    
	                Object aux = nodes[i];
	                nodes[i] = nodes[child];
	                nodes[child] = aux;
	                
	                descer(child);
            	}
            }
        }
    }
    
    /**
     * Insere novo elemento. Caso o HeapMax esteja cheio, nada ser· inserido.
     * @param key Chave do elemento
     * @param data Valor do elemento.
     */
    public void insert(Double key, D data){
        if(actualSize < maxSize){
            actualSize++;
            Node<Double, D> node = new Node<Double, D>(key, data);
            
            nodes[actualSize] = node;
            
            subir(actualSize);
        }
    }
    
    /**
     * Remove o elemento maximo deste HeapMax, diminuindo assim seu tamanho atual.
     */
    public void removeMax(){
        if(actualSize > 0){
            nodes[1] = nodes[actualSize];
            actualSize--;
            descer(1);
        }
    }
}


