package br.ufrn.imd.pitagoras.geneticz.dataStructures;


/**
 * Classe de n� generico com chave para ser utilizado em um Heap.
 * 
 * @author Pitagoras Alves 
 * @version 1.0
 */
public class Node<K, D>
{

    private K key;
    private D data;

    /**
     * Construtor para objetos da classe Node
     * @param key Chave do n�.
     * @param data Dados de para serem armazenados.
    */
    public Node(K key, D data)
    {
        // inicializa variáveis de instância
        this.key = key;
        this.data = data;
    }

    public K getKey(){
        return key;
    }
    
    public D getData(){
        return data;
    }
}
