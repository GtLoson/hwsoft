package com.hwsoft.common.product;

public enum ProductRecoveryRecordType {

	NORMAL(){

		@Override
		public String toString() {
			return "正常还款";
		}
		
	},
	ADVANCE(){
		@Override
		public String toString() {
			return "提前还款";
		}
	},
	GUARANTE(){
		@Override
		public String toString() {
			return "垫付还款";
		}
	}
	
}
