package com.tiger.crm.repository.enumerate;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum FeedHistoryType
{
	 REGISTRATION("REGISTRATION", "최초등록")
	,STATE_CHANGE("STATE_CHANGE", "상태변경");

	private final String value;
	private final String name;

	FeedHistoryType(String value, String name){
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
	        Stream.of(values()).collect(Collectors.toMap(FeedHistoryType::getValue, FeedHistoryType::name)));

	public static String ofName(final String value) {
        return FeedHistoryType.valueOf(MAP.get(value)).getName();
    }
}
