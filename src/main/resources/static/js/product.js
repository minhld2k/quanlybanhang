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
	
	$("#soluongAjax").change(function(event){
		event.preventDefault();
		var id = $("#idAjax").val();
		var soluong = $("#soluongAjax").val();
		var giatien = $("#giatienAjax").val();
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
				alert(data);
			},

		});
	});
	
	$("#giatienAjax").change(function(event){
		event.preventDefault();
		var id = $("#idAjax").val();
		var soluong = $("#soluongAjax").val();
		var giatien = $("#giatienAjax").val();
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
				alert(data);
			},

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

function GetURLParameter(sPageURL,sParam) {
    var sURLVariables = sPageURL.split('?');
    var sParameterName = sURLVariables[1].split('=');
    if (sParameterName[0] == sParam) {
        return sParameterName[1];
    }
}