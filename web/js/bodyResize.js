function resizeBoard(){
	if($('.board').height()>750){
		$('#wrap').css('height',$(this).height());
	}
}