/**
 * 
 */

$(document).ready(function() {
	
});

function renderChitiet(id){
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
			$('.viewChiTiet'+id).html(data);
		},
	});
}