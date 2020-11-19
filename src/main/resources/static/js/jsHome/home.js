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
		var id = $("#id").val();
		var productid = $("#productid").val();
		var soluong = $("#soluong").val();
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/giohang/save",
			data : {
				id : id,
				productid : productid,
				soluong : soluong
			},
			dataType : "json",
			timeout : 10000,
			success : function(data) {
				console.log(data);
				if(data > 0){
					$('#deleteModal').modal('show');
				}else{
					location.href = "/login";
				}
			},
		});
	});
	
	$('.deleteButton').on('click',function(event){
		event.preventDefault();
		$('#deleteModal').modal('show');
		$("#deleteId").click(function() {
			$("#formDelete").submit();
		});
	});
});
function update(id){
	var soluong = $("#soluong"+id).val();
	var giatien = $("#giatien"+id).val();
	var thanhtien = soluong * giatien;
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/giohang/save",
		data : {
			id : id,
			productid : "0",
			soluong : soluong
		},
		dataType : "json",
		timeout : 10000,
		success : function(data) {
			$(".sum").html(data);
			$(".thanhtien"+id).html(thanhtien);
		},
	});
}

function formValidation(){
	var fullname = $('#fullname').val();
	var phone = $('#phone').val();
	var address = $('#address').val();
	if (fullname == "") {
		$('#errorFullname').show();
		$('#errorFullname').text("Vui lòng nhập họ và tên");
		return false;
	}else{
		$('#errorFullname').hide();
	}

	if (phone == "") {
		$('#errorPhone').show();
		$('#errorPhone').text("Vui lòng nhập số điện thoại");
		return false;
	} else {
		$('#errorPhone').hide();
	}
	
	if (address == "") {
		$('#errorAddress').show();
		$('#errorAddress').text("Vui lòng nhập địa chỉ");
		return false;
	} else {
		$('#errorAddress').hide();
	}
	return true;
}