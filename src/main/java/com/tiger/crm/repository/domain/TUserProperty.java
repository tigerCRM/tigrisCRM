package com.tiger.crm.repository.domain;

import com.tiger.crm.common.TigrisGlobal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <code>TUserProperty.java</code>
 *
 * @author Jaehwan Lee
 * @since 2016. 3. 14.
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class TUserProperty extends Common {

	private String userPropertyId;
	private String userId;
	private String kind;
	private String type;
	private String value;

	public TUserProperty() {
		super();
		this.userPropertyId = TigrisGlobal.generateID();
	}

	public TUserProperty(String userId) {
		super(userId);
		this.userPropertyId = TigrisGlobal.generateID();
	}

}
