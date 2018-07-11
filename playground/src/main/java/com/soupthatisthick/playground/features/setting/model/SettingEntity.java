package com.soupthatisthick.playground.features.setting.model;

import com.soupthatisthick.playground.features.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="setting")
public class SettingEntity extends BaseEntity {

	@Id
	@Column(name="name", nullable=false)
	private String name;

	@Column(name="value")
	private String value;

	// ---------------------------------------------------- //

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
