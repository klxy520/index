window.onload=function(){
	$(".pull").click(function(){
		$(".banner_xiala").slideToggle();
		scrollTo(0,0);// 滚动到顶部
	})
//	layer.config({
//		extend: [
//		'extend/layer.ext.js', 'skin/layer.ext.css'
//		]
//		});
	// 初始化字典数据
	//fn_GetDictionary();
};

/* 弹窗 */
function popup(title,url,w,h){
	if(w && h){
		layer_show(title,url,w,h);
	}else{
		layer_show(title,url,'900','500');
	}
}

/*弹层（页面类型）
* 直接弹层显示页面标签内容（E.G. : <div>test</div> -> test） 
*/
function popupForHtml(title,content,w,h){
	// 删除validform 验证错误信息
	removeValidErrorMsg(content);
	if(!(w && h)){
		w='80%';  
		h='80%';
	}
	layer.open({
		type: 1,
		title: title,
		area: [w, h], //定义层的宽高
		maxmin:true, 
		zIndex:1050,
		scrollbar: false,
		content:content
	});

}

/**
 * 描述：jQuery Datatables 产生columns
 * 
 * @param columns
 * @returns {Array}
 * @author liujicheng 2016年10月8日 下午3:26:56
 * @version 1.0
 */
function getColumns(columns){
	var resultColums=new Array();
	for ( var ele in columns) {
		var thisColumn=new Object();
		
		if (columns[ele].filed) {
			thisColumn['sName']=thisColumn['mData']=columns[ele].filed
		}
		
		if (columns[ele].render) {
			// 检测是否是单选框
			if(columns[ele].render=='checkBox'){
				thisColumn['sName']=thisColumn['mData']=columns[ele].filed
				thisColumn['mRender']=function(data, type, full){
					return '<input type="checkbox" class="tbListCheckbox" name="tbListCheckbox" recordId='+data+'>';
				}
			}else{
				thisColumn['mRender']=columns[ele].render;
			}
		}else{
			thisColumn['mRender']=function(data, type, full){
          	 if(null==data||undefined==data){
          		 return '';
          	 }
          	 return data;
           }
		}
		
		if (columns[ele]['class']) {
			thisColumn['sClass']=columns[ele]['class'];
		}else{
			thisColumn['sClass']='center';
		}
		resultColums.push(thisColumn);
	}
	return resultColums;
}



/**
 * 描述：
 * 
 * @author liujicheng 2016年10月8日 下午3:26:39
 * @version 1.0
 */
function iniWebUploader(paramObj){ 
	
	if ( !WebUploader.Uploader.support() ) {
        alert( 'Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
        throw new Error( 'WebUploader does not support the browser you are using.' );
    }
	
	if(typeof((paramObj.fileList)=='undefined'||typeof(paramObj.pick)=='undefined')&&typeof(paramObj.uploaderPanelId)=='undefined'){
		throw new Error('请传入参数:fileList和pick 或者uploaderPanelId');
	}
	else if(typeof(paramObj.formData)=='undefined')
	{
		throw new Error('请传入参数:formData');
	}
	else if(!(paramObj.formData.fileType=='image'||paramObj.formData.fileType=='file')){// 判断传入文件类型是否正确
		throw new Error('fromData 参数错误,请传入正确的fileType');
	}
	else{
		//如果传入uploaderPanelId则自动生成html
		if(typeof(paramObj.uploaderPanelId)!='undefined'){
			ini_uploaderHtml(paramObj.uploaderPanelId);
		}
		
	var $ = jQuery, $list = $(paramObj.fileList?paramObj.fileList:'#fileList'),
	// 优化retina, 在retina下这个值是2
	ratio = window.devicePixelRatio || 1,

	// 缩略图大小
	thumbnailWidth = typeof(paramObj.thumbnailWidth) != 'undefined'?paramObj.thumbnailWidth:(100 * ratio, thumbnailHeight = 100 * ratio),
			
	// WebUploader实例
	uploader;
	
	// 初始化Web Uploader
	 uploader = WebUploader.create({
		 
		 formData:paramObj.formData,

		// 选完文件后，是否自动上传。
		auto : true,
		
		// 文件接收服务端。
		server : typeof(paramObj.server)!='undefined'?paramObj.server:contextPath + '/upFile/save',
		
		// 相同文件去重，这里的true是不去重
		duplicate:true,

		// swf文件路径
		swf : contextPath + '/lib/webuploader/0.1.5/Uploader.swf',
		
		dnd: paramObj.dnd,
		
		paste: typeof(paramObj.dnd)!='undefined'?paramObj.dnd:document.body,
		
		disableGlobalDnd: typeof(paramObj.dnd)!='undefined'?paramObj.dnd:true,
		
		//是否压缩
		compress:typeof(paramObj.compress)!='undefined'?paramObj.compress:{
		    ///width: 1600,
  		    //height: 1600,
		    // 图片质量，只有type为`image/jpeg`的时候才有效。
		    quality: 80,
		    // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
		    allowMagnify: false,
		    // 是否允许裁剪。
		    crop: false,
		    // 是否保留头部meta信息。
		    preserveHeaders: true,
		    // 如果发现压缩后文件大小比原来还大，则使用原来图片
		    // 此属性可能会影响图片自动纠正功能
		    noCompressIfLarger: false,
		    // 单位字节，如果图片大小小于此值，不会采用压缩。
		    compressSize: 500000
		},
		// 验证单个文件大小是否超出限制,超出则不允许加入队列。
		fileSingleSizeLimit:1000000,

		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick: typeof(paramObj.pick)!='undefined'?paramObj.pick:{
            id: '#filePicker',
            label: '点击选择图片',
            multiple : false
        },

		// 只允许选择图片文件。
		accept : typeof(paramObj.accept)!='undefined'?paramObj.accept:{
			title : 'Images',
			extensions : 'gif,jpg,jpeg,bmp,png',
			mimeTypes : 'image/jpg,image/jpeg,image/png,image/gif'
		}
	});
	 
	 uploader.readonly=paramObj.readonly?paramObj.readonly:false;//readonly为true则不能上传图片,删除按钮消失
	 
	 uploader.fnSuccess=paramObj.fnSuccess;//添加上传成功后的回调函数
	 
	 uploader.fnRemove=paramObj.fnRemove;//添加删除图片回调函数
	 
	 uploader.fileNumLimit=paramObj.fileNumLimit?paramObj.fileNumLimit:300//允许文件最大数量,默认300张
			 
	 uploader.filePicker=typeof(paramObj.pick)!='undefined'?paramObj.pick:{
         id: '#filePicker',
         label: '点击选择图片',
         multiple : false
     }
	 
	 uploader.files=new Array();//用于存储文件信息
	 
	 //初始化后台已经存在图片
	 if(typeof(paramObj.filesIni)!='undefined'){
		 ini_uploaderFiles(uploader,paramObj.filesIni,thumbnailWidth,thumbnailHeight,$list);
		 checkFileLength(uploader);//根据文件数量和限制数量的关系操作按钮显示和隐藏
	 }
	 
	//如果配置参数为只读，则隐藏上传按钮和删除按钮
		if(uploader.readonly){
			$(uploader.filePicker.id).css('visibility','hidden');
			$('.fileItem p').unbind();
		}
	 
	// 当有文件添加进来的时候
	uploader.on('fileQueued', function(file) {
		
	});
	
	uploader.on('error', function(file, reason) {
		var errorType;
		switch(file){
		case 'F_EXCEED_SIZE':errorType='上传文件大小超出'+(reason/1000000).toFixed(2)+'M';break;
		default:errorType=file;
		}
		
		layer.alert(errorType, {title:'错误',skin: 'layui-layer-lan',closeBtn: 0,shift: 4 },function(index){
			layer.close(index);
		});
	});

	
	uploader.on('uploadSuccess', function(file,response) {
		if (0===response.status) {
		if(response.data[0]){
			var filePath=response.data[0].filePath;
			var fileId=response.data[0].fileId;
	         uploader.files.push({thumbId:file.id,filePath:filePath,fileId:fileId});
	         uploaderThumb(uploader,thumbnailWidth,thumbnailHeight,$list,file.id,filePath);
		}
		checkFileLength(uploader);
		if(typeof(uploader.fnSuccess)!='undefined'){
			uploader.fnSuccess(uploader.files);
		};
		}else{
			throw response.message||"上传错误！";
		}
	});


	}
	return uploader;
}


// 删除略缩图
function uploader_removeThumb(uploader,imgBoxId){
	var filePosi=-1;//路径所在uploader.files中的索引
	if(uploader.files){
	for (var i = 0; i < uploader.files.length; i++) {
			if (uploader.files[i].thumbId == imgBoxId){
				uploader.files.splice(i,1);
				if(typeof(uploader.fnRemove)!='undefined'){
					uploader.fnRemove(uploader.files);
				}
				 var $li = $('#'+imgBoxId);
			     $li.off().find('p').off().end().remove();
			     checkFileLength(uploader);
			};
		}
	}
}

function ini_uploaderHtml(uploaderPanelId){
	var html='<div>'
		+'<div style="width:100%; min-height:120px; border: 3px dashed #e6e6e6;padding-left:10px;padding-top:10px;">'
		+'<div id="fileList" class="uploader-list"></div>'
		+'</div>'
		+'<div id="filePicker" style="margin-top:20px;"></div>'
		+'</div>';
	$('#'+uploaderPanelId).append(html);
}

function ini_uploaderFiles(uploader,files,thumbnailWidth,thumbnailHeight,$list){
	for(var i=0;i<files.length;i++){
		 uploader.files.push({thumbId:"INI_WU_FILE_"+i,fileId:files[i].fileId,filePath:files[i].filePath});
		 uploaderThumb(uploader,thumbnailWidth,thumbnailHeight,$list,"INI_WU_FILE_"+i,files[i].filePath);	
	}
}

function uploaderThumb(uploader,thumbnailWidth,thumbnailHeight,$list,divId,imgPath){
	// 添加略缩图鼠标经过事件
	var $li=$('<div id="' + divId + '" class="fileItem">'
			+'<p style="'
			+'margin:5px 5px;'
			+'text-align:center;'
			+'float: left;'
			+'cursor: default;'
			+'width:'+thumbnailWidth+'px;'
			+'height:'+thumbnailHeight+'px;"><img class="showDetail" style="margin-top:-30px;max-width:'+thumbnailWidth+'px;max-height:'+thumbnailHeight+'px"></p></div>');
	var $img = $li.find('img');
	var $btn = "<p class='btn_p' style='"
		+"width:100%;"
		+"height:100%;"
		+"padding:5px 0;"
		+"height:20px;"
		+"background-color: rgba(0,0,0,0.5);"
		+"color: #fff;"
		+"visibility:hidden;"
		+"text-align: center;"
		+"position: relative;"
		+"margin:0 0;"
		+"font-size: 18px;"
		+"cursor: pointer;' "
		+"title='删除'>"
		+"<img src='data:image/png;base64,iVBORw0K"
		+"GgoAAAANSUhEUgAAABEAAAAQCAYAAADwMZRfAAAACXBIWXMAAAsTAAALEwEAmpwYA"
		+"AAAIGNIUk0AAHolAACAgwAA+f8AAIDpAAB1MAAA6mAAADqYAAAXb5JfxUYAAADvSUR"
		+"BVHja1JNBTsNADEXfOCmCW3AVDtJrdJ9Fb4PEDbrrDbgBKyQWWVQCZfr/sCCJoqiUR"
		+"F1h6Wsky/4e29+plMKtFk3TYHt0SELSAShzSDpIGmNt0zQN9Zy1qiqAp0sVq6q66A+"
		+"gBk6zqtdsGncC6gDOpZQX23nNHGznUsozcB7a2dreAkTEEgJsk1L6ceScB+wldWWBSe"
		+"pyzvshN02m3UXEZk07wF3/+6DHSNC27aPtV0lHIEk62n4DUv8SEZsx96+Kc138tuLbFf"
		+"t/SPoz4JqGpiv+jIj7FSv+Ah4Aor9aJO1sfywkeJe0G3K/BwBqTMbLpUy3HAAAAABJRU"
		+"5ErkJggg=='/></p>";
	
	$li.find('p').prepend($btn);
	$list.append($li);
	$list.find($li).find('.btn_p').click(function(){
		uploader_removeThumb( uploader,divId );
	});
	
	$(".fileItem p").mouseenter(function(){
		$(this).find("p").css({visibility:"visible"});
	});
	$(".fileItem p").mouseleave(function(){
		$(this).find("p").css({visibility:"hidden"});
	});
	
	$img.attr('src', imagePath+'/'+imgPath);
	//图片预览
	$list.find($li).find('.showDetail').click(function(){
		var path = "../../"+imgPath;
		layer.ready(function(){ //为了layer.ext.js加载完毕再执行
			  layer.photos({
				  photos: {
			    	  "title": "", //相册标题
			    	  "id": 123, //相册id
			    	  "start": 0, //初始显示的图片序号，默认0
			    	  "data": [   //相册包含的图片，数组格式
			    	    {
			    	      "alt": "",
			    	      "pid": 666, //图片id
			    	      "src": path, //原图地址
			    	      "thumb": path //缩略图地址
			    	    }
			    	  ]
			    	}
			    ,anim: 0 //0-6的选择，指定弹出图片动画类型，默认随机
			  });
	    });
	});
}


/**
 * 描述：检测上传文件是否超过最大限制
 * @param uploader
 * @author liujicheng 2016年10月17日 下午4:14:07 
 * @version 1.0
*/
function checkFileLength(uploader){
	if(uploader.files.length>=uploader.fileNumLimit){
		$(uploader.filePicker.id).css('visibility','hidden');
	}else{
		$(uploader.filePicker.id).css('visibility','visible');
	}
}

/**
 * Validform 弹窗效果
 * @param msg
 * @param o
 * @param cssctl
 * @returns
 */
function validformTiptype(msg,o,cssctl){
	//msg：提示信息;
	//o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
	//cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
	if(!o.obj.is("form")){//验证表单元素时o.obj为该表单元素，全部验证通过提交表单时o.obj为该表单对象;
		var errorDom = o.obj.prev(".error_info");
		if(!errorDom || typeof errorDom =="undefined" || errorDom.length==0){
			o.obj.before('<div class="error_info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>');
			errorDom=o.obj.prev(".error_info");
		}
		var objtip=errorDom.find(".Validform_checktip");
		cssctl(objtip,o.type);
		objtip.text(msg);
		var	top=o.obj.offset().top;
		if(o.type==2 || o.type==4){
			errorDom.remove();
		}else{
			if(errorDom.is(":visible")){return;}
			errorDom.css({"margin-top":"-32px",display:"inline-block"}).animate({
				top:top-75
			},200);
		}
	}	
}
// 自定义验证类型
var customDataType={
		"fzh2-200":/^[^\u4e00-\u9fa5]{2,200}$/,//除了中文,其他都可以输入
		"zh2-20":/^[\u4E00-\u9FA5\uf900-\ufa2d]{2,20}$/,
		"name":/^[\u4e00-\u9fa5\\.]{2,20}$/,//输入2-20个中文字符允许小数点
		"zh4-20":/^[\u4E00-\u9FA5\uf900-\ufa2d]{4,20}$/,
		"zh2-50":/^[\u4E00-\u9FA5\uf900-\ufa2d]{2,50}$/,
		"zhy2-50":/^[\u4e00-\u9fa5a-zA-Z]{2,50}$/,
		"lm2-4":/^[a-zA-Z0-9_]{4,12}$/,
		"nf":/^\d+(\.\d+)?$/,    //正数的正则表达式
		"n8-20":/^[\d+]{8,20}$/,//8-20位数字
		"n3-10":/^[\d+]{3,10}$/,//3-10位数字
		"sfzh": /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/ ,//身份证号(15位,18位都可以)
		"lxdh":/^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/,//固定电话和手机号码都可以
		"pos_int":/^(0|[1-9][0-9]*)$/, //正整数
		"empty": /^\s*$/,
		"checkLoginName":function(gets,obj,curform,regxp){
			return checkLoginName(gets,obj,curform,regxp);
		},
		"checkSn":function(gets,obj,curform,regxp){
			return checkSn(gets,obj,curform,regxp);
		},
		"s500":function(gets,obj,curform,regxp){
			if(gets.length<=0){
				return"公告内容不能为空"
			}
			if(gets.length>200){
				return "公告内容过长";
			}
			return true
		},
		"lm8":/^[a-zA-Z0-9_]{8}$/, // 8位字母+数字
		"lm8-50":/^[a-zA-Z0-9_]{8,50}$/, // 8位到50位字母+数字
		"n9":/^[0-9]{9}$/, // 9位数字
		"tel":/^((\d{3,4}\-)|)\d{7,8}$/, // 固定电话：区号+号码
		"n0":/^[0-9a-zA-Z]*$/, // 字母或数字
		"wsdlurl":/^(http|https|ftp)\:\/\/([a-zA-Z0-9\.\-]+(\:[a-zA-Z0-9\.&amp;%\$\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0-9\-]+\.)*[a-zA-Z0-9\-]+\.[a-zA-Z]{2,4})(\:[0-9]+)?(\/[^/][a-zA-Z0-9\.\,\?\'\\/\+&amp;%\$#\=~_\-@]*)*\?wsdl$/,
		"extensionWorker_datatype":function(gets,obj,curform,regxp){
			return checkWxtensionWorker(gets,obj,curform,regxp);
		} // 推广人验证规则
};

/**
 * 检查登录名是否合法
 * @param gets
 * @param obj
 * @param curform
 * @param regxp
 * @returns {Boolean}
 */
function checkLoginName(gets,obj,curform,regxp){
	var check=false;
	$.ajax({
         "type": 'post',
         "url": contextPath+"/user/checkLoginName",
         "dataType": "json",
         "async" : false,
         "data": {
             'loginName': gets
         },
         "success": function(data) {
         	if(data.status==0){
         		check=true;
			}else{
				check="该账号已存在";
			}   
         }
     });
	return check;
}


/**
 * 检查工号是否合法
 * @param gets
 * @param obj
 * @param curform
 * @param regxp
 * @returns {Boolean}
 */
function checkSn(gets,obj,curform,regxp){
	var check=false;
	$.ajax({
         "type": 'post',
         "url": contextPath+"/user/checkSn",
         "dataType": "json",
         "async" : false,
         "data": {
             'sn': gets
         },
         "success": function(data) {
         	if(data.status==0){
         		check=true;
			}else{
				check="该工号已存在";
			}   
         }
     });
	return check;
}

/**
 * 检查推广人员姓名、编号是否合法
 * @param gets
 * @param obj
 * @param curform
 * @param regxp
 * @returns {Boolean}
 */
function checkWxtensionWorker(gets,obj,curform,regxp){
	var check=false;
	var extensionWorkerName="";
	var extensionWorkerId="";
	if(obj.attr("id")=="extensionWorkerName"){
		extensionWorkerName = gets;
		extensionWorkerId = curform.find("#extensionWorkerId").val();
		//addUserForm_Validform.check(true,curform.find("#extensionWorkerId"));
	}else{
		extensionWorkerName = curform.find("#extensionWorkerName").val();
		extensionWorkerId = gets;
		//addUserForm_Validform.check(true,curform.find("#extensionWorkerName"));
	}
	if(extensionWorkerName.length<1 && extensionWorkerId.length<1 ){
		check=true;
		removeValidErrorMsgForInput(curform.parent("div"),"extensionWorkerName");
		removeValidErrorMsgForInput(curform.parent("div"),"extensionWorkerId");
	}else{
		if(obj.attr("id")=="extensionWorkerName" && extensionWorkerName.length>0 && (regxp["zh2-20"].exec(extensionWorkerName))!=null){
			check=true;
		}
		if(obj.attr("id")=="extensionWorkerId" && extensionWorkerId.length>0 &&  (regxp["lm2-4"].exec(extensionWorkerId))!=null){
			check=true;
		}
	}
	return check;
}

/**
 * 获取选中的多选框数据id
 * @returns {Array}
 */
function getCheckedIds(){
	var checkedIds = null;
	var checkedDoms = $(".checkbox_id:checked");
	if(checkedDoms && checkedDoms.length>0){
		checkedIds = new Array();
		for(var i=0;i<checkedDoms.length;i++){
			checkedIds.push($(checkedDoms[i]).val());
		}
	}
	return checkedIds;
}

/**
 * 获取一个选中的多选框数据id
 * @returns {Array}
 */
function getCheckedObject(dataTable){
	var data = null;
	var nTrs = dataTable.fnGetNodes();
	for(var i = 0; i < nTrs.length; i++){
		var checked = $(nTrs[i]).find('.checkbox_id:checked');
        if(checked && checked.length>0){
        	data = dataTable.fnGetData(nTrs[i]);
        	break;
        }
    }
	return data;
}

/**
 * 获取所有选中的多选框数据
 * @returns {Array}
 */
function getAllCheckedObject(dataTable){
	var checkedObjectArray = new Array();
	var nTrs = dataTable.fnGetNodes();
	for(var i = 0; i < nTrs.length; i++){
		var checked = $(nTrs[i]).find('.checkbox_id:checked');
        if(checked && checked.length>0){
        	checkedObjectArray.push(dataTable.fnGetData(nTrs[i]));
        }
    }
	return checkedObjectArray;
}

/**
 * 通过给tr添加点击事件,实现选中checkbox(单选)
 * 
 * @param str
 */
function checkedOfClickTr(str){
	$(str).click(function(e){
		 e.stopPropagation();
		 $(str).not(this).attr("checked", false);
	});
	// 绑定 tr 事件 点击checkbox更容易
	$(str).closest('tr').click(function(){
		var checked=$(this).find(str)[0].checked
		$(str).prop('checked',false);
		if(checked){
			$(this).find(str).prop('checked',false);
		}else{
			$(this).find(str).prop('checked',true);
		}
	})
}
/**
 * str 复选框的class的值 如 .checkbox_id
 * checkbox_id_all 全选复选框的id
 * 通过给tr添加点击事件,实现选中checkbox(多选)
 */
function checkedOfManyClickTr(str,checkbox_id_all){
	$(str).click(function(e){
		 e.stopPropagation(); 
	});
	$(str).closest('tr').click(function(){
		var any=$(this).find(str)
		if(any.is(':checked')){
			any.prop('checked',false);
		}else{
			if(any.attr('id')!=checkbox_id_all){
				any.prop('checked',true);
			}
		}
	})
}
/**
 * 添加日志
 */
function fn_addOperationLog(formName, recordId, type, detail, remark){
	$.ajax({    
	    url:contextPath + '/operationLog/addOperationLog',  
	    data:{"formName":formName,"recordId":recordId,"type":type,"detail":detail,"remark":remark},    
	    type:'post',    
	    cache:false,    
	    dataType:'json',
	    success:function(data) {
	    	if(data.status ==0){
	    	}
	    },    
	    error : function() {    
	   	 	layer.alert("网络异常，请稍候重试！");    
	    }    
	});
}

//删除validform 验证错误信息
var removeValidErrorMsg=function removeValidErrorMsg(fromDiv){
	if(fromDiv && fromDiv.find("form")){
		var from = fromDiv.find("form");
		from.find(".Validform_error").removeClass("Validform_error");
		if(from.find("div").find(".error_info")){from.find("div").find(".error_info").remove()};
		from.find("[datatype]").removeData("cked").removeData("dataIgnore").each(function(){
			this.validform_lastval=null;
		});
	}
}

//删除validform 验证错误信息 指定输入框id
var removeValidErrorMsgForInput=function removeValidErrorMsgForInput(fromDiv,inputId){
	if(fromDiv && fromDiv.find("form") && fromDiv.find("form").find("#"+inputId)){
		var from = fromDiv.find("form");
		var input = from.find("#"+inputId);
		input.removeClass("Validform_error");
		if(input.prev(".error_info")){input.prev(".error_info").remove()};
		input.removeData("cked").removeData("dataIgnore").validform_lastval=null;
	}
}

/**
 * 获取数据字典
 */
function fn_GetDictionary(){
	$.ajax({
		url:contextPath + '/dictionary/getAllDict',  
		type:'get',    
		async: false,
		dataType:'json',
		success:function(data) {
		    if(data.status ==0){
		    	var dictList = data.data;
		    	if(dictList!=null && dictList.length>0){
		    		dictionaryList = dictList;
		    		window.top['_dictionaryList'] = dictionaryList;
		    		//window.top['_CACHE2'][dicFlag] = dictionaryList;
		    	}
		    }else{
		    	layer.alert("初始化数据字典失败，请稍候重试");
		    }
		 },    
		 error : function() {    
		   	 layer.alert("初始化数据字典失败，请稍候重试！");    
		 }    
	});
}

/**
 * 根据 key 和 code 获取名称
 * @param dictKey
 * @param code
 * @returns
 */
function fn_getDictByKeyCode(dictKey,code){
	var str = "暂无数据";
	var list = window.top['_dictionaryList'];
	if(list==null){
		fn_GetDictionary();
		list = window.top['_dictionaryList'];
	}
	var subDicitonaryList = null;
	for(var i in list){ 
		if(dictKey===list[i].dictkey){
			// 获取二级字典
			subDicitonaryList = list[i].subDicitonaryList;
			break; 
		}
	}
	for(var j in subDicitonaryList){
		if(code+""===subDicitonaryList[j].description){
			// 获取二级字典
			str = subDicitonaryList[j].value;
			break; 
		}
	}
	return str;
}

/**
 * 根据key和value判断数据字典是否存在
 * @param dictKey
 * @param code
 */
function fn_getDictByKeyValue(dictKey,value){
	var flag = false;
	var list = window.top['_dictionaryList'];
	if(list==null){
		fn_GetDictionary();
		list = window.top['_dictionaryList'];
	}
	var subDicitonaryList = null;
	for(var i in list){ 
		if(dictKey===list[i].dictkey){
			// 获取二级字典
			subDicitonaryList = list[i].subDicitonaryList;
			for(var j in subDicitonaryList){
				if(value+""===subDicitonaryList[j].value){
					flag = true;
					break; 
				}
			}
			break; 
		}
	}
	return flag;
}
/**
 * 根据 key 和 code 判断描述+值是否存在
 * @param dictKey
 * @param code
 * @returns
 */
function fn_getDictByKeyCodeAndDescription(dictKey,code){
	var falg = true;
	var list = window.top['_dictionaryList'];
	if(list==null){
		fn_GetDictionary();
		list = window.top['_dictionaryList'];
	}
	var subDicitonaryList = null;
	for(var i in list){ 
		if(dictKey===list[i].dictkey){
			// 获取二级字典
			subDicitonaryList = list[i].subDicitonaryList;
			break; 
		}
	}
	for(var j in subDicitonaryList){
		var dbCode=subDicitonaryList[j].description+"-"+subDicitonaryList[j].value
		if(code===dbCode){
			// 获取二级字典
			falg =false;
			break; 
		}
	}
	return falg;
}
/**
 * 获取机构信息
 */
function fn_GetHospital(){
	$.ajax({
		url:contextPath + '/hospital/loadAllHospital',  
		type:'get',    
		async: false,
		dataType:'json',
		success:function(data) {
		    if(data.status ==0){
		    	var hospitalList = data.data;
		    	if(hospitalList!=null && hospitalList.length>0){
		    		hospitalLists = hospitalList;
		    		window.top['_hospitalLists'] = hospitalLists;
		    	}
		    }else{
		    	layer.alert("初始化机构信息失败，请稍候重试");
		    }
		 },    
		 error : function() {    
		   	 layer.alert("初始化机构信息失败，请稍候重试！");    
		 }    
	});
}
/**
 * 根据 key 获取机构信息
 * @param Key
 * @param code
 * @returns
 */
function fn_getHospitalByKey(Key){
	var str = "暂无数据";
	var list = window.top['_hospitalLists'];
	if(list==null){
		fn_GetHospital();
		list = window.top['_hospitalLists'];
	}
	for(var i in list){ 
		if(Key===list[i].hKey){
			str = list[i].name;
			break; 
		}
	}
	return str;
}
