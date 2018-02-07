package com.javainuse.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.javainuse.util.ExtractPojo;


public class ClaimsRowMapper implements RowMapper<ExtractPojo>{

	public ExtractPojo mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ExtractPojo extractPojo = new ExtractPojo();
		extractPojo.setClient_ID(rs.getString("CLIENT_ID"));
		extractPojo.setBatchSeq(rs.getString("BATCHSEQ"));
		extractPojo.setSystdate(rs.getString("CREATEDATE"));
		return extractPojo;
	}

}