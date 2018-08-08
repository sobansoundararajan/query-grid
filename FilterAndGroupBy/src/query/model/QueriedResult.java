/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import grid.Grid;
import grid.Value;
import java.util.*;
import query.formula.Formula;

/**
 *
 * @author admin
 */
public class QueriedResult {

    private List<Integer> rowList;
    private final List<Value> value;
    private List<QueriedResult> nextAction;
    private Map<ColumnFormula, Value> functionMap;

    public QueriedResult(List<Integer> row, List<Value> value) {
        this.rowList = row;
        this.value = value;
        this.nextAction = new LinkedList();
        this.functionMap = new HashMap();
    }

    public void setFunctionMap(Map<ColumnFormula, Value> functionMap) {
        this.functionMap = functionMap;
    }

    public void setRow(List<Integer> row) {
        this.rowList = row;
    }

    public List<Integer> getRow() {
        return rowList;
    }

    public List<Value> getValue() {
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

    public Map<ColumnFormula, Value> getFunctionMap() {
        return functionMap;
    }

    public Value evaluateFormula(Grid grid, QueriedRange range, ColumnFormula columnFormula) {
        Value value = null;
        if (columnFormula == null) {
            for (Map.Entry<ColumnFormula, Value> entry : this.functionMap.entrySet()) {
                List<Value> valueList = new LinkedList();
                ColumnFormula formula = entry.getKey();
                int col = formula.getCol();
                for (int row : this.rowList) {
                    valueList.add(grid.get(row, col));
                }
                value = formula.getFormula().getValue(valueList);
                entry.setValue(value);
            }
        } else {
            List<Value> valueList = new LinkedList();
            int col = columnFormula.getCol();
            for (int row : this.rowList) {
                valueList.add(grid.get(row+range.getStartRow(), col));
            }
            value = columnFormula.getFormula().getValue(valueList);
            this.functionMap.put(columnFormula, value);
        }
//        System.out.println(this.value.get(0).getValue()+" "+value.getValue()+" "+value.getType());
        return value;
    }
}
