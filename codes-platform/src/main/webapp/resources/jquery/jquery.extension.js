(function($){
	//静态接口扩展
	$.extend({
		//命名空间
		namespace: function(){
			var ns = arguments[0];
			if( isString(ns) ){
				ns = ns.trim();
				ns = ns.replace(/^($|jQuery)/g, '');
				var names = ns.split('.');
				if( ! isEmpty(names) ){
					var tmp = $;
					for(var i=0; i<names.length; i++){
						tmp = tmp[names[i]] = (tmp[names[i]] || {});
					}
					return tmp;
				}
			}	
		},
		//工具
		util: {
			extend: function(){
				if(arguments.length > 0){
					var tmp = {};
					for(var i=0; i<arguments.length; i++){
						tmp = $.extend(true, tmp, arguments[i]);
					}
					return tmp;
				}
			},
			/** 封装ajax请求 */
			ajax: function(){
				var options = this.extend({
					type: 'POST',
					dataType: 'json',
					paramPrefix: null,
					data: null,
					error: function(xhr, ts, err){
						$.messager.showError();//提示错误信息
						$('body').find('.custom-dialog').dialog().dialog('close');//关闭弹出框
						console.info('对于路径：' + this.url + ' 请求失败. \n状态文本：' + ts + ' \n错误信息:' + err  );
					},
					complete: function(xhr, ts){
//						console.info('================ 请求完成后的信息(开始) ==================');
//						console.info('状态-> ', ts);
//						console.info('返回信息-> ', xhr);
//						console.info('================ 请求完成后的信息(结束) ==================');
					}
				}, arguments[0]);
				if( options.url ){
					//参数处理
					if( (!isEmpty(options.paramPrefix)) && (!isEmpty(options.data)) ){
						var pf = options.paramPrefix + '.';
						var data = options.data;
						if( ! isEmpty( data ) ){
							var sep = '&';
							//字符串
							if( isString(data) ){		//字符串
								data = sep === data.charAt(0) ? data : sep + data;
								data = data.replace(/&[^=]+=/g, function(text){
									return text.replace(sep, sep + pf);
								});
								data = sep === data.charAt(0) ? data.substring(1) : data;
								options.data = data;
							}else if( isArray(data) ){	//数组
								var tmpData = [];
								var item;
								for(var i=0; i<data.length; i++){
									item = data[i];
									isObject(item) ? tmpData.push({name: pf + item.name, value: item.value}) : tmpData.push(item);
								}
								options.data = tmpData;
							}else if( isObject(data) ){	//对象
								var tmpData = {};
								for(var prop in data){
									if( data.hasOwnProperty(prop) ){
										tmpData[pf + prop] = data[prop];
									}
								}
								options.data = tmpData;
							}
						}
					}
					//发送请求
					$.ajax(options);
				}
			},
			
			/** 同步的ajax请求 */
			sjax: function(){
				this.ajax( this.extend(arguments[0], {async: false}) );
			},
			
			/** 解析表单数据 */
			parseFormData: function(){
				var opts = arguments[0];
				var fid = opts.id;
				if( fid ){
					fid = '#' === fid.charAt(0) ? fid : '#' + fid;
				}
				var form = $( fid );
				if( form && form.length > 0 ){
					var convertType = opts.convertType ? opts.convertType : 'obj'; 
					var namePrefix = opts.namePrefix ? (opts.namePrefix + '.') : '';	//参数名称前缀
					var data = ('str' === convertType) ? form.serialize() : form.serializeArray();	//表单数据
					//字符串处理
					if( isString(data) ){
						var sep = '&';
						data = sep === data.charAt(0) ? data : sep + data;
						data = data.replace(/&[^=]+=/g, function(text){
								return text.replace(sep, sep + namePrefix);
						});
						data = sep === data.charAt(0) ? data.substring(1) : data;
						return data;
					}
					//数组处理
					var multivalueDelimiter = opts.mvd ? opts.mvd : ',';				//多值分隔符
					var tmpData = {};
					var item;
					var itemName;
					var itemValue;
					for(var i=0; i<data.length; i++){
						item = data[i];
						if( isObject(item) ){
							itemName = namePrefix + item.name;
							tmpData[itemName] = tmpData[itemName] ? (tmpData[itemName] + multivalueDelimiter +  item.value) : item.value; 
						}
					}
					return tmpData;
				}
			},
			
			/** 设置表单的值 */
			setFormValue: function(){
				var reg = /^\#/ig;
				var formId = arguments[0];
				var formVal = arguments[1];
				if( !isEmpty(formId) && !isEmpty(formVal) ){
					var $form =  isObject(formId) ? formId : reg.test(formId) ? $(formId) : $('#' + formId);
					for(var name in formVal){
						var eles = $form.find('[name='+name+']');	//获取对应的元素
						var eleVal = formVal[name];	//元素的值
						if(eles.length > 0){
							var type = eles[0].type;
							if( eles.length > 1 && type == 'checkbox' && $.isArray(eleVal) && eleVal.length > 0){	//checkbox
								$.each(eleVal, function(){
									$form.find(':checkbox[name='+name+'][value='+this+']').attr('checked', true);
								});
							}else{
								if( type == 'radio' ){
									$form.find(':radio[name='+name+'][value='+eleVal+']').attr('checked', true);
								}else if(type == 'text'){
									$form.find(':text[name='+name+']').val(eleVal);
								}else if(type == 'select-one'){
									$form.find('select[name='+name+'] option[value='+eleVal+']').attr('selected', true);
								}else if(type == 'hidden'){
									$form.find(':hidden[name='+name+']').val(eleVal);
								}else{	
									$form.find('textarea[name='+name+']').html(eleVal);
								}
							}	
						}
					}
				}
			},
			//去重复
			set: function(){
				var args = arguments;
				if( ! isEmpty(args) ) {
					var arr = [];
					var item;
					for (var i = 0; i < args.length; i++) {
						item = args[i];
						if( isArray(item) ){
							$.each(item, function(idx, ele){
								if(! arr.contains( ele )){
									arr.push(ele);
								}
							});
						}else if( ! arr.contains( item ) ){
							arr.push(item);
						}
					}
					return arr;
				}
			},
			getTimerName: function(){
				return 'Timer_' + uuidNoSymbol();
			},
			//合并
			merge: function(){
			},
			ajaxDownload: function(){
				var args = arguments[0];
				var url = isString( args ) ? args : args['url'];
				var data = isObject( args ) ? args['data'] : null;
				
				var $form = $('<form></form>');
				$form.attr('style', 'display:none'); 
		        $form.attr('target', '');
		        $form.attr('method', 'post');
		        $form.attr('action', url);
		        //在form表单中添加查询参数
		        if( data ){
		        	var val;
		        	for(var name in data){
		        		val = data[name];
		        		if( isArray(val) ){
		        			$.each(val, function(idx, obj){
		        				$form.append( $('<input/>').attr({'type':'hidden', 'name':name, 'value':obj}) );
		        			});
		        		}else{
			        		$form.append( $('input/').attr({'type':'hidden', 'name':name, 'value':data[name]}) );
		        		}
		        	}
		        }
		        $('body').append($form);
		        $form.submit(); 
			},
			//json处理
			parseJson: function(val){
				return $.parseJSON( val );				
			},
			formatJson: function(val){
				return JSON.stringify( val );
			},
			toJsonObject: function(val){
				return $.parseJSON( val );				
			},
			toJsonString: function(val){
				return JSON.stringify( val );
			}
		
		}
		
	});
	
	//对象接口扩展
	$.fn.ser = function(){
		return $.fn.serialize.call(this, arguments);
	};
	$.fn.serArray = function(){
		return $.fn.serializeArray.call(this, arguments);
	};
	
	
})(jQuery);