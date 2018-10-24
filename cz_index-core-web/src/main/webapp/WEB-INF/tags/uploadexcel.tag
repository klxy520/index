<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}"/>

<link rel="stylesheet" type="text/css" href="${contextPath}/lib/webuploader/0.1.5/webuploader.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/lib/webuploader/0.1.5/boo.css" />
<script type="text/javascript" src="${contextPath}/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${contextPath}/lib/webuploader/0.1.5/webuploader.js"></script>

<%@ attribute name="id" required="true"%>
<%@ attribute name="successCallback"%>
<%@ attribute name="errorCallback"%>

<c:set var="id" value="${id}"/>

	<div id="uploader" class="wu-example">
		<!--用来存放文件信息-->
		<div id="thelist" class="uploader-list"></div>
		<div class="btns">
			<div id="${id}">选择文件</div>
			<input type="hidden" id="${id}ExcelPath" name="${id}ExcelPath"/>
		</div>
	</div>

<script type="text/javascript">
/**
 * 初始化 上传Excel文件
 */
// $(function(){
// 	loadWebUploaderExcel();
// });

//初始化 上传文件
function loadWebUploaderExcel(){
	if(document.getElementById('${id}'+'ExcelPath').value != null 
			&& document.getElementById('${id}'+'ExcelPath').value != "") {
		$('#thelist *').remove();
		document.getElementById('${id}'+'ExcelPath').value = "";
	}
	var uploaderExcel = WebUploader.create({
		auto : true,
		swf : contextPath + '/lib/webuploader/0.1.5/Uploader.swf',
		// 文件接收服务端。
		server : contextPath + '/upFile/saveExcel',
		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick : {
			id : '#${id}',
			//只能选择一个文件上传
			multiple: false,
			label: '点击选择文件'
		},
		// 验证单个文件大小是否超出限制,超出则不允许加入队列。
 		fileSingleSizeLimit:1000000, 
		//限制只能上传一个文件
		fileNumLimit: 1, 
		// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
		resize : false,
		// 只允许选择excel表格文件。
		accept : {
			title : 'Excel',
			extensions : 'xls,xlsx',
			mimeTypes : 'application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
		}
	 });
	
	//当有文件被添加进队列的时候
	uploaderExcel.on( 'fileQueued', function( file ) {
		$('#thelist').append( '<div id="' + file.id + '" class="item">' +
			'<h4 class="info">' + file.name + '</h4>' +
			'<p class="state">等待上传...</p>' +
		'</div>' );
	});
	
	//当文件覆盖之前的文件时
	uploaderExcel.on( 'beforeFileQueued', function( file ) {
		var files = uploaderExcel.getFiles();
		if (files.length > 0) {
			for (var i = 0; i < files.length; i++) {
				uploaderExcel.removeFile(files[i], true);
				$("#"+files[i].id).remove();
			}
		}
	});
	
	// 文件上传过程中创建进度条实时显示。
	uploaderExcel.on( 'uploadProgress', function( file, percentage ) {
		var $li = $( '#'+file.id ),
			$percent = $li.find('.progress .progress-bar');

		if ( !$percent.length ) {
			$percent = $('<div class="progress progress-striped active">' +
			  '<div class="progress-bar" role="progressbar" style="width: 0%;color:green;">' +
			  '</div>' +
			'</div>').appendTo( $li ).find('.progress-bar');
		}
		$li.find('p.state').text('上传中');
		$percent.css( 'width', percentage * 100 + '%' );
	});

	// 文件上传成功、失败处理
	uploaderExcel.on( 'uploadSuccess', function( file, response ) {
		if (0===response.status) {
		$( '#'+file.id ).find('p.state').text('已上传');
			if(response.data[0]){
				var filePath=response.data[0].filePath;
				document.getElementById('${id}'+'ExcelPath').value =filePath;
				
				// 成功后回调
				${successCallback};
			}
		} else {
			$( '#'+file.id ).find('p.state').text('上传出错:'+response.message);
			if(response.data!=null&&parseInt(response.data)==-1){
				top.location.reload();
				}
			// 出错后回调
			${errorCallback};
		}
	});
	uploaderExcel.on( 'uploadError', function( file ) {
		$( '#'+file.id ).find('p.state').text('上传出错');
		 // 出错后回调
		${errorCallback};
	});
	uploaderExcel.on( 'uploadComplete', function( file ) {
		$( '#'+file.id ).find('.progress').fadeOut();
 	});
}
</script>
