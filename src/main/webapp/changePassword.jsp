    <style>
        .initialPass{
            background: #3399cc;
            margin:0 auto;
            margin-top:1%;
            padding:10px;
            text-align:center;
            text-decoration:none;
            color:#fff;
        }
    </style>

    <script>
        $(document).ready(function(){
            var isInitial = <%=session.getAttribute("accountStatus")%>
            if (isInitial == 0){
                $('p').show();
            } else {
                $('p').hide();
            }
        });
    </script>

<p class="initialPass">
    It is good to change your password because it is initial right now and your account is insecure.
</p>

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
