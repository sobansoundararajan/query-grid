/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import grid.Value;

/**
 *
 * @author admin
 */
public interface GroupByCriteria {
    public abstract Value getKey(Value value);
}
