package com.hwsoft.common.template;

/**
 * 
 * @author tzh
 *
 */
public enum TemplateType {

	
	PRODUCTE_DETAIL_CONTENT(){

		@Override
		public String toString() {
			return "产品详情网页模板";
		}
		
	},
	
	SECURITY_CONTENT(){

		@Override
		public String toString() {
			return "安全保障网页模板";
		}
		
	}
	
}
