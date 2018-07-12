/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import grid.Value;
import java.util.*;
import java.util.function.BiFunction;

/**
 *
 * @author admin
 */
public class QueriedResult {

    private List<Integer> row;
    private final List<Object> value;
    private List<QueriedResult> nextAction;
    private Map<FunctionCondition,Value>functionMap;
    
    public QueriedResult(List<Integer> row, List<Object> value) {
        this.row = row;
        this.value = value;
        this.nextAction = new LinkedList();
        this.functionMap= new HashMap ();
    }

    public void setFunctionMap(Map<FunctionCondition, Value> functionMap) {
        this.functionMap = functionMap;
    }

    public void setRow(List<Integer> row) {
        this.row = row;
    }


    public List<Integer> getRow() {
        return row;
    }

    public List<Object> getValue() {
        return value;
    }

    public List<QueriedResult> getNextAction() {
        return nextAction;
    }

    public boolean addNextAction(QueriedResult e) {
        return nextAction.add(e);
    }

    public void setNextAction(List<QueriedResult> nextAction) {
        this.nextAction = nextAction;
    }

    public Map<FunctionCondition, Value> getFunctionMap() {
        return functionMap;
    }    
}
