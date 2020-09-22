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

		$.get(href, function(chucnang, status) {
			$('#idEdit').val(chucnang.id);
			$('#keyEdit').val(chucnang.key);
			$('#nameEdit').val(chucnang.name);
			$('#urlEdit').val(chucnang.url);
			$('#comboboxEdit').val(chucnang.chucnangcha.id);
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
			url : "/chucnang/findByKey",
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
			url : "/chucnang/findByKey",
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
$(function(){
    $('#keysearch').multiSelect({
 
    });
});
$( function() {
    $.widget( "custom.combobox", {
      _create: function() {
        this.wrapper = $( "<span>" )
          .addClass( "custom-combobox" )
          .insertAfter( this.element );
 
        this.element.hide();
        this._createAutocomplete();
        this._createShowAllButton();
      },
 
      _createAutocomplete: function() {
        var selected = this.element.children( ":selected" ),
          value = selected.val() ? selected.text() : "";
 
        this.input = $( "<input>" )
          .appendTo( this.wrapper )
          .val( value )
          .attr( "title", "" )
          .addClass( "custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left" )
          .autocomplete({
            delay: 0,
            minLength: 0,
            source: $.proxy( this, "_source" )
          })
          .tooltip({
            classes: {
              "ui-tooltip": "ui-state-highlight"
            }
          });
 
        this._on( this.input, {
          autocompleteselect: function( event, ui ) {
            ui.item.option.selected = true;
            this._trigger( "select", event, {
              item: ui.item.option
            });
          },
 
          autocompletechange: "_removeIfInvalid"
        });
      },
 
      _createShowAllButton: function() {
        var input = this.input,
          wasOpen = false
 
        $( "<a>" )
          .attr( "tabIndex", -1)
          .attr( "title", "Show All Items" )
          .attr( "height", "" )
          .tooltip()
          .appendTo( this.wrapper )
          .button({
            icons: {
              primary: "ui-icon-triangle-1-s"
            },
            text: "false"
          })
          .removeClass( "ui-corner-all" )
          .addClass( "custom-combobox-toggle ui-corner-right" )
          .on( "mousedown", function() {
            wasOpen = input.autocomplete( "widget" ).is( ":visible" );
          })
          .on( "click", function() {
            input.trigger( "focus" );
 
            // Close if already visible
            if ( wasOpen ) {
              return;
            }
 
            // Pass empty string as value to search for, displaying all results
            input.autocomplete( "search", "" );
          });
      },
 
      _source: function( request, response ) {
        var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
        response( this.element.children( "option" ).map(function() {
          var text = $( this ).text();
          if ( this.value && ( !request.term || matcher.test(text) ) )
            return {
              label: text,
              value: text,
              option: this
            };
        }) );
      },
 
      _removeIfInvalid: function( event, ui ) {
 
        // Selected an item, nothing to do
        if ( ui.item ) {
          return;
        }
 
        // Search for a match (case-insensitive)
        var value = this.input.val(),
          valueLowerCase = value.toLowerCase(),
          valid = false;
        this.element.children( "option" ).each(function() {
          if ( $( this ).text().toLowerCase() === valueLowerCase ) {
            this.selected = valid = true;
            return false;
          }
        });
 
        // Found a match, nothing to do
        if ( valid ) {
          return;
        }
 
        // Remove invalid value
        this.input
          .val( "" )
          .attr( "title", value + " didn't match any item" )
          .tooltip( "open" );
        this.element.val( "" );
        this._delay(function() {
          this.input.tooltip( "close" ).attr( "title", "" );
        }, 2500 );
        this.input.autocomplete( "instance" ).term = "";
      },
 
      _destroy: function() {
        this.wrapper.remove();
        this.element.show();
      }
    });
 
    $( "#combobox" ).combobox();
    $( "#toggle" ).on( "click", function() {
      $( "#combobox" ).toggle();
    });
    
    $( "#comboboxEdit" ).combobox();
    $( "#toggle" ).on( "click", function() {
      $( "#comboboxEdit" ).toggle();
    });
  } );

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