package com.javainuse.util;

public class ExtractPojo {
	private String client_ID;
	private String batchSeq;
	private String Systdate;
	private String outFileName;
	
	public String getClient_ID() {
		return client_ID;
	}
	public void setClient_ID(String client_ID) {
		this.client_ID = client_ID;
	}
	public String getBatchSeq() {
		return batchSeq;
	}
	public void setBatchSeq(String batchSeq) {
		this.batchSeq = batchSeq;
	}
	public String getSystdate() {
		return Systdate;
	}
	public void setSystdate(String systdate) {
		Systdate = systdate;
	}
	
	public String getOutFileName() {
		return outFileName;
	}
	public void setOutFileName(String outFileName) {
		this.outFileName = outFileName;
	}
	
	public String toString() {
		return "clientId:" + client_ID + "batchSeq:" + batchSeq + "Systdate:" + Systdate;
		
	}

}