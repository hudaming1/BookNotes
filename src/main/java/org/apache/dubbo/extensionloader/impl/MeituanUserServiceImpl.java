package org.apache.dubbo.extensionloader.impl;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.extensionloader.UserService;

@Activate(group = { "B" }, value = { "MEI" })
public class MeituanUserServiceImpl implements UserService {

	@Override
	public String regist(String name) {
		return "regist meituan user " + name;
	}

	@Override
	public String login(URL url, String name) {
		return "login meituan user " + name;
	}

	@Override
	public String logout(String name) {
		return "logout meituan user " + name;
	}
}
