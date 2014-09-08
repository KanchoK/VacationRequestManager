<script>
    $(document).ready(function(){
        var accessLevel = <%=session.getAttribute("access")%>
        if (accessLevel == 1){
            $('#logoutButton').show();
            $('#changePassButton').show();
            $('#myRequestsButton').show();
            $('#vacationCalendarButton').show();
            $('#requestManagerButton').show();
            $('#controlPanelButton').show();
        } else if (accessLevel == 2){
            $('#logoutButton').show();
            $('#changePassButton').show();
            $('#myRequestsButton').show();
            $('#vacationCalendarButton').show();
            $('#requestManagerButton').show();
            $('#controlPanelButton').hide();
        } else {
            $('#logoutButton').show();
            $('#changePassButton').show();
            $('#myRequestsButton').show();
            $('#vacationCalendarButton').show();
            $('#requestManagerButton').hide();
            $('#controlPanelButton').hide();
        }
    });
</script>

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
</span>

<script>
    var selectedName = "All";
    $(document).ready(function() {

        $.ajax({
            url: 'EmployeeListServlet',
            type: 'POST',
            success: function (data) {
                for (i = 0; i < data.employeesList.length; i++) {
                    $('#employeeSelect').append("<option value=\"" + data.employeesList[i].name + "\">" + data.employeesList[i].name + "</option>");
                }
            },
            error: function (e) {
                alert(e);
            }
        });

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
            eventSources: {
                url: 'VacationCalendarController',
                type: 'POST',
                data: {
                    name: selectedName
                }
            }
        });

    });

    function selectEmployee(name){
        selectedName = name;
        newSource = {
            url: 'VacationCalendarController',
            type: 'POST',
            data: {
                name: name
            }
        };
        $('#calendar').fullCalendar('removeEvents');
        $('#calendar').fullCalendar('removeEventSource', newSource);
        $('#calendar').fullCalendar('addEventSource', newSource);
    }
</script>

<div class="drop-down">
    <select id="employeeSelect" onchange="selectEmployee(this.value)">
        <option value="All">Всички</option>
    </select>
</div>

<div id="calendar"></div>