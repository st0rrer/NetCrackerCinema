<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<style type="text/css">
    <%@include file="css/app.css" %>
    <%@include file="css/bootstrap.css" %>
</style>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Login page</title>
    <link rel="stylesheet" type="text/css" href="/VAADIN/themes/cinema/styles.css">
    <link rel="stylesheet" type="text/css"
          href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.css"/>
</head>

<body>
<div class="container">

    <div class="" id="login-wrapper">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div id="logo-login">
                    <h1>WEL<span>COME</span>
                        <span>login panel</span>
                    </h1>
                </div>
            </div>
        </div> <!--end of .row-->
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="account-box">

                    <c:url var="loginUrl" value="/login"/>
                    <form action="${loginUrl}" method="post" class="form">
                        <c:if test="${param.error != null}">
                            <div class="alert alert-danger">
                                <p>Invalid username and password.</p>
                            </div>
                        </c:if>
                        <c:if test="${param.logout != null}">
                            <div class="alert alert-success">
                                <p>You have been logged out successfully.</p>
                            </div>
                        </c:if>

                        <div class="input-group">
                            <label class="input-group-addon" for="username"><i
                                    class="fa fa-user"></i></label>
                            <input type="text" class="form-control" id="username" name="ssoId"
                                   placeholder="Enter Username"
                                   required>
                        </div>
                        <div class="input-group">
                            <label class="input-group-addon" for="password"><i
                                    class="fa fa-lock"></i></label>
                            <input type="password" class="form-control" id="password" name="password"
                                   placeholder="Enter Password" required>
                        </div>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign In</button>
                    </form>

                </div> <!--end of .account-box-->
            </div> <!--end of .col-md-4 col-md-offset-4-->
        </div> <!--end of .row-->
    </div> <!--end of #login-wrapper-->
</div> <!--end of .container-->

</body>
</html>