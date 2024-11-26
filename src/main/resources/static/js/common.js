/*common.js
최초 작성자 : 제예솔
설명 : 각종 script 코드 여기에. 꼭 주석 달아주세요!
*/

//1. 화면 로드시 실행
document.addEventListener("DOMContentLoaded", function () {
    //날짜 값 포맷 변환
    common.convertDateFormat();

});

//2. event listener


//3. 함수 저장소
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
    loadOrInitializeEditor : function(editor , mode){

        editor = new toastui.Editor.factory({
            el: document.querySelector('#editorContent'),      // viewer와 editor 컨테이너 구분
            viewer: mode == 'read',                              // boardId 값에 따라 viewer 모드 설정
            height: 'auto',                                    // 높이 설정
            initialValue: mode == 'write'? '내용을 입력해 주세요.' : document.querySelector('#content').value ,
            initialEditType: mode == 'read' ? undefined : 'wysiwyg', // viewer 모드일 때 편집 타입 생략
            previewStyle: 'vertical',
            hideModeSwitch: mode != 'read',                      // editor 모드일 때만 모드 전환 숨김
        });

        return editor;
    },

//end
}
