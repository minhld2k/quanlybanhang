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