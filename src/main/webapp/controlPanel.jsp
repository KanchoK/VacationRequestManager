    <script type="text/javascript">

        var cachedCityOptions = null;

        $(document).ready(function () {
            $('#PersonTableContainer').jtable({
                title: 'Control Panel',
                paging: true,
                pageSize: 10,
                sorting: true,
                defaultSorting: 'accessLevel ASC',
                actions: {
                    listAction: 'EmployeeTableController?action=list',
                    createAction: 'EmployeeTableController?action=create',
                    updateAction: 'EmployeeTableController?action=update',
                    deleteAction: 'EmployeeTableController?action=delete'
                },
                fields: {
                    employee_id:{
                        title: 'ID',
                        key: true,
                        create: false,
                        edit: false,
                        list: true,
                        width: '5%'
                    },
                    fName: {
                        title: "First name",
                        list: false,
                        sorting: false
                    },
                    surname: {
                        title: "Surname",
                        list: false,
                        sorting: false
                    },
                    lName: {
                        title: "Last name",
                        list: false,
                        sorting: false
                    },
                    employeeName: {
                        title: 'Employee',
                        width: '18%',
                        create: false,
                        edit: false
                    },
                    email: {
                        title: 'Email',
                        width: '20%'
                    },
//                    password: {
//                        title: 'Password',
//                        width: '20%',
//                        type: 'password',
//                        list: false,
//                        create: false,
//                        edit: false
//                    },
                    accessLevel: {
                        title: 'Access level',
                        width: '14%',
                        type: 'radiobutton',
                        options: {'0': 'Waiting for conformation', '1': 'Admin', '2': 'Manager', '3': 'Normal user'}
                    },
                    myManager: {
                        title: 'Manager',
                        width: '18%',
                        options: function() {
                            if (cachedCityOptions) { //Check for cache
                                return cachedCityOptions;
                            }

                            var options = [];

                            $.ajax({
                                url: 'ManagerListServlet',
                                type: 'POST',
                                dataType: 'json',
                                async: false,
                                success: function (data) {
                                    if (data.Result != 'OK') {
                                        alert(data.Message);
                                        return;
                                    }
                                    options = data.Records;
                                }
                            });

                            return cachedCityOptions = options; //Cache results and return options
                        }
                    },
                    vacationDaysLeft: {
                        title: 'Vacation days left',
                        width: '10%',
                        defaultValue: '25'
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

<span id="header">

<span ng-controller="menuController">
    <span class="menuSpan">
        <button type="button" class="menuButton" ng-click="logout()">Log out</button>
    </span>

    <span class="menuSpan">
        <button type="button" class="menuButton" ng-click="changePassword()">Change password</button>
    </span>

    <span class="menuSpan">
        <button type="button" class="menuButton" ng-click="myRequests()">My requests</button>
    </span>

    <span class="menuSpan">
        <button type="button" class="menuButton" ng-click="vacationCalendar()">Vacation calendar</button>
     </span>

    <span class="menuSpan">
        <button type="button" class="menuButton" ng-click="requestManager()">Request Manager</button>
    </span>

    <span class="menuSpan" id="controlPanel">
        <button type="button" class="menuButton" ng-click="controlPanel()">Control Panel</button>
    </span>
</span>

</span>

<div id="PersonTableContainer"></div>
