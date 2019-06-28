$(function () {

    var dp = new DayPilot.Scheduler("dp");

    dp.eventHeight = 40;
    dp.startDate = DayPilot.Date.today();
    dp.days = DayPilot.Date.today().daysInYear();
    dp.scale = "Day";
    dp.timeHeaders = [{
            groupBy: "Month"
        },
        {
            groupBy: "Day",
            format: "d"
        }
    ];

    dp.rows.load("/dp/getRowsData");
    dp.events.load("/dp/getEvents");


    var form;
    var dialog;
    var numberOfPeople = $("input#people");
    var numberRegex = /^\d+$/;


    function createReservation(args) {
        var dp = args.control;
        dp.clearSelection();

        var numberOfPeopleVal = numberOfPeople.val();

        if ((typeof numberOfPeopleVal == "string") && numberRegex.test(numberOfPeopleVal)) {
            numberOfPeopleVal = parseInt(numberOfPeopleVal);
        } else {
            alert("Incorrect number of people!");
            return;
        }


        var params = {
            start: args.start.toString(),
            end: args.end.toString(),
            text: "Reservation",
            people: numberOfPeopleVal,
            resource: args.resource
        };

        $.ajax({
            type: 'POST',
            url: '/dp/createReservation',
            data: JSON.stringify(params),
            success: function (data) {
                dp.events.add(new DayPilot.Event(data));
                dp.message("Reservation created");
            },
            contentType: "application/json",
            dataType: 'json',
            cache: false,
            timeout: 600000,
            error: function (e) {
                alert("Error " + e.val());
            }
        });
    }



    function findForm(args) {
        form = dialog.find("form").on("submit", function (event) {
            event.preventDefault();
            createReservation(args);
        });
    }

    function createDialog(args) {
        dialog = $("#dialog-form").dialog({
            autoOpen: false,
            height: 400,
            width: 350,
            modal: true,
            buttons: {
                "Create a reservation": function () {
                    createReservation(args);
                    dialog.dialog("close");
                },
                Cancel: function () {
                    dialog.dialog("close");
                }
            },
            close: function () {
                form[0].reset();
            }
        });
    }




    dp.onTimeRangeSelected = function (args) {
        createDialog(args);
        findForm(args);
        dialog.dialog("open");
        // dp.events.load("/dp/getEvents");

    };

    dp.init();
});