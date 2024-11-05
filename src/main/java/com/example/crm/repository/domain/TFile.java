package com.example.crm.repository.domain;

import com.example.crm.core.TigrisGlobal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TFile extends Common
{
	private String 	fileId;
	private int 	seq;
	private String 	filePath;
	private String 	fileName;
	private String 	originFileName;
	private long 	fileSize;
	private String 	fileExt;
	private String 	mimeType;
	private String 	userAgent;
	private String 	createDtStr;
	private String 	previewYn 	= "N";
	private String 	deleteYn 	= "N";

	public TFile()
	{
		super();
		this.fileId = TigrisGlobal.generateID();
	}

	public TFile(String userId)
	{
		super(userId);
		this.fileId = TigrisGlobal.generateID();
	}
}
