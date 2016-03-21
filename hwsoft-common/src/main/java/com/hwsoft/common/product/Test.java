package com.hwsoft.common.product;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Test {

	/**
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
			
		Field[] fields = ProductBuyerRecordStatus.class.getDeclaredFields();
        Field field = ProductBuyerRecordStatus.class.getField("USING_STATUS");
       
        boolean isStatic = Modifier.isStatic(field.getModifiers());
        
        if(isStatic) {
            System.out.println(field.get(null).toString());
        }
			

	}

}
