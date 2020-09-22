/**
 * 
 */

$(document).ready(function() {
	
	$("#viewcn").hide();
	
	$('.view').on('click', function(event) {
		event.preventDefault();
		$('input[name="chucnangview[]"]').prop("checked", false);
		var href = $(this).attr('href');
		var id = GetURLParameter(href,'id');
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/user/oneUserCNS",
			data : {
				id : id
			},
			dataType : "json",
			timeout : 10000,
			success : function(data) {
				var temp = getData(data.chucnang);
				if (temp != null) {
					$('input[name="chucnangview[]"]').val(temp);
				}
			},
		});
		if ($("#viewcn").is(":hidden")) {
			$("#viewcn").show();
		}else{
			$("#viewcn").hide();
		}
	});
	
	$("#selectAll").click(function() {
		$('input[name="id[]"]').prop("checked", $(this).prop("checked"));
	});
	$('input[name="id[]"]').click(function() {
		if (!$(this).prop("checked")) {
			$("#selectAll").prop("checked", false);
		}
	});
	
	$('.table .editButton').on('click', function(event) {
		event.preventDefault();
		$('#roleEdit option:selected').each(function(){
		     $(this).prop('selected', false);
		});
		$('#roleEdit').multiselect('refresh');
		$('#chucnangEdit option:selected').each(function(){
		     $(this).prop('selected', false);
		});
		$('#chucnangEdit').multiselect('refresh');
		var href = $(this).attr('href');
		var id = GetURLParameter(href,'id');
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/user/oneUser",
			data : {
				id : id
			},
			dataType : "json",
			timeout : 10000,
			success : function(data) {
				$('#idEdit').val(data.id);
				$('#emailEdit').val(data.email);
				$('#fullnameEdit').val(data.fullname);
				$('#phoneEdit').val(data.phone);
				$('#birthdayEdit').val(data.birthday);
				$('input:radio[value="'+data.gender+'"]').prop('checked',true);
				$('#addressEdit').val(data.address);
				var role = getData(data.roles);
				$('#roleEdit').multiselect('select', role);
				var chucnang = getData(data.chucnangs);
				$('#chucnangEdit').multiselect('select', chucnang);
			},
		});
		var url = $('#formEdit').attr('action');
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/check",
			data : {
				url : url
			},
			dataType : "json",
			timeout : 10000,
			success : function(data) {
				if (data < 0) {
					alert("ACCESS DENID");
					$('#editModal').modal('hide');
				}else{
					$('#editModal').modal('show');
				}
			},

		});
	});

	$("#formAdd").submit(function(event) {
		event.preventDefault(); // prevent default action
		var check = true;
		var email = $('#emailAdd').val();
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/user/findByEmail",
			data : {
				email : email
			},
			dataType : "json",
			timeout : 10000,
			success : function(data) {
				if (data != "-1") {
					$('#errorEmailAdd').show();
					$('#errorEmailAdd').text("Email đã tồn tại");
					check = false;
				} else {
					$("#formAdd").unbind('submit').submit();
				}
			},

		});
	});

	$("#formEdit").submit(function(event) {
		event.preventDefault(); // prevent default action
		var check = true;
		var id = $('#idEdit').val();
		var email = $('#emailEdit').val();
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/user/findByEmail",
			data : {
				email : email
			},
			dataType : "json",
			timeout : 10000,
			success : function(data) {
				if (data != "-1" && data != id) {
					$('#errorEmailEdit').show();
					$('#errorEmailEdit').text("Email đã tồn tại");
					check = false;
				} else {
					$("#formEdit").unbind('submit').submit();
				}
			},

		});
	});
	
	$("#formFind").submit(function(event) {
		event.preventDefault(); // prevent default action
		var gender = $('#genderfind').val();
		$("#formFind").unbind('submit').submit();
	});
	
	$('.deleteButton').on('click',function(event){
		event.preventDefault();
		var url = $('#formDelete').attr('action');
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/check",
			data : {
				url : url
			},
			dataType : "json",
			timeout : 10000,
			success : function(data) {
				if (data < 0) {
					alert("ACCESS DENID");
					$('#deleteModal').modal('hide');
				}else{
					$('#deleteModal').modal('show');
				}
			},

		});
		$("#deleteId").click(function() {
			$("#formDelete").submit();
		});
	});
	
	$('.addButton').on('click',function(event){
		event.preventDefault();
		var url = $('#formAdd').attr('action');
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/check",
			data : {
				url : url
			},
			dataType : "json",
			timeout : 10000,
			success : function(data) {
				if (data < 0) {
					alert("ACCESS DENID");
					$('#addModal').modal('hide');
				}else{
					$('#addModal').modal('show');
				}
			},

		});
	});

});
$(function() {
	$('#roleAdd').multiselect({
		nonSelectedText : 'Chọn Vai trò',
		enableFiltering : true,
		enableCaseInsensitiveFiltering : true,
		includeSelectAllOption : true,
		buttonWidth : '100%'
	});
	$('#roleEdit').multiselect({
		nonSelectedText : 'Chọn Vai trò',
		enableFiltering : true,
		enableCaseInsensitiveFiltering : true,
		includeSelectAllOption : true,
		buttonWidth : '100%'
	});
	$('#chucnangAdd').multiselect({
		nonSelectedText : 'Chọn Chức năng',
		enableFiltering : true,
		enableCaseInsensitiveFiltering : true,
		includeSelectAllOption : true,
		buttonWidth : '100%'
	});
	$('#chucnangEdit').multiselect({
		nonSelectedText : 'Chọn Chức năng',
		enableFiltering : true,
		enableCaseInsensitiveFiltering : true,
		includeSelectAllOption : true,
		buttonWidth : '100%'
	});
	$('#genderfind').multiselect({
		nonSelectedText : 'Chọn Giới tính',
		enableFiltering : true,
		enableCaseInsensitiveFiltering : true,
		includeSelectAllOption : true,
		buttonWidth : '100%'
	});

});

function formAddValidation() {
	var emailAdd = $('#emailAdd').val();
	var passwordAdd = $('#passwordAdd').val();
	var fullnameAdd = $('#fullnameAdd').val();
	var birthdayAdd = $('#birthdayAdd').val();
	//var phoneAdd = $('#phoneAdd').val();
	var genderAdd = $('input[name="gender"]:checked').val();
	var roleAdd = $('#roleAdd').val();
	
	if (emailAdd == "") {
		$('#errorEmailAdd').show();
		$('#errorEmailAdd').text("Vui lòng nhập email");
		return false;
	} else {
		$('#errorEmailAdd').hide();
		var pattern_email = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
		if (!pattern_email.test(emailAdd)) {
			$('#errorEmailAdd').show();
			$('#errorEmailAdd').text("Vui lòng nhập chính xác email");
			return false;
		} else {
			$('#errorEmailAdd').hide();
		}
	}

	if (passwordAdd == "") {
		$('#errorPasswordAdd').show();
		$('#errorPasswordAdd').text("Vui lòng nhập password");
		return false;
	} else {
		$('#errorPasswordAdd').hide();
		var pattern_password = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;
		if (!pattern_password.test(passwordAdd)) {
			$('#errorPasswordAdd').show();
			$('#errorPasswordAdd').text(
					"password từ 6 đến 20 kí tự và chứa ít nhất một chữ số, một chữ hoa và một chữ cái viết thường");
			return false;
		} else {
			$('#errorPasswordAdd').hide();
		}
	}
	
	if (fullnameAdd != "") {
		$('#errorFullnameAdd').hide();
		var pattern_fullname = /^([^\t]+)$/;
		if (!pattern_fullname.test(fullnameAdd)) {
			$('#errorFullnameAdd').show();
			$('#errorFullnameAdd').text(
					"fullname chỉ chứa kí tự, chữ số và dấu khoảng trắng");
			return false;
		} else {
			$('#errorFullnameAdd').hide();
		}
	}
	
	if (birthdayAdd != "") {
		$('#errorBirthdayAdd').hide();
		var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
		if (!dateformat.test(birthdayAdd)) {
			$('#errorBirthdayAdd').show();
			$('#errorBirthdayAdd').text("Ngày sinh không hợp lệ");
			return false;
		}else if (!validatedate(birthdayAdd)) {
			$('#errorBirthdayAdd').show();
			$('#errorBirthdayAdd').text("Ngày sinh không hợp lệ");
			return false;
		}
	} else {
		$('#errorBirthdayAdd').hide();
	}
	
	if (null == genderAdd || "" == genderAdd) {
		$('#errorGenderAdd').show();
		$('#errorGenderAdd').text("Vui lòng chọn giới tính");
		return false;
	}else {
		$('#errorGenderAdd').hide();
	}
	
	if (null == roleAdd || "" == roleAdd) {
		$('#errorRoleAdd').show();
		$('#errorRoleAdd').text("Vui lòng chọn vai trò");
		return false;
	}else {
		$('#errorRoleAdd').hide();
	}
	return true;
}

function formEditValidation() {
	var emailEdit = $('#emailEdit').val();
	var fullnameEdit = $('#fullnameEdit').val();
	var birthdayEdit = $('#birthdayEdit').val();
	//var phoneEdit = $('#phoneEdit').val();
	var genderEdit = $('input[name="gender"]:checked').val();
	var roleEdit = $('#roleEdit').val();
	
	if (emailEdit == "") {
		$('#errorEmailEdit').show();
		$('#errorEmailEdit').text("Vui lòng nhập email");
		return false;
	} else {
		$('#errorEmailEdit').hide();
		var pattern_email = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
		if (!pattern_email.test(emailEdit)) {
			$('#errorEmailEdit').show();
			$('#errorEmailEdit').text("Vui lòng nhập chính xác email");
			return false;
		} else {
			$('#errorEmailEdit').hide();
		}
	}
	
	if (fullnameEdit != "") {
		$('#errorFullnameEdit').hide();
		var pattern_fullname = /^([^\t]+)$/;
		if (!pattern_fullname.test(fullnameEdit)) {
			$('#errorFullnameEdit').show();
			$('#errorFullnameEdit').text(
					"fullname chỉ chứa kí tự, chữ số và dấu khoảng trắng");
			return false;
		} else {
			$('#errorFullnameEdit').hide();
		}
	}
	
	if (birthdayEdit != "") {
		$('#errorBirthdayEdit').hide();
		var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
		if (!dateformat.test(birthdayEdit)) {
			$('#errorBirthdayEdit').show();
			$('#errorBirthdayEdit').text("Ngày sinh không hợp lệ");
			return false;
		}else if (!validatedate(birthdayEdit)) {
			$('#errorBirthdayEdit').show();
			$('#errorBirthdayEdit').text("Ngày sinh không hợp lệ");
			return false;
		}
	} else {
		$('#errorBirthdayEdit').hide();
	}
	
	if (null == genderEdit || "" == genderEdit) {
		$('#errorGenderEdit').show();
		$('#errorGenderEdit').text("Vui lòng chọn giới tính");
		return false;
	} else {
		$('#errorGenderEdit').hide();
	}
	
	if (null == roleEdit || "" == roleEdit) {
		$('#errorRoleEdit').show();
		$('#errorRoleEdit').text("Vui lòng chọn vai trò");
		return false;
	} else {
		$('#errorRoleEdit').hide();
	}
	return true;
}
function GetURLParameter(sPageURL,sParam) {
    var sURLVariables = sPageURL.split('?');
    var sParameterName = sURLVariables[1].split('=');
    if (sParameterName[0] == sParam) {
        return sParameterName[1];
    }
}

function getData(data){
	if (data == "[]") {
		return null;
	}else{
		var temp = data.substring(1,data.length-1);
		return temp.split(", ");
	}
}
function validatedate(inputText) {

	// Test which seperator is used '/' or '-'
	var opera1 = inputText.split('/');
	var opera2 = inputText.split('-');
	lopera1 = opera1.length;
	lopera2 = opera2.length;
	// Extract the string into month, date and year
	if (lopera1 > 1) {
		var pdate = opera1;
	} else if (lopera2 > 1) {
		var pdate = opera2;
	}
	var dd = parseInt(pdate[0]);
	var mm = parseInt(pdate[1]);
	var yy = parseInt(pdate[2]);
	// Create list of days of a month [assume there is no leap year by
	// default]
	var ListofDays = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
	if (mm == 1 || mm > 2) {
		if (dd > ListofDays[mm - 1]) {
			return false;
		}
	}
	if (mm == 2) {
		var lyear = false;
		if ((!(yy % 4) && yy % 100) || !(yy % 400)) {
			lyear = true;
		}
		if ((lyear == false) && (dd >= 29)) {
			return false;
		}
		if ((lyear == true) && (dd > 29)) {
			return false;
		}
	}
	return true;

}
