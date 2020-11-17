/**
 * 
 */

function formValidation() {
	var email = $('#email').val();
	var password = $('#password').val();
	var confirmpas = $('#confirmPassword').val();
	var fullname = $('#fullname').val();
	var birthday = $('#birthday').val();
	var gender = $('input[name="gender"]:checked').val();
	
	if (email == "") {
		$('#errorEmail').show();
		$('#errorEmail').text("Vui lòng nhập email");
		return false;
	} else {
		$('#errorEmail').hide();
		var pattern_email = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
		if (!pattern_email.test(email)) {
			$('#errorEmail').show();
			$('#errorEmail').text("Vui lòng nhập chính xác email");
			return false;
		} else {
			$('#errorEmail').hide();
		}
	}

	if (password != ""){
		if (password != confirmpas) {
			$('#errorConfirmPassword').show();
			$('#errorConfirmPassword').text("Xác nhận mật khẩu không chính xác");
			return false;
		} else {
			$('#errorConfirmPassword').hide();
		}
	}
	
	if (fullname == "") {
		$('#errorFullname').show();
		$('#errorFullname').text("Vui lòng nhập họ và tên");
		return false;
	}else {
		$('#errorFullname').hide();
		var pattern_fullname = /^([^\t]+)$/;
		if (!pattern_fullname.test(fullname)) {
			$('#errorFullname').show();
			$('#errorFullname').text(
					"Họ và tên chỉ chứa kí tự, chữ số và dấu khoảng trắng");
			return false;
		} else {
			$('#errorFullname').hide();
		}
	}
	
	if (birthday != "") {
		$('#errorBirthdayAdd').hide();
		var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
		if (!dateformat.test(birthday)) {
			$('#errorBirthday').show();
			$('#errorBirthday').text("Ngày sinh không hợp lệ");
			return false;
		}else if (!validatedate(birthdayAdd)) {
			$('#errorBirthday').show();
			$('#errorBirthday').text("Ngày sinh không hợp lệ");
			return false;
		}
	} else {
		$('#errorBirthday').hide();
	}
	
	if (null == gender || "" == gender) {
		$('#errorGender').show();
		$('#errorGender').text("Vui lòng chọn giới tính");
		return false;
	}else {
		$('#errorGender').hide();
	}
	
	return true;
}