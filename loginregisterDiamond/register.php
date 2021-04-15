<?php

require "conn.php";
$personName = $_POST["personName"];
 $address = $_POST["address"];
 $email = $_POST["email"];
$password = $_POST["password"];

// $personName = 'adbg';
// $address = 'b1';
// $email = 'c2@gmail.com';
// $password = 'd12345';

$isValidEMail = filter_var($email , FILTER_VALIDATE_EMAIL);
if($conn){
	 if(strlen($password ) > 40 || strlen($password ) < 6){
	 echo "Password length must be more than 6 and less than 40";
	 }
	 else if($isValidEMail === false){
		echo "This Email is not valid";
	 }else{
		 // $sqlCheckUname = "SELECT * FROM registration WHERE personName LIKE '$personName'";
		 // $u_name_query = mysqli_query($conn, $sqlCheckUname);
		 $sqlCheckEmail = "SELECT * FROM registration WHERE email LIKE '$email'";
		 $email_query = mysqli_query($conn, $sqlCheckEmail);
		 // if(mysqli_num_rows($u_name_query) > 0){
		 // echo "User name is already used, type another one";
		 // }else
		 // if(mysqli_num_rows($email_query) > 0)
		 // {
			 // echo "This Email is allready registered";
		 // }else{
			
			$sql = "insert into registration(company,contact_person,address,city,state,zip,email,password,feedback) values('none','$personName','$address','none','none',0,'$email','$password','none') ";
		
			if(mysqli_query($conn,$sql))
			{
				echo "You are registered successfully";
			}else
			{
				echo "Failed to register you account";
			}
		//}
	}
}
else{
	echo "Connection Error";
}

?>