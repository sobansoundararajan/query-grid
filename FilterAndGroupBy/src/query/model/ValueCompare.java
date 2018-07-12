/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import grid.DataTypes;
import grid.Grid;
import grid.Value;
import java.util.Comparator;
import java.util.List;
import query.model.QueriedRange;

/**
 *
 * @author admin
 */
public class ValueCompare implements Comparator<Integer> {
    
    private final List<Integer> colList;
    private final QueriedRange range;
    private final Grid grid;
    public ValueCompare(List<Integer> colList,QueriedRange range,Grid grid) {
        this.colList = colList;
        this.range=range;
        this.grid=grid;
    }
    
    @Override
    public int compare(Integer r1, Integer r2) {
        
        for(Integer col:colList)
        {
        
        if(grid.get(r1, col).getType().equals(DataTypes.String))
        {
            int value=grid.get(r1+range.getStartRow(), col+range.getStartCol()).getValue().toString().compareTo(grid.get(r2+range.getStartRow(), col+range.getStartCol()).getValue().toString());
            if(value!=0)
                return value;
        }
        else
        {
            int value=(int) (((double)grid.get(r1+range.getStartRow(), col+range.getStartCol()).getValue())-((double)grid.get(r1+range.getStartRow(), col+range.getStartCol()).getValue()));
            if(value!=0)
                return value;
        }
        }
        return 0;
    }
    
}
