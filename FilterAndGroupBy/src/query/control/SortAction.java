/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.control;

import grid.Grid;
import java.util.Collections;
import java.util.List;
import query.model.QueriedRange;
import query.model.QueriedResult;
import query.model.Sorting;
import query.model.ValueCompare;
import test.Main;

/**
 *
 * @author admin
 */
public class SortAction {
    
    public void sort(Grid grid,QueriedRange range,int col) throws Exception
    {
        Sorting sort=new Sorting(col);
        ValueCompare valueCompare=new ValueCompare(col,range,grid);
        QueriedResult orginalResult=range.getQueriedResult();
        QueriedResult clonedResult= new QueriedResult(orginalResult.getRow(),orginalResult.getValue());
        SortAction.action(orginalResult,clonedResult,valueCompare);
        sort.setQueriedResult(clonedResult);
        range.addResult(sort);
        
    }
    private static void action(QueriedResult queriedResult,QueriedResult clonedResult,ValueCompare valueCompare)
    {
         if (queriedResult.getNextAction().isEmpty()) {
            Collections.sort(clonedResult.getRow(), valueCompare);
        } else {
            for (QueriedResult nextQR : queriedResult.getNextAction()) {
                QueriedResult nextCR=new QueriedResult(nextQR.getRow(),nextQR.getValue());
                clonedResult.getNextAction().add(nextCR);
                SortAction.action(nextQR,nextCR,valueCompare);
            }
        }
    }
    
}
