/**
 * javascript内置对象的类型
 * @type 
 */
var _TYPES = {
	Undefined: 	'[object Undefined]',
	Null: 		'[object Null]',
	Boolean: 	'[object Boolean]',
	String: 	'[object String]',
	Number: 	'[object Number]',
	Array: 		'[object Array]',
	Object: 	'[object Object]',
	Function: 	'[object Function]'
};

/**
 * 判断是否是Undefiend的类型
 * @return {}
 */
function isUndefined(){
	return is(arguments[0], _TYPES.Undefined);
}

/**
 * 判断是否是Null类型
 * @return {}
 */
function isNull(){
	return is(arguments[0], _TYPES.Null);
}

/**
 * 判断是否是Boolean类型
 * @return {}
 */
function isBoolean(){
	return is(arguments[0], _TYPES.Boolean);
}

/**
 * 判断是否是String类型
 * @return {}
 */
function isString(){
	return is(arguments[0], _TYPES.String);
}

/**
 * 判断是否是Number类型
 * @return {}
 */
function isNumber(){
	return is(arguments[0], _TYPES.Number);
}

/**
 * 判断是否是Array类型
 * @return {}
 */
function isArray(){
	return is(arguments[0], _TYPES.Array);
}

/**
 * 判断是否是Object类型
 * @return {}
 */
function isObject(){
	return is(arguments[0], _TYPES.Object);
}

/**
 * 判断是否是Function类型
 * @return {}
 */
function isFunction(){
	return is(arguments[0], _TYPES.Function);
}

/**
 * 类型判断
 * @param {} obj
 * @param {} type
 * @return {}
 */
function is(obj, type){
	return Object.prototype.toString.call(obj) === type;
}

/**
 * 判断是否为空
 * @return {}
 */
function isEmpty(){
	var args = arguments;
	var val = args[0];
	if( isUndefined(val) || isNull(val) ){
		return true;
	}
	if( isString( val ) ){
		return val.trim() === '';
	}
	if( isArray( val ) ){
		return val.length === 0;
	}
	if( isObject(val) ){
		for(var prop in val){
			if( val.hasOwnProperty(prop) ){
				return false;
			}
		}
		return true;
	}
	return false;
}

/**
 * 获取对象自身的属性
 * @return {}
 */
function getOwnProperties(){
	var obj = arguments[0];
	var props = [];
	if( isObject(obj) ){
		for(var p in obj){
			if( obj.hasOwnProperty(p) ){
				props.push(p);
			}
		}
	}
	return props;
}

function uuid(len, radix) {
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    var uuid = [], i;
    radix = radix || chars.length;
 
    if (len) {
      // Compact form
      for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
    } else {
      // rfc4122, version 4 form
      var r;
 
      // rfc4122 requires these characters
      uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
      uuid[14] = '4';
 
      // Fill in random data.  At i==19 set the high bits of clock sequence as
      // per rfc4122, sec. 4.1.5
      for (i = 0; i < 36; i++) {
        if (!uuid[i]) {
          r = 0 | Math.random()*16;
          uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
        }
      }
    }
 
    return uuid.join('');
}

function uuidNoSymbol(){
	return uuid().replace(/\-/g, '');
}

/**
 * 扩展Object对象方法,获取自身的属性名称

Object.prototype.getPropertys = function(){
	var props = [];
	for(var prop in this){
		if( this.hasOwnProperty(prop) ){
			props.push(prop);
		}
	}
	return props;
}
 */


/**
 * 扩展Array的对象方法,寻找元素的索引
 * @return {}
 */
Array.prototype.indexOf = function(){
	for(var i=0; i<this.length; i++){
		if( this[i] === arguments[0] ){
			return i;
		}
	}
	return -1;
}

/**
 * 扩展Array的对象方法,判断是否存在指定的元素
 */
Array.prototype.contains = function(){
	return this.indexOf(arguments[0]) !== -1;
}

/**
 * 扩展Array的对象方法,根据索引获取元素
 * @return {}
 */
Array.prototype.get = function(){
	return this[arguments[0]];
}

/**
 * 扩展Array的对象方法,删除元素
 * @return {}
 */
Array.prototype.remove = function(){
	var index = this.indexOf(arguments[0]);
	if( -1 !== index ){
		return this.splice(index, 1);
	}
}

/**
 * 扩展Array的对象方法,根据索引删除元素
 * @return {}
 */
Array.prototype.removeByIndex = function(){
	if( isNumber(arguments[0]) ){
		return this.remove( arguments[0] );
	}
}

/**
 * 扩展Array的对象方法,数字类型数组排序
 * @return {}
 */
Array.prototype.sortNumber = function(){
	return this.sort(function(a, b){
		return a - b;
	});
}

/**
 * 扩展Array的对象方法,获取数组中的最大值
 * @return {}
 */
Array.prototype.max = function(){
	if( this.length > 0 ){
		var temp = this[0];
		for(var i=1; i<this.length; i++){
			temp = Math.max(this[i], temp);
		}
		return temp;
	}
}

/**
 * 扩展Array的对象方法,获取数组中的最小值
 * @return {}
 */
Array.prototype.min = function(){
	if( this.length > 0 ){
		var temp = this[0];
		for(var i=1; i<this.length; i++){
			temp = Math.min(this[i], temp);
		}
		return temp;
	}
}

/**
 * 扩展Array的对象方法,统计数组中各项的和
 * @return {}
 */
Array.prototype.sum = function(){
	if( this.length > 0 ){
		var temp = this[0];
		for(var i=1; i<this.length; i++){
			temp += this[i];
		}
		return temp;
	}
}

/**
 * 扩展Array的对象方法,统计数组的平均值
 * @return {}
 */
Array.prototype.avg = function(){
	if( this.length > 0 ){
		return this.sum() / this.length;
	}
}

/**
 * 扩展Array的对象方法,随机数组中的元素
 * @return {}
 */
Array.prototype.random = function(){
	var len = arguments[0] || 1;
	var ret = [];
	for(var i=0; i<len; i++){
		ret.push(Math.rone(this))
	}
	return ret.length === 1 ? ret[0] : ret;
}

/**
 * 扩展Array的对象方法,比当前参数大的最小的数
 * @return {}
 */
Array.prototype.gtMin = function(){
	if( this.length > 0 ){
		var arg = arguments[0] || this[0];
		var newArr = [];
		for (var i = 0; i < this.length; i++) {
			if( this[i] > arg ){
				newArr.push( this[i] );			
			}
		}
		return newArr.min();
	}
}

/**
 * 扩展Array的对象方法,比当前参数小的最大的数
 * @return {}
 */
Array.prototype.ltMax = function(){
	if( this.length > 0 ){
		var arg = arguments[0] || this[0];
		var newArr = [];
		for (var i = 0; i < this.length; i++) {
			if( this[i] < arg ){
				newArr.push( this[i] );			
			}
		}
		return newArr.max();
	}
}


// Date

/**
 * 扩展Date添加静态方法,获取当前时间
 * @return {}
 */
Date.getInstance = function(){
	return new Date();
}

/**
 * 扩展Date静态方法,把日起对象转换为字符串格式
 * @return {}
 */
Date.format = function(){
	var date = arguments[0] || Date.getInstance();
	var pattern = arguments[1] || 'yyyy-MM-dd';
	var str = pattern;   
    var Week = ['日','一','二','三','四','五','六'];  
  	var year = date.getFullYear(); 
    var month = date.getMonth() + 1;
    str=str.replace(/yyyy|YYYY/,year);   
    str=str.replace(/yy|YY/,year.toString().subend(2));   
    str=str.replace(/MM/, month>9? month : '0' + month);   
    str=str.replace(/M/g, month);   
    str=str.replace(/w|W/g, Week[date.getDay()]);   
    str=str.replace(/dd|DD/,date.getDate()>9?date.getDate().toString():'0' + date.getDate());   
    str=str.replace(/d|D/g,date.getDate());   
    str=str.replace(/hh|HH/,date.getHours()>9?date.getHours().toString():'0' + date.getHours());   
    str=str.replace(/h|H/g,date.getHours());   
    str=str.replace(/mm/,date.getMinutes()>9?date.getMinutes().toString():'0' + date.getMinutes());   
    str=str.replace(/m/g,date.getMinutes());   
    str=str.replace(/ss|SS/,date.getSeconds()>9?date.getSeconds().toString():'0' + date.getSeconds());   
    str=str.replace(/s|S/g,date.getMilliseconds()); 
    return str;   
}

/**
 * 扩展Date的对象方法,把日期转换为字符串格式
 * @return {}
 */
Date.prototype.format = function(){
	return Date.format(this, arguments[0]);
}

//时间差
Date.diffYear = function(){
	var args = Array.prototype.slice.call(arguments, 0);
	args.push('Y');
	return Date.diff.apply(this, args);
}

Date.diffMonth = function(){
	var args = Array.prototype.slice.call(arguments, 0);
	args.push('M');
	return Date.diff.apply(this, args);
}

Date.diffWeek = function(){
	var args = Array.prototype.slice.call(arguments, 0);
	args.push('W');
	return Date.diff.apply(this, args);
}

Date.diffDay = function(){
	var args = Array.prototype.slice.call(arguments, 0);
	args.push('D');
	return Date.diff.apply(this, args);
}

Date.diffMinutes = function(){
	var args = Array.prototype.slice.call(arguments, 0);
	args.push('m');
	return Date.diff.apply(this, args);
}

Date.diffSeconds = function(){
	var args = Array.prototype.slice.call(arguments, 0);
	args.push('s');
	return Date.diff.apply(this, args);
}

//'Y-M-D-W-h-m-s-S' 年-月-日-星期-时-分-秒-毫秒
Date.diff = function(){
	var tempDate = new Date( ( arguments[0] || Date.getInstance() ).getTime() );
	var diff = arguments[1] || 1;
	var unit = arguments[2] || 'S';
	switch(unit){
		case 'Y':
			tempDate.setFullYear( tempDate.getFullYear() + diff );
			break;
		case 'M':
			tempDate.setMonth( tempDate.getMonth() + diff );
			break;
		case 'D':
			tempDate.setDate( tempDate.getDate() + diff );
			break;
		case 'W':
			tempDate.setDate( tempDate.getDate() + diff*7 );
			break;
		case 'h':
			tempDate.setHours( tempDate.getHours() + diff );
			break;
		case 'm':
			tempDate.setMinutes( tempDate.getMinutes() + diff );
			break;
		case 's':
			tempDate.setSeconds( tempDate.getSeconds() + diff );
			break;
		case 'S':
			tempDate.getMilliseconds( tempDate.getMilliseconds() + diff );
			break;
		default:
			throw '不支持的日期格式：' + unit + ' 正确的日期格式：[Y-M-D-W-h-m-s-S] -> [年-月-日-星期-时-分-秒-毫秒]'
			break;
	}
	return tempDate;
	
}



// Math
/**
 * 扩展Math的静态方法,随机一个范围的数据
 * @return {}
 */
Math.rlimit = function(){
	var l = arguments[0] || Number.MIN_VALUE;	//左边界
	var r = arguments[1] || Number.MAX_VALUE;	//右边界
	l = Math.min(l, r);
	r = Math.max(l, r);
	return this.random() * (r - 1) + l;
}

/**
 * 扩展Math的静态方法,随机数组里面的一个元素
 * @return {}
 */
Math.rone = function(){
	var arr = arguments[0];
	if( isArray(arr) ){
		return arr.get( Math.rlimit(0, arr.length).toFixed(0) );
	}
}

// Number
/**
 * 扩展Number的对象方法,把数字转换为整型
 * @return {}
 */
Number.prototype.toInt = function(){
	return this.toFixed(0);
}

// String
/**
 * 扩展String的对象方法,去除String两端的空格
 * @return {}
 */
String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, '');
}

/**
 * 扩展String的对象方法,去除String左端的空格
 * @return {}
 */
String.prototype.ltrim = function(){
	return this.replace(/(^\s*)/g,'');
}

/**
 * 扩展String的对象方法,去除String有段空格
 * @return {}
 */
String.prototype.rtrim = function(){
	return this.replace(/(\s*$)/g,'');
}

/**
 * 扩展String的对象方法,从末尾截取
 * @return {}
 */
String.prototype.subend = function(){
	return this.substr(-arguments[0], this.length);
}

/**
 * 扩展String的对象方法,从起始位置截取
 * @return {}
 */
String.prototype.substart = function(){
	return this.substr(arguments[0], this.length);
}

/**
 * 扩展String的对象方法,把字符串解析为一个Date类型的对象
 * @return {}
 */
String.prototype.parseDate = function(){
	return new Date( Date.parse(this.replace(/-/, '/')) );
}
