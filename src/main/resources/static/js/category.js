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
	
	$('.addButton').on('click',function(event){
		event.preventDefault();
		var url = $('#formAdd').attr('action');
		checkQuyen(url,'addModal');
	});
	
	$('.table .editButton').on('click', function(event) {
		event.preventDefault();
		var href = $(this).attr('href');

		$.get(href, function(category, status) {
			$('#idEdit').val(category.id);
			$('#keyEdit').val(category.categorykey);
			$('#nameEdit').val(category.categoryname);
		});
		var url = $('#formEdit').attr('action');
		checkQuyen(url,'editModal');
	});
	
	$('.deleteButton').on('click',function(event){
		event.preventDefault();
		var url = $('#formDelete').attr('action');
		checkQuyen(url,'deleteModal');
		$("#deleteId").click(function() {
			$("#formDelete").submit();
		});
	});
	
	$("#formAdd").submit(function(event) {
		event.preventDefault(); // prevent default action
		var check = true;
		var categorykey = $('#keyAdd').val();
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/category/findByKey",
			data : {
				categorykey : categorykey
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
		var categorykey = $('#keyEdit').val();
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/category/findByKey",
			data : {
				categorykey : categorykey
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