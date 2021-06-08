package com.hazir.Hazirlaniyor.entity.concretes;

import org.intellij.lang.annotations.JdkConstants;
import org.springframework.ui.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChargeParameter {
	private final String email;
	private final ChargeRequest mChargeRequest;
	private final Model mModel;



}
