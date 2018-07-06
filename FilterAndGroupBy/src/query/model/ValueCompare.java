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
import query.model.QueriedRange;

/**
 *
 * @author admin
 */
public class ValueCompare implements Comparator<Integer> {
    
    private int col;
    private QueriedRange range;
    private Grid grid;
    public ValueCompare(int col,QueriedRange range,Grid grid) {
        this.col = col;
        this.range=range;
        this.grid=grid;
    }
    
    @Override
    public int compare(Integer r1, Integer r2) {
        
        if(grid.get(r1, col).getType().equals(DataTypes.String))
        {
            return grid.get(r1+range.getStartRow(), col+range.getStartCol()).getValue().toString().compareTo(grid.get(r2+range.getStartRow(), col+range.getStartCol()).getValue().toString());
        }
        else
        {
            return (int) (((double)grid.get(r1+range.getStartRow(), col+range.getStartCol()).getValue())-((double)grid.get(r1+range.getStartRow(), col+range.getStartCol()).getValue()));
        }
    }
    
}
