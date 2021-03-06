package org.javaosc.galaxy.convert;

import java.util.HashMap;
import java.util.Map;

import org.javaosc.galaxy.assist.ClassHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @description
 * @author Dylan Tao
 * @date 2014-09-09
 * Copyright 2014 Javaosc Team. All Rights Reserved.
 */
public class ConvertFactory {
	
	private static final Logger log = LoggerFactory.getLogger(ConvertFactory.class);
	
	static final String PREFIX = "_$PARAM_TYPE_";
	
	private static Map<String,Convert<?,?>> typeConvert = new HashMap<String, Convert<?,?>>();
	
	static{
		
		typeConvert.put(PREFIX + Integer.class.getName(), new IntegerConvert());
		typeConvert.put(PREFIX + String.class.getName(), new StringConvert());
		typeConvert.put(PREFIX + Long.class.getName(), new LongConvert());
		typeConvert.put(PREFIX + Byte.class.getName(), new ByteConvert());
		typeConvert.put(PREFIX + Double.class.getName(), new DoubleConvert());
		typeConvert.put(PREFIX + Short.class.getName(), new ShortConvert());
		typeConvert.put(PREFIX + Float.class.getName(), new FloatConvert());
		typeConvert.put(PREFIX + Boolean.class.getName(), new BooleanConvert());
		
		typeConvert.put(PREFIX + Integer[].class.getName(), new IntegerWrapArrConvert());
		typeConvert.put(PREFIX + String[].class.getName(), new StringArrConvert());
		typeConvert.put(PREFIX + Long[].class.getName(), new LongWrapArrConvert());
		typeConvert.put(PREFIX + Byte[].class.getName(), new ByteWrapArrConvert());
		typeConvert.put(PREFIX + Double[].class.getName(), new DoubleWrapArrConvert());
		typeConvert.put(PREFIX + Short[].class.getName(), new ShortWrapArrConvert());
		typeConvert.put(PREFIX + Float[].class.getName(), new FloatWrapArrConvert());
		typeConvert.put(PREFIX + Boolean[].class.getName(), new BooleanWrapArrConvert());
		
		typeConvert.put(PREFIX + int.class.getName(), new IntegerConvert());
		typeConvert.put(PREFIX + long.class.getName(), new LongConvert());
		typeConvert.put(PREFIX + byte.class.getName(), new ByteConvert());
		typeConvert.put(PREFIX + double.class.getName(), new DoubleConvert());
		typeConvert.put(PREFIX + short.class.getName(), new ShortConvert());
		typeConvert.put(PREFIX + float.class.getName(), new FloatConvert());
		typeConvert.put(PREFIX + boolean.class.getName(), new BooleanConvert());
		
		typeConvert.put(PREFIX + int[].class.getName(), new IntegerArrConvert());
		typeConvert.put(PREFIX + long[].class.getName(), new LongArrConvert());
		typeConvert.put(PREFIX + byte[].class.getName(), new ByteArrConvert());
		typeConvert.put(PREFIX + double[].class.getName(), new DoubleArrConvert());
		typeConvert.put(PREFIX + short[].class.getName(), new ShortArrConvert());
		typeConvert.put(PREFIX + float[].class.getName(), new FloatArrConvert());
		typeConvert.put(PREFIX + boolean[].class.getName(), new BooleanArrConvert());
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T convert(Class<T> cls,Object val){  
		 Convert cv = typeConvert.get(PREFIX + cls.getName());  
	     if(cv == null){  
	        log.warn("{} convert failed: unsupport type, value: {}", cls.getName(), val);  
	        return null;  
	     }  
	     return (T)cv.convert(val);
	}  
	
	@SuppressWarnings("rawtypes")
	public static void put(Class<?> implCls){
		String key = PREFIX + implCls.getName();
		if(ClassHandler.isImplClass(implCls, Convert.class) && !typeConvert.containsKey(key)){
			try {
				typeConvert.put(key, (Convert)ClassHandler.newInstanceObj(implCls));
			} catch (Exception e) {
				log.warn("{} convert put failed: {}", implCls.getName(), e); 
			}
		}
	}
	
}
