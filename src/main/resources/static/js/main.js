/**
 * 
 */

$(document).ready(function() {
	$(".viewForm").hide();
	$('.viewFind').on('click', function() {	
		if ($(".viewForm").is(":hidden")) {
			$(".viewForm").show();
		}else{
			$(".viewForm").hide();
		}
	});
});

function checkQuyen(url,formid){
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
				$('#'+formid).modal('hide');
			}else{
				$('#'+formid).modal('show');
			}
		},

	});
}