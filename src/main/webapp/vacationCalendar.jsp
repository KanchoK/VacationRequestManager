<%@ page contentType="text/html; charset=UTF-8" %>

<span id="header" ng-controller="menuController">
    <span class="menuSpan" id="logoutButton">
        <button type="button" class="menuButton" ng-click="logout()">Log out</button>
    </span>

    <span class="menuSpan" id="changePassButton">
        <button type="button" class="menuButton" ng-click="changePassword()">Change password</button>
    </span>

    <span class="menuSpan" id="myRequestsButton">
        <button type="button" class="menuButton" ng-click="myRequests()">My requests</button>
    </span>

    <span class="menuSpan" id="vacationCalendarButton">
        <button type="button" class="menuButton" ng-click="vacationCalendar()">Vacation calendar</button>
    </span>

    <span class="menuSpan" id="requestManagerButton">
        <button type="button" class="menuButton" ng-click="requestManager()">Request Manager</button>
    </span>

    <span class="menuSpan" id="controlPanelButton">
        <button type="button" class="menuButton" ng-click="controlPanel()">Control Panel</button>
    </span>

    <span class="menuSpan" id="holidaysManagerButton">
        <button type="button" class="menuButton" ng-click="holidaysManager()">Holidays Manager</button>
    </span>
</span>

<script>
    $(document).ready(function(){
        menuAccessibility(<%=session.getAttribute("access")%>);
    });
</script>

<script>
    var selectedName = "All";
    $(document).ready(function() {

        // page is now ready, initialize the calendar...

        $('#calendar').fullCalendar({
//            put your options and callbacks here
            firstDay: 1,
            height: 650,
//            weekends: false,
            header: {
                left: '',
                center : 'title',
                right: 'today prev,next'
            },
            weekNumbers: true,
            weekMode: 'variable',
            eventColor: '#0082ba',
            eventSources: [
                {
                    url: 'VacationCalendarController',
                    type: 'POST',
                    color: '#0082ba',
                    data: {
                        name: selectedName,
                        vacationType: 1
                    }
                },
                {
                    url: 'VacationCalendarController',
                    type: 'POST',
                    color: 'green',
                    data: {
                        name: selectedName,
                        vacationType: 2
                    }
                }
            ],
            dayRender: function (date, cell) {

// ajax request to servlet which will check if the date is holiday or working day


                    if("2014-09-06" == date.format("YYYY-MM-DD")){
                        cell.css("background-color", "red");
//                        cell.first().addClass("holiday-number-" + i);
//                        $("td.holiday-number-" + i).children().append("<p class='holidayName'>"+ holidays[i][1] +"</p>");
                    }

            }
        });

        $(".fc-header-left").append("<div class='fc-dropdown'>" +
                                        "<select id='employeeSelect' onchange='selectEmployee(this.value)'>" +
                                            "<option value='All'>Всички</option>" +
                                        "</select>" +
                                    "</div>");

        $.ajax({
            url: 'EmployeeListServlet',
            type: 'POST',
            success: function (data) {
                for (i = 0; i < data.employeesList.length; i++) {
                    $('.fc-header-left #employeeSelect').append("<option value='" + data.employeesList[i].name + "'>" + data.employeesList[i].name + "</option>");
                }
            },
            error: function (e) {
                alert(e);
            }
        });

    });

    function selectEmployee(name){
        selectedName = name;
        newSourceOne = {
            url: 'VacationCalendarController',
            type: 'POST',
            color: '#0082ba',
            data: {
                name: name,
                vacationType: 1
            }
        };
        newSourceTwo = {
            url: 'VacationCalendarController',
            type: 'POST',
            color: 'green',
            data: {
                name: name,
                vacationType: 2
            }
        };
        $('#calendar').fullCalendar('removeEvents');
        $('#calendar').fullCalendar('removeEventSource', newSourceOne);
        $('#calendar').fullCalendar('removeEventSource', newSourceTwo);
        $('#calendar').fullCalendar('addEventSource', newSourceOne);
        $('#calendar').fullCalendar('addEventSource', newSourceTwo);
    }
</script>

<div id="calendar"></div>