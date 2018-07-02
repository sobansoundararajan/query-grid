/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grid;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class Grid {
    public static final int MAXROWS=2*65536;
    public static final int MAXCOLS=512;
    public final List<List<Value>>VALUES=new ArrayList (MAXROWS);

    public Grid() {
        //System.out.println("Grid Cons Started");
        for(int row=0;row<=MAXROWS;row++)
        {
            //System.out.println(row);
            this.VALUES.add(new ArrayList (MAXCOLS));
            for(int col=0;col<=MAXCOLS;col++)
            {
                this.VALUES.get(row).add(new StringValue(DataTypes.String,""));
            }
        }
    }

    public void set(int row,int col,Value v)
    {
        this.VALUES.get(row).set(col, v);
    }
    public Value get(int row,int col)
    {
        return VALUES.get(row).get(col);
    }
          
}
