<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>SignUp</title>
    <link rel="stylesheet" type="text/css" href="./css/style.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />

</head>

<body>
    <div class="container sign-up-mode">
    
        <div class="forms-container">
            <div class="signin-signup">
                <form action="su" class="sign-up-form" method="post" id="su">
                   <h2 class="title">Sign Up</h2>
					<p id="message"></p>
                    <div class="input-field">
                        <i class="fas fa-user"></i>
                        <input type="text" placeholder="Firstname" name="firstname" id="fn"  />
                    </div>
                    <span><p id="message_fn"></p></span>
                    <div class="input-field">
                        <i class="fas fa-user"></i>
                        <input type="text" placeholder="Lastname" name="lastname" id="ln"/>
                    </div>
                    <span><p id="message_ln"></p></span>
                    <div class="input-field">
                        <i class="fas fa-envelope"></i>
                        <input type="email" placeholder="Email" name="email" id="email"/>
                    </div>
                    <span><p id="message_email"></p></span>
                    <div class="input-field" id="df">
                        <i class="fa-regular fa-calendar-days" id="calendar-icon" style="cursor: pointer;" onclick="showCalendar()"></i>
                        <input type="date" id="dob" name="dob" min="1950-01-01" max="2050-12-31" >
                    </div>
                    <span><p id="message_dob"></p></span>
                    <div class="input-field">
                        <i class="fas fa-lock"></i>
                        <input type="password" placeholder="Password" name="pass" id ="pass"/>
                    </div>
                    <span><p id="message_pass"></p></span>
                     <div class="input-field">
                        <i class="fas fa-lock"></i>
                        <input type="password" placeholder="Confirm-Password"  id="cpass"/>
                    </div>
                    <span><p id="message_cpass"></p></span>
                    <input type="submit" value="Sign Up" class="btn solid" />
                </form>
            </div>
        </div>
        <div class="panels-container">
            <div class="panel left-panel">
                
            </div>
            <div class="panel right-panel">
                <div class="content">
                    <h3>One of us?</h3>
                    <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Optio minus natus est.</p>
                    <a href="./index.jsp"><button class="btn transparent" id="sign-in-btn">Sign In</button></a>
                </div>
                <img src="./img/register.svg" class="image" alt="">
            </div>


        </div>
    
    </div>
	<script src="./js/signup.js"></script>
</body>

</html>