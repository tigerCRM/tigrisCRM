package com.tiger.crm.repository.dto.file;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileUploadDto
{
	private String 	userId;
	private String 	fileId;
	private Boolean firstUploaded;
}
