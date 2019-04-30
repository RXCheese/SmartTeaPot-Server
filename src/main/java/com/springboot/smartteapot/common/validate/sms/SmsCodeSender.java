package com.springboot.smartteapot.common.validate.sms;

/**

 */
public interface SmsCodeSender {
	
	/**
	 * @param mobile
	 * @param code
	 */
	void send(String mobile, String code);

}
