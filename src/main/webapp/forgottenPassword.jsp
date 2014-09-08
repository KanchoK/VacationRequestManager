<%@ page contentType="text/html; charset=UTF-8" %>

<div id="changePass">
    <div id="triangle"></div>
    <h1>Reset your password</h1>
    <form class="form" ng-controller="forgottenPasswordController" ng-submit="resetPassword(newPassword, confirmNewPassword)" novalidate>
        <input type="password" ng-model="newPassword" placeholder="New password" />
        <input type="password" ng-model="confirmNewPassword" placeholder="Confirm new password" />
        <input type="submit" value="Reset password" />
    </form>
</div>