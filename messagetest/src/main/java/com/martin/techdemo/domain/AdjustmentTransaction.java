package com.martin.techdemo.domain;

import java.util.Objects;

import com.martin.techdemo.types.AdjustmentType;

public class AdjustmentTransaction extends Transaction {
	
	private AdjustmentType operation;
	private double amount;
	
	public AdjustmentTransaction(String company, long identifier, String operation, double amount) {
		super(company, identifier);
		this.amount = amount;		
		if (operation.equals(AdjustmentType.UP.getOperationTypeCode())) {
			this.operation=AdjustmentType.UP;
		} 
		else if (operation.equals(AdjustmentType.DOWN.getOperationTypeCode())) {
			this.operation=AdjustmentType.DOWN;
		}
		else if (operation.equals(AdjustmentType.MULTIPLY.getOperationTypeCode())) {
			this.operation=AdjustmentType.MULTIPLY;
		}
	}
	
	public AdjustmentType getOperation() {
		return operation;
	}
	
	public double getAmount() {
		return amount;
	}
	
	@Override
	public String toString() {
		return ":<Adjustment" + super.toString() + String.format(":%s:%s>", operation, amount);
	}
	
	@Override
    public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		else if (!(o instanceof AdjustmentTransaction)) {
            return false;
        }
		else {
        	AdjustmentTransaction s = (AdjustmentTransaction) o;
        	return identifier == s.identifier &&
             	   company.equals(s.company); 
        }          	
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, company, this.getClass().getName());
    }
		
}
