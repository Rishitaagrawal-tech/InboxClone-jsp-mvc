<%!
	String saveEmail = "";
	String savedPassword = "";
%>
<%
	
	
	Cookie[] cookies = request.getCookies();
	if(cookies!=null){
		for(Cookie c: cookies){
			if(c.getName().equals("email")){
				saveEmail = c.getValue();
			}
			if(c.getName().equals("password")){
				savedPassword = c.getValue();
			}
		}
	}
%>
<jsp:useBean id="mb" class="pack.LoginBean">
	<jsp:setProperty name="mb" property="email" value="<%=saveEmail%>"/>
	<jsp:setProperty name="mb" property="password" value="<%=savedPassword%>"/>
</jsp:useBean>
<%
	if(saveEmail != null && savedPassword !=null){
		String result = mb.validate();
		if("Success".equals(result)){
			session.setAttribute("name",mb.getFirstname());
			session.setAttribute("email",mb.getEmail());
			response.sendRedirect("inbox.jsp");
		}
	}
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>SignIn</title>
    <link rel="stylesheet" type="text/css" href="./css/style.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
  </head>
  <body onload="noBack();"onpageshow="if(event.persisted)noBack();">
    <div class="container">
      <div class="forms-container">
        <div class="signin-signup">
          <form action="si" class="sign-in-form" method="post">
            <h2 class="title">Sign In</h2>
			<p id="message" style="color:red;" >
				<%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %>
			</p>
            <div class="input-field">
              <i class="fas fa-user"></i>
              <input type="email" placeholder="Email" name="email"  />
            </div>
            <div class="input-field">
              <i class="fas fa-lock"></i>
              <input type="password" placeholder="Password" name="pass" />
            </div>
			<div class="social-media"style="gap:10px;">
				<input type="checkbox" name="remember" value="yes" id="remember">
				<label for="remember" >Remember me</label>
			</div>

            <input type="submit" value="Login" class="btn solid" />

            <p class="social-text">Or Sign in with social platforms</p>
            <div class="social-media">
              <a href="#" class="social-icon">
                <i class="fab fa-facebook-f"></i>
              </a>
              <a href="#" class="social-icon">
                <i class="fab fa-twitter"></i>
              </a>
              <a href="#" class="social-icon">
                <i class="fab fa-google"></i>
              </a>
              <a href="#" class="social-icon">
                <i class="fab fa-linkedin-in"></i>
              </a>
              </a>
            </div>
          </form>


          
        </div>
      </div>
      <div class="panels-container">

        <div class="panel left-panel">
            <div class="content">
                <h3>New here?</h3>
                <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Optio minus natus est.</p>
                <button class="btn transparent" id="sign-up-btn"><a href="./Signup.jsp" style="text-decoration: none; color: aliceblue;">Sign Up</a></button>
            </div>
            <img src="./img/log.svg" class="image" alt="">
        </div>

       
      </div>
    </div>

   
  </body>
  <script>
	function disableBack() {
	  window.history.forward();
	}

	// Call the function on page load and when navigating back/forward
	window.onload = disableBack;
	window.onpageshow = function(evt) {
	  if (evt.persisted) disableBack();
	};
  </script>
</html>
