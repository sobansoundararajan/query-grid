/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import java.util.Comparator;
import java.util.List;

/**
 *
 * @author admin
 */
public class FuctionCompare implements Comparator<QueriedResult>{

    private final List<FunctionSortCondition> functionSortConditionList;

    public FuctionCompare(List<FunctionSortCondition> functionSortConditionList) {
        this.functionSortConditionList = functionSortConditionList;
    }
    
    @Override
    public int compare(QueriedResult queriedResult1, QueriedResult queriedResult2) {
        for(FunctionSortCondition functionSortCondition:this.functionSortConditionList)
        {
            FunctionCondition functionCondition=functionSortCondition.getFunctionCondition();
            double val1=(double)queriedResult1.getFunctionMap().get(functionCondition).getValue();
            double val2=(double)queriedResult2.getFunctionMap().get(functionCondition).getValue();
            if(val1!=val2)
                if(functionSortCondition.getSortingCriteria().equals(SortingCriteria.ASCENDING))
                    return (int) (val1-val2);
                else
                    return ((int) (val1-val2))*-1;
        }
        return 0;
    }
    
}
