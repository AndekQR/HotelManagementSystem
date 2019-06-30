$(function () {
    $.ajax({
        url: '/management/getBookings',
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
    $.ajax({
        url: '/management/getRooms',
        success: function (data) {
            $("#roomTable").dynatable({
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
    $.ajax({
        url: '/management/getUsers',
        success: function (data) {
            $("#userTable").dynatable({
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