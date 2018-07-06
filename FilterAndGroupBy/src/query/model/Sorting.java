/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class Sorting implements GroupByAndFilter{
    private final int col;
    private QueriedResult queriedResult;

    public Sorting(int col) {
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public QueriedResult getQueriedResult() {
        if(queriedResult==null)
               try {
                   throw new Exception("This Result is Reseted to null");
        } catch (Exception ex) {
            Logger.getLogger(Sorting.class.getName()).log(Level.SEVERE, null, ex);
        }
        return queriedResult;
    }

    public void setQueriedResult(QueriedResult queriedResult) {
        this.queriedResult = queriedResult;
    }
    
    
    
    
}
