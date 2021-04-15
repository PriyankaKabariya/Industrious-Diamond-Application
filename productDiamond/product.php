<?php                                                                                                     
require "conn.php";

if($conn){
	// $query = mysqli_query($conn,"SELECT * FROM product");
	// if($query){
		// while($row=mysqli_fetch_array($query)){
			// $flag[]=$row;
		// }
		// print(json_encode($flag));
	// }
	
	//creating a query
 $stmt = $conn->prepare("SELECT id, shape, cut, carat, price, image FROM product");
 
 //executing the query 
 $stmt->execute();
 
 //binding results to the query 
 $stmt->bind_result($id, $shape, $cut, $carat, $price, $image);
 
 $product = array(); 
 
 //traversing through all the result 
 while($stmt->fetch()){
 $temp = array();
 $temp['id'] = $id; 
 $temp['shape'] = $shape; 
 $temp['cut'] = $cut; 
 $temp['carat'] = $carat; 
 $temp['price'] = $price; 
 $temp['image'] = $image; 
 array_push($product, $temp);
 }
 
 //displaying the result in json format 
 echo json_encode($product);
 
}
else{
	echo "Connection Error";
} 
?>