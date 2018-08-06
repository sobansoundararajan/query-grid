/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import java.util.Objects;

/**
 *
 * @author admin
 */
public class FunctionCondition {
    private final int col;
    private final Function function;

    public FunctionCondition(int col, Function function) {
        this.col = col;
        this.function = function;
    }

    public int getCol() {
        return col;
    }

    public Function getFunction() {
        return function;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.col;
        hash = 79 * hash + Objects.hashCode(this.function);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FunctionCondition other = (FunctionCondition) obj;
        if (this.col != other.col) {
            return false;
        }
        if (this.function != other.function) {
            return false;
        }
        return true;
    }
    
    
}
