$(function () {
    var dialogOneField;
    var dialogTwoFields;
    var whatTOChange;

    var newData = {
        firstName: null,
        lastName: null,
        uniqueName: null,
        email: null,
        password: null
    };

    $("input#fullName").on("click", function (event) {
        createOneFieldDialog();
        whatTOChange = "fullName";
        dialogOneField.dialog("open");
    });
    $("input#uniqueName").on("click", function (event) {
        createOneFieldDialog();
        whatTOChange = "uniqueName";
        dialogOneField.dialog("open");

    });
    $("input#email").on("click", function (event) {
        createOneFieldDialog();
        whatTOChange = "email";
        dialogOneField.dialog("open");

    });
    $("input#password").on("click", function (event) {
        createTwoFieldsDialog();
        whatTOChange = "password";
        dialogTwoFields.dialog("open");
    });

    function changeEmail() {
        var newEmail = $("input#newValue").val();
        newData.email = newEmail;

    }

    function changeUniqueName() {
        var newUniqueName = $("input#newValue").val();
        newData.uniqueName = newUniqueName;
    }

    function changeFullName() {
       var fullName = $("input#newValue").val();
       var space = fullName.indexOf(' ');
       var firstName = fullName.substring(0, space);
       var lastName = fullName.substring(space, fullName.length);
       newData.lastName = lastName;
       newData.firstName = firstName;

    }

    function changePassword() {
        var password1 = $("input#newValueOne").val();
        var password2 = $("input#newValueTwo").val();
        if (password1 === password2){
            newData.password = password1;
        }
    }

    function sendData() {
        $.ajax({
            type: 'POST',
            url: '/data/changeUserData',
            data: JSON.stringify(newData),
            success: function (data) {
                alert("Data changed!");
            },
            contentType: "application/json",
            dataType: 'json',
            cache: false,
            timeout: 600000,
            error: function(jqXHR, textStatus, errorThrown) {
                console.log(textStatus, errorThrown);
            }
        });
    }

    function createOneFieldDialog() {
        if (typeof dialogOneField != "undefined") return;
        dialogOneField = $("form#oneFieldForm").dialog({
            autoOpen: false,
            height: 400,
            width: 350,
            modal: true,
            buttons: {
                "Send new value": function () {
                    if (whatTOChange === "email"){
                        changeEmail();
                    }
                    else if (whatTOChange === "fullName"){
                        changeFullName();
                    }
                    else if (whatTOChange === "uniqueName"){
                        changeUniqueName();
                    }
                    sendData();
                    dialogOneField.dialog("close");
                },
                Cancel: function () {
                    dialogOneField.dialog("close");
                }
            },
            close: function () {
                $("#oneFieldForm")[0].reset();
            }
        });
    }

    function createTwoFieldsDialog() {
        if (typeof dialogTwoFields != "undefined") return;
        dialogTwoFields = $("form#twoFieldsForm").dialog({
            autoOpen: false,
            height: 400,
            width: 350,
            modal: true,
            buttons: {
                "Send new password": function () {
                    changePassword();
                    sendData();
                    dialogTwoFields.dialog("close");
                },
                Cancel: function () {
                    dialogTwoFields.dialog("close");
                }
            },
            close: function () {
                $("#twoFieldForm")[0].reset();
            }
        });
    }

    $.ajax({
        url: '/data/getBookingTableData',
        success: function (data) {
            $("#bookingTable").dynatable({
                dataset: {
                    records: data
                },
                features: {
                    search: false,
                    perPageSelect: false
                }
            });
        }
    });


});