    <script type="text/javascript">
        $(document).ready(function () {
            $('#PersonTableContainer').jtable({
                title: 'My Vacation requests',
                paging: true,
                pageSize: 10,
                sorting: true,
                defaultSorting: 'vacationStatus ASC',
                actions: {
                    listAction: 'MyRequestsController?action=list',
                    createAction: 'MyRequestsController?action=create',
                    updateAction: 'MyRequestsController?action=update',
                    deleteAction: 'MyRequestsController?action=delete'
                },
                fields: {
                    vacationID: {
                        key: true,
                        create: false,
                        edit: false,
                        list: false
                    },
                    employeeName: {
                        title: 'Employee',
                        create: false,
                        edit: false,
                        list: false
                    },
                    beginDate: {
                        title: 'Vacation begin date',
                        width: '15%',
                        type: 'date',
                        displayFormat: 'dd.mm.yy'
                    },
                    endDate: {
                        title: 'Vacation end date',
                        width: '15%',
                        type: 'date',
                        displayFormat: 'dd.mm.yy'
                    },
                    vacationType: {
                        title: 'Vacation type',
                        width: '20%',
                        options: { '1': 'Полагаем годишен отпуск', '2': 'Болнични' }
                    },
                    requestText: {
                        title: 'Request message',
                        list: false,
                        type: 'textarea'
                    },
                    vacationStatus: {
                        title: 'Vacation status',
                        create: false,
                        edit: false,
                        width: '15%',
                        type: 'radiobutton',
                        options: { '1': 'Waiting for approval', '2': 'Approved', '3': 'Unapproved' }
                    },
                    responseText: {
                        title: 'Response message',
                        width: '35%',
                        type: 'textarea',
                        edit: false,
                        create: false
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

<div id="PersonTableContainer"></div>