/**
 * 
 */

$(document).ready(function() {
	
});

function renderChitiet(id){
	var element1 = document.getElementById('viewChiTiet'+id);
	var element2 = document.getElementById('viewCapNhat'+id);
	if (element1.style.display === "none") {
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/renderchitiet",
			data : {
				cartid : id
			},
			dataType : "html",
			timeout : 10000,
			success : function(data) {
				element1.style.display = "block";
				element2.style.display = "none";
				$('.viewChiTiet'+id).html(data);
			},
		});
	}else{
		element1.style.display = "none";
	}
}

function renderTrangThai(id){
	var element1 = document.getElementById('viewChiTiet'+id);
	var element2 = document.getElementById('viewCapNhat'+id);
	var trangthai = $('#trangthai'+id).val();
	if (element2.style.display === "none") {
		$("#trangthai").val(trangthai);
		element2.style.display = "block";
		element1.style.display = "none";
	}else{
		element2.style.display = "none";
	}
}