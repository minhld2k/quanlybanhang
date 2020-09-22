/**
 * 
 */
$(document).ready(function(){
	
	$("#selectAll").click(function() {
		$("input[type=checkbox]").prop("checked", $(this).prop("checked"));
	});
	$("input[type=checkbox]").click(function() {
		if (!$(this).prop("checked")) {
			$("#selectAll").prop("checked", false);
		}
	});
	
	$('#categoryAdd').multiselect({
		nonSelectedText : 'Chọn danh mục',
		enableFiltering : true,
		enableCaseInsensitiveFiltering : true,
		includeSelectAllOption : true,
		buttonWidth : '100%'
	});
	
	$('#categoryEdit').multiselect({
		nonSelectedText : 'Chọn danh mục',
		enableFiltering : true,
		enableCaseInsensitiveFiltering : true,
		includeSelectAllOption : true,
		buttonWidth : '100%'
	});
	
	$('#categorySearch').multiselect({
		nonSelectedText : 'Chọn danh mục',
		enableFiltering : true,
		enableCaseInsensitiveFiltering : true,
		includeSelectAllOption : true,
		buttonWidth : '100%'
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
	
	$('.table .editButton').on('click', function(event) {
		event.preventDefault();
		$('#categoryEdit option:selected').each(function(){
		     $(this).prop('selected', false);
		});
		$('#categoryEdit').multiselect('refresh');
		var href = $(this).attr('href');
		var id = GetURLParameter(href,'id');
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/hangsx/oneHangsx",
			data : {
				id : id
			},
			dataType : "json",
			timeout : 10000,
			success : function(data) {
				$('#idEdit').val(data.id);
				$('#keyEdit').val(data.key);
				$('#nameEdit').val(data.name);
				var category = getData(data.category);
				$('#categoryEdit').multiselect('select', category);
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
	
	$("#formAdd").submit(function(event) {
		event.preventDefault(); // prevent default action
		var check = true;
		var key = $('#keyAdd').val();
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/hangsx/findByKey",
			data : {
				key : key
			},
			dataType : "json",
			timeout : 10000,
			success : function(data) {
				if (data != "-1") {
					$('#errorkeyAdd').show();
					$('#errorkeyAdd').text("key đã tồn tại");
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
		var key = $('#keyEdit').val();
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/hangsx/findByKey",
			data : {
				key : key
			},
			dataType : "json",
			timeout : 10000,
			success : function(data) {
				if (data != "-1" && data != id) {
					$('#errorkeyEdit').show();
					$('#errorkeyEdit').text("key đã tồn tại");
					check = false;
				} else {
					$("#formEdit").unbind('submit').submit();
				}
			},

		});
	});
	
});

function formAddValidation() {
	var keyAdd = $('#keyAdd').val();
	var nameAdd = $('#nameAdd').val();
	var category = $('#categoryAdd').val();
	
	if (null == category || "" == category) {
		$('#errornameAdd').show();
		$('#errornameAdd').text("Vui lòng Chọn danh mục");
		return false;
	}else{
		$('#errornameAdd').hide();
	}
	if (keyAdd == "") {
		$('#errorkeyAdd').show();
		$('#errorkeyAdd').text("Vui lòng nhập key");
		return false;
	} else {
		$('#errorkeyAdd').hide();
		var pattern_key = /^[a-zA-Z0-9]+$/;
		if (!pattern_key.test(keyAdd)) {
			$('#errorkeyAdd').show();
			$('#errorkeyAdd').text("Key chỉ chứa kí tự và chữ số");
			return false;
		} else {
			$('#errorkeyAdd').hide();
		}
	}

	if (nameAdd == "") {
		$('#errornameAdd').show();
		$('#errornameAdd').text("Vui lòng nhập name");
		return false;
	} else {
		$('#errornameAdd').hide();
		var pattern_name = /^([^\t]+)$/;
		if (!pattern_name.test(nameAdd)) {
			$('#errornameAdd').show();
			$('#errornameAdd').text(
					"name chỉ chứa kí tự, chữ số và dấu khoảng trắng");
			return false;
		} else {
			$('#errornameAdd').hide();
		}
	}
	return true;
}

function formEditValidation() {
	var key = $('#keyEdit').val();
	var name = $('#nameEdit').val();
	var category = $('#categoryEdit').val();
	
	if (null == category) {
		$('#errornameEdit').show();
		$('#errornameEdit').text("Vui lòng Chọn danh mục");
		return false;
	}else{
		$('#errornameEdit').hide();
	}
	if (key == "") {
		$('#errorkeyEdit').show();
		$('#errorkeyEdit').text("Vui lòng nhập key");
		return false;
	} else {
		$('#errorkeyEdit').hide();
		var pattern_key = /^[a-zA-Z0-9]+$/;
		if (!pattern_key.test(key)) {
			$('#errorkeyEdit').show();
			$('#errorkeyEdit').text("Key chỉ chứa kí tự và chữ số");
			return false;
		} else {
			$('#errorkeyEdit').hide();
		}
	}

	if (name == "") {
		$('#errornameEdit').show();
		$('#errornameEdit').text("Vui lòng nhập name");
		return false;
	} else {
		$('#errornameEdit').hide();
		var pattern_name = /^([^\t]+)$/;
		if (!pattern_name.test(name)) {
			$('#errornameEdit').show();
			$('#errornameEdit').text(
					"name chỉ chứa kí tự, chữ số và dấu khoảng trắng");
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