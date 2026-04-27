package com.martin.techdemo.domain;

import java.util.Objects;

import com.martin.techdemo.types.AdjustmentType;

public class Stock {
	
	private String name;
	private double price;

	public Stock(String name) {
		this.name = name;
	}
	
	public Stock(String name, double price) {
		this.name = name;
		this.price = price;
		//System.out.printf("%s:%s\n", name, price);
	}
	
	public String getName() {
		return name;
	}
	
	public void setPrice(double amount, AdjustmentType adjustment){
		if ( adjustment == AdjustmentType.UP) {
			price += amount ;
		}
		else if ( adjustment == AdjustmentType.DOWN) {
			price -= amount ;
		}
		//System.out.printf("%s:%s\n", name, price);
	}
	
	public void setPrice(double amount){		
		price = amount ;
		//System.out.printf("%s:%s\n", name, price);
	}
	
	public double getPrice(){
		return price;
	}
	
	@Override
	public String toString() {
		return ":<Stock:" + name + ">";
	}
	
	@Override
    public boolean equals(Object o) {

        if (o == this) { 
        	return true;
        }
        else if (!(o instanceof Stock)) {
        	return false;
        }
        else {
        	Stock p = (Stock) o;
        	return name.equals(p.getName());
        } 
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
  
}
