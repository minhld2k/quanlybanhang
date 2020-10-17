/**
 * 
 */

$(document).ready(function() {
	
	$("#selectAll").click(function() {
		$('input[name="id[]"]').prop("checked", $(this).prop("checked"));
	});
	$('input[name="id[]"]').click(function() {
		if (!$(this).prop("checked")) {
			$("#selectAll").prop("checked", false);
		}
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
	
	$('.addButton').on('click',function(event){
		event.preventDefault();
		var url = $('#formAdd').attr('action');
		
		checkQuyen(url,'addModal');
	});

	$('.table .editButton').on('click', function(event) {
		event.preventDefault();
		$('#chucnangEdit option:selected').each(function(){
		     $(this).prop('selected', false);
		});
		$('#chucnangEdit').multiselect('refresh');
		var href = $(this).attr('href');
		var id = GetURLParameter(href,'id');
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/role/oneRole",
			data : {
				id : id
			},
			dataType : "json",
			timeout : 10000,
			success : function(data) {
				$('#idEdit').val(data.id);
				$('#rolekeyEdit').val(data.rolekey);
				$('#rolenameEdit').val(data.rolename);
				var chucnang = getData(data.chucnang);
				$('#chucnangEdit').multiselect('select', chucnang);
			},
		});
		var url = $('#formEdit').attr('action');
		
		checkQuyen(url,'editModal');
	});

	$("#formAdd").submit(function(event) {
		event.preventDefault(); // prevent default action
		var check = true;
		var rolekey = $('#rolekeyAdd').val();
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/role/findByKey",
			data : {
				rolekey : rolekey
			},
			dataType : "json",
			timeout : 10000,
			success : function(data) {
				if (data != "-1") {
					$('#errorkeyAdd').show();
					$('#errorkeyAdd').text("Rolekey đã tồn tại");
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
		var rolekey = $('#rolekeyEdit').val();
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/role/findByKey",
			data : {
				rolekey : rolekey
			},
			dataType : "json",
			timeout : 10000,
			success : function(data) {
				if (data != "-1" && data != id) {
					$('#errorkeyEdit').show();
					$('#errorkeyEdit').text("Rolekey đã tồn tại");
					check = false;
				} else {
					$("#formEdit").unbind('submit').submit();
				}
			},

		});
	});
	
	$('.deleteButton').on('click',function(event){
		event.preventDefault();
		var url = $('#formDelete').attr('action');
	
		checkQuyen(url,'deleteModal');
		$("#deleteId").click(function() {
			$("#formDelete").submit();
		});
	});

});

function formAddValidation() {
	var rolekeyAdd = $('#rolekeyAdd').val();
	var rolenameAdd = $('#rolenameAdd').val();
	var chucnang = $('#chucnangAdd').val();
	
	if (null == chucnang || "" == chucnang) {
		$('#errornameAdd').show();
		$('#errornameAdd').text("Vui lòng Chọn chức năng");
		return false;
	}else{
		$('#errornameAdd').hide();
	}
	if (rolekeyAdd == "") {
		$('#errorkeyAdd').show();
		$('#errorkeyAdd').text("Vui lòng nhập rolekey");
		return false;
	} else {
		$('#errorkeyAdd').hide();
		var pattern_rolekey = /^[a-zA-Z0-9]+$/;
		if (!pattern_rolekey.test(rolekeyAdd)) {
			$('#errorkeyAdd').show();
			$('#errorkeyAdd').text("Rolekey chỉ chứa kí tự và chữ số");
			return false;
		} else {
			$('#errorkeyAdd').hide();
		}
	}

	if (rolenameAdd == "") {
		$('#errornameAdd').show();
		$('#errornameAdd').text("Vui lòng nhập rolename");
		return false;
	} else {
		$('#errornameAdd').hide();
		var pattern_rolename = /^([^\t]+)$/;
		if (!pattern_rolename.test(rolenameAdd)) {
			$('#errornameAdd').show();
			$('#errornameAdd').text(
					"rolename chỉ chứa kí tự, chữ số và dấu khoảng trắng");
			return false;
		} else {
			$('#errornameAdd').hide();
		}
	}
	return true;
}

function formEditValidation() {
	var rolekey = $('#rolekeyEdit').val();
	var rolename = $('#rolenameEdit').val();
	var chucnang = $('#chucnangEdit').val();
	
	if (null == chucnang) {
		$('#errornameEdit').show();
		$('#errornameEdit').text("Vui lòng Chọn chức năng");
		return false;
	}else{
		$('#errornameEdit').hide();
	}
	if (rolekey == "") {
		$('#errorkeyEdit').show();
		$('#errorkeyEdit').text("Vui lòng nhập rolekey");
		return false;
	} else {
		$('#errorkeyEdit').hide();
		var pattern_rolekey = /^[a-zA-Z0-9]+$/;
		if (!pattern_rolekey.test(rolekey)) {
			$('#errorkeyEdit').show();
			$('#errorkeyEdit').text("Rolekey chỉ chứa kí tự và chữ số");
			return false;
		} else {
			$('#errorkeyEdit').hide();
		}
	}

	if (rolename == "") {
		$('#errornameEdit').show();
		$('#errornameEdit').text("Vui lòng nhập rolename");
		return false;
	} else {
		$('#errornameEdit').hide();
		var pattern_rolename = /^([^\t]+)$/;
		if (!pattern_rolename.test(rolename)) {
			$('#errornameEdit').show();
			$('#errornameEdit').text(
					"rolename chỉ chứa kí tự, chữ số và dấu khoảng trắng");
			return false;
		} else {
			$('#errornameEdit').hide();
		}
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

