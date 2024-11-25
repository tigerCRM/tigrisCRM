/*common.js
최초 작성자 : 제예솔
설명 : 각종 script 코드 여기에. 꼭 주석 달아주세요!
*/

//화면 로드 시
document.addEventListener("DOMContentLoaded", function () {
    //날짜 값 포맷 변환
    convertDateFormat();

});

/*
convertDateFormat(날짜 값 포맷 변환)
작성자 : 제예솔
설명 : html 단에 입력한 값 중 Date타입인 값이 화면에 Mon Nov 25 14:37:35 KST 2024 이런 식으로 뜨는 경우
class 에 convertDateFormat 값 추가하면 로드될때 변환해줍니다.
*/
function convertDateFormat(){
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
}