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

    public Value evaluatedFormula(Grid grid, int startRow, ColumnFormula columnFormula) {
        Value value = this.functionMap.get(columnFormula);
        if (value != null) {
            return value;
        } else {
            List<Value> valueList = new LinkedList();
            int col = columnFormula.getCol();
            for (int row : this.rowList) {
                valueList.add(grid.get(row + startRow, col));
            }
            value = columnFormula.getFormula().getValue(valueList);
            this.functionMap.put(columnFormula, value);
            return value;
        }
    }

    public void inValidateColumnFormula() {
        this.functionMap = new HashMap();
    }
}
