package com.skplanet.pinky.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.skplanet.pinky.domain.Member;

public class MemberValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return MemberValidator.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Member member = (Member)target;
		if(member.getName() == null || member.getName().trim().isEmpty()) {
			errors.rejectValue("name", "required");
		}
		if(member.getPersonalId() < 1000000 || member.getPersonalId() > 9999999) {
			errors.rejectValue("personalId", "out of range");			
		}
		if(member.getPhoneNumber() > 1999999999 || member.getPhoneNumber() < 100000000) {
			errors.rejectValue("phoneNumber", "out of range");
		}
	}

}
