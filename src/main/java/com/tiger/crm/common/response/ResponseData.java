package com.tiger.crm.common.response;

import com.tiger.crm.common.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseData {

	private int code;
	private String message;
	private Object data;

	public ResponseData(){
	}

	public ResponseData(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	@Builder
	public ResponseData(int code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}


	public static ResponseData ERROR(String message) {
		return ResponseData.builder()
						.code(ErrorCode.FAIL.getErrorCode())
						.message(message)
						.build();
	}



}
