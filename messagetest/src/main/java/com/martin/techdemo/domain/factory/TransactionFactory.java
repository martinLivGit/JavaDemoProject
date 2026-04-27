package com.martin.techdemo.domain.factory;

import com.martin.techdemo.domain.AdjustmentTransaction;
import com.martin.techdemo.domain.definitions.Fields;
import com.martin.techdemo.domain.SaleTransaction;
import org.json.simple.JSONObject;

import com.martin.techdemo.types.AdjustmentType;
import com.martin.techdemo.util.Logger;

public class TransactionFactory implements Fields {
	
	public static SaleTransaction createSaleTransaction(JSONObject jmessage) throws Exception{
		JSONObject jsale = null;
		try{	
			jsale = (JSONObject) jmessage.get(SALE);
			long saleQuantity = DEFAULT_QUANTITY;
			String company = (String) jmessage.get(COMPANY); 
	        long identifier = (Long) jmessage.get(IDENTIFIER); 		
	        double saleAmount = (Double) (jsale.get(AMOUNT));
	        if ((Long) jsale.get(QUANTITY) != null) {
	        	saleQuantity = (Long) jsale.get(QUANTITY);
	        }
	        return new SaleTransaction(company, identifier, saleAmount, saleQuantity);
		}
	    catch (Exception  e) {
        	Logger.log("\ncreateSaleTransaction:Problem parsing message:%s%n", e.getMessage()); 
        	if ( jsale != null ) {
        		Logger.log("createSaleTransaction:Message:%s%n", jsale.toJSONString());
        	}
        	throw e;
	    }
	}
	
	public static AdjustmentTransaction createAdjustmentTransaction(JSONObject jmessage) throws Exception {
		JSONObject jadjustment = null;
		try {
			jadjustment = (JSONObject) jmessage.get(ADJUSTMENT);
			if (jadjustment == null) {
				return null;
			}
		    String company = (String) jmessage.get(COMPANY);
		    long identifier = (Long) jmessage.get(IDENTIFIER); 
		    String adjustmentOperation = (String) jadjustment.get(OPERATION);
		    if ( ! ( adjustmentOperation.equals(AdjustmentType.UP.getOperationTypeCode())
		    		 || adjustmentOperation.equals(AdjustmentType.DOWN.getOperationTypeCode())
		    		 //|| adjustmentOperation.equals(AdjustmentType.MULTIPLY.getOperationTypeCode()) 
		            ) 
		    	) {
		    	throw new Exception("Invalid adjustment operation");
		    }
	        double adjustmentAmount = (Double) jadjustment.get(AMOUNT);
	        return new AdjustmentTransaction(company, identifier, adjustmentOperation, adjustmentAmount) ;
		}
	    catch (Exception e ) {
	    	Logger.log("\ncreateAdjustmentTransaction:Problem parsing message:%s%n", e.getMessage()); 
	    	Logger.log("createAdjustmentTransaction:Message:%s%n", jadjustment);
	    	throw e;
	    } 
 	}

}
