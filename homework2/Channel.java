package homework2;

import java.util.*;

/**
 * a Channel is a class representing channel which is connected only to participants, in both in and out edges.
 * It's implemented Simulatable of String.
 * a Channel is identified by it's label, and has limit of product's amount it can hold.
 */

public class Channel implements Simulatable<String>{

    /**
     * Abstraction function:	a channel holds in a bipartite graph as a white node,
     *                          so it is connected only to black nodes - participant.
     *                          It is holds the limit in '_limit'.
     *                          It is holds the products in list '_trans'.
     *                          It is holds the amount of products it currently has in '_curAmount'.
     */

    /**
     * Rep. Invariant:	_limit >= _curAmount && _curAmount >= 0.
     */

    private String _label;
    private int _limit;
    private int _curAmount = 0;
    private List<Transaction> _trans = new ArrayList<Transaction>();

    private boolean checkRep(){
        return _limit >= _curAmount && _curAmount >= 0;
    }

    /**
     * @modifies this
     * @effects Constructs a new Channel.
     */
    public Channel(String label, int limit){
        _label = label;
        _limit = limit;
        assert checkRep();
    }

    /**
     * @return a random element from 'collection' if it's not empty.
     */
    private static <T> Optional<T> getRandomElement (Collection<T> collection) {
        return collection.stream().skip((int) (collection.size() * Math.random())).findFirst();
    }

    /**
     * @modifies this, graph
     * @effects Simulates this pipe in a system modeled by graph
     * @param graph - the containing graph.
     */
    @Override
    public void simulate(BipartiteGraph<String> graph) {
        assert checkRep();
        Iterator<Transaction> transIter = _trans.iterator();
        while(transIter.hasNext()) {
            Transaction t = transIter.next();
            Collection<String> childrenNames = new ArrayList<>(graph.listChildren(_label));
            Optional<String> dest = getRandomElement(childrenNames);
            if(dest.isPresent()){//always successful transaction
                Participant child = (Participant)graph.findNode(dest.get()).getData();
                child.addTrans(t);
                _curAmount-=t.getAmount();
                transIter.remove();
                break;
            }
        }
        assert checkRep();
    }

    /**
     * @modifies this
     * @effects add transaction t to the channel if _curAmount < _limit
     */
    public boolean addTrans(Transaction t){
        assert checkRep();
        if(_curAmount == _limit){
            return false;
        }
        int addedAmount = 0;
        addedAmount = Math.min(_limit - _curAmount, t.getAmount());
        assert addedAmount > 0;//zero Transaction should disappear before
        t.setAmount(addedAmount);
        _trans.add(t);
        _curAmount += addedAmount;
        assert checkRep();
        return true;
    }

    /**
     * @return A string containing all the channel's transaction's amount, space-separated
     */
    public String getTransactions(){
        assert checkRep();
        StringBuilder spaceSeparated = new StringBuilder();
        for (Transaction t : _trans) {
            spaceSeparated.append(t.getAmount()).append(" ");
        }
        assert checkRep();
        return spaceSeparated.toString().trim();
    }

}
