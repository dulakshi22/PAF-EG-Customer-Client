$(document).ready(function()
{
	 $("#alertSuccess").hide();
 	 $("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	$("#alertSuccess").text("");
 	$("#alertSuccess").hide();
 	$("#alertError").text("");
 	$("#alertError").hide();

	// Form validation-------------------
	var status = validateCustomerForm();
	if (status != true)
	{
		 $("#alertError").text(status);
 		 $("#alertError").show();
 		 return;
 	}
 	
	// If valid-------------------------
 	var type = ($("#hidCustomerIDSave").val() == "") ? "POST" : "PUT";

	$.ajax(
 	{
 		url : "CustomerAPI",
 		type : type,
 		data : $("#formCustomer").serialize(),
 		dataType : "text",
 		complete : function(response, status)
 		{
 			onCustomerSaveComplete(response.responseText, status);
 		}
 	}); 
 });

function onCustomerSaveComplete(response, status)
	{
		if (status == "success")
		{
			 var resultSet = JSON.parse(response);
 			 if (resultSet.status.trim() == "success")
			 {
 				$("#alertSuccess").text("Successfully saved.");
 				$("#alertSuccess").show();
 				$("#divCustomerGrid").html(resultSet.data);
 			 } 
 			 else if (resultSet.status.trim() == "error")
			 {
 				$("#alertError").text(resultSet.data);
 				$("#alertError").show();
 			 }
 		} 
 		else if (status == "error")
 		{
 			$("#alertError").text("Error while saving.");
 			$("#alertError").show();
 		} 
 		else
 		{
 			$("#alertError").text("Unknown error while saving..");
 			$("#alertError").show();
 		}
		$("#hidCustomerIDSave").val("");
 		$("#formCustomer")[0].reset();
}

	// UPDATE==========================================
	$(document).on("click", ".btnUpdate", function(event)
	{
		 $("#hidCustomerIDSave").val($(this).data("electricityAcNo"));
		 $("#CustomerAcNo").val($(this).closest("tr").find('td:eq(0)').text());
		 $("#CustomerName").val($(this).closest("tr").find('td:eq(1)').text());
		 $("#NIC").val($(this).closest("tr").find('td:eq(2)').text());
 		 $("#Address").val($(this).closest("tr").find('td:eq(3)').text());
 		 $("#PhoneNumber").val($(this).closest("tr").find('td:eq(4)').text());
 		 $("#Email").val($(this).closest("tr").find('td:eq(5)').text());
 		 $("#Province").val($(this).closest("tr").find('td:eq(6)').text());
	});
	
	
	
	$(document).on("click", ".btnRemove", function(event)
	{
 		$.ajax(
 		{
 			url : "CustomerAPI",
 			type : "DELETE",
 			data : "ElectricityAcNo=" + $(this).data("ElectricityAcNo"),
 			dataType : "text",
 			complete : function(response, status)
 			{
 				onCustomerDeleteComplete(response.responseText, status);
 			}
 		});
	});


	function onCustomerDeleteComplete(response, status)
	{
		if (status == "success")
 		{
 			var resultSet = JSON.parse(response);
 			if (resultSet.status.trim() == "success")
 			{
 				$("#alertSuccess").text("Successfully deleted.");
 				$("#alertSuccess").show();
 				$("#divCustomerGrid").html(resultSet.data);
 			} 
 			else if (resultSet.status.trim() == "error")
 			{
 				$("#alertError").text(resultSet.data);
 				$("#alertError").show();
 			}
 		} 
 		else if (status == "error")
 		{
 				$("#alertError").text("Error while deleting.");
 				$("#alertError").show();
 		} 
 		else
 		{
 				$("#alertError").text("Unknown error while deleting..");
 				$("#alertError").show();
 		}
}
	

	// CLIENT-MODEL================================================================
	function validateCustomerForm()
	{
		// CODE
		if ($("#CustomerAcNo").val().trim() == "")
		{
 			return "Insert Customer Account Number.";
 		}

		// NAME
		if ($("#CustomerName").val().trim() == "")
 		{
 			return "Insert Customer Name.";
 		}

		// PRICE-------------------------------
		if ($("#NIC").val().trim() == "")
 		{
 			return "Insert Customer NIC.";
 		}

		// DESCRIPTION------------------------
		if ($("#Address").val().trim() == "")
		{
 			return "Insert Customer Address.";
 		}
 		// DESCRIPTION------------------------
		if ($("#PhoneNumber").val().trim() == "")
		{
 			return "Insert Phone Number.";
 		}
 		// DESCRIPTION------------------------
		if ($("#Email").val().trim() == "")
		{
 			return "Insert Email.";
 		}
 		// DESCRIPTION------------------------
		if ($("#Province").val().trim() == "")
		{
 			return "Insert Province.";
 		}

		return true;
	}