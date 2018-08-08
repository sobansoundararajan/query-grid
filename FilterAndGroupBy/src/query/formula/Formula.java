/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.formula;

import grid.Value;
import java.util.List;

/**
 *
 * @author admin
 */
public interface Formula {

    public abstract Value getValue(List<Value> valueList);
}
