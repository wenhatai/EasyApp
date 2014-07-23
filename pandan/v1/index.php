<?php


require '.././libs/Slim/Slim.php';

\Slim\Slim::registerAutoloader();

$app = new \Slim\Slim();

$app->get('/note', function() {
	$data = array();
	for($i = 0;$i<10;$i++){
		$note['id'] = $i;
		$note['title'] = "title";
		$note['content'] = "content";
		$note['create_timestamp'] = time()-rand(0,100);
		$resource['url'] = "http://rack.1.mshcdn.com/media/ZgkyMDE0LzA2LzI1LzU5L0FuZHJvaWQxLjE3YjNiLmpwZwpwCXRodW1iCTEyMDB4NjI3IwplCWpwZw/293f6197/1cb/Android1.jpg";
		$resource['description'] = "description";
		$resource['id'] = $i;
		$note['resource'] = $resource;
		array_push($data, $note);
	}
	echo json_encode($data);
});

$app->run();
?>