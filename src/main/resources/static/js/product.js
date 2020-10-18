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
	
	$('.addButton').on('click',function(event){
		event.preventDefault();
		var url = $('#formAdd').attr('action');
		checkQuyen(url,'addModal');
	});
	
	$('.table .editButton').on('click', function(event) {
		event.preventDefault();
		
		var href = $(this).attr('href');
		var id = GetURLParameter(href,'id');
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/sanpham/oneProduct",
			data : {
				id : id
			},
			dataType : "json",
			timeout : 10000,
			success : function(data) {
				$('#idEdit').val(data.id);
				$('#tensanphamEdit').val(data.tensanpham);
				$('#ramEdit').val(data.ram);
				$('#manhinhEdit').val(data.manhinh);
				$('input:radio[value="'+data.trangthai+'"]').prop('checked',true);
				//$('#imageEdit').val(data.image);
				$('#motaEdit').val(data.mota);
				$('#categoryEdit').val(data.categoryid);
				$('#hangsxEdit').val(data.hangsxid);
			},
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
	
	$('#categoryfind').multiselect({
		nonSelectedText : 'Chọn loại sản phẩm',
		enableFiltering : true,
		enableCaseInsensitiveFiltering : true,
		includeSelectAllOption : true,
		buttonWidth : '100%'
	});
	
	$('#hangsxfind').multiselect({
		nonSelectedText : 'Chọn hãng sản xuất',
		enableFiltering : true,
		enableCaseInsensitiveFiltering : true,
		includeSelectAllOption : true,
		buttonWidth : '100%'
	});
	
	$('#trangthaifind').multiselect({
		nonSelectedText : 'Chọn trạng thái',
		enableFiltering : true,
		enableCaseInsensitiveFiltering : true,
		includeSelectAllOption : true,
		buttonWidth : '100%'
	});
});
function updateAjax(id){
	var soluong = $("#soluong"+id).val();
	var giatien = $("#giatien"+id).val();
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/sanpham/updateAjax",
		data : {
			idAjax : id,
			soluongAjax : soluong,
			giatienAjax : giatien
		},
		dataType : "json",
		timeout : 10000,
		success : function(data) {
			location.reload();
		},

	});
}
function GetURLParameter(sPageURL,sParam) {
    var sURLVariables = sPageURL.split('?');
    var sParameterName = sURLVariables[1].split('=');
    if (sParameterName[0] == sParam) {
        return sParameterName[1];
    }
}

function formAddValidation() {
	var tenSanPhamAdd = $('#tensanphamAdd').val();
	var soLuongAdd = $('#soluongAdd').val();
	var giaTienAdd = $('#giatienAdd').val();
	var trangThaiAdd = $('input[name="trangthai"]:checked').val();
	var categoryAdd = $('#categoryAdd').val();
	var hangsxAdd = $('#hangsxAdd').val();
	
	if (tenSanPhamAdd == "") {
		$('#errorTensanphamAdd').show();
		$('#errorTensanphamAdd').text("Vui lòng nhập tên sản phẩm");
		return false;
	} else {
		$('#errorTensanphamAdd').hide();
	}

	if (soLuongAdd == "") {
		$('#errorSoluongAdd').show();
		$('#errorSoluongAdd').text("Vui lòng nhập số lượng");
		return false;
	} else {
		$('#errorSoluongAdd').hide();
		var pattern_password = /^[0-9]+$/;
		if (!pattern_password.test(soLuongAdd)) {
			$('#errorSoluongAdd').show();
			$('#errorSoluongAdd').text("Sai dữ liệu");
			return false;
		} else {
			$('#errorSoluongAdd').hide();
		}
	}
	
	if (giaTienAdd == "") {
		$('#errorGiatienAdd').show();
		$('#errorGiatienAdd').text("Vui lòng nhập giá tiền");
		return false;
	} else {
		$('#errorGiatienAdd').hide();
		var pattern_password = /^[0-9]+$/;
		if (!pattern_password.test(giaTienAdd)) {
			$('#errorGiatienAdd').show();
			$('#errorGiatienAdd').text("Sai dữ liệu");
			return false;
		} else {
			$('#errorGiatienAdd').hide();
		}
	}
	
	if (null == categoryAdd || "" == categoryAdd) {
		$('#errorCategoryAdd').show();
		$('#errorCategoryAdd').text("Vui lòng chọn Loại sản phẩm");
		return false;
	} else {
		$('#errorCategoryAdd').hide();
	}
	
	if (null == trangThaiAdd || "" == trangThaiAdd) {
		$('#errorTrangThaiAdd').show();
		$('#errorTrangThaiAdd').text("Vui lòng chọn trạng thái");
		return false;
	}else {
		$('#errorTrangThaiAdd').hide();
	}
	
	if (null == hangsxAdd || "" == hangsxAdd) {
		$('#errorHangsxAdd').show();
		$('#errorHangsxAdd').text("Vui lòng chọn hãng sản xuất");
		return false;
	}else {
		$('#errorHangsxAdd').hide();
	}
	return true;
}

function formEditValidation() {
	var tensanphamEdit = $('#tensanphamEdit').val();
	var trangthaiEdit = $('input[name="trangthai"]:checked').val();
	var categoryEdit = $('#categoryEdit').val();
	var hangsxEdit = $('#hangsxEdit').val();
	
	if (tensanphamEdit == "") {
		$('#errorTensanphamEdit').show();
		$('#errorTensanphamEdit').text("Vui lòng nhập tên sản phẩm");
		return false;
	} else {
		$('#errorTensanphamEdit').hide();
	}

	if (null == categoryEdit || "" == categoryEdit) {
		$('#errorCategoryEdit').show();
		$('#errorCategoryEdit').text("Vui lòng chọn Loại sản phẩm");
		return false;
	} else {
		$('#errorCategoryEdit').hide();
	}
	
	if (null == trangthaiEdit || "" == trangthaiEdit) {
		$('#"errorTrangThaiEdit"').show();
		$('#"errorTrangThaiEdit"').text("Vui lòng chọn trạng thái");
		return false;
	}else {
		$('#"errorTrangThaiEdit"').hide();
	}
	
	if (null == hangsxEdit || "" == hangsxEdit) {
		$('#errorHangsxEdit').show();
		$('#errorHangsxEdit').text("Vui lòng chọn hãng sản xuất");
		return false;
	}else {
		$('#errorHangsxEdit').hide();
	}
	return true;
}