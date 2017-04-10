$(function(){
    var _validFileExtensions = ["jpg", "jpeg", "bmp", "gif", "png"];    
    var storedFiles = [];
    var cloneDom = [];
    var uploaded = false; // 업로드 버튼 눌렀는지 여부
    var imgNum = 0;
    
    $('#submitbtn').attr('disabled','disabled');
    //버튼으로 file 열기
    $('#upload').first().on('click',function(e){
        e.preventDefault();
        $('#files').trigger('click');
    });
    
    
    //input type="file" 변경될 때
    $('#files').on('change',function(e){
        var wrongfileext =  new Array();
        var returnval = false;
        // 항목 개수 확인
        if($(this).prop('files').length+storedFiles.length+($('#numberofImg').val()*1)>5){
        	tryAlert("outoflimit");
        	$(this).val();
        	storredFiles = [];
        }
        else
        {
        // 파일의 확장자 확인
        $.each($(this).prop('files'),function(index,data){
            var filename = data.name;
            for(var j=0;j<_validFileExtensions.length;j++){
                var arr = filename.split('.');
                arr[1] = arr[1].toLowerCase();
                if(_validFileExtensions[j]==arr[1].toLowerCase()){
                    returnval = true;
                }
                if(j==_validFileExtensions.length-1){
                    wrongfileext.push(returnval);
                }
            }
            returnval = false;
        });
        	/*유효성 체크*/
        	var validation = false;
        	for(var i=0;i<wrongfileext.length;i++){
            	if(wrongfileext[i]==false){
                	if(i==wrongfileext.length-1){
                		validation = true;
                	}
                	tryAlert();
                	break;
            	} 
        	}
        	$.each($(this).prop('files'),function(index,data){
        		if(data.size/(1048576*5)>1){
        			tryAlert('size');
        			validation = true;
        			return false;
        		}
        	});
        	//이상이 없다면 thumbnail 생성
        	if(validation==false){
        		$.each($(this).prop('files'),function(index,data){
        			createDOM(data);  
        		});
        		$(this).val();
        		if(imgNum>0){
        			$('#submitbtn').removeClass('disabled').removeAttr('disabled');
        		}
        	}
        }
    });
    //thumbnail 삭제
    $(document).on('click','.removebtn',removeFile);

    function createDOM(data){
    	storedFiles.push(data);
            var size;
            if(data.size/1048576>=1){
                size = Math.round(data.size/1048576*100)/100 + "mB";
            }else{
                size = Math.round(data.size/1024*100)/100 + "kB";
            }
            var reader = new FileReader();
            reader.readAsDataURL(data);
            reader.onloadend = function(){
                var appendVal = "<div class=\"thumbnail-wrapper newImg\">";
                appendVal += "<div class=\"img-wrapper\"><img class=\"thumbnail-img\" src=\""+this.result+"\"></div>";
                appendVal += "<div class=\"file-name\">"+data.name+"</div>";
                appendVal += "<div class=\"file-size\">"+size+"<button class=\"removebtn\"><span class=\"removeIcon\"><span></button></div>"+"</div>";
                $('.thumbnail-area').append(appendVal);
            }
            imgNum +=1;
    }
    
    //AJAX
    $("#fileform").on("submit", handleForm);
    
    $('#submitbtn').on('click',function(){
    	$("#fileform").trigger("submit");
    });
    
    
    $('.mainbtn').on('click',function(){
    	if((imgNum>0&&uploaded==false)){
    		alert('업로드 버튼을 눌러주세요.');
    	}else{
    		$('#mainform').submit();
    	}
    });
    
 
    function tryAlert(val){
    	if(val==null){
    		alert('jpg, jpeg, bmp, gif, png만 업로드 할 수 있습니다.');
    	}else if(val=='size'){
    		alert('이미지 크기는 한 파일당 최대 5mB입니다.');
    	}else if(val=='outoflimit'){
    		alert('업로드는 최대 5개까지만 할 수 있습니다.');
    	}
        $('#files').val('');
    }
    function handleForm(e) {
        e.preventDefault();
        var data = new FormData();
        
        for(var i=0, len=storedFiles.length; i<len; i++) {
            data.append('files', storedFiles[i]); 
        }
        $.ajax({
            url: '/noticeImageCancel.do',
            type: "post",
            data: data,
            // cache: false,
            processData: false,
            contentType: false,
            success: function(x, textStatus, jqXHR) {
            	var str = "";
            	var numofpair = storedFiles.length;
            	$('#submitbtn').addClass('disabled').attr('disabled','disabled');
            	$('#allcancelbtn').removeClass('disabled').removeAttr('disabled');
            	$('.removebtn').remove();
            	uploaded = true;
            	alert("파일 "+numofpair+"개가 업로드 되었습니다.");
            	imgNum = 0;
            }, error: function(jqXHR, textStatus, errorThrown) {
            	if(jqXHR.responseText==null||jqXHR.responseText==""){
            		alert('업로드를 할 수 없습니다. 파일 한 개의 Size는 5MB미만, 업로드 파일의 개수는 5개 이하여야 합니다.');
            	}else{
            		alert("message:"+errorThrown);
            	}
            }
        });
    }
    $('#allcancelbtn').on('click',function(e){
    	e.preventDefault();
    	$.ajax({
            url: '/noticeImageCancel.do',
            type: "get",
            // cache: false,
            processData: false,
            contentType: false,
            success: function(x, textStatus, jqXHR) {
            	$('#allcancelbtn').addClass('disabled').attr('disabled','disabled');
            	$('.newImg').remove();
            	uploaded = false;
            	storedFiles.splice(0, storedFiles.length);
            	alert("업로드한 파일을 모두 제거했습니다.");
            }, error: function(jqXHR, textStatus, errorThrown) {
            	if(jqXHR.responseText==null||jqXHR.responseText==""){
            		alert('파일 제거에 실패했습니다.');
            	}else{
            		alert("message:"+errorThrown);
            	}
            }
        });
    });
    $(document).on('click',$('#cancelbtn'),function(e){
    	var activeEle = document.activeElement;
    	if($(activeEle).text()=="삭제"){
    	var filename = $.trim($(activeEle).parent().siblings('.file-name').text());
    	var tagval = "<input type=\"hidden\" name=\"deleteFile[]\" value=\""+filename
    	+"\">";
    	$('#mainform').append(tagval);
    	cloneDom.push($(activeEle).parent().parent());
    	$('#retract').removeClass('disabled').removeAttr('disabled');
    	$('#numberofImg').val($('#numberofImg').val()-1);
    	$(activeEle).parent().parent().remove();
    	}
    });
    
    $('#retract').on('click',function(e){
    	e.preventDefault();
    	if($('.thumbnail-wrapper').length+cloneDom.length<=5){
    	$.each($(cloneDom),function(index,data){
    		$('.thumbnail-area').prepend(data);
    	});
    	cloneDom=[];
    	$( "input[name='deleteFile[]']" ).remove();
    	$('#retract').addClass('disabled').attr('disabled');
    	}else{
    		tryAlert();
    	}
    });
    
    
    function handleFileSelect(e) {
        var files = e.target.files;
        var filesArr = Array.prototype.slice.call(files);
        filesArr.forEach(function(f) {          

            if(!f.type.match("image.*")) {
                return;
            }
            
            storedFiles.push(f);
            
            var reader = new FileReader();
            reader.onload = function (e) {
            	var appendVal = "<div class=\"thumbnail-wrapper\">";
                appendVal += "<div class=\"img-wrapper\"><img class=\"thumbnail-img\" src=\""+this.result+"\"></div>";
                appendVal += "<div class=\"file-name\">"+data.name+"</div>";
                appendVal += "<div class=\"file-size\">"+size+"<button class=\"removebtn\"><span class=\"removeIcon\"><span></button></div>"+"</div>";
                $('.thumbnail-area').append(appendVal);
                
            }
            reader.readAsDataURL(f); 
        }); 
    }
    function removeFile(e) {
        var file = $(this).parent().parent().find('.file-name').text();
        for(var i=0;i<storedFiles.length;i++) {
            if(storedFiles[i].name === file) {
                storedFiles.splice(i,1);
                break;
            }
        }
        $(this).parent().parent().remove();
        imgNum -=1;
        if(imgNum<1){
        	$('#submitbtn').addClass('disabled').attr('disabled','disabled');
        	uploaded = false;
        }
    }
    if($('.board').height()>750){
		$('#wrap').css('height',$('.board').height());
	}
}); 


