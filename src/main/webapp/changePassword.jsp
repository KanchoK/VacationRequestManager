    <script>
        $(document).ready(function(){
            var isInitial = <%=session.getAttribute("accountStatus")%>
            if (isInitial == 0) {
                alertify.alert("It is good to change your password because it is initial right now and your account is insecure.")
            }
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

<div id="changePass">
    <div id="triangle"></div>
    <h1>Change your password</h1>
    <form class="form" ng-controller="changePasswordController" ng-submit="changePassword(oldPassword, newPassword, confirmNewPassword)" novalidate>
        <input type="password" ng-model="oldPassword" placeholder="Old password" />
        <input type="password" ng-model="newPassword" placeholder="New password" />
        <input type="password" ng-model="confirmNewPassword" placeholder="Confirm new password" />
        <input type="submit" value="Update password" />
    </form>
</div>
