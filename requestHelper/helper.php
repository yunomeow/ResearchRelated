<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<!-- Custom styles for this template -->
		<link href="css/site.css" rel="stylesheet">
	</head>
<script>
function cal(){
	document.getElementById('textform').submit();
}

</script>
<body>
<div class="wrap">
	<div class="header">
	<h1>群眾外包文章產生輔助系統</h1>
	</div>
	<hr>
	<div class="container">
	<form action = "<?php $_PHP_SELF ?>" method = "GET" id="textform">
		<div class="row page-header">
			<div class="col-md-12">
			<div class="btn btn-info" onclick="cal()">開始計算</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">				
				<div class="form-group">
				  <label>文章:</label>
				  <textarea class="form-control" rows="8" id="content" name="input" ></textarea>
				  


      </form>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
			<label>建議:</label>
			<div class="advise">
			<br>

<?php			

	//echo $_GET['input'];
	if($_GET['input']){
	$fp = fopen('test/in/text.txt', 'w');
	fwrite($fp, $_GET['input']);
	fclose($fp);
	$locale = 'zh_TW.UTF-8';
	setlocale(LC_ALL, $locale);
	putenv('LC_ALL='.$locale);
	$output = shell_exec("../jre/bin/java -jar CKIPClient.jar ckipsocket-utf-8.propeties test/in test/out");
	//print_r($output);
	$output = shell_exec("../jre/bin/java -jar LIWC.jar");		
	print_r($output);
}				
?>
			
			<br>
			</div>
			</div>
		</div>
		<hr>
	</div>
</div>

</body>
<script>
<?php
function go(){
	echo  str_replace("\r\n","\\n",$_GET['input']);	
}


?>
document.getElementById("content").innerHTML = "<?php go()?>";
</script>
</html>