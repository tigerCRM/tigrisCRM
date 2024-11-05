var widN;
var widH;
var menuH;
var widgetH;
var slideH;
(function($){
	//$(window).load(function(){

		//가로 해상도가 1280 이상인 경우에만 각 컨텐츠 스크롤 작동
		//스크롤 붙이기
		scrAdd = function(){
			scrRemove();
			$('.menu_scr nav').css('height', menuH+'px' );
			$('.wrap_widget .scr').css('height', widgetH+'px' );
			$('.min_hei').css('height', widgetH+'px' );
			//lnb scroll
			$('.menu_scr nav').slimscroll({
				height: 'auto',
				size: '6px',
				color: '#b7b7b7',
				alwaysVisible: false,
				distance: '0px'
			});

			//widget scroll
			$('.wrap_widget .scr').slimscroll({
				height: 'auto',
				size: '7px',
				color: '#b7b7b7',
				alwaysVisible: false
			});
			$('.wrap_widget .slimScrollDiv').css('z-index', 1 ); //위젝영역이 자꾸 곂쳐서 추가
			//background
			if($('.contents_area').hasClass('no_widget')){
				$('body').addClass("min_hei");
			}

		}
		//스크롤 지우기
		scrRemove = function(){
			$('.menu_scr nav').slimScroll({destroy: true});
			$('.menu_scr nav').css('height', '100%' );
			$('.wrap_widget .scr').slimScroll({destroy: true});
			$('.wrap_widget .scr').css('height', '100%' );
			$('.menu_scr .slimScrollBar').remove();
			$('.menu_scr .slimScrollRail').remove();
			$('.wrap_widget .slimScrollBar').remove();
			$('.wrap_widget .slimScrollRail').remove();
		}

		widthCheck = function(){
			widN = $( window ).width();
			widH = $( window ).height();
			menuH = $(window).height() - 241;
			widgetH = $(window).height() - 84;
			slideH = $(window).height() - 62;
			if(widN > 1278){
				$(window).unbind("scroll.header");
				$(".min_wid .wrap_header").css("left", 0);
				$('body').removeClass('min_wid');
				scrAdd();
			} else {
				$('body').addClass('min_wid');
				$(window).unbind("scroll.header").bind("scroll.header", function(e) {
					var headerWidth = $(".min_wid .wrap_header").width();
					var htmlWidth = $("html").width();
					var differenceWidth = headerWidth - htmlWidth;

					if(differenceWidth > 0) {
						var scrollLeft = $("html").scrollLeft();

						$(".min_wid .wrap_header").css("left", (scrollLeft > differenceWidth ? -differenceWidth : -scrollLeft));
					}
				});

				scrRemove();
			}


			//높이 조정
			$('.slide_area').css('height', slideH+'px' );
			$('.slide_area').find('div.slimScrollDiv').css('height', slideH+'px' );
			$('.slide_area').find('section').css('height', slideH+'px' );

			$('.edit_slide_area').css('height', slideH+'px' );
			$('.edit_slide_area').find('div.slimScrollDiv').css('height', slideH+'px' );
			$('.edit_slide_area').find('section').css('height', slideH+'px' );

		}
		$(window).resize(function(){
			widthCheck();
		});

		widthCheck();

		//글작성&상세 스크롤바
		add_slidescroll = function(target){
			$(target).css('height', slideH+'px' );
			$(target).slimscroll({
				height: 'auto',
				size: '7px',
				color: '#b7b7b7',
				alwaysVisible: true
			});
		}
		remove_slidescroll = function(target, num){
			$(target).slimScroll({ scrollTo: '0px' });
			$(target).slimScroll({destroy: true});
			$(target).css('height', num+'px' );
			$('.slide_area .slimScrollBar').remove();
			$('.slide_area .slimScrollRail').remove();
		}

		//검은투명박 컨트롤.(플라워용)
		flower_dimm_control = function(type){
			if(type=="in"){
				$('body').append('<div class="flower_dimm"></div>');
			} else if (type=="out") {
				$(".flower_dimm").remove();
			}
		}

		//검은투명박 컨트롤.
		dimm_control = function(type){
			if(type=="in"){
				$('body').append('<div class="dimm"></div>');
				$('body').addClass("over_hidden");
			} else if (type=="out")
			{
				$(".dimm").remove();
				$('body').removeClass("over_hidden");
			}
		}

		//검은투명박 컨트롤.(수정용)
		edit_dimm_control = function(type){
			if(type=="in"){
				$('body').append('<div class="edit_dimm"></div>');
			} else if (type=="out") {
				$(".edit_dimm").remove();
			}
		}
		edit_add_slidescroll = function(target){
			$(target).css('height', slideH+'px' );
			$(target).slimscroll({
				height: 'auto',
				size: '7px',
				color: '#b7b7b7',
				alwaysVisible: true
			});
		}
		edit_remove_slidescroll = function(target, num){
			$(target).slimScroll({ scrollTo: '0px' });
			$(target).slimScroll({destroy: true});
			$(target).css('height', num+'px' );
			$('.edit_slide_area .slimScrollBar').remove();
			$('.edit_slide_area .slimScrollRail').remove();
		}

		video_dimm_control = function(type){
			if(type=="in"){
				$('body').append('<div class="video_dimm"></div>');
			} else if (type=="out") {
				$(".video_dimm").remove();
			}
		}

		password_dimm_control = function(type){
			if(type=="in"){
				$('body').append('<div class="password_dimm"></div>');
			} else if (type=="out") {
				$(".password_dimm").remove();
			}
		}

		//onClick
		show_slidedown = function(target){$(target).slideDown();};
		show_slideup = function(target){$(target).slideUp();};
		show_target = function(target){$(target).show(); }
		hide_target = function(target){$(target).hide();}

		jQuery.fn.clickToggle = function(a,b) {
			var ab=[b,a];
			function cb(){ ab[this._tog^=1].call(this); }
			return this.on("click", cb);
		};


		//********************************* 각 버튼별 *********************************//
		//달력 닫기
		$('.calendar .close,.calendar td').click(function(){
			$('.lay_pop.calendar').hide();
		});




		//header 영역 검색활성화버튼
		$('.wrap_inform .search').click(function(){
			$('.wrap_inform').addClass('active');
			$(this).next('input').focus();
		});

		//********************************* 각 페이지별(캘린더) *********************************//
		calendar_size = function(){
			$('.calendar_area').parent('.contents_area').css('height', slideH+'px' );
			$('.calendar_area').css('height', slideH+'px' );
			$('.calendar_area .wrap_dateinfo').css('height', slideH-101+'px' );
			$('.calendar_area .wrap_dateinfo .month_table').css('height', slideH-130+'px' );
			$('.calendar_area .wrap_dateinfo .wrap_monthly_list').css('height', slideH-111+'px' );
		}
		$(window).resize(function(){
			calendar_size();
		});


	//});
})(jQuery);