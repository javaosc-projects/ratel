package org.javaosc.framework.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WrapByteArrConvert implements Convert<Object[],Byte[]>{
	
	private static final Logger log = LoggerFactory.getLogger(WrapByteArrConvert.class);

	@Override
	public Byte[] convert(Object[] source) {
		if(source == null) return null;  
		Byte[] res = new Byte[source.length];  
        for(int i=0;i<source.length;i++){  
            try {  
                res[i] = Byte.parseByte(String.valueOf(source[i]));  
            } catch (NumberFormatException e) {  
            	log.info("WrapByteArrConvert failed, value: {} exception: {}", String.valueOf(source[i]), e);  
                return null;  
            }  
        }  
        return res;  
	}

}
