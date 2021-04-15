<?php                                                                                                     
require "conn.php";

if($conn){
	$query = mysqli_query($conn,"SELECT * FROM events");
	if($query){
		while($row=mysqli_fetch_array($query)){
			$flag[]=$row;
		}
		print(json_encode($flag));
	}
}
else{
	echo "Connection Error";
} 
?>