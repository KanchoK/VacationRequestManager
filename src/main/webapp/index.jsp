<!DOCTYPE html>
<html ng-app="myApp">
<head lang="en">
    <meta charset="UTF-8">

    <script src="js/jquery.min.js" type="text/javascript"></script>

    <link rel="stylesheet" type="text/css" href="css/formDesign.css">
    <link rel="stylesheet" type="text/css" href="css/header.css">

    <link rel="stylesheet" href="css/alertify.css" type="text/css"/>
    <link rel="stylesheet" href="css/themes/default.css" type="text/css"/>

    <script src="js/angular.js" type="text/javascript"></script>
    <script src="js/main.js" type="text/javascript"></script>
    <script src="js/alertify.js" type="text/javascript"></script>

    <link rel="stylesheet" href="jquery-ui-flick-theme/jquery-ui.css" />
    <script src="js/jquery-ui.js" type="text/javascript"></script>

    <link href="jtable/themes/metro/blue/jtable.min.css" rel="stylesheet" type="text/css" />
    <script src="jtable/jquery.jtable.min.js" type="text/javascript"></script>

    <link rel="stylesheet" href="css/fullcalendar.css" type="text/css">
    <script src="js/moment.min.js" type="text/javascript"></script>
    <script src="js/fullcalendar.js" type="text/javascript"></script>

    <script>
        function menuAccessibility(access){
            var accessLevel = access;
            if (accessLevel == 1){
                $('#logoutButton').show();
                $('#changePassButton').show();
                $('#myRequestsButton').show();
                $('#vacationCalendarButton').show();
                $('#requestManagerButton').show();
                $('#controlPanelButton').show();
                $('#holidaysManagerButton').show();
            } else if (accessLevel == 2){
                $('#logoutButton').show();
                $('#changePassButton').show();
                $('#myRequestsButton').show();
                $('#vacationCalendarButton').show();
                $('#requestManagerButton').show();
                $('#controlPanelButton').hide();
                $('#holidaysManagerButton').hide();
            } else {
                $('#logoutButton').show();
                $('#changePassButton').show();
                $('#myRequestsButton').show();
                $('#vacationCalendarButton').show();
                $('#requestManagerButton').hide();
                $('#controlPanelButton').hide();
                $('#holidaysManagerButton').hide();
            }
        }
    </script>

</head>
<body ng-controller="mainController">
<div id="container">
    <span>
        <img class="logo" border="0" src="images/novarto_logo_white.png" width="300px" height="75px">
    </span>

    <%--<span ng-controller="menuController">--%>
        <%--<span class="menuSpan" id="logoutButton">--%>
            <%--<button type="button" class="menuButton" ng-click="logout()">Log out</button>--%>
        <%--</span>--%>

        <%--<span class="menuSpan" id="changePassButton">--%>
            <%--<button type="button" class="menuButton" ng-click="changePassword()">Change password</button>--%>
        <%--</span>--%>

        <%--<span class="menuSpan" id="myRequestsButton">--%>
            <%--<button type="button" class="menuButton" ng-click="myRequests()">My requests</button>--%>
        <%--</span>--%>

        <%--<span class="menuSpan" id="vacationCalendarButton">--%>
            <%--<button type="button" class="menuButton" ng-click="vacationCalendar()">Vacation calendar</button>--%>
        <%--</span>--%>

        <%--<span class="menuSpan" id="requestManagerButton">--%>
            <%--<button type="button" class="menuButton" ng-click="requestManager()">Request Manager</button>--%>
        <%--</span>--%>

        <%--<span class="menuSpan" id="controlPanelButton">--%>
            <%--<button type="button" class="menuButton" ng-click="controlPanel()">Control Panel</button>--%>
        <%--</span>--%>
    <%--</span>--%>

<span id="main">

    <span ng-view></span>

</span>
</div>
</body>
</html>