const firstname = document.getElementById("fn")
const lastname = document.getElementById("ln")
const calendarIcon = document.getElementById("calendar-icon");
const dateInput = document.getElementById("dob");
const password = document.getElementById("pass");
const confirm_password = document.getElementById("cpass");
const email = document.getElementById("email");
const form = document.getElementById("su");
// Array of key–value objects to store the field values where we have to apply requird valdation  
const fields = [
  { id: "fn", messageId: "message_fn", message: "First name is required." },
  { id: "ln", messageId: "message_ln", message: "Last name is required." },
  { id: "email", messageId: "message_email", message: "Email is required." },
  { id: "pass", messageId: "message_pass", message: "Password is required." },
  { id: "cpass", messageId: "message_cpass", message: "Confirm password is required." },
];
function showCalendar(){
    dateInput.showPicker();
  }

fields.forEach(({ id, messageId, message })=>{
    const field = document.getElementById(id);
    const msg = document.getElementById(messageId);
    // for blur event
    field.addEventListener("blur", () => {
    if (field.value.trim() === "") {
      msg.textContent = message;
      msg.style.color = "red";
    } else {
      msg.textContent = "";
    }

    // Special check for password match
    if (id === "cpass" && field.value.trim() !== password.value.trim()) {
      msg.textContent = "Passwords do not match.";
      msg.style.color = "red";
    }
})})

form.addEventListener("submit",(e)=>{
    if(firstname.value.trim()==="" || lastname.value.trim()==="" || email.value.trim()===""|| password.value.trim()==="" || confirm_password.value.trim()===""||dateInput.value.trim()===""){
        e.preventDefault();
        message.textContent = "Please fill in all fields.";
        message.style.color = "red";
}})

