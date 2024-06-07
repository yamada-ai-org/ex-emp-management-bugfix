package com.example.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 管理者情報登録時に使用するフォーム.
 * 
 * @author igamasayuki
 * 
 */
public class InsertAdministratorForm {
	/** 名前 */
	@NotBlank(message = "名前を入力してください")
	private String name;
	/** メールアドレス */
	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "形式が不正です")
	private String mailAddress;
	/** パスワード */
	@NotBlank(message = "パスワードを入力してください")
	@Size(min = 8, max = 16, message = "パスワードは8文字以上16文字以下で入力してください")
	private String password;
	

	@NotBlank(message = "確認用パスワードを入力してください")
	private String confirmPassword;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "InsertAdministratorForm{" +
				"name='" + name + '\'' +
				", mailAddress='" + mailAddress + '\'' +
				", password='" + password + '\'' +
				", confirmPassword='" + confirmPassword + '\'' +
				'}';
	}
}
