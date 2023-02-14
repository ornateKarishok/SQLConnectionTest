<?php
$db_name = "apkh_meeting_voicerecord";
$mysql_username = "apkh_voicerecord";
$mysql_password = "UDu#3u6KrB!ChM9fdn$T*4EVC";
$server_name = "185.221.108.136:2083";
echo "connection..."
$conn =  mysqli_connect($server_name,$mysql_username,$mysql_password,$db_name );
if($conn){
	echo "connection success"
}else{
	echo "connection not success"
}
  flush();
    ob_flush();
    sleep(2);
    exit(0);
?>




ZJZ2PA7FMALNEU5NOGC6O12QLBE9K3YX