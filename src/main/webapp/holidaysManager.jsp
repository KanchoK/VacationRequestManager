<%@ page contentType="text/html; charset=UTF-8" %>

<script type="text/javascript">
    $(document).ready(function () {
        $('#HolidaysTableContainer').jtable({
            title: 'Holidays',
            paging: true,
            pageSize: 10,
            sorting: true,
            defaultSorting: 'vacationStatus ASC',
            actions: {
                listAction: '#',
                createAction: '#',
                updateAction: '#',
                deleteAction: '#'
            },
            fields: {
                holidayID: {
                    key: true,
                    create: false,
                    edit: false,
                    list: false
                },
                year: {
                    title: 'Year',
                    width: '10%'
                },
                month: {
                    title: 'Month'
                },
                day: {
                    title: 'Day'

                },
                text: {
                    title: 'Text'

                }
            }
        });
        $('#HolidaysTableContainer').jtable('load');


        $('#PersonTableContainer').jtable({
            title: 'Working Saturdays',
            paging: true,
            pageSize: 10,
            sorting: true,
            defaultSorting: 'vacationStatus ASC',
            actions: {
                listAction: '#',
                createAction: '#',
                updateAction: '#',
                deleteAction: '#'
            },
            fields: {
                holidayID: {
                    key: true,
                    create: false,
                    edit: false,
                    list: false
                },
                year: {
                    title: 'Year'

                },
                month: {
                    title: 'Month'
                },
                day: {
                    title: 'Day'

                },
                text: {
                    title: 'Text'

                }
            }
        });
        $('#PersonTableContainer').jtable('load');
    });
</script>

<script>
    $(document).ready(function(){
        menuAccessibility(<%=session.getAttribute("access")%>);
    });
</script>

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

<span id="HolidaysTableContainer" style="float: left; width: 48%; margin-top: 10px;"></span>
<span id="PersonTableContainer" style="float: right; width: 48%;"></span>

