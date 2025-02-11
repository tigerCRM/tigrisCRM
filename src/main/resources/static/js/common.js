/*common.js
최초 작성자 : 제예솔
설명 : 각종 script 코드 여기에. 꼭 주석 달아주세요!
*/

//1. 화면 로드시 실행 //////////////////////////////////////////////////////////////////////////////////////////////////////////
document.addEventListener("DOMContentLoaded", function () {
    //날짜 값 포맷 변환
    common.convertDateFormat();

    /*
    event on : 첨부파일 영역의 "파일추가" 버튼 눌렀을 때
    result : #attachFilesTemp 열림
    */
    if(document.querySelector('#uploadButton')){
        document.querySelector('#uploadButton').addEventListener('click', () => {
            document.getElementById('attachFilesTemp').click();
        });
    }

    /*
    event on : "파일추가" 가 되었을 때
    result : selectedFiles(전송할 파일 배열) 에 파일을 추가, 목록업데이트
    */
    if(document.getElementById('attachFilesTemp')){
        document.getElementById('attachFilesTemp').addEventListener('change', () => {
            const fileInput = document.getElementById('attachFilesTemp');
            console.log("파일 선택 완료");
            console.log(fileInput.files);
            const files = Array.from(fileInput.files); // 새로운 파일 배열
            files.forEach(file => {
              // 동일한 파일명이 있으면 추가하지 않음
              if (!selectedFiles.some(f => f.name === file.name)) {
                  selectedFiles.push(file);
              }
            });
            fileInput.value = ''; // 파일 선택창 초기화
            common.renderFileList(); // 목록 업데이트
        });
    }

    /*
    event on : "전체삭제" 눌렀을 때
    result : 파일 입력값 및 html 요소 삭제
    */
    $("#fileDelete").on('click', function () {
        // 파일 입력 값 초기화
        $("#attachFiles").val('');

        // deleteSavedAttachFiles에 모든 파일 이름 추가
        $(".list01 li").each(function() {
            var fileName = $(this).find('.deleteFileButton').data('filename'); // data-filename 속성 읽기
            if (fileName) {
                deleteSavedAttachFiles.push(fileName);
            }
        });

        // 동적 리스트 초기화
        $(".list01").empty();
        selectedFiles = [];
    });
    /*
    event on : 첨부파일 개별 "X" 자 버튼 눌렀을때
    result : 전송할 파일에서 지운 파일 삭제하고 html 요소 삭제
    */
    if(document.querySelectorAll('.deleteFileButton')){
        document.querySelectorAll('.deleteFileButton').forEach(button => {
            button.addEventListener('click', function() {
                var fileName = this.dataset.filename; // data-filename 속성 읽기
                deleteSavedAttachFiles.push(fileName);
                if($("#deleteSavedAttachFiles")!=undefined)
                {
                    $("#deleteSavedAttachFiles").val(deleteSavedAttachFiles)
                }
                this.closest('li').remove();

            });
        });
    }

});


$(document).ready(function(){
    $('#adminmenu').click(function(e){
        e.preventDefault(); // 링크 기본 동작 방지

        // 클릭한 메뉴의 하위 메뉴를 찾고 토글 (보이기/숨기기)
        var subMenu = $(this).next('.tree-list--sub-2');
        subMenu.slideToggle(); // 슬라이딩 효과로 토글

        // 아이콘 방향 변경 (선택 사항)
        var arrowBtn = $(this).find('.tree-arrow-btn');
        arrowBtn.toggleClass('tree--open'); // 아이콘 상태 변경
    });
    $('#analytics').click(function(e){
            e.preventDefault(); // 링크 기본 동작 방지

            // 클릭한 메뉴의 하위 메뉴를 찾고 토글 (보이기/숨기기)
            var subMenu = $(this).next('.tree-list--sub-2');
            subMenu.slideToggle(); // 슬라이딩 효과로 토글

            // 아이콘 방향 변경 (선택 사항)
            var arrowBtn = $(this).find('.tree-arrow-btn');
            arrowBtn.toggleClass('tree--open'); // 아이콘 상태 변경
        });
    /*// 메뉴 클릭 시 오른쪽 컨텐츠만 업데이트
    $('.menu-item').click(function(e) {
        e.preventDefault(); // 기본 링크 동작 방지

        var url = $(this).data('url'); // 클릭한 메뉴의 URL 가져오기

        // AJAX로 오른쪽 컨텐츠 로드
        $.ajax({
            url: url,
            method: 'GET',
            success: function(response) {
                // 오른쪽 영역에 새로운 콘텐츠를 로드
                $('.main-area').html(response);
            },
            error: function() {
                alert('콘텐츠를 불러오는 데 오류가 발생했습니다.');
            }
        });
    });*/
});


//2. 함수 저장소  //////////////////////////////////////////////////////////////////////////////////////////////////////////////
var common = {

    /*
    convertDateFormat(날짜 값 포맷 변환)
    작성자 : 제예솔
    설명 : html 단에 입력한 값 중 Date타입인 값이 화면에 Mon Nov 25 14:37:35 KST 2024 이런 식으로 뜨는 경우
    class 에 convertDateFormat 값 추가하면 로드될때 변환해줍니다.
    */
    convertDateFormat: function(){
        const dateElements = document.querySelectorAll(".convertDateFormat");

        dateElements.forEach((element) => {
            const originalDate = element.textContent.trim(); // 태그의 원래 날짜 값
            console.log(originalDate);

            if (originalDate) {
                // 문자열 파싱
                const parts = originalDate.split(' '); // 공백 기준 분리
                const monthMap = {
                    Jan: '01', Feb: '02', Mar: '03', Apr: '04',
                    May: '05', Jun: '06', Jul: '07', Aug: '08',
                    Sep: '09', Oct: '10', Nov: '11', Dec: '12'
                };

                const year = parts[5]; // 연도
                const month = monthMap[parts[1]]; // 월
                const day = parts[2]; // 일

                if (year && month && day) {
                    const formattedDate = `${year}-${month}-${day}`;
                    console.log(formattedDate);

                    // 태그에 변환된 날짜 설정
                    element.textContent = formattedDate;
                }
            }
        });
    },
    /*
    loadOrInitializeEditor(에디터 또는 뷰어 로더)
    작성자 : 제예솔
    설명 : 매개변수로 선언만 한 editor, boolean isViewerMode 를 받습니다.
    isViewerMode 가 true = 글 상세보기 = 토스트 뷰어모드
    isViewerMode 가 false = 글 신규작성 = 토스트 에디터모드
    초기화 해준 후 editor 를 리턴 합니다.
    */
    loadOrInitializeEditor : function(editor , mode, contentValue){

        editor = new toastui.Editor.factory({
            el: document.querySelector('#editorContent'),      // viewer와 editor 컨테이너 구분
            viewer: mode == 'read',                              // boardId 값에 따라 viewer 모드 설정
            height: 'auto',                                    // 높이 설정
            initialValue: mode == 'write'? contentValue : document.querySelector('#content').value ,
            initialEditType: mode == 'read' ? undefined : 'wysiwyg', // viewer 모드일 때 편집 타입 생략
            previewStyle: 'vertical',
            hideModeSwitch: mode != 'read',                      // editor 모드일 때만 모드 전환 숨김
            toolbarItems: [
                      ['heading', 'bold', 'italic', 'strike'],
                      ['hr', 'quote'],
                      ['ul', 'ol', 'task', 'indent', 'outdent'],
                      ['table', 'image', 'link']
                ]
        });

        return editor;
    },
    /*
    attachFileWorks(글제출전 첨부파일 묶음처리)
    작성자 : 제예솔
    설명 : 문서에 하기 태그를 필요로 합니다. 글 제출 전 첨부파일 리스트 변동사항을 반영합니다
    <input type="file" multiple="multiple" id="attachFilesTemp" name="attachFilesTemp" class="file_attach_input">
    <input type="file" multiple="multiple" id="attachFiles" name="attachFiles" class="file_attach_input">
    */
    attachFileWorks : function(){
        const realFileInput = document.getElementById('attachFiles');
        if (!realFileInput) {
            console.error('요소를 찾을 수 없습니다.');
            return;
        }
        // DataTransfer 객체를 활용해 selectedFiles를 설정
        const dataTransfer = new DataTransfer();
        selectedFiles.forEach(file => {
            dataTransfer.items.add(file);
        });
        // 기존 attachFilesTemp에 파일 설정
        realFileInput.files = dataTransfer.files;
        console.log('첨부 파일이 업데이트되었습니다:', selectedFiles);
    },
    /*
    renderFileList
    작성자 : 제예솔
    설명 : 사용자에게 보이는 첨부파일 리스트 부분을 동적으로 그려줍니다.
    */
    renderFileList : function(){
        const list = document.querySelector('#addFileAttach');
        list.innerHTML = ''; // 기존 목록 초기화

        selectedFiles.forEach((file, index) => {
        // 파일 이름
            const fileName = file.name;

            // 동적으로 생성할 HTML 문자열
            const listItem = `
                <li class="list__item">
                    <div class="item__title">
                        <svg class="icon icon--20 color-gray700">
                            <use href="/assets/images/icon/sprite-sheet.svg#attachment"></use>
                        </svg>
                        <span>${fileName}</span>
                        <button type="button" class="icon icon--20 just-icon" data-index="${index}">
                            <svg class="icon">
                                <use href="/assets/images/icon/sprite-sheet.svg#close"></use>
                            </svg>
                        </button>
                    </div>
                </li>
            `;

            // 문자열을 DOM 요소로 변환
            const tempDiv = document.createElement('div');
            tempDiv.innerHTML = listItem;
            const listItemElement = tempDiv.firstElementChild;

            // 삭제 버튼에 이벤트 리스너 추가
            const deleteButton = listItemElement.querySelector('button');
            deleteButton.addEventListener('click', () => {
                selectedFiles.splice(index, 1); // 파일 삭제
                common.renderFileList(); // 목록 다시 렌더링
            });

            // 리스트에 추가
            list.appendChild(listItemElement);
        });
    },
    /*
    toCamelCase
    작성자 : 제예솔
    */
    toCamelCase : function(snakeCaseObj){
    const camelCaseObj = {};
        for (let key in snakeCaseObj) {
            if (snakeCaseObj.hasOwnProperty(key)) {
                // Snake Case를 Camel Case로 변환
                const camelCaseKey = key
                    .toLowerCase()
                    .replace(/_([a-z])/g, (_, letter) => letter.toUpperCase());
                camelCaseObj[camelCaseKey] = snakeCaseObj[key];
            }
        }
        return camelCaseObj;
    },

    /*
    loadPopupPosts
    작성자 : 제예솔
    */
    loadPopupPosts : function(){
        // Fetch API로 서버에 요청 전송
        var url = `/loadPopup`;
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ test: 'test' }),
        })
        .then(response => {
            if (response.ok) {
                return response.text().then(text => {
                    // 응답이 비어 있지 않으면 JSON으로 파싱
                    return text ? JSON.parse(text) : {}; // 빈 응답이면 빈 객체 반환
                });
            } else {
                return response.json().then(data => {
                    if (data.errors) {
                        alert(data.errors.join('\n')); // 오류 메시지 출력
                    } else {
                        alert('알 수 없는 오류가 발생했습니다.');
                    }
                });
            }
        })
        .then(data => {
                console.log('Popup Data:', data); // 서버에서 받은 데이터를 콘솔에 출력
            // 데이터가 배열인지 확인
            if (Array.isArray(data)) {
                // 배열이면 forEach 사용
                data.forEach(item => {
                    if (common.compareToLocalStorage(item.BOARD_ID)) {
                        this.openPopup(item); // 각 데이터 항목에 대해 팝업 열기
                    }
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('서버 요청 중 문제가 발생했습니다.');
        });
    },
    /*
    openPopup
    작성자 : 제예솔
    */
    openPopup : function(data){
        const popup = window.open('/html/popup.html', '_blank', 'width=568,height=750');
        if (!popup || popup.closed || typeof popup.closed === "undefined") {
            // 팝업 차단
            showMessage('info', '팝업차단', "팝업 차단이 활성화되어 있습니다. 팝업 차단을 해제해주세요.");
            return false;
        }
        popup.onload = function () {
            popup.postMessage(data, '*'); // 데이터를 팝업 창으로 전송
        };
    },
    /*
    compareToLocalStorage
    작성자 : 제예솔
    */
    compareToLocalStorage : function(boardId){
        var popupHideData = localStorage.getItem("popupHideData");
        popupHideData = JSON.parse(popupHideData);
        if (!popupHideData || popupHideData === "") {
            return true;
        } else {
            for (var i = 0; i < popupHideData.length; i++) {
                if (popupHideData[i].hideBoardId === boardId.toString()) {
                    if(popupHideData[i].hideDate === new Date(Date.now() -(new Date().getTimezoneOffset()*60000)).toISOString().split("T")[0] ){
                        return false;
                    }
                }
            }
            return true;
        }

    },



//end
}
    /*
    * 메세지창 공통 구현
    */
    function showMessage(type, title, text) {
        Swal.fire({
                icon: type,  // 'success', 'error', 'warning', 'info', 'question'
                title: title,
                text: text,
                confirmButtonText: '확인',
                confirmButtonColor: '#3085d6',
                html: `<span style="font-size: 15px; font-weight: bold;">${text}</span>`,
            }).then((result) => {
                if (result.isConfirmed) {
                    setTimeout(() => {  // 0.5초 지연
                        if (title === '성공') {
                            location.reload();
                        } else if (title === '삭제') {
                            window.location.href = '/ticketList';
                        } else if (title === '공지사항삭제' || title === '공지사항저장') {
                            window.location.href = '/noticeBoardList';
                        } else if (title === '시스템게시글삭제' || title === '시스템게시글저장') {
                            window.location.href = '/systemBoardList';
                        } else if (title === "필수값" || title === "팝업차단" || title === "저장") {

                            //location.reload();
                        } else {
                            location.reload();
                        }
                    }, 500);  // 팝업 확인 후 0.5초 후 동작 실행
                }
            });
    }

    function showSuccess(title, text) {
        showMessage('success', title, text);
    }

    function showError(title, text) {
        showMessage('error', title, text);
    }

    function showWarning(title, text) {
        showMessage('warning', title, text);
    }

    function showInfo(title, text) {
        showMessage('info', title, text);
    }

    //Confirm 창 공통 구현
    function showConfirm(type, title, text, afterFunction) {
        Swal.fire({
            icon: type,  // 'success', 'error', 'warning', 'info', 'question'
            title: title,
            text: text,

            showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
            confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
            cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
            confirmButtonText: '확인', // confirm 버튼 텍스트 지정
            cancelButtonText: '취소', // cancel 버튼 텍스트 지정
       //     html: `<span style="font-size: 15px; font-weight: bold;">${text}</span>`,  // 메시지 텍스트 스타일
        }).then((result) => {
            if (result.isConfirmed) {
                if (typeof afterFunction === 'function') {
                    afterFunction();
                }else{
                    afterConfirm();
                }
            }else if (result.isDismissed) { // 만약 모달창에서 cancel 버튼을 눌렀다면
                location.reload();
             }
        });
    }

    function showFeedbackConfirm(title, text, afterFunction) {
        Swal.fire({
            title: `<span style="font-size: 20px;">${title}</span>`,
            html: `
                <span style="font-size: 15px;">${text}</span>
                <div style="display: flex; flex-direction: column; margin-left:95px; margin-top: 15px;">
                    <label style="display: flex; align-items: center; font-size: 14px;">
                        <input type="radio" name="feedback" value="5" style="margin-right: 8px;" selected>매우만족
                    </label>
                    <label style="display: flex; align-items: center; font-size: 14px; margin-top: 8px;">
                        <input type="radio" name="feedback" value="4" style="margin-right: 8px;">만족
                    </label>
                    <label style="display: flex; align-items: center; font-size: 14px; margin-top: 8px;">
                        <input type="radio" name="feedback" value="3" style="margin-right: 8px;">보통
                    </label>
                    <label style="display: flex; align-items: center; font-size: 14px; margin-top: 8px;">
                        <input type="radio" name="feedback" value="2" style="margin-right: 8px;">불만
                    </label>
                    <label style="display: flex; align-items: center; font-size: 14px; margin-top: 8px;">
                        <input type="radio" name="feedback" value="1" style="margin-right: 8px;">매우불만
                    </label>
                </div>
                <textarea id="feedbackText" placeholder="피드백 의견을 입력해주세요."
                    style="width: 100%; height: 80px; margin-top: 15px; font-size: 14px; border: 1px solid #ccc; border-radius: 4px; resize: none;"></textarea>
            `,
            icon: 'question',
            showCancelButton: true,
         //   confirmButtonColor: '#3085d6',
         //   cancelButtonColor: '#6c757d',
            confirmButtonText: '확인',
            cancelButtonText: '취소',
            customClass: {
                confirmButton: 'btn btn--s primary-btn',
                cancelButton: 'btn btn--s secondary-btn'
            },
            preConfirm: () => {
                const selectedOption = document.querySelector('input[name="feedback"]:checked');
                const feedbackText = document.getElementById('feedbackText').value.trim();
                if (!selectedOption) {
                    Swal.showValidationMessage('의견을 선택하세요.');
                    return false;
                }
               return {
                   feedback: selectedOption.value,
                   comment: feedbackText
               };
            }
        }).then((result) => {
            if (result.isConfirmed) {
                if (typeof afterFunction === 'function') {
                    afterFunction(result.value);
                }
            } else if (result.isDismissed) {
                if (typeof afterFunction === 'function') {
                    afterFunction('cancelled');
                }
            }
        });
    }


    function showPasswordChangeModal(afterFunction) {
        Swal.fire({
            title: '비밀번호 변경',
            html: `
                <input type="password" id="newPassword" class="swal2-input" placeholder="새 비밀번호 입력">
                <input type="password" id="confirmPassword" class="swal2-input" placeholder="비밀번호 확인">
            `,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: '변경',
            cancelButtonText: '취소',
         //   confirmButtonColor: '#0D349B',
         //   cancelButtonColor: '#3085d6',
             customClass: {
                        confirmButton: 'btn btn--s primary-btn',
                        cancelButton: 'btn btn--s secondary-btn'
                    },
            preConfirm: () => {
                const newPassword = document.getElementById('newPassword').value;
                const confirmPassword = document.getElementById('confirmPassword').value;

                if (!newPassword || !confirmPassword) {
                    Swal.showValidationMessage('비밀번호를 입력하세요.');
                    return false;
                }
                if (newPassword !== confirmPassword) {
                    Swal.showValidationMessage('비밀번호가 일치하지 않습니다.');
                    return false;
                }
                return newPassword; // 유효성 검사 통과 시, 새로운 비밀번호 반환
            }
        }).then((result) => {
            if (result.isConfirmed) {
                if (typeof afterFunction === 'function') {
                    afterFunction(result.value); // 변경된 비밀번호를 콜백 함수로 전달
                } else {
                    console.log("새 비밀번호:", result.value);
                }
            }
        });
    }


    function callPage() {
       const target = event.target.closest("a[data-url][data-page]"); // `data-url` 및 `data-page` 속성이 있는 <a> 태그만 선택
       if (target) {
           const url = target.getAttribute("data-url");
           const page = target.getAttribute("data-page");
           goPage(url, page); // goPage 함수 호출
       }
    }
