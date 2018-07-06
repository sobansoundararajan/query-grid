/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import java.util.*;

/**
 *
 * @author admin
 */
public class QueriedResult {

    private List<Integer> row;
    private List<Object> value;
    private List<QueriedResult> nextAction;

    public QueriedResult(List<Integer> row, List<Object> value) {
        this.row = row;
        this.value = value;
        this.nextAction = new LinkedList();
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
}
