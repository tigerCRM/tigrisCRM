package com.example.crm.repository.enumerate;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum State
{
	 WAITING_FOR_APPROVAL("WAITING_FOR_APPROVAL", "승인대기")
	,REQUEST_FOR_REVIEW("REQUEST_FOR_REVIEW", "검토의뢰")
	,UNDER_REVIEW("UNDER_REVIEW", "검토중")
	,REQUEST_FOR_CORRECTION("REQUEST_FOR_CORRECTION", "수정요구")
	,REJECT("REJECT", "반려")
	,REVIEW_COMPLETED("REVIEW_COMPLETED", "검토완료")
	,REPLY("REPLY", "댓글");

	private final String value;
	private final String name;

	State(String value, String name){
		this.value 	= value;
		this.name 	= name;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}


	private static final Map<String, String> MAP = Collections.unmodifiableMap(
	        Stream.of(values()).collect(Collectors.toMap(State::getValue, State::name)));

	public static String ofName(final String value) {
        return State.valueOf(MAP.get(value)).getName();
    }
}
