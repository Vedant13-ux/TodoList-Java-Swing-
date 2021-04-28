<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Email Verification - Checkit</title>
    </head>
    <body>
        <h1>Your Email is Being Verified</h1>
        <form action="VerifyEmail" id="form" method="POST">
            <input type="hidden" name="emailToken"></input>
        </form>
        
        <script>
           const form=document.querySelector("#form");
           form.emailToken.value=location.search.split("=")[1]
           console.log(form.emailToken.value);
           form.submit();
        </script>
    </body>
</html>
