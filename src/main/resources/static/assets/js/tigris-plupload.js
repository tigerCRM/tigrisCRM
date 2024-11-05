/*
 * Copyright (c) 2011-2015, TigerCompany. All rights reserved.
 * http://www.tigersw.com
 * http://www.tigercompany.kr
 * http://www.tigercompany.co.kr
 *
 * This software is the confidential and proprietary information of
 * TigerCompany. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with TigerCompany.
 */

/**
 * Default options
 * var options = {
				'uploaderId': 'uploader',
				'dropElement': 'dropElement',
				'filePrefix': 'FEED',
				'fileId': null,
				'callback': null,
				'autoStart': false,
				'debug': true
		}
 */

var console = window.console;
if (!console || !console.log || !console.error) {
	console = {log: function(){}, error: function(){}};
}

function loadPlupload(options) {

	if(!options) {
		alert('Option parameter is not defined!');
		return;
	}

	var url = GLB_FILE_URL + '/upload';
	var fileSizeLimit = (GLB_SITE_FILE_SIZE * 1024 * 1024);
	var fileId = '';
	var firstUploaded = options.firstUploaded;
	// Options initialization
	var uploaderId 		= options.uploaderId || 'uploader';
	var dropElement		= options.dropElement || 'dropElement';
	var filePrefix 		= options.filePrefix || 'FEED';

	var uploader = new plupload.Uploader({

		runtimes : 'html5,flash,silverlight,html4',
        url : url,

        browse_button : uploaderId,
        drop_element : dropElement,

        unique_names : true,

        filters : {
            max_file_size : fileSizeLimit
        },

        flash_swf_url : '/static/lib/plupload/Moxie.swf',
        silverlight_xap_url : '/static/lib/plupload/Moxie.xap',


		// PreInit events, bound before any internal events
		preinit : {
            Init: function(up, info) {
            	if(options.debug)
            		log('[Init]', 'Info:', info, 'Features:', up.features);
            },

            UploadFile: function(up, file) {
            	if(options.debug)
            		log('[UploadFile]', file);

                // You can override settings before the file is uploaded
                // up.setOption('url', 'upload.php?id=' + file.id);
                // up.setOption('multipart_params', {param1 : 'value1', param2 : 'value2'});
            }
        },

        // Post init events, bound after the internal events
        init : {
            PostInit: function(up) {
                // Called after initialization is finished and internal event handlers bound

            	if(options.fileId == null || options.fileId == '') {
            		$.getJSON(GLB_FILE_URL + '/createId', function(data) {
                		fileId = data.fileId;
                		up.setOption('url', GLB_FILE_URL+'/upload?filePrefix=' + filePrefix + '&fileId=' + fileId + '&firstUploaded=' + firstUploaded);
                	});
            	} else {
            		fileId = options.fileId;
            		up.setOption('url', GLB_FILE_URL+'/upload?filePrefix=' + filePrefix + '&fileId=' + fileId + '&firstUploaded=' + firstUploaded);
            	}

            	if(options.debug)
            		log('[PostInit]');
            },

            Browse: function(up) {
                // Called when file picker is clicked
            	if(options.debug)
            		log('[Browse]');
            },

            Refresh: function(up) {
                // Called when the position or dimensions of the picker change
            	if(options.debug)
            		log('[Refresh]');
            },

            StateChanged: function(up) {
                // Called when the state of the queue is changed
            	if(options.debug)
            		log('[StateChanged]', up.state == plupload.STARTED ? "STARTED" : "STOPPED");
            },

            QueueChanged: function(up) {
                // Called when queue is changed by adding or removing files
            	if(options.debug)
            		log('[QueueChanged]');
            },

            OptionChanged: function(up, name, value, oldValue) {
                // Called when one of the configuration options is changed
            	if(options.debug)
            		log('[OptionChanged]', 'Option Name: ', name, 'Value: ', value, 'Old Value: ', oldValue);
            },

            BeforeUpload: function(up, file) {
                // Called right before the upload for a given file starts, can be used to cancel it if required
            	if(options.debug)
            		log('[BeforeUpload]', 'File: ', file);
            },

            UploadProgress: function(up, file) {
                // Called while file is being uploaded
            	if(options.debug)
            		log('[UploadProgress]', 'File:', file, "Total:", up.total);
            },

            FileFiltered: function(up, file) {
                // Called when file successfully files all the filters
            	if(options.debug)
            		log('[FileFiltered]', 'File:', file);
            },

            FilesAdded: function(up, files) {
                // Called when files are added to queue

            	if(options.autoStart)
            		up.start();

            	if(options.debug) {
            		log('[FilesAdded]');

            		plupload.each(files, function(file) {
                        log('  File:', file);
                    });
            	}
            },

            FilesRemoved: function(up, files) {
                // Called when files are removed from queue

            	if(options.debug) {
            		log('[FilesRemoved]');

                    plupload.each(files, function(file) {
                        log('  File:', file);
                    });
            	}
            },

            FileUploaded: function(up, file, info) {
                // Called when file has finished uploading
            	if(options.debug)
            		log('[FileUploaded] File:', file, "Info:", info);
            },

            UploadComplete: function(up, files) {
                // Called when all files are either uploaded or failed
            	if(options.callback && typeof options.callback == 'function')
            		options.callback(fileId, up, files);
            	else
            		alert('Callback method is not defined!');

            	if(options.debug) {
            		log('[UploadComplete]');
            		log('[Callback method] : ', options.callback);
            	}
            },

            Destroy: function(up) {
                // Called when uploader is destroyed
            	if(options.debug)
            		log('[Destroy] ');
            },

            Error: function(up, args) {
                // Called when error occurs
                log('[Error] ', args);
            }
        }
    });
	uploader.init();

	return uploader;
}

/**
 * Default options
 * var options = {
				'uploaderId': 'uploader',
				'filePrefix': 'FEED',
				'fileId': null,
				'callback': null,
				'fileCounLlimit': 10,
				'debug': true
		}
 */
function loadPluploadQueue(options) {

	if(!options) {
		alert('Option parameter is not defined!');
		return;
	}

	var fileSizeLimit = (GLB_SITE_FILE_SIZE * 1024 * 1024);
	var fileId = '';

	// Options initialization
	var uploaderId 		= options.uploaderId || 'uploader';
	var filePrefix 		= options.filePrefix || 'FEED';
	var fileCounLlimit 	= options.fileCounLlimit || 10;
	var storeType 		= options.storeInfo ? options.storeInfo.storetype : 'S3';

	var url = GLB_FILE_URL+'/upload?filePrefix=' + filePrefix + '&storeType=' + storeType;


	$("#" + uploaderId).pluploadQueue({
		// General settings
        runtimes : 'html5,flash,silverlight,html4',
        url : url,
        max_file_size : fileSizeLimit,

        // Add max_file_count properties by julee 2015-06-23
        max_file_count : fileCounLlimit,
        unique_names : true,
        storeInfo: options.storeInfo,

        // Resize images on client-side if we can
        //resize : {width : 320, height : 240, quality : 90},

        /*
        filters : [
				{title : "Image files", extensions : "jpg,gif,png"}
			],
        */

        flash_swf_url : '/static/lib/plupload/Moxie.swf',
        silverlight_xap_url : '/static/lib/plupload/Moxie.xap',

		// PreInit events, bound before any internal events
		preinit : {
            Init: function(up, info) {
            	if(options.debug)
            		log('[Init]', 'Info:', info, 'Features:', up.features);
            },

            UploadFile: function(up, file) {
            	if(options.debug)
            		log('[UploadFile]', file);

                // You can override settings before the file is uploaded
                // up.setOption('url', 'upload.php?id=' + file.id);
                // up.setOption('multipart_params', {param1 : 'value1', param2 : 'value2'});
            }
        },

        // Post init events, bound after the internal events
        init : {
            PostInit: function(up) {
                // Called after initialization is finished and internal event handlers bound

            	if(options.fileId == null || options.fileId == '') {
            	} else {
            		fileId = options.fileId;

            		if(storeType != "RIM_DRIVE"){
            			$.ajax({
                			'url': GLB_FILE_URL+'/list/' + fileId,
                			'data': {},
                			'dataType': 'json',
                			'method': 'get',
                			'async': false,
                			'success': function(data, status, xhr) {
                				var files = new Array();

                				plupload.each(data.files, function(file) {
                        			var f = new plupload.File({'name': file.originFileName, 'size': file.fileSize});
                        			f.id = file.fileId + '_' + file.seq;
                        			f.fileId = fileId;
                        			f.seq = file.seq;
                        			f.status = plupload.DONE;
                        			files.push(f);

                        			if(options.debug)
                        				log('  File:', file);
                                });
                        		up.addFile(files);

                        		if(files.length > 0) {
                        			$('#'+uploaderId).show().parent(".input_file").addClass('active');
                        		}
                			}
                		});
            		}

            	}

            	if(options.debug)
            		log('[PostInit]');
            },

            Browse: function(up) {
                // Called when file picker is clicked
            	if(options.debug)
            		log('[Browse]');
            },

            Refresh: function(up) {
                // Called when the position or dimensions of the picker change
            	if(options.debug)
            		log('[Refresh]');
            },

            StateChanged: function(up) {
                // Called when the state of the queue is changed
            	if(options.debug)
            		log('[StateChanged]', up.state == plupload.STARTED ? "STARTED" : "STOPPED");
            },

            QueueChanged: function(up) {
                // Called when queue is changed by adding or removing files
            	if(options.debug)
            		log('[QueueChanged]');
            },

            OptionChanged: function(up, name, value, oldValue) {
                // Called when one of the configuration options is changed
            	if(options.debug)
            		log('[OptionChanged]', 'Option Name: ', name, 'Value: ', value, 'Old Value: ', oldValue);
            },

            BeforeUpload: function(up, file) {
                // Called right before the upload for a given file starts, can be used to cancel it if required
            	var preview = $("#" + file.id + ' input[name=preview]').val();
            	var storeType = $("#" + file.id + ' input[name=storeType]').val();
            	var storeObjectId = $("#" + file.id + ' input[name=storeObjectId]').val();
            	var storeTreePath = $("input[name=storeTreePath]").val();

            	var uploadUrl = url;
            	var multipart_params = {'preview': preview, 'storeType': storeType, 'storeObjectId': storeObjectId, 'storeTreePath': storeTreePath};

            	if(options.appendParams && typeof(options.appendParams) == 'function') {
            		params = options.appendParams();
            		if (params) {
            			if(params['shareScope']) {
            				multipart_params['shareScope'] = JSON.stringify(params['shareScope']);
            			}
            			if(params['participantIds']) {
            				multipart_params['participantIds'] = params['participantIds'];
            			}
            		} else {
            			up.stop();
            			return;
            		}
            	} else {
            		if(!fileId) {
            			$.ajax({
                			'url': GLB_FILE_URL + '/createId',
                			'method': 'get',
                			'async': false,
                			'success': function(data, status, xhr) {
                				fileId = data.fileId;
                			}
                		});
            		}

            		uploadUrl = uploadUrl + '&fileId=' + fileId + '&firstUploaded=' + options.firstUploaded;
            	}
            	up.setOption('multipart_params', multipart_params);
            	up.setOption('url', uploadUrl);

            	if(options.debug)
            		log('[BeforeUpload]', 'File: ', file);
            },

            UploadProgress: function(up, file) {
                // Called while file is being uploaded
            	if(options.debug)
            		log('[UploadProgress]', 'File:', file, "Total:", up.total);
            },

            FileFiltered: function(up, file) {
                // Called when file successfully files all the filters
            	if(options.debug)
            		log('[FileFiltered]', 'File:', file);
            },

            FilesAdded: function(up, files) {
                // Called when files are added to queue

            	if(options.debug) {
            		log('[FilesAdded]');

            		plupload.each(files, function(file) {
                        log('  File:', file);
                    });
            	}
            },

            FilesRemoved: function(up, files) {
                // Called when files are removed from queue

            	if(options.debug) {
            		log('[FilesRemoved]');

                    plupload.each(files, function(file) {
                        log('  File:', file);
                    });
            	}
            },

            FileUploaded: function(up, file, info) {
                // Called when file has finished uploading
            	if(options.debug)
            		log('[FileUploaded] File:', file, "Info:", info);

            	var result = JSON.parse(info.response);
            	if(result.code != 0) {
            		up.state = plupload.STARTED;

            		file.loaded = 0; // reset all progress

    				up.trigger('Error', {
    					code : plupload.HTTP_ERROR,
    					message : result.message,
    					file : file
    				});
    				up.stop();
//            		alert(result.message);
            		return;
            	}

            	if(options.uploadedCallback && typeof options.uploadedCallback == 'function') {
            		options.uploadedCallback(result);
            	}
            },

            UploadComplete: function(up, files) {
                // Called when all files are either uploaded or failed
            	if(options.callback && typeof options.callback == 'function')
            		options.callback(false);
            	else
            		alert('Callback method is not defined!');

            	if(options.debug) {
            		log('[UploadComplete]');
            		log('[Callback method] : ', options.callback);
            	}
            },

            Destroy: function(up) {
                // Called when uploader is destroyed
            	if(options.debug)
            		log('[Destroy] ');
            },

            Error: function(up, args) {
                // Called when error occurs
                log('[Error] ', args);
                alert(args.message);
                args.file.status = plupload.QUEUED;
                up.refresh();
            	if(options.uploadError && typeof options.uploadError == 'function')
            		options.uploadError(args);
            }
        }
    });

	return $("#" + uploaderId).pluploadQueue();
}
function renderPreview(id, preview) {

	var isChecked, checked, isDisabled, disabled, value, html = '';

	if(preview) {
		isChecked = (preview == 'Y');
		isDisabled = true;
	} else {
		isChecked = $('#' + id + ' input:checkbox[name=preview]').is(':checked');
		isDisabled = false;
	}

	disabled = isDisabled ? 'disabled' : '';
	checked = isChecked ? 'checked' : '';
	value = isChecked ? 'Y' : 'N';
	html = '<input type="checkbox" name="preview" value="' + value + '" onclick="setPreview(\'' + id + '\')" ' + checked + ' ' + disabled + '>';

	return html;
}
function setPreview(id) {
	var obj = $('#' + id + ' input:checkbox[name=preview]');
	var checked = obj.is(':checked');
	obj.attr("checked", checked).val(checked ? 'Y' : 'N');
}
function setPreviewToggle(id) {
	var checked = $('#' + id + ' input:checkbox[name=previewToggle]').is(':checked');
	$('#' + id + '_filelist input:checkbox[name=preview]').each(function() {
		$(this).attr("checked", checked).val(checked ? 'Y' : 'N');
	});
}
function log() {
    var str = "";

    plupload.each(arguments, function(arg) {
        var row = "";

            if (typeof(arg) != "string") {
            	plupload.each(arg, function(value, key) {
            		// Convert items in File objects to human readable form
            		if (arg instanceof plupload.File) {
            			// Convert status to human readable
            			switch (value) {
            				case plupload.QUEUED:
            					value = 'QUEUED';
                                break;

                            case plupload.UPLOADING:
                                value = 'UPLOADING';
                                break;

                            case plupload.FAILED:
                                value = 'FAILED';
                                break;

                            case plupload.DONE:
                                value = 'DONE';
                                break;
                        }
                    }

                    if (typeof(value) != "function") {
                    	row += (row ? ', ' : '') + key + '=' + value;
                    }
                });
                str += row + " ";
            } else {
            	str += arg + " ";
            }
    });

    console.log(str);
}