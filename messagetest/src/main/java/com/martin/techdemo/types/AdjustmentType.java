package com.martin.techdemo.types;

public enum AdjustmentType {
	
	UP ("UP"),
	DOWN ("DOWN"),
	MULTIPLY ("MULTIPLY");
	
	private String operationTypeCode;
	
	private AdjustmentType(String operationTypeCode) {
		this.operationTypeCode=operationTypeCode;
	}
	
	public String getOperationTypeCode() {
		return operationTypeCode;
	}
}
