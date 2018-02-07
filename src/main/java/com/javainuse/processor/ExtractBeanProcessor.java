package com.javainuse.processor;

import org.springframework.batch.item.ItemProcessor;

import com.javainuse.util.ExtractPojo;


public class ExtractBeanProcessor implements ItemProcessor<ExtractPojo, ExtractPojo> {

	public ExtractPojo process(ExtractPojo item) throws Exception {
		String outFileName = item.getClient_ID() +"_"+ item.getBatchSeq()+"_"+ item.getSystdate();
		item.setOutFileName(outFileName);
		return item; 
	}

	
}